package io.labsit.interview.challenge.eduardo.web.api.repository.model.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ACCOUNT_SCHEMA", name = "AGENCY_INFO")
public class AgencyInfo {

	@Id
	@Column(name = "AGENCY_NUMBER")
	private Integer number;
	@Column(name = "AGENCY_NAME")
	private String name;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
