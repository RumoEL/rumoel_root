package com.github.rumoel.rumosploit.server.network.bots;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.server.header.Header;

public class BothandlerUtils {

	public static IoSession searchSessionForBot(BotEntity botEntity) {
		Map<Long, IoSession> allBotsSessions = Header.getHandlerBots().getAcceptor().getManagedSessions();
		for (Map.Entry<Long, IoSession> botMapEntry : allBotsSessions.entrySet()) {
			IoSession session = botMapEntry.getValue();
			if (session.containsAttribute(TCPHandlerBots.ENTITIATTR)) {
				BotEntity bot = (BotEntity) session.getAttribute(TCPHandlerBots.ENTITIATTR);
				if (bot.getBotId().equals(botEntity.getBotId())) {
					return session;
				}
			}
		}
		return null;
	}

}
