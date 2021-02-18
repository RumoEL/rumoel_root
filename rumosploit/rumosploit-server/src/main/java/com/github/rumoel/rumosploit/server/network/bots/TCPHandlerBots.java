package com.github.rumoel.rumosploit.server.network.bots;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.bot.network.packet.PingPacket;
import com.github.rumoel.rumosploit.event.bot.BotLeaveEvent;
import com.github.rumoel.rumosploit.server.header.Header;

import lombok.Getter;

public class TCPHandlerBots extends IoHandlerAdapter {
	@Getter
	NioSocketAcceptor acceptor = new NioSocketAcceptor();
	static Logger logger = LoggerFactory.getLogger(TCPHandlerBots.class);

	String entityAttr = "ENTITY";

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof PingPacket) {
			return;
		}
		if (message instanceof BotEntity) {
			BotEntity entity = (BotEntity) message;
			if (entity.getBotId() != null) {
				session.setAttribute(entityAttr, entity);
				// send to all clients
				Header.getHandlerClients().getAcceptor().broadcast(entity);
			}
			return;
		}
		logger.info("{}", message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.closeNow();
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("NeworkError", cause);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		if (session.containsAttribute(entityAttr)) {
			BotEntity entity = (BotEntity) session.getAttribute(entityAttr);
			BotLeaveEvent botLeaveEvent = new BotLeaveEvent();
			botLeaveEvent.setEntity(entity);
			Header.getHandlerClients().getAcceptor().broadcast(botLeaveEvent);
		}
	}
}