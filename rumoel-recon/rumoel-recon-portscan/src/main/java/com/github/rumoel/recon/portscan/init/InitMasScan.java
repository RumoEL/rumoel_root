package com.github.rumoel.recon.portscan.init;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.recon.portscan.configs.Config;
import com.github.rumoel.recon.portscan.configs.HEADER_ALL;
import com.github.rumoel.recon.portscan.engine.ScanThread;
import com.github.rumoel.recon.portscan.proxy.InitProxy;

import sockslib.client.SocksSocket;

public class InitMasScan {

	protected static final Logger logger = LoggerFactory.getLogger(SocksSocket.class);

	static ArrayList<ScanThread> scanThreads = new ArrayList<>();
	public static boolean stop = false;
	static File configFile = new File("Config.yml");

	public static void prepare() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		if (!configFile.exists()) {
			HEADER_ALL.config = new Config();
			mapper.writeValue(configFile, HEADER_ALL.config);
			System.err.println("Отредактируйте " + configFile.getAbsolutePath());
			System.exit(0);
		} else {
			try {
				HEADER_ALL.config = mapper.readValue(configFile, Config.class);
			} catch (Exception e) {
				System.err.println("Ошибка в " + configFile.getAbsolutePath());
				e.printStackTrace();

				System.exit(1);
			}

			System.out.println(ReflectionToStringBuilder.toString(HEADER_ALL.config, ToStringStyle.MULTI_LINE_STYLE));
		}
		System.out.println("Конфиг настроен");
	}

	public static void startCleaner() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				do {
					for (int j = 0; j < scanThreads.size(); j++) {
						ScanThread thread = scanThreads.get(j);
						if (thread != null) {
							State state = thread.getState();
							if (state.equals(Thread.State.TERMINATED)) {
								scanThreads.remove(thread);
								// System.out.println(scanThreads.size());
								thread = null;
								state = null;
							}
						}
					}
					if (stop) {
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (stop == false);
			}
		}).start();
		System.out.println("Очистка потоков запущена");
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		prepare();

		// Выбираем режим и запускаем
		if (HEADER_ALL.config != null) {
			if (HEADER_ALL.config.isPrepared() == true) {
				System.out.println("Настройка-прокси");
				InitProxy.prepareProxy();
				System.out.println("Настройка-прокси-настроен");

				startCleaner();

				cidrModeScan();

				// RANDOM
				randomModeScan();
				// RANDOM

				while (!(scanThreads.size() == 0)) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				stop = true;

			} else {
				System.err.println("Отредактируйте " + configFile.getAbsolutePath() + " и установите prepared в true");
			}
		}
	}

	public static void randomModeScan() {
		if (HEADER_ALL.config.isRandomMode()) {
			System.out.println("Запущен в RandomMode");
			do {
				int[] ports = HEADER_ALL.config.getPorts();
				for (int j2 = 0; j2 < ports.length; j2++) {
					int port = ports[j2];
					sleepThreads();

					String host = rnd(1, 255) + "." + rnd(1, 255) + "." + rnd(1, 255) + "." + rnd(1, 255);

					if (ipInPrivateRange(host)) {
						continue;
					}
					ScanThread th = new ScanThread(host, port);

					scanThreads.add(th);
					th.start();
				}

			} while (true);
		}
	}

	public static void cidrModeScan() {
		if (HEADER_ALL.config.isCIDRMode()) {
			if (HEADER_ALL.config.getCidr() != null) {
				for (String cidr : HEADER_ALL.config.getCidr()) {
					SubnetUtils utils = new SubnetUtils(cidr);
					String[] hosts = utils.getInfo().getAllAddresses();
					for (String host : hosts) {
						scanPortInCidrMode(host);
					}
				}
			}
			if (HEADER_ALL.config.getSinglIP() != null) {
				for (String host : HEADER_ALL.config.getSinglIP()) {
					scanPortInCidrMode(host);
				}
			}
		}
		System.out.println("CIDRMode завершен");
	}

	public static void scanPortInCidrMode(String host) {
		if (HEADER_ALL.config.getPorts() != null) {
			for (int port : HEADER_ALL.config.getPorts()) {
				sleepThreads();
				ScanThread th = new ScanThread(host, port);

				scanThreads.add(th);
				th.start();
			}
		}
		if (HEADER_ALL.config.getPortsRange() != null) {
			for (String port : HEADER_ALL.config.getPortsRange()) {
				String cleared = port.replaceAll(" ", "");
				int first = Integer.parseInt(StringUtils.substringBefore(cleared, "-"));
				int last = Integer.parseInt(StringUtils.substringAfter(cleared, "-"));
				for (int i = first; i < last; i++) {
					sleepThreads();
					ScanThread th = new ScanThread(host, i);

					scanThreads.add(th);
					th.start();
				}
			}
		}

	}

	private static void sleepThreads() {
		while (scanThreads.size() > HEADER_ALL.config.getThreads()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isValidRange(String ipStart, String ipEnd, String ipToCheck) {
		try {
			long ipLo = ipToLong(InetAddress.getByName(ipStart));
			long ipHi = ipToLong(InetAddress.getByName(ipEnd));
			long ipToTest = ipToLong(InetAddress.getByName(ipToCheck));
			return (ipToTest >= ipLo && ipToTest <= ipHi);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static long ipToLong(InetAddress ip) {
		byte[] octets = ip.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	public static int rnd(final int min, int max) {
		max -= min;
		return (int) (Math.random() * ++max) + min;
	}

	public static boolean ipInPrivateRange(String host) {
		if (isValidRange("127.0.0.0", "127.255.255.255", host)) {
			return true;
		} else if (isValidRange("192.168.0.0", "192.168.255.255", host)) {
			return true;
		} else if (isValidRange("10.0.0.0", "10.255.255.255", host)) {
			return true;
		} else if (isValidRange("172.16.0.0", "172.31.255.255", host)) {
			return true;
		}
		return false;
	}
}
