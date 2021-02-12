package com.github.rumoel.rumosploit.bot.network.handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.bot.header.Header;
import com.github.rumoel.rumosploit.bot.network.packet.PingPacket;

public class HandlerBot extends IoHandlerAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof PingPacket) {
			session.write(new PingPacket());
			return;
		}
		logger.info("{}", message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		if (Header.getConfig().isDebug()) {
			logger.warn("sessionClosed");
		}
		Thread.sleep(300);
		Header.getBot().reconnect();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if (Header.getConfig().isDebug()) {
			logger.warn("sessionIdle");
		}
		session.closeNow();
	}

}