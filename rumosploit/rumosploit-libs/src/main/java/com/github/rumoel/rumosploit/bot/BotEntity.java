package com.github.rumoel.rumosploit.bot;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BotEntity implements Serializable {

	private static final long serialVersionUID = -1441510719450406247L;
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String osName;
	@Getter
	@Setter
	private String osVersion;
	@Getter
	@Setter
	private String osArch;

	@Getter
	@Setter
	private String osUserName;
	@Getter
	@Setter
	private String osUserGroups;

}
