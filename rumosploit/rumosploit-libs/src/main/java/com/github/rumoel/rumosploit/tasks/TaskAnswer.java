package com.github.rumoel.rumosploit.tasks;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TaskAnswer implements Serializable {
	private static final long serialVersionUID = -8328168052049360011L;
	@Getter
	@Setter
	private Task task;

	@Getter
	@Setter
	private byte[] output;
	@Getter
	@Setter
	private Part part;
}
