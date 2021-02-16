package com.github.rumoel.rumosploit.client.init;

import java.io.IOException;

import com.github.rumoel.rumosploit.client.header.Header;

public class Init {

	public static void main(String[] args) throws IOException {
		Header.getClient().readConfig();
		Header.getClient().startGui();
		Header.getClient().initNetwork();
		Header.getClient().start();
	}
}