package com.github.rumoel.rumosploit.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.client.header.Header;

public final class BotEntityUtils {
	static Logger logger = LoggerFactory.getLogger(BotEntityUtils.class);

	private BotEntityUtils() {
	}

	public static void addFromNetwork(BotEntity input) {
		if (containsInHeader(input)) {
			BotEntity byId = getById(input);
			Header.getBotEntities().set(Header.getBotEntities().indexOf(byId), input);
		} else {
			Header.getBotEntities().add(input);
		}
	}

	private static BotEntity getById(BotEntity input) {
		for (BotEntity inArrayEntity : Header.getBotEntities()) {
			if (inArrayEntity.getBotId().equals(input.getBotId())) {
				return inArrayEntity;
			}
		}
		return null;
	}

	private static boolean containsInHeader(BotEntity input) {
		for (BotEntity inArrayEntity : Header.getBotEntities()) {
			if (inArrayEntity.getBotId().equals(input.getBotId())) {
				return true;
			}
		}
		return false;
	}

	public static void removeById(BotEntity botLeaved) {
		if (containsInHeader(botLeaved)) {
			int index = Header.getBotEntities().indexOf(getById(botLeaved));
			Header.getBotEntities().remove(index);
			logger.info("bot:{} is removed", botLeaved.getBotId());
		}
	}

}
