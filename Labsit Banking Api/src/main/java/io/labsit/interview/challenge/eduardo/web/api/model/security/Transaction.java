package io.labsit.interview.challenge.eduardo.web.api.model.security;

public class Transaction {
	private String name;
	private Integer id;

	
	public Transaction(io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Transaction t) {
		this(t.getName(), t.getId());
		// TODO Auto-generated constructor stub
	}
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
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
