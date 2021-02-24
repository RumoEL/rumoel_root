package com.github.rumoel.rumosploit.tasks;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TaskPair implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8315986998005042557L;
	@Getter
	@Setter
	BotTask task;
	@Getter
	@Setter
	BotTaskAnswer answer;
}
