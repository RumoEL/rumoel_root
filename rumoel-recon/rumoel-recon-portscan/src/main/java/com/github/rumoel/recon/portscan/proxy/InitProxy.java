package com.github.rumoel.recon.portscan.proxy;

import java.net.InetSocketAddress;

import com.github.rumoel.recon.portscan.header.PortScanHeader;

import sockslib.client.Socks5;
import sockslib.common.UsernamePasswordCredentials;

public class InitProxy {
	public static void prepareProxy() {
		PortScanHeader.proxy = new Socks5(
				new InetSocketAddress(PortScanHeader.config.getProxy_host(), PortScanHeader.config.getProxy_port()));
		PortScanHeader.proxy.setCredentials(new UsernamePasswordCredentials(PortScanHeader.config.getProxy_user(),
				PortScanHeader.config.getProxy_password()));
	}
}
