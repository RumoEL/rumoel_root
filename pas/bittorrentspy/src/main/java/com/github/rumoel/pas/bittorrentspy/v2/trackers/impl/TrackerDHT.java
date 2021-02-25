package com.github.rumoel.pas.bittorrentspy.v2.trackers.impl;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.github.rumoel.pas.bittorrentspy.dht.MagnetLinkFileReader;
import com.github.rumoel.pas.bittorrentspy.dht.PeerStats;
import com.github.rumoel.pas.bittorrentspy.dht.StatsDumper;
import com.github.rumoel.pas.bittorrentspy.v2.header.Header;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.magnet.MagnetUri;
import bt.metainfo.TorrentId;
import bt.runtime.BtClient;
import bt.runtime.BtRuntime;
import bt.runtime.Config;

public class TrackerDHT extends Tracker {
	private static FileSystem FS = Jimfs.newFileSystem(Configuration.unix());
	private static BtRuntime RUNTIME = createRuntime();
	private final ConcurrentHashMap<TorrentId, PeerStats> STATS = new ConcurrentHashMap<>();

	private static ScheduledExecutorService STATS_WRITER = Executors.newSingleThreadScheduledExecutor(r -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});
	CopyOnWriteArrayList<BtClient> btClients = new CopyOnWriteArrayList<>();
	StatsDumper dumper = new StatsDumper(System.currentTimeMillis());

	@Override
	public void init() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				STATS_WRITER.shutdownNow();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				FS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		//
		try {
			if (!Header.getConfig().getTorrentsMagnets().exists()) {
				Header.getConfig().getTorrentsMagnets().getParentFile().mkdirs();
			}
			logger.info("magnets file[{}] is created:{}", Header.getConfig().getTorrentsMagnets().getAbsolutePath(),
					Header.getConfig().getTorrentsMagnets().createNewFile());

			Header.getMagnetUris().addAll(
					new MagnetLinkFileReader().readFromFile(Header.getConfig().getTorrentsMagnets().getAbsolutePath()));
		} catch (Exception e2) {
			logger.error("read", e2);
		}

		// new MagnetLinkFileReader().readFromFile("FILE");
		for (MagnetUri magnetUri : Header.getMagnetUris()) {
			getLogger().info("Creating client for info hash: {}", magnetUri.getTorrentId());
			attachPeerListener(RUNTIME, magnetUri.getTorrentId());
			btClients.add(createClient(RUNTIME, magnetUri));
		}
	}

	@Override
	public void dump() {
		dumper.dumpStats(STATS);
		STATS.clear();
	}

	@Override
	public boolean startTr() {
		getLogger().info("Starting clients...");
		CopyOnWriteArrayList<CompletableFuture<?>> futures = new CopyOnWriteArrayList<>();

		for (BtClient btClient : btClients) {
			futures.add(btClient.startAsync());
		}

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

		return true;
	}

	private void attachPeerListener(BtRuntime runtime, TorrentId torrentId) {
		PeerStats perTorrentStats = STATS.computeIfAbsent(torrentId, it -> new PeerStats());
		runtime.getEventSource().onPeerDiscovered(perTorrentStats::onPeerDiscovered)
				.onPeerConnected(perTorrentStats::onPeerConnected)
				.onPeerDisconnected(perTorrentStats::onPeerDisconnected)
				.onPeerBitfieldUpdated(perTorrentStats::onPeerBitfieldUpdated);
	}

	private static BtClient createClient(BtRuntime runtime, MagnetUri magnetUri) {
		Storage storage = new FileSystemStorage(FS.getPath("/" + UUID.randomUUID()));
		return Bt.client(runtime).magnet(magnetUri).storage(storage).initEagerly().build();
	}

	private static BtRuntime createRuntime() {
		final int MAX_PEER_CONNECTIONS = 5000;

		Config config2 = new Config();
		config2.setMaxConcurrentlyActivePeerConnectionsPerTorrent(0);

		Config mainConfig = new Config() {
			@Override
			public int getAcceptorPort() {
				return Header.getConfig().getUdpTcpTrackerPort();
			}

			@Override
			public int getMaxPeerConnections() {
				return MAX_PEER_CONNECTIONS;
			}

			@Override
			public int getMaxPeerConnectionsPerTorrent() {
				return MAX_PEER_CONNECTIONS;
			}
		};

		DHTModule dhtModule = new DHTModule(new DHTConfig() {
			@Override
			public int getListeningPort() {
				return Header.getConfig().getDhtTrackerPort();
			}

			@Override
			public boolean shouldUseRouterBootstrap() {
				return true;
			}
		});

		return BtRuntime.builder(mainConfig).autoLoadModules().module(dhtModule).build();
	}

}
