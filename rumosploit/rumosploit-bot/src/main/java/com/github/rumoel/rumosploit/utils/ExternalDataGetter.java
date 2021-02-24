package com.github.rumoel.rumosploit.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ExternalDataGetter {
	static ArrayList<String> resolvers = new ArrayList<>();

	private ExternalDataGetter() {
	}

	public static String getExternalIP() throws MalformedURLException {
		if (resolvers.isEmpty()) {
			resolvers.add("https://ident.me");
		}
		String tmpIp = null;
		for (String urlString : resolvers) {
			try {
				tmpIp = Sites.getStringFromUrl(new URL(urlString));
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
