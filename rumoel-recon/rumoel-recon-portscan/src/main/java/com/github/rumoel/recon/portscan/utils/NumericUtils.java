package com.github.rumoel.recon.portscan.utils;

public final class NumericUtils {
	private NumericUtils() {
	}

	public static int rnd(final int min, int max) {
		max -= min;
		return (int) (Math.random() * ++max) + min;
	}

}
