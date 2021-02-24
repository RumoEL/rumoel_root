package com.github.rumoel.rumosploit.client.network;

import java.io.Serializable;

import com.github.rumoel.rumosploit.client.header.Header;

public final class NetworkUtils {
	private NetworkUtils() {
	}

	public static void sendToAuthedServers(Serializable objectToSend) {
		Header.getSession().write(objectToSend);
	}

}
