package com.github.rumoel.pas.bittorrentspy.config;

import java.io.File;

import com.github.rumoel.pas.bittorrentspy.header.PASBittorrentSpyHeader;

import lombok.Getter;
import lombok.Setter;

public class PASBittorrentSpyConfig {
	@Getter
	@Setter
	boolean prepare;

	@Getter
	@Setter
	private File torrentsDir = new File(PASBittorrentSpyHeader.getROOTDIR(), "torrents");
	@Getter
	private final File torrentsMagnets = new File(torrentsDir, "magnets");

	@Getter
	@Setter
	private int httpTrackerPort = 8010;
	@Getter
	@Setter
	private int udpTcpTrackerPort = 8011;
	@Getter
	@Setter
	private int dhtTrackerPort = 8012;
}
