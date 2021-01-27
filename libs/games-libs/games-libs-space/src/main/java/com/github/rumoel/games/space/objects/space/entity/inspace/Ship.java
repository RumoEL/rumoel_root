package com.github.rumoel.games.space.objects.space.entity.inspace;

import java.security.NoSuchAlgorithmException;

import com.github.rumoel.games.space.objects.space.entity.basic.SpaceEntity;

import lombok.Getter;
import lombok.Setter;

public class Ship extends SpaceEntity {
	public Ship() throws NoSuchAlgorithmException {
		super();
	}

	@Getter
	@Setter
	public long lastShotTime;

}