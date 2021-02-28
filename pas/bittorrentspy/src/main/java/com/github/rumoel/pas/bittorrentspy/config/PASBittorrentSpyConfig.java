package com.github.rumoel.pas.bittorrentspy.config;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.rumoel.pas.bittorrentspy.v2.header.Header;

import lombok.Getter;
import lombok.Setter;

public class PASBittorrentSpyConfig {
	@Getter
	@Setter
	boolean prepare;

	@Getter
	@Setter
	private File torrentsDir = new File(Header.getROOTDIR(), "torrents");
	@Getter
	private final File torrentsMagnets = new File(Header.getROOTDIR(), "magnets");

	@Getter
	@Setter
	private int httpTrackerPort = 6882;
	@Getter
	@Setter
	private int udpTcpTrackerPort = 6882;
	@Getter
	@Setter
	private int dhtTrackerPort = 6881;

	@Getter
	@Setter
	private int maxPeerConnections = 5000;

	@Getter
	@Setter
	private CopyOnWriteArrayList<InetSocketAddress> bootstrapNodes = new CopyOnWriteArrayList<>(Arrays.asList(
			new InetSocketAddress("dht.transmissionbt.com", 6881),
			new InetSocketAddress("dht.transmissionbt.com", 6881), new InetSocketAddress("router.utorrent.com", 6881),
			new InetSocketAddress("dht.aelitis.com", 6881)));

}
