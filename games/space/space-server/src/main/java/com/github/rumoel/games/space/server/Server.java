package com.github.rumoel.games.space.server;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.util.ServiceConfigurationError;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.games.space.StaticData;
import com.github.rumoel.games.space.network.ProtoType;
import com.github.rumoel.games.space.server.config.ServerConfig;
import com.github.rumoel.games.space.server.header.ServerHeader;
import com.github.rumoel.games.space.server.header.network.NetWorkHeader;

public class Server {
	private int port;
	private ProtoType configProtoType;
	static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public void initConfig() throws IOException {
		logger.info("initConfig-start");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		// CHECK and create
		if (!ServerHeader.getConfigFile().exists()) {
			if (!ServerHeader.getConfigFile().getParentFile().exists()) {
				ServerHeader.getConfigFile().getParentFile().mkdirs();
			}
			if (ServerHeader.getConfigFile().createNewFile()) {

			}
			mapper.writeValue(ServerHeader.getConfigFile(), ServerHeader.getServerConfig());
			throw new ServiceConfigurationError("please edit " + ServerHeader.getConfigFile().getAbsolutePath());
		}
		// READ
		ServerHeader.setServerConfig(mapper.readValue(ServerHeader.getConfigFile(), ServerConfig.class));
		if (!ServerHeader.getServerConfig().isPrepare()) {
			throw new ServiceConfigurationError("please edit " + ServerHeader.getConfigFile().getAbsolutePath());
		}
		logger.info("initConfig-end");
	}

	public void initNetwork() {
		logger.info("initNetwork-start");
		this.configProtoType = ServerHeader.getServerConfig().getNetworkConfig().getProto();
		this.port = ServerHeader.getServerConfig().getNetworkConfig().getServerPort();

		ObjectSerializationCodecFactory oscf = new ObjectSerializationCodecFactory();
		oscf.setDecoderMaxObjectSize(Integer.MAX_VALUE);

		if (configProtoType == ProtoType.TCP) {
			NetWorkHeader.getTcpHandler().getAcceptor().setHandler(NetWorkHeader.getTcpHandler());

			// NetWorkHeader.getTcpHandler().getAcceptor().getFilterChain().addLast("logger",
			// new LoggingFilter());

			NetWorkHeader.getTcpHandler().getAcceptor().getFilterChain().addLast("codec",
					new ProtocolCodecFilter(oscf));
			NetWorkHeader.getTcpHandler().getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		} else {
			// FOR UDP
			NetWorkHeader.getUdpHandler().getAcceptor().setHandler(NetWorkHeader.getUdpHandler());
			// NetWorkHeader.getUdpHandler().getAcceptor().getFilterChain().addLast("logger",
			// new LoggingFilter());
			NetWorkHeader.getUdpHandler().getAcceptor().getFilterChain().addLast("codec",
					new ProtocolCodecFilter(oscf));
			NetWorkHeader.getUdpHandler().getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			DatagramSessionConfig dcfg = NetWorkHeader.getUdpHandler().getAcceptor().getSessionConfig();
			dcfg.setReuseAddress(true);
		}
		logger.info("initNetwork-end");
	}

	public void startNetwork() throws IOException {
		logger.info("startNetwork-start");
		boolean error = false;
		do {
			try {
				if (configProtoType == ProtoType.TCP) {
					NetWorkHeader.getTcpHandler().getAcceptor().bind(new InetSocketAddress(port));
					logger.info("Server:startNetwork:TCP:STARTED");
				} else {
					// FOR UDP
					NetWorkHeader.getUdpHandler().getAcceptor().bind(new InetSocketAddress(port));
					logger.info("Server:startNetwork:UDP:STARTED");
				}
				error = false;
			} catch (Exception e) {
				logger.info("startNetwork-error: [{}] by [{}]", e.getMessage(), e.getCause().getMessage());
				error = true;
				try {
					Thread.sleep(5000);
				} catch (Exception e2) {
					// IGNORE
				}
			}
		} while (error);
		logger.info("startNetwork-end");
	}

	public void initGame() {
		logger.info("initGame-start");
		ServerHeader.getServerEngine().init();
		logger.info("initGame-end");
	}

	public void startGame() {
		logger.info("startGame-start");
		ServerHeader.getServerEngine().start();
		logger.info("startGame-end");
	}

	public void initData() throws IOException {
		logger.info("initData-start");
		StaticData.init();
		logger.info("initData-end");
	}

}
