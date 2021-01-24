package com.github.rumoel.libs.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = -2746198389992079640L;

	@Getter
	@Setter
	@Id
	@Column
	private Integer id;

	@Getter
	@Setter
	@Column
	private String name;
	@Getter
	@Setter
	@Column
	private String email;
	@Getter
	@Setter
	@Column
	private String passwd;
}
