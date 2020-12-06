package io.labsit.interview.challenge.eduardo.web.api.model.client;

public class FullClientDTO {
	private ClientDTO client;
	private ClientAccount account;
	private ClientCredentials credentials;
	
	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public ClientAccount getAccount() {
		return account;
	}

	public void setAccount(ClientAccount account) {
		this.account = account;
	}

	public ClientCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(ClientCredentials credentials) {
		this.credentials = credentials;
	}

}
