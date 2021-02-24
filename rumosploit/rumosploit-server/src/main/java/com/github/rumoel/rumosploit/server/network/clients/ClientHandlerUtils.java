package com.github.rumoel.rumosploit.server.network.clients;

import com.github.rumoel.rumosploit.server.header.Header;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public final class ClientHandlerUtils {
	private ClientHandlerUtils() {
	}

	public static void sendToAllAuthed(BotTaskAnswer answer) {
		// TODO add check auth
		Header.getHandlerClients().getAcceptor().broadcast(answer);
	}
}
