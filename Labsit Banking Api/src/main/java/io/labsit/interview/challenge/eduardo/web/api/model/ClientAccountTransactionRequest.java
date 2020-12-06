package io.labsit.interview.challenge.eduardo.web.api.model;

import java.math.BigDecimal;

public class ClientAccountTransactionRequest {
	private AccountIdDTO accountId;
	private BigDecimal value;

	public AccountIdDTO getAccountId() {
		return accountId;
	}

	public void setAccountId(AccountIdDTO accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
