package com.github.rumoel.rumosploit.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExternalDataGetter {
	static CopyOnWriteArrayList<URL> resolvers = new CopyOnWriteArrayList<>();

	private ExternalDataGetter() {
		System.err.println(111231231);
	}

	public static String getExternalIP() throws MalformedURLException {
		if (resolvers.isEmpty()) {
			resolvers.add(new URL("https://ident.me"));
		}
		String tmpIp = null;
		for (URL url : resolvers) {
			try {
				tmpIp = Sites.getStringFromUrl(url);
				if (tmpIp != null) {
					return tmpIp;
				}
			} catch (Exception e) {
				// IGNORE
			}
		}

		return tmpIp;
	}
}
