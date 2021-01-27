package com.github.rumoel.games.space.server.header.network;

import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.github.rumoel.games.space.server.network.handler.PostHandler;
import com.github.rumoel.games.space.server.network.handler.tcp.TCPHandler;
import com.github.rumoel.games.space.server.network.handler.udp.UDPHandler;

import lombok.Getter;

public class NetWorkHeader {
	private NetWorkHeader() {
	}

	@Getter
	static UDPHandler udpHandler = new UDPHandler();
	@Getter
	static TCPHandler tcpHandler = new TCPHandler();
	@Getter
	static PostHandler postHandler = new PostHandler();

	public static void sendBroadcast(Object object) {
		NioDatagramAcceptor udpAcceptor = udpHandler.getAcceptor();
		NioSocketAcceptor tcpAcceptor = tcpHandler.getAcceptor();
		if (udpAcceptor != null) {
			udpAcceptor.broadcast(object);
		}
		if (tcpAcceptor != null) {
			tcpAcceptor.broadcast(object);
		}

	}

}
