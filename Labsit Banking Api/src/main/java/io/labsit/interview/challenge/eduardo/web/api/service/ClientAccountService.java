package io.labsit.interview.challenge.eduardo.web.api.service;

import java.time.LocalDateTime;
import java.util.List;

import io.labsit.interview.challenge.eduardo.web.api.model.AccountIdDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.ClientAccountTransactionRequest;
import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientAccount;
import io.labsit.interview.challenge.eduardo.web.api.model.security.TransactionLog;

public interface ClientAccountService {
	public TransactionLog doWithdraw(ClientAccountTransactionRequest transaction);
	
	public TransactionLog doDeposit(ClientAccountTransactionRequest transaction);
	
	public ClientAccount geClientAccountById(AccountIdDTO accId);
	
	public AccountIdDTO getAccountIdByCpfCnpj(String cpfCnpj);

	List<TransactionLog> getStatement(AccountIdDTO accountId, LocalDateTime init, LocalDateTime finish);
}
