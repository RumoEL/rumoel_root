package com.github.rumoel.rumosploit.bot.init;

import java.io.IOException;

import com.github.rumoel.rumosploit.bot.header.Header;

public class Init {

	public static void main(String[] args) throws IOException {
		Header.getBot().readConfig();
		Header.getBot().initNetwork();
		Header.getBot().start();
	}
}
