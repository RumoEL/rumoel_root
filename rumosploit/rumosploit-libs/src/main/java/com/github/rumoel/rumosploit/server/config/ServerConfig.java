package com.github.rumoel.rumosploit.server.config;

import lombok.Getter;
import lombok.Setter;

public class ServerConfig {
	@Getter
	@Setter
	private boolean prepare;
	@Getter
	@Setter
	private boolean debug;

	// RUMO
	@Getter
	@Setter
	private boolean useRumoHub = true;
	@Getter
	@Setter
	private String rumoHost = "127.0.0.1";
	@Getter
	@Setter
	private int rumoPort = 10001;
	// RUMO

	@Getter
	@Setter
	private int incomingPortBot = 10000;
	@Getter
	@Setter
	private int incomingPortClient = 10001;
	@Getter
	@Setter
	private long pingDelayBots = 6000;
	@Getter
	@Setter
	private long pingDelayClients = 6000;

}
