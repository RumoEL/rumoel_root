package com.github.rumoel.rumosploit.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.rumosploit.client.header.Header;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public class BotTaskAnswerUtils {
	static Logger logger = LoggerFactory.getLogger(BotTaskAnswerUtils.class);

	private BotTaskAnswerUtils() {
	}

	public static void addFromNetwork(BotTaskAnswer input) {
		if (containsInHeader(input)) {
			BotTaskAnswer byId = getById(input);
			Header.getBotTaskAnswers().set(Header.getBotTaskAnswers().indexOf(byId), input);
		} else {
			logger.info("add");
			Header.getBotTaskAnswers().add(input);
		}
	}

	private static boolean containsInHeader(BotTaskAnswer input) {
		for (BotTaskAnswer inArrayEntity : Header.getBotTaskAnswers()) {
			if (inArrayEntity.getId().equals(input.getId())) {
				return true;
			}
		}
		return false;
	}

	private static BotTaskAnswer getById(BotTaskAnswer input) {
		for (BotTaskAnswer inArrayEntity : Header.getBotTaskAnswers()) {
			if (inArrayEntity.getId().equals(input.getId())) {
				return inArrayEntity;
			}
		}
		return null;
	}

}
