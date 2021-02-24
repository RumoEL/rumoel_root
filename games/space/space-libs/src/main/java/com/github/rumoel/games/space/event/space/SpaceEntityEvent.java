package com.github.rumoel.games.space.event.space;

import com.github.rumoel.games.space.objects.space.entity.basic.SpaceEntity;

import lombok.Getter;
import lombok.Setter;

public class SpaceEntityEvent extends SpaceEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4184058426407735L;
	@Getter
	@Setter
	SpaceEntity targetEntity;
}
