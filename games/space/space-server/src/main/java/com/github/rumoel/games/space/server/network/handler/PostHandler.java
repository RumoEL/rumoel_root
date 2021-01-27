package com.github.rumoel.games.space.server.network.handler;

import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.games.space.network.packets.ReadyPacket;
import com.github.rumoel.games.space.objects.space.entity.inspace.Asteroid;
import com.github.rumoel.games.space.objects.space.entity.inspace.Ship;
import com.github.rumoel.games.space.objects.space.entity.inspace.Shot;
import com.github.rumoel.games.space.server.engine.ServerEngine;

public class PostHandler extends IoHandlerAdapter {
	static Logger logger = LoggerFactory.getLogger(PostHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {

		if (message instanceof Shot) {
			Shot shot = (Shot) message;
			ServerEngine.directShots.add(shot);
			Map<Long, IoSession> targetSessions = session.getService().getManagedSessions();
			for (Map.Entry<Long, IoSession> entry : targetSessions.entrySet()) {
				Long key = entry.getKey();
				if (session.getId() != key) {
					IoSession targetSession = entry.getValue();
					targetSession.write(message);
				}
			}
		} else if (message instanceof ReadyPacket) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (Ship ship : ServerEngine.localShips) {
						session.write(ship);
					}
					for (Asteroid asteroid : ServerEngine.localAsteroids) {
						session.write(asteroid);
					}
				}
			}).start();
		}
	}
}
