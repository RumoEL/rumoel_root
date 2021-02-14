package com.github.rumoel.rumosploit.configurator.gui;

import com.github.rumoel.rumosploit.configurator.header.Header;

public class WindowUtil {

	// SPLOIT BOT HOST
	public static void botSetSploitHost(String host) {
		Header.window.getTf_sploitHost_sb().setText(host);
	}

	public static String botGetSploitHost() {
		return Header.window.getTf_sploitHost_sb().getText();
	}
	// SPLOIT BOT HOST

	// SPLOIT BOT PORT
	public static void botSetSploitPort(int port) {
		Header.window.getTf_sploitPort_sb().setText(String.valueOf(port));
	}

	public static int botGetSploitPort() {
		return Integer.parseInt(Header.window.getTf_sploitPort_sb().getText());
	}
	// SPLOIT BOT PORT

}
