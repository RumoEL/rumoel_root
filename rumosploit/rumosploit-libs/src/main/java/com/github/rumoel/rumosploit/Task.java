package com.github.rumoel.rumosploit;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Task implements Serializable {
	private static final long serialVersionUID = 8126639112001903670L;
	@Getter
	private String id = UUID.randomUUID().toString();

	public enum type {
		READ, WRITE, EXECUTE
	}

	@Getter
	@Setter
	private String inputPath;
	@Getter
	@Setter
	private boolean background;
}
