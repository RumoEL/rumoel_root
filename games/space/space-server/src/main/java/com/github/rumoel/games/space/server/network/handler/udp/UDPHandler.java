package com.github.rumoel.games.space.server.network.handler.udp;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.games.space.network.packets.connections.ConnectionClosePacket;
import com.github.rumoel.games.space.server.header.network.NetWorkHeader;

import lombok.Getter;

public class UDPHandler extends IoHandlerAdapter {
	@Getter
	NioDatagramAcceptor acceptor = new NioDatagramAcceptor();

	static Logger logger = LoggerFactory.getLogger(UDPHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof ConnectionClosePacket) {
			session.closeNow();
		} else {
			NetWorkHeader.getPostHandler().messageReceived(session, message);
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.closeNow();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("NeworkError", cause);
	}

}
