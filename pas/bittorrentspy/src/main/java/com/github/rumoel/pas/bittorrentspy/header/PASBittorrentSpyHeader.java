package com.github.rumoel.pas.bittorrentspy.header;

import java.io.File;

import com.github.rumoel.pas.bittorrentspy.config.PASBittorrentSpyConfig;
import com.github.rumoel.pas.bittorrentspy.init.PASBittorrentSpyInit;

import lombok.Getter;
import lombok.Setter;

public class PASBittorrentSpyHeader {
	private PASBittorrentSpyHeader() {
	}

	public static PASBittorrentSpyInit pasBittorrentSpyInit = new PASBittorrentSpyInit();

	@Getter
	public static final File ROOTDIR = new File("PBS");

	@Getter
	public static final File CONFIGFILE = new File(ROOTDIR, "config.yml");

	@Getter
	@Setter
	public static PASBittorrentSpyConfig config = new PASBittorrentSpyConfig();
}
