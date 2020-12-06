package io.labsit.interview.challenge.eduardo.web.api.repository.model.account;

import java.io.Serializable;
import java.util.Objects;

public class AccountId implements Serializable{
	
	private static final long serialVersionUID = 322949886745897478L;
	private Integer agencyNumber;
	private Long accountNumber;

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

	public AccountId(Integer agencyNumber, Long accountNumber) {
		super();
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
	}

	public AccountId() {
		super();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return accountNumber.equals(accountId.accountNumber) &&
        		agencyNumber.equals(accountId.agencyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agencyNumber);
    }

}
