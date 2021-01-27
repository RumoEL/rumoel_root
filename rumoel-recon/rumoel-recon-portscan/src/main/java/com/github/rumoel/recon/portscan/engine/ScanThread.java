package com.github.rumoel.recon.portscan.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.rumoel.recon.portscan.configs.HEADER_ALL;

import lombok.Getter;
import lombok.Setter;
import me.tongfei.progressbar.ProgressBar;
import sockslib.client.SocksSocket;

public class ScanThread extends Thread implements Runnable {
	@Getter
	@Setter
	private String HOST;
	@Getter
	@Setter
	private int PORT;

	private ProgressBar progressBar;

	public ScanThread(String host, int port, ProgressBar pb) {
		this.setHOST(host);
		this.setPORT(port);
		if (pb != null) {
			progressBar = pb;
		}
	}

	public ScanThread(String string, Integer integer) {
		this.setHOST(string);
		this.setPORT(integer);
	}

	@Override
	public void run() {
		Thread.currentThread().setName(this.getClass().getName() + ":" + getHOST() + ":" + getPORT());
		System.err.println(Thread.currentThread().getName());
		if (this.checkPort(this.getHOST(), this.getPORT())) {
			String data = this.getHOST() + ":" + this.getPORT();
			System.out.println(data);
			try (FileWriter writer = new FileWriter("log.txt", true)) {
				writer.write(data);
				writer.append('\n');
				writer.flush();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		if (progressBar != null) {
			progressBar.step();
		}
	}

	private boolean checkPort(String host, int port) {
		Socket aaSocket = null;
		try {
			if (HEADER_ALL.config.isProxy_use()) {
				aaSocket = new SocksSocket(HEADER_ALL.proxy);
			} else {
				aaSocket = new Socket();
			}
			aaSocket.connect(new InetSocketAddress(this.getHOST(), this.getPORT()), HEADER_ALL.config.getTimeout());
		} catch (IOException e) {
			try {
				aaSocket.close();
			} catch (IOException e1) {
			}
			return false;
		}
		try {
			aaSocket.close();
		} catch (IOException e) {
		}
		return true;
	}

}