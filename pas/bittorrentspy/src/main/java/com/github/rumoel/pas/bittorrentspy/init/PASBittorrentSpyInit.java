package com.github.rumoel.pas.bittorrentspy.init;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rumoel.pas.bittorrentspy.config.PASBittorrentSpyConfig;
import com.github.rumoel.pas.bittorrentspy.header.PASBittorrentSpyHeader;
import com.github.rumoel.rumoel.libs.pas.torrents.ReportForTorrentPeer;
import com.turn.ttorrent.common.PeerUID;
import com.turn.ttorrent.tracker.TrackedPeer;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

public class PASBittorrentSpyInit extends Thread {

	Logger logger = LoggerFactory.getLogger(getClass());
	Tracker tracker;

	public static void main(String[] args) throws IOException {
		PASBittorrentSpyHeader.pasBittorrentSpyInit.readConfig();
		PASBittorrentSpyHeader.pasBittorrentSpyInit.initTracker();
		PASBittorrentSpyHeader.pasBittorrentSpyInit.readTorrents();
		PASBittorrentSpyHeader.pasBittorrentSpyInit.startTracker();
	}

	@Override
	public void run() {
		try {
			do {
				if (tracker != null) {
					Collection<TrackedTorrent> trackedTorrents = tracker.getTrackedTorrents();
					for (TrackedTorrent trackedTorrent : trackedTorrents) {
						Map<PeerUID, TrackedPeer> trackedPeers = trackedTorrent.getPeers();
						for (Map.Entry<PeerUID, TrackedPeer> trackedPeerEntry : trackedPeers.entrySet()) {
							TrackedPeer trackedPeer = trackedPeerEntry.getValue();
							ReportForTorrentPeer report = new ReportForTorrentPeer();

							report.setTime(System.currentTimeMillis() / 1000);
							report.setTorrentHash(trackedPeer.getHexInfoHash());
							report.setPeerHash(trackedPeer.getStringPeerId());
							report.setIp(trackedPeer.getIp());
							logger.info("{}", report);
						}
					}
				}
				try {
					sleep(300);
				} catch (Exception e) {
					logger.error(getName(), e);
				}
			} while (true);
		} catch (Exception e) {
			logger.error(getName(), e);
		}
	}

	private void startTracker() throws IOException {
		tracker.start(true);
		start();
	}

	private void initTracker() throws IOException {
		tracker = new Tracker(Tracker.DEFAULT_TRACKER_PORT);
		tracker.setAcceptForeignTorrents(true);
	}

	private void readTorrents() throws IOException {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".torrent");
			}
		};
		if (!PASBittorrentSpyHeader.getConfig().getTorrentsDir().exists()) {
			logger.info("dir {} is created:{}", PASBittorrentSpyHeader.getConfig().getTorrentsDir().getAbsolutePath(),
					PASBittorrentSpyHeader.getConfig().getTorrentsDir().mkdirs());
		}

		for (File f : PASBittorrentSpyHeader.getConfig().getTorrentsDir().listFiles(filter)) {
			logger.info("_____________________________________");
			tracker.announce(TrackedTorrent.load(f));
			logger.info("torrent: {} is loaded", f.getName());
			logger.info("_____________________________________");
		}

	}

	private void readConfig() throws IOException {
		logger.info("readConfig-start");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		if (!PASBittorrentSpyHeader.getCONFIGFILE().exists()) {
			if (!PASBittorrentSpyHeader.getCONFIGFILE().getParentFile().exists()) {
				logger.info("dir {} is created ?:{}",
						PASBittorrentSpyHeader.getCONFIGFILE().getParentFile().getAbsolutePath(),
						PASBittorrentSpyHeader.getCONFIGFILE().getParentFile().mkdirs());
			}
			if (PASBittorrentSpyHeader.getCONFIGFILE().createNewFile()) {
				mapper.writeValue(PASBittorrentSpyHeader.getCONFIGFILE(), PASBittorrentSpyHeader.getConfig());
				throw new ServiceConfigurationError(
						"please edit " + PASBittorrentSpyHeader.getCONFIGFILE().getAbsolutePath());
			}
		}
		PASBittorrentSpyHeader
				.setConfig(mapper.readValue(PASBittorrentSpyHeader.getCONFIGFILE(), PASBittorrentSpyConfig.class));
		if (!PASBittorrentSpyHeader.getConfig().isPrepare()) {
			throw new ServiceConfigurationError(
					"please edit " + PASBittorrentSpyHeader.getCONFIGFILE().getAbsolutePath());
		}
		logger.info("readConfig-end");

	}

}
