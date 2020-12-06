package io.labsit.interview.challenge.eduardo.web.api.repository.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "USER_SCHEMA", name = "STATUS_TYPE")
public class StatusType {
	
	@Id
	@Column(name = "STATUS_ID")
	private Integer id;
	@Column(name = "STATUS_TYPE_NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
