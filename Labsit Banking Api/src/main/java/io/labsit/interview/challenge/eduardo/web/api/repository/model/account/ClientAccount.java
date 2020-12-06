package io.labsit.interview.challenge.eduardo.web.api.repository.model.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(AccountId.class)
@Table(schema = "ACCOUNT_SCHEMA", name = "CLIENT_ACCOUNT")
public class ClientAccount implements Serializable{
	
	private static final long serialVersionUID = 6081947888373544335L;
	
	@Id
	@Column(name = "AGENCY_NUMBER")
	private Integer agencyNumber;
	@Id
	@Column(name = "ACCOUNT_NUMBER")
	private Long accountNumber;
	@Column(name = "CLIENT_ID")
	private String cpfCnpj;
	@Column(name = "BALANCE")
	private BigDecimal balance;

	public ClientAccount() {
		super();
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

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

}
