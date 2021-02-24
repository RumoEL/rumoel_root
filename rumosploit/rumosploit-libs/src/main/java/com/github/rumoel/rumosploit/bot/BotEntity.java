package com.github.rumoel.rumosploit.bot;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BotEntity implements Serializable {

	private static final long serialVersionUID = -1441510719450406247L;
	@Getter
	@Setter
	public String botId;
	@Getter
	@Setter
	public String machineId;

	@Getter
	@Setter
	public String externalIP;

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
	public String hostName;

	@Getter
	@Setter
	public String osUserName;
	@Getter
	@Setter
	public String osUserGroups;
	@Getter
	@Setter
	public long pid;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BotEntity [botId=");
		builder.append(botId);
		builder.append(", machineId=");
		builder.append(machineId);
		builder.append(", externalIP=");
		builder.append(externalIP);
		builder.append(", osName=");
		builder.append(osName);
		builder.append(", osVersion=");
		builder.append(osVersion);
		builder.append(", osArch=");
		builder.append(osArch);
		builder.append(", hostName=");
		builder.append(hostName);
		builder.append(", osUserName=");
		builder.append(osUserName);
		builder.append(", osUserGroups=");
		builder.append(osUserGroups);
		builder.append(", pid=");
		builder.append(pid);
		builder.append("]");
		return builder.toString();
	}

}
