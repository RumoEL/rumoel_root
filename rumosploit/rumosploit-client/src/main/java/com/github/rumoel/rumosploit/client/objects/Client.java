package com.github.rumoel.rumosploit.client.objects;

import java.io.IOException;
import java.util.ServiceConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.rumosploit.client.config.Config;
import com.github.rumoel.rumosploit.client.header.Header;

public class Client extends Thread {
	Logger logger = LoggerFactory.getLogger(getClass());

	public void readConfig() throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("initConfig-start");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		// CHECK and create

		if (!Header.getConfigFile().getParentFile().exists()) {
			Header.getConfigFile().getParentFile().mkdirs();
		}
		if (!Header.getConfigFile().exists()) {
			logger.info("File {} is created:{}", Header.getConfigFile().getAbsolutePath(),
					Header.getConfigFile().createNewFile());

			mapper.writeValue(Header.getConfigFile(), Header.getConfig());
			throw new ServiceConfigurationError("please edit " + Header.getConfigFile().getAbsolutePath());
		}
		// READ
		Header.setConfig(mapper.readValue(Header.getConfigFile(), Config.class));
		if (!Header.getConfig().isPrepare()) {
			throw new ServiceConfigurationError("please edit " + Header.getConfigFile().getAbsolutePath());
		}
		logger.info("initConfig-end");

	}

	public void initNetwork() {

	}

	@Override
	public void run() {
	}

	public void startGui() {
		Header.getWindow().setVisible(true);
	}
}
