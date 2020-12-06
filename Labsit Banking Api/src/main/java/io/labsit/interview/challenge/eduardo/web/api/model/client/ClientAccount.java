package io.labsit.interview.challenge.eduardo.web.api.model.client;

import java.math.BigDecimal;

public class ClientAccount {
	private Integer agencyNumber;
	private Long accountNumber;
	private BigDecimal balance;
	
	public ClientAccount(Integer agencyNumber, Long accountNumber, BigDecimal balance) {
		super();
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	
	public ClientAccount(io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount acc) {
		this(acc.getAgencyNumber(), acc.getAccountNumber(), acc.getBalance());
	}

	public ClientAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(Integer agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
