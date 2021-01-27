package com.github.rumoel.games.space.network.packets;

import lombok.Getter;
import lombok.Setter;

public class PingPacket extends Packet {
	@Getter
	@Setter
	boolean pong;
}
