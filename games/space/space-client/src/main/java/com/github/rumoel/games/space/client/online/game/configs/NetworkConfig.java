package com.github.rumoel.games.space.client.online.game.configs;

import com.github.rumoel.games.space.network.ProtoType;

import lombok.Getter;
import lombok.Setter;

public class NetworkConfig {

	@Getter
	@Setter
	private String serverHost = "127.0.0.1";

	@Getter
	@Setter
	private int serverPort = 23000;
	@Getter
	@Setter
	private ProtoType protoType = ProtoType.TCP;
}
