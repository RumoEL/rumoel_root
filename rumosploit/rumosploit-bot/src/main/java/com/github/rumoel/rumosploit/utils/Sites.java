package com.github.rumoel.rumosploit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Sites {
	private Sites() {
	}

	public static String getStringFromUrl(URL url) throws IOException {
		String ip;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			ip = in.readLine();
			if (ip != null) {
				return ip;
			}
		}
		return null;
	}

}
