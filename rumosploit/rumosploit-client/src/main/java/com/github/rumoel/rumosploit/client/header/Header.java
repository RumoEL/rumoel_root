package com.github.rumoel.rumosploit.client.header;

import java.io.File;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.github.rumoel.rumosploit.client.config.Config;
import com.github.rumoel.rumosploit.client.gui.Window;
import com.github.rumoel.rumosploit.client.network.ConnectionHandler;
import com.github.rumoel.rumosploit.client.objects.Client;

import lombok.Getter;
import lombok.Setter;

public final class Header {
	private Header() {
	}

	@Getter
	private static Client client = new Client();
	@Getter
	private static Window window = new Window();

	@Getter
	private static final File ROOTDIR = new File("rumosploit");
	@Getter
	private static final File ROOTDIR_CLIENT = new File(ROOTDIR, "client");

	// CONFIG
	@Getter
	private static File configFile = new File(ROOTDIR_CLIENT, "Cconfig.yml");
	@Getter
	@Setter
	private static Config config = new Config();
	// CONFIG

	// NETWORK
	static @Getter NioSocketConnector nioSocketConnector = new NioSocketConnector();
	static @Getter ConnectionHandler connectionHandler = new ConnectionHandler();
	static @Getter @Setter IoSession session;
	// NETWORK
}
