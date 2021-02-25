package com.github.rumoel.pas.bittorrentspy.v2.trackers;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rumoel.pas.bittorrentspy.v2.trackers.impl.Tracker;

import lombok.Getter;

public class TrackerHandler extends Thread {
	@Getter
	Logger logger = LoggerFactory.getLogger(getClass());

	CopyOnWriteArrayList<Tracker> trackers = new CopyOnWriteArrayList<>();

	ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

	public void add(Tracker tracker) {
		if (!trackers.contains(tracker)) {
			if (!tracker.getHandlers().contains(this)) {
				tracker.getHandlers().add(this);
			}
			trackers.add(tracker);
		}
	}

	public void init() {
		for (Tracker tracker : trackers) {
			tracker.init();
		}
	}

	@Override
	public void run() {
		for (Tracker tracker : trackers) {
			logger.info("tracker{} is started:{}", tracker.getClass().getSimpleName(), tracker.startTr());

			int statsDumpIntervalSecond = 10;
			getLogger().info("Scheduling stats dump every {} seconds...", statsDumpIntervalSecond);
			executor.scheduleWithFixedDelay(() -> tracker.dump(), 0, statsDumpIntervalSecond, TimeUnit.SECONDS);
		}
	}
}
