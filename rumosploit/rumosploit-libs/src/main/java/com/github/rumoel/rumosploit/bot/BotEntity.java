package com.github.rumoel.rumosploit.bot;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BotEntity implements Serializable {

	private static final long serialVersionUID = -1441510719450406247L;
	@Getter
	@Setter
	public String id;
	@Getter
	@Setter
	public String osName;
	@Getter
	@Setter
	public String osVersion;
	@Getter
	@Setter
	public String osArch;

	@Getter
	@Setter
	public String osUserName;
	@Getter
	@Setter
	public String osUserGroups;

}
