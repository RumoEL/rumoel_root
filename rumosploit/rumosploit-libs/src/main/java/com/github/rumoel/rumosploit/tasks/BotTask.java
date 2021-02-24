package com.github.rumoel.rumosploit.tasks;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.rumoel.rumosploit.bot.BotEntity;

import lombok.Getter;
import lombok.Setter;

public class BotTask implements Serializable {
	private static final long serialVersionUID = 8126639112001903670L;
	@Getter
	private String id = UUID.randomUUID().toString();

	public enum type {
		READ, WRITE, MODIFY, EXECUTE, OTHER
	}

	@Getter
	private CopyOnWriteArrayList<BotEntity> bots = new CopyOnWriteArrayList<BotEntity>();
	@Getter
	@Setter
	private type tasktype;
	@Getter
	@Setter
	private String input;
	@Getter
	@Setter
	private boolean background;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BotTask [id=");
		builder.append(id);
		builder.append(", bots=");
		builder.append(bots);
		builder.append(", tasktype=");
		builder.append(tasktype);
		builder.append(", input=");
		builder.append(input);
		builder.append(", background=");
		builder.append(background);
		builder.append("]");
		return builder.toString();
	}

}
