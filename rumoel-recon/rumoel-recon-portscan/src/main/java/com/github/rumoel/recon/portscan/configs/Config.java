package com.github.rumoel.recon.portscan.configs;

import lombok.Getter;
import lombok.Setter;

public class Config {

	@Getter
	@Setter
	private String apiAddr = "http://127.0.0.1:2080/api/insecure/recon";

	// PROXY
	@Getter
	@Setter
	private boolean proxy_use = true;

	@Getter
	@Setter
	private String proxy_host = "localhost";
	@Getter
	@Setter
	private int proxy_port = 9050;

	@Getter
	@Setter
	private String proxy_user = "secretUser";
	@Getter
	@Setter
	private String proxy_password = "secretPass";
	// PROXY

	@Getter
	@Setter
	private boolean customMode = true;
	@Getter
	@Setter
	private boolean randomMode = true;
	@Getter
	@Setter
	private boolean CIDRMode = true;

	@Getter
	@Setter
	private String[] cidr = { "111.111.111.111/16", "222.222.222.222/16" };

	@Getter
	@Setter
	private String[] singlIP = { "127.0.0.1", "255.255.0.1" };

	@Getter
	@Setter
	private int[] ports = { 5432, 80, 21 };

	@Getter
	@Setter
	private String[] portsRange = { "1 - 1024", "25560-25570" };

	@Getter
	@Setter
	private boolean prepared = false;

	// SOCKET
	@Getter
	@Setter
	private int timeout = 3000;
	// SOCKET

	@Getter
	@Setter
	private int threads = 40;
}
