package com.github.rumoel.libs.recon.info;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hostport")
public class HostPort implements Serializable {
	private static final long serialVersionUID = -3885435248654748800L;

	@Getter
	@Setter
	@Id
	@Column
	private String id;
	@Getter
	@Setter
	@Column
	private long time;
	@Getter
	@Setter
	@Column
	private String host;
	@Getter
	@Setter
	@Column
	private int port;

	public HostPort() {
	}

	public HostPort(long timeToSet, String hostToSet, int portToSet) {
		setTime(timeToSet);
		setHost(hostToSet);
		setPort(portToSet);
		setId(getHost() + ":" + getPort());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HostPort [id=");
		builder.append(id);
		builder.append(", time=");
		builder.append(time);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}

}
