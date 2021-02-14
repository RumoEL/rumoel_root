package com.github.rumoel.rumosploit.configurator.header;

import com.github.rumoel.rumosploit.bot.BotConfig;
import com.github.rumoel.rumosploit.server.config.ServerConfig;

import lombok.Getter;
import lombok.Setter;

public final class ConfigHeader {
	private ConfigHeader() {
	}

	@Getter
	@Setter
	private static BotConfig botconfig = new BotConfig();
	@Getter
	@Setter
	private static ServerConfig serverConfig = new ServerConfig();
}
