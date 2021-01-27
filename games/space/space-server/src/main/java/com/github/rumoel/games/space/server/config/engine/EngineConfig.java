package com.github.rumoel.games.space.server.config.engine;

import lombok.Getter;
import lombok.Setter;

public class EngineConfig {
	@Getter
	@Setter
	private int asteroidCount = 1024;

	@Getter
	@Setter
	private int shipCount = 10;
	@Getter
	@Setter
	private int shipRespawnDelay = 100;

	@Getter
	@Setter
	private double shipRespawnRangeXmin = -1000D;
	@Getter
	@Setter
	private double shipRespawnRangeXmax = 1000D;
	@Getter
	@Setter
	private double shipRespawnRangeYmin = -1000D;
	@Getter
	@Setter
	private double shipRespawnRangeYmax = 1000D;
	@Getter
	@Setter
	private double shipRespawnRangeZmin = -1000D;
	@Getter
	@Setter
	private double shipRespawnRangeZmax = 1000D;

}
