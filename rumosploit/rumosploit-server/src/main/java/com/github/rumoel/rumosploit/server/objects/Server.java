package com.github.rumoel.rumosploit.server.objects;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.bot.network.packet.PingPacket;
import com.github.rumoel.rumosploit.server.config.ServerConfig;
import com.github.rumoel.rumosploit.server.header.Header;

import lombok.Getter;
import lombok.Setter;

public class Server extends Thread {

	static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Getter
	@Setter
	private ScheduledExecutorService pingService;

	// tmp
	Thread starterClientsThread;
	private Thread starterBotsThread;

	private void startThreads() {
		setPingService(Executors.newScheduledThreadPool(2));

		// PING for clients/bots
		getPingService().scheduleWithFixedDelay(Server::pingAllBots, 0, Header.getConfig().getPingDelayBots(),
				TimeUnit.MILLISECONDS);
		getPingService().scheduleWithFixedDelay(Server::pingAllClients, 0, Header.getConfig().getPingDelayBots(),
				TimeUnit.MILLISECONDS);

		// send bots to clients
		getPingService().scheduleWithFixedDelay(Server::sendAllBotsToAllClients, 0, 2000, TimeUnit.MILLISECONDS);

		// send serverStat to clients
		getPingService().scheduleWithFixedDelay(Server::sendStatToAllClients, 0, 2000, TimeUnit.MILLISECONDS);
	}

	private static void sendStatToAllClients() {
		Map<Long, IoSession> clients = Header.getHandlerClients().getAcceptor().getManagedSessions();

		Header.getStat().setBotConnected(Header.getHandlerBots().getAcceptor().getManagedSessions().size());
		Header.getStat().setClientConnected(clients.size());

		for (Map.Entry<Long, IoSession> clientEntry : clients.entrySet()) {
			IoSession clientSession = clientEntry.getValue();
			clientSession.write(Header.getStat());
		}
	}

	private static void pingAllBots() {
		Header.getHandlerBots().getAcceptor().broadcast(new PingPacket());
	}

	private static void pingAllClients() {
		Header.getHandlerClients().getAcceptor().broadcast(new PingPacket());
	}

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
		Header.setConfig(mapper.readValue(Header.getConfigFile(), ServerConfig.class));
		if (!Header.getConfig().isPrepare()) {
			throw new ServiceConfigurationError("please edit " + Header.getConfigFile().getAbsolutePath());
		}
		logger.info("initConfig-end");

	}

	public void initNetwork() {
		ObjectSerializationCodecFactory oscf = new ObjectSerializationCodecFactory();
		oscf.setDecoderMaxObjectSize(Integer.MAX_VALUE);

		Header.getHandlerBots().getAcceptor().setHandler(Header.getHandlerBots());
		Header.getHandlerBots().getAcceptor().getFilterChain().addLast("codec", new ProtocolCodecFilter(oscf));
		Header.getHandlerBots().getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		Header.getHandlerClients().getAcceptor().setHandler(Header.getHandlerClients());
		Header.getHandlerClients().getAcceptor().getFilterChain().addLast("codec", new ProtocolCodecFilter(oscf));
		Header.getHandlerClients().getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}

	private static void sendAllBotsToAllClients() {
		Map<Long, IoSession> bots = Header.getHandlerBots().getAcceptor().getManagedSessions();
		Map<Long, IoSession> clients = Header.getHandlerClients().getAcceptor().getManagedSessions();
		for (Map.Entry<Long, IoSession> clientEntry : clients.entrySet()) {
			IoSession clientSession = clientEntry.getValue();
			for (Map.Entry<Long, IoSession> botEntry : bots.entrySet()) {
				try {
					IoSession botSession = botEntry.getValue();
					BotEntity botEntity = (BotEntity) botSession.getAttribute("ENTITY");
					if (botEntity != null) {
						clientSession.write(botEntity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			startNetwork();
			startThreads();
		} catch (Exception e) {
			logger.error("error", e);
		}
	}

	public void startNetwork() throws InterruptedException {
		logger.info("startNetwork-start");

		startClientThread();
		startBotThread();

		starterClientsThread.join();
		starterBotsThread.join();

		logger.info("startNetwork-end");
	}

	private void startBotThread() {
		// BOTS
		this.starterBotsThread = new Thread(() -> {
			boolean errorBots = false;
			do {
				try {
					int port = Header.getConfig().getIncomingPortBot();
					Header.getHandlerBots().getAcceptor().bind(new InetSocketAddress(port));
					logger.info("Server:startNetwork:STARTED");
					errorBots = false;
				} catch (Exception e) {
					logger.info("startNetwork-error: [{}] by [{}]", e.getMessage(), e.getCause().getMessage());
					errorBots = true;
					try {
						Thread.sleep(5000);
					} catch (Exception e2) {
						// IGNORE
					}
				}
			} while (errorBots);
		}, "Starter:BotsListener");
		starterBotsThread.start();
	}

	private void startClientThread() {
		this.starterClientsThread = new Thread(() -> {
			boolean errorClients = false;
			do {
				try {
					int port = Header.getConfig().getIncomingPortClient();
					Header.getHandlerClients().getAcceptor().bind(new InetSocketAddress(port));
					logger.info("Server:startNetwork:STARTED");
					errorClients = false;
				} catch (Exception e) {
					logger.info("startNetwork-error: [{}] by [{}]", e.getMessage(), e.getCause().getMessage());
					errorClients = true;
					try {
						Thread.sleep(5000);
					} catch (Exception e2) {
						// IGNORE
					}
				}
			} while (errorClients);

		}, "Starter:ClientListener");
		starterClientsThread.start();
	}

}
