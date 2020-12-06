package io.labsit.interview.challenge.eduardo.web.api.model.security;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;

public class TransactionLog {
	
	private String id;

	private Transaction transactionId;

	private User userId;
	
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime transactionDate;

	private BigDecimal transactionValue;
	
	public TransactionLog() {
		super();
	}
	
	public TransactionLog(String id, Transaction transactionId, User userId, LocalDateTime transactionDate,
			BigDecimal transactionValue) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.userId = userId;
		this.transactionDate = transactionDate;
		this.transactionValue = transactionValue;
	}

	public TransactionLog(io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog transaction) {
		this(transaction.getId(), new Transaction(transaction.getTransactionId()), transaction.getUserId(), transaction.getTransactionDate(), transaction.getTransactionValue());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Transaction getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Transaction transactionId) {
		this.transactionId = transactionId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(BigDecimal transactionValue) {
		this.transactionValue = transactionValue;
	}

}
