package com.github.rumoel.rumosploit.server.network.clients;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

public class TCPHandlerClient extends IoHandlerAdapter {
	@Getter
	NioSocketAcceptor acceptor = new NioSocketAcceptor();
	static Logger logger = LoggerFactory.getLogger(TCPHandlerClient.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// if (message instanceof ConnectionClosePacket) {
		// session.closeNow();
		// } else {
		logger.info("{}", message);
		// }
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