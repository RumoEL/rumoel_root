package com.github.rumoel.recon.portscan.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.rumoel.libs.recon.info.HostPort;
import com.github.rumoel.recon.portscan.header.PortScanHeader;
import com.github.rumoel.recon.portscan.spring.PortscanSpringClientService;

import lombok.Getter;
import lombok.Setter;
import sockslib.client.SocksSocket;

public class ScanThread extends Thread implements Runnable {
	@Getter
	@Setter
	private String HOST;
	@Getter
	@Setter
	private int PORT;

	public ScanThread(String host, int port) {
		this.setHOST(host);
		this.setPORT(port);
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
			HostPort result = new HostPort(System.currentTimeMillis() / 1000, getHOST(), getPORT());
			PortscanSpringClientService.sendResult(result);
		}

	}

	private boolean checkPort(String host, int port) {
		try {
			if (PortScanHeader.config.isProxy_use()) {

				/*
				 * SocketAddress addr = new
				 * InetSocketAddress(PortScanHeader.config.getProxy_host(),
				 * PortScanHeader.config.getProxy_port());
				 * 
				 * Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket socket = new
				 * Socket(proxy); InetSocketAddress dest = new
				 * InetSocketAddress("server.example.org", 1234); socket.connect(dest); //
				 * 
				 */

				try (Socket tmpSocket = new SocksSocket(PortScanHeader.proxy)) {
					connect(tmpSocket, host, port);
				}
			} else {
				try (Socket tmpSocket = new Socket()) {
					connect(tmpSocket, host, port);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void connect(Socket socket, String host, int port) throws IOException {
		socket.connect(new InetSocketAddress(host, port), PortScanHeader.config.getTimeout());
	}
}