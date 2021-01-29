package com.github.rumoel.games.space.objects.space.entity.inspace;

import java.security.NoSuchAlgorithmException;

import com.github.rumoel.games.space.objects.space.entity.basic.SpaceEntity;

import lombok.Getter;
import lombok.Setter;

public class Ship extends SpaceEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8802711377991560305L;

	public Ship() throws NoSuchAlgorithmException {
		super();
	}

	@Getter
	@Setter
	public long lastShotTime;

}