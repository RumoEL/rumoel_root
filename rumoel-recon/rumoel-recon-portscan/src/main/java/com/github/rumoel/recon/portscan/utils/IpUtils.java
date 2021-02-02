package com.github.rumoel.recon.portscan.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IpUtils {
	private IpUtils() {
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

	public static boolean ipInPrivateRange(String host) {
		if (isValidRange("127.0.0.0", "127.255.255.255", host) || isValidRange("192.168.0.0", "192.168.255.255", host)
				|| isValidRange("10.0.0.0", "10.255.255.255", host)
				|| isValidRange("172.16.0.0", "172.31.255.255", host)) {
			return true;
		}

		return false;
	}

}
