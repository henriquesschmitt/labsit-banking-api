package io.labsit.interview.challenge.eduardo.web.api.service;

import java.util.List;

import javax.transaction.Transactional;

import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.client.FullClientDTO;

public interface ClientService {

	List<ClientDTO> getAllClients();
	
	ClientDTO getClient(String cpfCppj);
	@Transactional
	public FullClientDTO createClient(FullClientDTO client);
	
	void deleteClient(String cpfCnpj);

	void updateClient(String cpfCnpj, ClientDTO client);	
}