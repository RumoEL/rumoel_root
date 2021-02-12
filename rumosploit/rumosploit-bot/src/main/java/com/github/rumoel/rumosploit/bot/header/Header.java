package com.github.rumoel.rumosploit.bot.header;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.github.rumoel.rumosploit.bot.Bot;
import com.github.rumoel.rumosploit.bot.BotConfig;
import com.github.rumoel.rumosploit.bot.network.handlers.HandlerBot;

import lombok.Getter;
import lombok.Setter;

public final class Header {
	private Header() {
	}

	@Getter
	private static Bot bot = new Bot();
	@Getter
	@Setter
	public static BotConfig config = new BotConfig();
	@Getter
	@Setter
	private static HandlerBot handler = new HandlerBot();
	@Getter
	private static NioSocketConnector nioSocketConnector = new NioSocketConnector();
	@Getter
	@Setter
	private static IoSession session;
}
