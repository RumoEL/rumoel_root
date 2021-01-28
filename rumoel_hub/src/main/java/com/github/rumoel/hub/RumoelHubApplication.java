package com.github.rumoel.hub;

import java.io.IOException;
import java.util.Properties;
import java.util.ServiceConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.hub.config.RumoHubConfig;
import com.github.rumoel.hub.header.RumoHubHeader;

@EntityScan(basePackages = { "com.github.rumoel.libs.core.model", "com.github.rumoel.libs.recon.info" })
@SpringBootApplication
public class RumoelHubApplication {
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws IOException {
		System.err.println("1111111111");
		RumoHubHeader.sa = new SpringApplication(RumoelHubApplication.class);
		startApp(args);
	}

	private static void startApp(String[] args) throws IOException {
		RumoelHubApplication rumoelHubApplication = new RumoelHubApplication();
		rumoelHubApplication.initConfig();
		rumoelHubApplication.runApp(args);
	}

	private void runApp(String[] args) {
		RumoHubHeader.sa.run(args);
	}

	private void initConfig() throws IOException {
		readConfig();
		setupEnv();
	}

	private void setupEnv() {
		Properties properties = new Properties();
		properties.setProperty("server.port", String.valueOf(RumoHubHeader.getConfig().getWebServerPort()));

		properties.setProperty("spring.datasource.url", "jdbc:postgresql://" + RumoHubHeader.getConfig().getDbHost()
				+ ":5432/" + RumoHubHeader.getConfig().getDbName());
		properties.setProperty("spring.datasource.username", RumoHubHeader.getConfig().getDbUser());
		properties.setProperty("spring.datasource.password", RumoHubHeader.getConfig().getDbPassword());
		properties.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
		properties.setProperty("spring.jpa.database", "postgresql");
		properties.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQL10Dialect");
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");

		RumoHubHeader.sa.setDefaultProperties(properties);
	}

	private void readConfig() throws IOException {
		logger.info("readConfig-start");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		if (!RumoHubHeader.getCONFIGFILE().exists()) {
			if (!RumoHubHeader.getCONFIGFILE().getParentFile().exists()) {
				logger.info("dir {} is created ?:{}", RumoHubHeader.getCONFIGFILE().getParentFile().getAbsolutePath(),
						RumoHubHeader.getCONFIGFILE().getParentFile().mkdirs());
			}
			if (RumoHubHeader.getCONFIGFILE().createNewFile()) {
				mapper.writeValue(RumoHubHeader.getCONFIGFILE(), RumoHubHeader.getConfig());
				throw new ServiceConfigurationError("please edit " + RumoHubHeader.getCONFIGFILE().getAbsolutePath());
			}
		}
		RumoHubHeader.setConfig(mapper.readValue(RumoHubHeader.getCONFIGFILE(), RumoHubConfig.class));
		if (!RumoHubHeader.getConfig().isPrepare()) {
			throw new ServiceConfigurationError("please edit " + RumoHubHeader.getCONFIGFILE().getAbsolutePath());
		}
		logger.info("readConfig-end");
	}
}
