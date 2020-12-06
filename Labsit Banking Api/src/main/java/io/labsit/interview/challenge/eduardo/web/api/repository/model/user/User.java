package io.labsit.interview.challenge.eduardo.web.api.repository.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "USER_SCHEMA", name = "USER")
public class User {
	@Id
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "USER_ROLE_ID")
	private Integer userRole;
	@Column(name = "STATUS_TYPE_ID")
	private Integer statusTypeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public Integer getStatusTypeId() {
		return statusTypeId;
	}

	public void setStatusTypeId(Integer statusTypeId) {
		this.statusTypeId = statusTypeId;
	}

}
