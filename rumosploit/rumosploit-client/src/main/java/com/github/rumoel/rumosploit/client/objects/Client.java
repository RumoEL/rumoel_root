package com.github.rumoel.rumosploit.client.objects;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceConfigurationError;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.rumosploit.bot.network.packet.ReadyPacket;
import com.github.rumoel.rumosploit.client.config.Config;
import com.github.rumoel.rumosploit.client.header.Header;

public class Client extends Thread {
	Logger logger = LoggerFactory.getLogger(getClass());

	public void readConfig() throws IOException {
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
		ObjectSerializationCodecFactory oscf = new ObjectSerializationCodecFactory();
		oscf.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		Header.getNioSocketConnector().setHandler(Header.getConnectionHandler());
		Header.getNioSocketConnector().getFilterChain().addLast("codec", new ProtocolCodecFilter(oscf));
		Header.getNioSocketConnector().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}

	@Override
	public void run() {
		try {
			reconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reconnect() throws InterruptedException {
		if (Header.getConfig().isDebug()) {
			logger.warn("reconnect");
		}

		for (;;) {
			try {
				connect();
				break;
			} catch (org.apache.mina.core.RuntimeIoException e) {
				e.getClass();// IGNORE
			} catch (Exception e) {
				e.printStackTrace();
				if (Header.getConfig().isDebug()) {
					logger.error("Failed to connect.[{}] by {}", e.getMessage(), e.getCause().getMessage());
				}
				Thread.sleep(5000);
			}
		}
	}

	private void connect() {
		if (Header.getConfig().isDebug()) {
			logger.warn("connect");
		}
		String host = Header.getConfig().getServerHost();
		int port = Header.getConfig().getServerPort();
		ConnectFuture future = Header.getNioSocketConnector().connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();
		Header.setSession(future.getSession());

		ReadyPacket ready = new ReadyPacket();
		Header.getSession().write(ready);
	}

	public void startGui() {
		Header.getWindow().setVisible(true);
	}
}
