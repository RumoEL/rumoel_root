package com.github.rumoel.rumosploit.tasks;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class BotTaskAnswer implements Serializable {
	private static final long serialVersionUID = -8328168052049360011L;

	@Getter
	@Setter
	public String id = UUID.randomUUID().toString();
	@Getter
	@Setter
	public BotTask task;

	@Getter
	@Setter
	public byte[] output;
	@Getter
	@Setter
	public Part part;
}
