package com.github.rumoel.sysutils.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;

public class MkdirMVFileToExtDir extends Thread {
	File inputDir;

	public static void main(String[] args) throws InterruptedException {
		MkdirMVFileToExtDir mkdirmvtoDir = new MkdirMVFileToExtDir();
		mkdirmvtoDir.initForArgs(args);
		mkdirmvtoDir.start();
		mkdirmvtoDir.join();
	}

	private void initForArgs(String[] args) {
		if (args.length == 0) {
			inputDir = new File(new File("").getAbsolutePath());
		} else {
			if (args.length > 1) {
				System.exit(1);
			}
			inputDir = new File(args[0]);
		}
	}

	@Override
	public void run() {
		File[] files = inputDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				try {
					String extension = FilenameUtils.getExtension(file.getAbsolutePath());
					File parentDir = file.getParentFile();
					File dirForMove = new File(parentDir, extension);
					File newFile = new File(dirForMove, file.getName());
					dirForMove.mkdirs();
					Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
