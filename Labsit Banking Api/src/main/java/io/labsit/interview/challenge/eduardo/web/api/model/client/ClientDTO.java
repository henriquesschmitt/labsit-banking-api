package io.labsit.interview.challenge.eduardo.web.api.model.client;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.client.Client;

public class ClientDTO {
	private String cpfCnpj;
	private String firstName;
	private String lastName;
	private ClientType type;

	public ClientDTO() {

	}

	public ClientDTO(String cpfCnpj, String firstName, String lastName, ClientType type) {
		super();
		this.cpfCnpj = cpfCnpj;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
	}

	public ClientDTO(Client clientDao) {
		this(clientDao.getId(), clientDao.getFirstName(), clientDao.getLastName(), ClientType.fromCode(clientDao.getType()));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientDTO [cpfCnpj=").append(cpfCnpj).append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName).append(", type=").append(type).append("]");
		return builder.toString();
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

}
