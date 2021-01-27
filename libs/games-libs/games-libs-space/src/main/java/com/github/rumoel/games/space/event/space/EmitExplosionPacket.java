package com.github.rumoel.games.space.event.space;

import org.joml.Vector3d;
import org.joml.Vector3f;

import com.github.rumoel.games.space.network.packets.Packet;

import lombok.Getter;
import lombok.Setter;

public class EmitExplosionPacket extends Packet {
	@Getter
	@Setter
	Vector3d p;
	@Getter
	@Setter
	Vector3f normal;
}
