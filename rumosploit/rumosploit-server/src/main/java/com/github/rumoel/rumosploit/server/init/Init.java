package com.github.rumoel.rumosploit.server.init;

import java.io.IOException;

import com.github.rumoel.rumosploit.server.header.Header;

public class Init {

	public static void main(String[] args) throws IOException {
		Header.getServer().readConfig();
		Header.getServer().initNetwork();
		Header.getServer().start();

	}

}
