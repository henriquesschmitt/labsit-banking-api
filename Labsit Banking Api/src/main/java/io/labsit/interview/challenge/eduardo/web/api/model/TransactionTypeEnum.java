package io.labsit.interview.challenge.eduardo.web.api.model;

public enum TransactionTypeEnum {
	DEPOSIT(1), WITHDRAW(2), BALANCE(3);

	private TransactionTypeEnum(Integer id) {
		this.id = id;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

}
