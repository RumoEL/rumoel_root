package com.github.rumoel.games.space.event.space;

import com.github.rumoel.games.space.objects.space.entity.basic.SpaceEntity;

import lombok.Getter;
import lombok.Setter;

public class SpaceEntityEvent extends SpaceEvent {
	@Getter
	@Setter
	SpaceEntity targetEntity;
}
