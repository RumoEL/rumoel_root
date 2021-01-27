package com.github.rumoel.games.space.client.online.game.handlers.network;

import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.games.space.client.online.game.engine.ClientGameEngine;
import com.github.rumoel.games.space.client.online.game.header.GameHeader;
import com.github.rumoel.games.space.client.online.game.header.network.NetworkHeader;
import com.github.rumoel.games.space.network.ProtoType;
import com.github.rumoel.games.space.network.packets.PingPacket;
import com.github.rumoel.games.space.network.packets.ReadyPacket;
import com.github.rumoel.games.space.network.packets.connections.ConnectionClosePacket;

public final class NetworkHandler {
	private NetworkHandler() {
	}

	static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	static ProtoType protoType;
	public static IoSession session;

	public static void initNetwork() {
		logger.info("initNetwork-start");
		protoType = GameHeader.getClientConfig().getNetworkConfig().getProtoType();
		logger.info("initNetwork using " + protoType);

		ObjectSerializationCodecFactory oscf = new ObjectSerializationCodecFactory();
		oscf.setDecoderMaxObjectSize(Integer.MAX_VALUE);

		if (protoType == ProtoType.UDP) {
			NetworkHeader.getNioDatagramConnector().setHandler(NetworkHeader.getUdpHandler());
			// NetworkHeader.getNioDatagramConnector().getFilterChain().addLast("logger",
			// new LoggingFilter());
			NetworkHeader.getNioDatagramConnector().getFilterChain().addLast("codec", new ProtocolCodecFilter(oscf));
			NetworkHeader.getNioDatagramConnector().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		} else if (protoType == ProtoType.TCP) {
			NetworkHeader.getNioSocketConnector().setHandler(NetworkHeader.getTcpHandler());
			// NetworkHeader.getNioSocketConnector().getFilterChain().addLast("logger", new
			// LoggingFilter());
			NetworkHeader.getNioSocketConnector().getFilterChain().addLast("codec", new ProtocolCodecFilter(oscf));
			NetworkHeader.getNioSocketConnector().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		}
		logger.info("initNetwork-end");
	}

	public static void startNetwork() throws InterruptedException {
		logger.info("startNetwork-start");
		if (protoType == ProtoType.UDP) {
			ConnectFuture connFuture = NetworkHeader.getNioDatagramConnector()
					.connect(new InetSocketAddress(GameHeader.getClientConfig().getNetworkConfig().getServerHost(),
							GameHeader.getClientConfig().getNetworkConfig().getServerPort()));
			connFuture.addListener(new IoFutureListener<IoFuture>() {
				@Override
				public void operationComplete(IoFuture future) {
					ConnectFuture connFuture = (ConnectFuture) future;
					if (connFuture.isConnected()) {
						session = future.getSession();
						logger.info("startNetwork-UDP-connected");
						session.write(new ReadyPacket());
					} else {
						logger.error("startNetwork-UDP-Not connected...exiting");
					}
				}
			});
		} else if (protoType == ProtoType.TCP) {
			for (;;) {
				try {
					ConnectFuture future = NetworkHeader.getNioSocketConnector()
							.connect(new InetSocketAddress(
									GameHeader.getClientConfig().getNetworkConfig().getServerHost(),
									GameHeader.getClientConfig().getNetworkConfig().getServerPort()));
					future.awaitUninterruptibly();
					session = future.getSession();
					session.write(new ReadyPacket());
					break;
				} catch (RuntimeIoException e) {
					logger.error("Failed to connect.", e);
					Thread.sleep(5000);
				}
			}
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!ClientGameEngine.rune) {
					try {
						if (session != null) {
							session.write(new PingPacket());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		logger.info("startNetwork-end");
	}

	public static void exit() {
		if (protoType == ProtoType.TCP) {
			NetworkHeader.getNioSocketConnector().broadcast(new ConnectionClosePacket());
			NetworkHeader.getNioSocketConnector().dispose();
		} else if (protoType == ProtoType.UDP) {
			NetworkHeader.getNioDatagramConnector().broadcast(new ConnectionClosePacket());
			NetworkHeader.getNioDatagramConnector().dispose();
		}
	}

}
