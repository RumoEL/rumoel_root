package com.github.rumoel.games.space.client.online.game.header.network;

import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.github.rumoel.games.space.client.online.game.handlers.network.OnlineHandler;
import com.github.rumoel.games.space.client.online.game.handlers.network.tcp.TCPHandler;
import com.github.rumoel.games.space.client.online.game.handlers.network.udp.UDPHandler;

import lombok.Getter;

public class NetworkHeader {
	private NetworkHeader() {
	}

	@Getter
	static NioDatagramConnector nioDatagramConnector = new NioDatagramConnector();
	@Getter
	static NioSocketConnector nioSocketConnector = new NioSocketConnector();

	@Getter
	static UDPHandler udpHandler = new UDPHandler();
	@Getter
	static TCPHandler tcpHandler = new TCPHandler();
	@Getter
	static OnlineHandler onlineHandler = new OnlineHandler();

}
