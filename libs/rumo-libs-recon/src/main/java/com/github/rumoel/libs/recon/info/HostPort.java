package com.github.rumoel.libs.recon.info;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hostport")
public class HostPort implements Serializable {
	private static final long serialVersionUID = -3885435248654748800L;

	public enum protocol {
		TCP, UDP
	}

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

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private protocol proto;

	public HostPort() {
	}

	public HostPort(long timeToSet, String hostToSet, int portToSet, protocol proto) {
		setTime(timeToSet);
		setHost(hostToSet);
		setPort(portToSet);
		setProto(proto);

		setId(getHost() + ":" + getPort() + ":" + getProto());
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
		builder.append(", proto=");
		builder.append(proto);
		builder.append("]");
		return builder.toString();
	}

}
