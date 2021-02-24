package com.github.rumoel.rumosploit.client.header;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.client.config.Config;
import com.github.rumoel.rumosploit.client.gui.Window;
import com.github.rumoel.rumosploit.client.network.ConnectionHandler;
import com.github.rumoel.rumosploit.client.objects.Client;
import com.github.rumoel.rumosploit.tasks.BotTask;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

import lombok.Getter;
import lombok.Setter;

public final class Header {
	private Header() {
	}

	// DATA
	static @Getter CopyOnWriteArrayList<BotEntity> botEntities = new CopyOnWriteArrayList<>();
	static @Getter CopyOnWriteArrayList<BotTask> botTasks = new CopyOnWriteArrayList<>();
	static @Getter CopyOnWriteArrayList<BotTaskAnswer> botTaskAnswers = new CopyOnWriteArrayList<>();
	// DATA

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
