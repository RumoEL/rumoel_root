package com.github.rumoel.games.space.client.online.game.handlers.network.udp;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.github.rumoel.games.space.client.online.game.header.network.NetworkHeader;

public class UDPHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		NetworkHeader.getOnlineHandler().messageReceived(session, message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.closeNow();
	}
}
