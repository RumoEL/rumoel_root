package com.github.rumoel.rumosploit.client.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.bot.network.packet.PingPacket;
import com.github.rumoel.rumosploit.client.BotEntityUtils;
import com.github.rumoel.rumosploit.client.BotTaskAnswerUtils;
import com.github.rumoel.rumosploit.client.header.Header;
import com.github.rumoel.rumosploit.event.bot.BotLeaveEvent;
import com.github.rumoel.rumosploit.server.config.ServerStat;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public class ConnectionHandler extends IoHandlerAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof PingPacket) {
			if (Header.getConfig().isDebug()) {
				logger.info("ping");
			}
			session.write(message);
			return;
		}
		if (message instanceof BotEntity) {
			BotEntity botEntity = (BotEntity) message;
			BotEntityUtils.addFromNetwork(botEntity);
			return;
		}
		if (message instanceof ServerStat) {
			ServerStat stat = (ServerStat) message;
			int botConnected = stat.getBotConnected();
			int clientConnected = stat.getClientConnected();
			Header.getWindow().getServerStatusFrame().getTfBotsConnected().setText(Integer.toString(botConnected));
			Header.getWindow().getServerStatusFrame().getTfCliConnected().setText(Integer.toString(clientConnected));
			return;
		}
		if (message instanceof BotLeaveEvent) {
			try {
				BotLeaveEvent event = (BotLeaveEvent) message;
				BotEntity botLeaved = event.getEntity();
				if (botLeaved == null) {
					logger.warn("BotLeaveEvent:botLeaved:null");
					return;
				}
				BotEntityUtils.removeById(botLeaved);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ANSW
		if (message instanceof BotTaskAnswer) {
			BotTaskAnswer answer = (BotTaskAnswer) message;
			BotTaskAnswerUtils.addFromNetwork(answer);
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
		Header.getClient().reconnect();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if (Header.getConfig().isDebug()) {
			logger.warn("sessionIdle");
		}
		session.closeNow();
	}
}
