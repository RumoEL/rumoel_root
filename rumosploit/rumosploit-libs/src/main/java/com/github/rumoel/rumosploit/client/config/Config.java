package com.github.rumoel.rumosploit.client.config;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Config {
	@Getter
	@Setter
	private boolean prepare;
	@Getter
	@Setter
	private boolean debug;

	@Getter
	@Setter
	private String serverHost = "server.rumoel.com";
	@Getter
	@Setter
	private int serverPort = 10001;
	@Getter
	@Setter
	private String username = "user-" + UUID.randomUUID().toString();
}