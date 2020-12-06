package io.labsit.interview.challenge.eduardo.web.api.repository.model.security;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;

@Entity
@Table(schema = "SECURITY_SCHEMA", name = "TRANSACTION_LOG")
public class TransactionLog {
	@Id
	@Column(name = "TRANSACTION_ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "TRANSACTION_TYPE_ID", referencedColumnName = "TRANSACTION_TYPE_ID")
	private Transaction transactionId;

	@ManyToOne
	@JoinColumn(name = "USER_ID",referencedColumnName = "USER_ID")
	private User userId;

	@Column(name = "TRANSACTION_DATE")
	private LocalDateTime transactionDate;

	@Column(name = "TRANSACTION_VALUE")
	private BigDecimal transactionValue;

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
