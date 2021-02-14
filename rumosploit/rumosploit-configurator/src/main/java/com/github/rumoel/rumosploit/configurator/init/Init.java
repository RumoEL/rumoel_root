package com.github.rumoel.rumosploit.configurator.init;

import com.github.rumoel.rumosploit.configurator.header.Header;

public class Init {

	public static void main(String[] args) {

		Header.configurator.readConfigs();
		Header.configurator.initGui();
		Header.configurator.setup();
	}

}
