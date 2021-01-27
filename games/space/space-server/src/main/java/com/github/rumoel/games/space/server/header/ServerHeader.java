package com.github.rumoel.games.space.server.header;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.rumoel.games.space.objects.space.entity.inspace.Ship;
import com.github.rumoel.games.space.server.config.ServerConfig;
import com.github.rumoel.games.space.server.engine.ServerEngine;

import lombok.Getter;
import lombok.Setter;

public final class ServerHeader {
	private ServerHeader() {
	}

	@Getter
	private static File rootDir = new File("rumoel_games_space_server");
	@Getter
	private static File configFile = new File(getRootDir(), "ServerConfig.yml");

	@Getter
	@Setter
	private static ServerConfig serverConfig = new ServerConfig();

	@Getter
	private static ServerEngine serverEngine = new ServerEngine();
	@Getter
	private static CopyOnWriteArrayList<Ship> ships = new CopyOnWriteArrayList<>();
}
