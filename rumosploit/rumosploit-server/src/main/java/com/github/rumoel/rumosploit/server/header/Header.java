package com.github.rumoel.rumosploit.server.header;

import java.io.File;

import com.github.rumoel.rumosploit.server.config.ServerConfig;
import com.github.rumoel.rumosploit.server.config.ServerStat;
import com.github.rumoel.rumosploit.server.network.bots.TCPHandlerBots;
import com.github.rumoel.rumosploit.server.network.clients.TCPHandlerClient;
import com.github.rumoel.rumosploit.server.objects.Server;

import lombok.Getter;
import lombok.Setter;

public final class Header {
	private Header() {
	}

	@Getter
	@Setter
	private static Server server = new Server();

	// INCOM
	@Getter
	private static TCPHandlerBots handlerBots = new TCPHandlerBots();
	@Getter
	private static TCPHandlerClient handlerClients = new TCPHandlerClient();
	// INCOM

	@Getter
	private static final File ROOTDIR = new File("rumosploit");
	@Getter
	private static final File ROOTDIR_SERVER = new File(ROOTDIR, "server");

	@Getter
	private static File configFile = new File(ROOTDIR_SERVER, "Sconfig.yml");
	@Getter
	@Setter
	private static ServerConfig config = new ServerConfig();

	// DATA
	@Getter
	@Setter
	private static ServerStat stat = new ServerStat();

}
