package io.labsit.interview.challenge.eduardo.web.api.service.client;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.labsit.interview.challenge.eduardo.web.api.model.UserRolesTypesEnum;
import io.labsit.interview.challenge.eduardo.web.api.model.UserStatusTypesEnum;
import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.client.FullClientDTO;
import io.labsit.interview.challenge.eduardo.web.api.repository.ClientAccountRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.ClientCredentialsRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.ClientRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.UserRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.AccountId;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.client.Client;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Credential;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;
import io.labsit.interview.challenge.eduardo.web.api.service.ClientService;
import io.labsit.interview.challenge.eduardo.web.utils.LabSitBankingUtils;

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ClientCredentialsRepository clientCredentialsRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ClientDTO> getAllClients() {

		List<ClientDTO> clients = new ArrayList<>();

		clientRepository.getAllActive().forEach(c -> clients.add(new ClientDTO(c)));

		return clients;
	}

	@Override
	@Transactional
	public ClientDTO getClient(String cpfCppj) {

		if (cpfCppj == null)
			throw new IllegalArgumentException("The cpf or cnpj is null");

		Client client = clientRepository.findActiveById(cpfCppj);

		if (client == null)
			return null;

		return new ClientDTO(client);
	}
	
	@Override
	@Transactional
	public void updateClient(String cpfCnpj, ClientDTO client) {
		if (cpfCnpj == null || cpfCnpj.isEmpty())
			throw new IllegalArgumentException("Cpf or Cnpj is required");
		
		if (client == null)
			throw new IllegalArgumentException("Client updates are required is required");
		
		Client clientResult = clientRepository.findActiveById(cpfCnpj);
		
		if (clientResult == null) 
			throw new IllegalStateException("The Client was not found.");
		
		clientResult.setFirstName(client.getFirstName() != null ? client.getFirstName() : clientResult.getFirstName());
		clientResult.setLastName(client.getLastName() != null ? client.getLastName() : clientResult.getLastName());
		clientResult.setType(client.getType() != null ? client.getType().toCode() : clientResult.getType());

		clientRepository.save(clientResult);
	}
	
	@Override
	@Transactional
	public FullClientDTO createClient(FullClientDTO client) throws IllegalArgumentException, IllegalStateException {
		if (client == null)
			throw new IllegalArgumentException("The main client object can not be null.");

		if (client.getClient() == null || client.getClient().getCpfCnpj() == null
				|| client.getClient().getFirstName() == null || client.getClient().getType() == null) {
			throw new IllegalArgumentException("Invalid client info.");
		}

		if (client.getAccount() == null || client.getAccount().getAccountNumber() == null
				|| client.getAccount().getAgencyNumber() == null)
			throw new IllegalArgumentException("Invalid account info.");

		if (client.getAccount().getAccountNumber().toString().length() < 8)
			throw new IllegalArgumentException("The account number needs to have 8 digits");

		if (client.getAccount().getAgencyNumber().toString().length() < 2)
			throw new IllegalArgumentException("The agency number needs to have at least 2 digits");

		if (client.getCredentials() == null || client.getCredentials().getPassword() == null
				|| client.getCredentials().getPassword().isEmpty()) {
			throw new IllegalArgumentException(
					"Invalid password. The password need to have between 5 and 10 characters.");
		}

		if (!validateAccount(client))
			throw new IllegalStateException("This Account alredy exists.");

		if (!validateClient(client))
			throw new IllegalStateException("This Client alredy exists.");
		
		String userId = LabSitBankingUtils.getUserId(
				new AccountId(client.getAccount().getAgencyNumber(), client.getAccount().getAccountNumber()));
		
		if (!validateUser(userId))
			throw new IllegalStateException("This User alredy exists.");
		
		createUser(userId);
		createClientBd(client, userId);
		createClientAccount(client);
		Credential createdCredential =  createCredentials(client, userId);
		
		client.getCredentials().setPassword(null);
		client.getCredentials().setUserName(createdCredential.getUserName());
		
		return client;
	}
	
	private User createUser(String userId) {
		User user = new User();
		user.setStatusTypeId(1);
		user.setUserId(userId);
		user.setUserRole(UserRolesTypesEnum.CLIENT.getKey());
		user.setStatusTypeId(UserStatusTypesEnum.OK.getKey());
		
		return userRepository.save(user);
	}
	
	private Credential createCredentials(FullClientDTO client, String userId) {
		Credential credential = new Credential();
		//TODO encrypt pass
		credential.setPassword(client.getCredentials().getPassword());
		credential.setUserName(userId);
		
		return clientCredentialsRepository.save(credential);
	}
	
	private Client createClientBd(FullClientDTO client, String userId) {
		Client c = new Client();
		c.setFirstName(client.getClient().getFirstName());
		c.setId(client.getClient().getCpfCnpj());
		c.setLastName(client.getClient().getLastName());
		c.setType(client.getClient().getType().toCode());
		c.setUserId(userId);
		
		return clientRepository.save(c);
	}
	
	private ClientAccount createClientAccount(FullClientDTO client) {
		ClientAccount c = new ClientAccount();
		c.setAccountNumber(client.getAccount().getAccountNumber());
		c.setAgencyNumber(client.getAccount().getAgencyNumber());
		c.setBalance(client.getAccount().getBalance());
		c.setCpfCnpj(client.getClient().getCpfCnpj());
		
		return clientAccountRepository.save(c);
	}

	private boolean validateAccount(FullClientDTO client) {
		return !clientAccountRepository.existsById(
				new AccountId(client.getAccount().getAgencyNumber(), client.getAccount().getAccountNumber()));
	}

	private boolean validateClient(FullClientDTO client) {
		return !clientRepository.existsById(client.getClient().getCpfCnpj());
	}

	private boolean validateUser(String userId) {
		return !userRepository.existsById(userId);
	}

	public ClientRepository getClientRepository() {
		return clientRepository;
	}

	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public ClientAccountRepository getClientAccountRepository() {
		return clientAccountRepository;
	}

	public void setClientAccountRepository(ClientAccountRepository clientAccountRepository) {
		this.clientAccountRepository = clientAccountRepository;
	}

	public ClientCredentialsRepository getClientCredentialsRepository() {
		return clientCredentialsRepository;
	}

	public void setClientCredentialsRepository(ClientCredentialsRepository clientCredentialsRepository) {
		this.clientCredentialsRepository = clientCredentialsRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void deleteClient(String cpfCnpj) {
		if (cpfCnpj == null || cpfCnpj.isEmpty())
			throw new IllegalArgumentException("The Client CPF or CNPJ can't be empty.");
		Client client =  clientRepository.findActiveById(cpfCnpj);
		if (client != null) {
			userRepository.deleteUser(client.getUserId());
		} else {
			throw new IllegalStateException("Client do not exists.");
		}
	}

}
