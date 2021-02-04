package com.github.rumoel.recon.portscan.init;

import java.io.IOException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.net.util.SubnetUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.recon.portscan.configs.Config;
import com.github.rumoel.recon.portscan.engine.ScanThread;
import com.github.rumoel.recon.portscan.header.PortScanHeader;
import com.github.rumoel.recon.portscan.proxy.InitProxy;
import com.github.rumoel.recon.portscan.utils.IpUtils;
import com.github.rumoel.recon.portscan.utils.NumericUtils;
import com.github.rumoel.recon.portscan.utils.ThreadUtils;

public class InitMasScan {

	protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InitMasScan.class);
	//////////////////////////////////////////////////////////////////////////////

	private void initConfig() throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		if (!PortScanHeader.configFile.exists()) {
			PortScanHeader.config = new Config();
			mapper.writeValue(PortScanHeader.configFile, PortScanHeader.config);
			logger.warn("Отредактируйте {}", PortScanHeader.configFile.getAbsolutePath());
			System.exit(0);
		} else {
			try {
				PortScanHeader.config = mapper.readValue(PortScanHeader.configFile, Config.class);
			} catch (Exception e) {
				logger.error("Ошибка в {} {}", PortScanHeader.configFile.getAbsolutePath(), e);
				System.exit(1);
			}
			String msg = ReflectionToStringBuilder.toString(PortScanHeader.config, ToStringStyle.MULTI_LINE_STYLE);
			System.out.println(msg);
		}

	}
	//////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws IOException {

		InitMasScan initMasScan = new InitMasScan();
		initMasScan.initConfig();

		initMasScan.startApp();

	}

	private void startApp() {
		if (PortScanHeader.config != null) {
			if (PortScanHeader.config.isPrepared()) {
				InitProxy.prepareProxy();
				ThreadUtils.startCleaner();
				// WAIT

				modeHostCustom();
				modeHostCIDR();
				modeHostRandom();

				while (!PortScanHeader.scanThreads.isEmpty()) {
					try {
						Thread.sleep(300);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				PortScanHeader.stop = true;
			} else {
				logger.warn("Отредактируйте {} и установите prepared в true",
						PortScanHeader.configFile.getAbsolutePath());
			}
		}

	}

	//////////////////////////////////////////////// MODE HOST START
	private void modeHostCustom() {
		if (PortScanHeader.config.isCustomMode() && PortScanHeader.config.getSinglIP() != null) {
			for (String singleHost : PortScanHeader.config.getSinglIP()) {
				scanHost(singleHost);
			}
		}
	}

	private void modeHostCIDR() {
		if (PortScanHeader.config.isCIDRMode() && PortScanHeader.config.getCidr() != null) {
			for (String cidr : PortScanHeader.config.getCidr()) {
				SubnetUtils utils = new SubnetUtils(cidr);
				String[] hosts = utils.getInfo().getAllAddresses();
				for (String host : hosts) {
					scanHost(host);
				}
			}
		}
	}

	private void modeHostRandom() {
		if (PortScanHeader.config.isRandomMode()) {
			do {
				int part1 = NumericUtils.rnd(1, 255);
				int part2 = NumericUtils.rnd(1, 255);
				int part3 = NumericUtils.rnd(1, 255);
				int part4 = NumericUtils.rnd(1, 255);
				String host = part1 + "." + part2 + "." + part3 + "." + part4;
				if (IpUtils.ipInPrivateRange(host)) {
					continue;
				}

				// singlePort
				if (PortScanHeader.config.getPorts() != null) {
					for (int port : PortScanHeader.config.getPorts()) {
						runScan(host, port);
					}
				}
				// singlePort
				// range
				if (PortScanHeader.config.getPortsRange() != null) {
					for (String portRange : PortScanHeader.config.getPortsRange()) {
						portRange = portRange.replaceAll(" ", "");
						int min = Integer.parseInt(portRange.split("-")[0]);
						int max = Integer.parseInt(portRange.split("-")[1]);
						for (int port = min; port <= max; port++) {
							runScan(host, port);
						}
					}
				}
				// range
			} while (true);

		}
	}

	//////////////////////////////////////////////// MODE HOST END
	// ------------------------------------------------------------
	private void scanHost(String singleHost) {
		modePortCustom(singleHost);
		modePortRange(singleHost);
	}
	//////////////////////////////////////////////// MODE PORT START

	private void modePortCustom(String singleHost) {
		for (int port : PortScanHeader.config.getPorts()) {
			runScan(singleHost, port);
		}
	}

	private void modePortRange(String singleHost) {
		if (PortScanHeader.config.getPortsRange() != null) {
			for (String portRange : PortScanHeader.config.getPortsRange()) {
				portRange = portRange.replaceAll(" ", "");
				int min = Integer.parseInt(portRange.split("-")[0]);
				int max = Integer.parseInt(portRange.split("-")[1]);
				for (int port = min; port <= max; port++) {
					runScan(singleHost, port);
				}
			}
		}
	}
	//////////////////////////////////////////////// MODE PORT END
	// ------------------------------------------------------------

	// RUN SCAN
	private void runScan(String singleHost, int port) {
		ThreadUtils.sleepThreads();
		new ScanThread(singleHost, port).start();
	}

}
