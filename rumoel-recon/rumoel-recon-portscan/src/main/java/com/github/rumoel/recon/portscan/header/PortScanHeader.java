package com.github.rumoel.recon.portscan.header;

import org.springframework.web.client.RestTemplate;

import com.github.rumoel.recon.portscan.configs.Config;

import sockslib.client.SocksProxy;

public class PortScanHeader {

	public static RestTemplate restTemplate = new RestTemplate();

	public static Config config = new Config();
	public static SocksProxy proxy;

}
