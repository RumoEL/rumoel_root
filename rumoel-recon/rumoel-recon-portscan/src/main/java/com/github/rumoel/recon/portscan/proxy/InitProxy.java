package com.github.rumoel.recon.portscan.proxy;

import java.net.InetSocketAddress;

import com.github.rumoel.recon.portscan.configs.HEADER_ALL;

import sockslib.client.Socks5;
import sockslib.common.UsernamePasswordCredentials;

public class InitProxy {
	public static void prepareProxy() {
		HEADER_ALL.proxy = new Socks5(
				new InetSocketAddress(HEADER_ALL.config.getProxy_host(), HEADER_ALL.config.getProxy_port()));
		HEADER_ALL.proxy.setCredentials(new UsernamePasswordCredentials(HEADER_ALL.config.getProxy_user(),
				HEADER_ALL.config.getProxy_password()));
	}

}
