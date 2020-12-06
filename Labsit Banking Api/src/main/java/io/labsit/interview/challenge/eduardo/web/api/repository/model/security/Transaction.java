package io.labsit.interview.challenge.eduardo.web.api.repository.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "SECURITY_SCHEMA", name = "TRANSACTION_TYPE")
public class Transaction {
	@Column(name = "TRANSACTION_NAME")
	private String name;
	@Id
	@Column(name = "TRANSACTION_TYPE_ID")
	private Integer id;
	
	

	public Transaction(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
