package com.github.rumoel.rumosploit.server.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ServerStat implements Serializable {
	private static final long serialVersionUID = -3873503623352301363L;
	@Getter
	@Setter
	private int clientConnected;
	@Getter
	@Setter
	private int botConnected;
}
