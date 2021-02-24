package com.github.rumoel.rumosploit.bot.network.handlers;

import com.github.rumoel.rumosploit.bot.header.Header;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public class BotTaskUtils {
	private BotTaskUtils() {
	}

	public static void response(BotTaskAnswer answer) {
		Header.getSession().write(answer);
	}

}
