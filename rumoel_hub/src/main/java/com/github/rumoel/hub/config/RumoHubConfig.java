package com.github.rumoel.hub.config;

import lombok.Getter;
import lombok.Setter;

public class RumoHubConfig {

	@Getter
	@Setter
	private boolean prepare;
	@Getter
	@Setter
	private int webServerPort = 8080;

	@Getter
	@Setter
	private String dbHost = "127.0.0.1";
	@Getter
	@Setter
	private String dbName = "dbName";
	@Getter
	@Setter
	private String dbUser = "dbUser";
	@Getter
	@Setter
	private String dbPassword = "dbPass";

}
