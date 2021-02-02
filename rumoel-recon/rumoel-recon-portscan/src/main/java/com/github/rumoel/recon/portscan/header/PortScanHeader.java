package com.github.rumoel.recon.portscan.header;

import java.io.File;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.github.rumoel.recon.portscan.configs.Config;
import com.github.rumoel.recon.portscan.engine.ScanThread;

import sockslib.client.SocksProxy;

public class PortScanHeader {

	public static RestTemplate restTemplate = new RestTemplate();
	public static File configFile = new File("Config.yml");

	public static Config config = new Config();
	public static SocksProxy proxy;

	public static ArrayList<ScanThread> scanThreads = new ArrayList<>();

	public static boolean stop = false;
}
