package com.github.rumoel.rumosploit.configurator;

import com.github.rumoel.rumosploit.bot.BotConfig;
import com.github.rumoel.rumosploit.configurator.gui.WindowUtil;
import com.github.rumoel.rumosploit.configurator.header.ConfigHeader;
import com.github.rumoel.rumosploit.configurator.header.Header;

public class Configurator {

	public void initGui() {
		Header.window.setVisible(true);
	}

	public void readConfigs() {
		// TODO Auto-generated method stub
		// sploit bot
		// sploit bot
		// sploit server
		// sploit server
		// sploit client
		// sploit client

	}

	public void setup() {
		BotConfig botconfig = ConfigHeader.getBotconfig();
		WindowUtil.botSetSploitHost(botconfig.getHost());
		WindowUtil.botSetSploitPort(botconfig.getPort());

		// Header.window.getTf_pingDelay_sb().setText(botconfig.get);;

	}

}
