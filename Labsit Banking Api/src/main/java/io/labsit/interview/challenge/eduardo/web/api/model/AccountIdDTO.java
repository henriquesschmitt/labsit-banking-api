package io.labsit.interview.challenge.eduardo.web.api.model;

public class AccountIdDTO {

	private Integer agencyNumber;
	private Long accountNumber;

	public AccountIdDTO(Integer agencyNumber, Long accountNumber) {
		super();
		this.agencyNumber = agencyNumber;
		this.accountNumber = accountNumber;
	}

	public AccountIdDTO() {
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

}
