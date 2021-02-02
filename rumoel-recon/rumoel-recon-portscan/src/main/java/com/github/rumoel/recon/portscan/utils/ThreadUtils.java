package com.github.rumoel.recon.portscan.utils;

import java.lang.Thread.State;

import com.github.rumoel.recon.portscan.engine.ScanThread;
import com.github.rumoel.recon.portscan.header.PortScanHeader;

public final class ThreadUtils {
	private ThreadUtils() {
	}

	// THREADS
	public static void startCleaner() {
		new Thread(() -> {
			do {
				for (int j = 0; j < PortScanHeader.scanThreads.size(); j++) {
					ScanThread thread = PortScanHeader.scanThreads.get(j);
					if (thread != null) {
						State state = thread.getState();
						if (state.equals(Thread.State.TERMINATED)) {
							PortScanHeader.scanThreads.remove(thread);
						}
					}
				}
				if (PortScanHeader.stop) {
					break;
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (!PortScanHeader.stop);
		}).start();
	}

	public static void sleepThreads() {
		while (PortScanHeader.scanThreads.size() > PortScanHeader.config.getThreads()) {
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // THREADS
}
