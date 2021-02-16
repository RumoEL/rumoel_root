package com.github.rumoel.rumosploit.client.header;

import java.io.File;

import com.github.rumoel.rumosploit.client.config.Config;
import com.github.rumoel.rumosploit.client.gui.Window;
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
}
