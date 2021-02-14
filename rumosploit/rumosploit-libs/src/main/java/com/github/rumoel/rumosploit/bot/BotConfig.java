package com.github.rumoel.rumosploit.bot;

import lombok.Getter;
import lombok.Setter;

public class BotConfig {
	@Getter
	@Setter
	private String host = "example.com";
	@Getter
	@Setter
	private int port = 10000;

	@Getter
	@Setter
	private boolean debug = false;

}
