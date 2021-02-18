package com.github.rumoel.rumosploit.tasks;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Part implements Serializable {

	private static final long serialVersionUID = 6833671158795494506L;
	@Getter
	@Setter
	private int current;
	@Getter
	@Setter
	private int max;

}
