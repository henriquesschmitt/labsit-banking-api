package io.labsit.interview.challenge.eduardo.web.api.service.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.labsit.interview.challenge.eduardo.web.api.exception.AccountNotFoundException;
import io.labsit.interview.challenge.eduardo.web.api.exception.UserNotFoundException;
import io.labsit.interview.challenge.eduardo.web.api.model.AccountIdDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.ClientAccountTransactionRequest;
import io.labsit.interview.challenge.eduardo.web.api.model.TransactionTypeEnum;
import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientAccount;
import io.labsit.interview.challenge.eduardo.web.api.model.security.TransactionLog;
import io.labsit.interview.challenge.eduardo.web.api.repository.ClientAccountRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.ClientRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.TransactionLogRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.UserRepository;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.AccountId;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.client.Client;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Transaction;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;
import io.labsit.interview.challenge.eduardo.web.api.service.ClientAccountService;
import io.labsit.interview.challenge.eduardo.web.api.service.ClientService;
import io.labsit.interview.challenge.eduardo.web.utils.LabSitBankingUtils;

@Service
public class ClientAccountImpl implements ClientAccountService {
	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClientService clientService;

	@Override
	public ClientAccount geClientAccountById(AccountIdDTO accId) {
		if (accId == null)
			throw new IllegalArgumentException("A account ID is required.");

		if (accId.getAccountNumber() == null || accId.getAccountNumber().toString().isEmpty())
			throw new IllegalArgumentException("Invalid account number.");

		if (accId.getAgencyNumber() == null || accId.getAgencyNumber().toString().isEmpty())
			throw new IllegalArgumentException("Invalid agency number.");

		io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount account = clientAccountRepository
				.findById(new AccountId(accId.getAgencyNumber(), accId.getAccountNumber())).orElse(null);

		if (account == null)
			throw new AccountNotFoundException("Account not found.");

		io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog transactionLog = 
				new io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog();

		transactionLog.setId(LabSitBankingUtils.getUID());
		transactionLog.setTransactionDate(LocalDateTime.now());
		transactionLog.setTransactionId(new Transaction());
		transactionLog.getTransactionId().setId(TransactionTypeEnum.BALANCE.getId());
		transactionLog.setTransactionValue(BigDecimal.ZERO);
		transactionLog.setUserId(new User());
		transactionLog.getUserId().setUserId(
				LabSitBankingUtils.getUserId(new AccountId(account.getAgencyNumber(), account.getAccountNumber())));

		transactionLog = transactionLogRepository.save(transactionLog);

		return new ClientAccount(account);

	}

	@Override
	@Transactional
	public TransactionLog doWithdraw(ClientAccountTransactionRequest transaction) {
		return doTransaction(TransactionTypeEnum.WITHDRAW, transaction, (acc, t) -> {
			if (acc.getBalance().compareTo(t.getValue()) < 0)
				throw new IllegalStateException("The account do not have enough balance to do this operation.");
			return true;
		}, (acc, t) -> {
			return clientAccountRepository.updateBalance(acc.getBalance().subtract(t.getValue().abs()),
					acc.getAgencyNumber(), acc.getAccountNumber());
		});
	}

	@Override
	public AccountIdDTO getAccountIdByCpfCnpj(String cpfCnpj) {
		if (cpfCnpj == null || cpfCnpj.isEmpty())
			throw new IllegalArgumentException("Invalid CPF or CNPJ");

		Client c = clientRepository.findActiveById(cpfCnpj);

		if (c == null)
			throw new UserNotFoundException("Client Not Found.");

		io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount acc = clientAccountRepository
				.findByCpfCnpj(cpfCnpj);

		if (acc == null)
			throw new AccountNotFoundException("Account not for the given user.");

		AccountIdDTO r = new AccountIdDTO(acc.getAgencyNumber(), acc.getAccountNumber());

		return r;
	}

	private TransactionLog doTransaction(TransactionTypeEnum type, ClientAccountTransactionRequest transaction,
			BiFunction<io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount, ClientAccountTransactionRequest, Boolean> validation,
			BiFunction<io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount, ClientAccountTransactionRequest, Integer> update) {

		if (transaction == null)
			throw new IllegalArgumentException("A transaction is required.");

		if (transaction.getAccountId() == null)
			throw new IllegalArgumentException("A account id is required.");

		if (transaction.getValue() == null)
			throw new IllegalArgumentException("A transaction value is required.");

		AccountId accId = new AccountId();
		accId.setAccountNumber(transaction.getAccountId().getAccountNumber());
		accId.setAgencyNumber((transaction.getAccountId().getAgencyNumber()));

		User user = userRepository.findById(LabSitBankingUtils.getUserId(accId)).orElse(null);

		if (user == null)
			throw new UserNotFoundException("The User for this account was not found.");

		if (user.getStatusTypeId() != 1)
			throw new IllegalStateException("The user associated to this account cant do this action.");

		io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount clientAcc = clientAccountRepository
				.findById(accId).orElse(null);

		if (clientAcc == null)
			throw new AccountNotFoundException();

		validation.apply(clientAcc, transaction);

		update.apply(clientAcc, transaction);

		io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog transactionLog = new io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog();

		transactionLog.setId(LabSitBankingUtils.getUID());
		transactionLog.setTransactionDate(LocalDateTime.now());
		transactionLog.setTransactionId(new Transaction());
		transactionLog.getTransactionId().setId(type.getId());
		transactionLog.setTransactionValue(transaction.getValue());
		transactionLog.setUserId(user);

		transactionLog = transactionLogRepository.save(transactionLog);

		return new TransactionLog(transactionLog);
	}

	@Override
	@Transactional
	public TransactionLog doDeposit(ClientAccountTransactionRequest transaction) {
		return doTransaction(TransactionTypeEnum.DEPOSIT, transaction, (acc, t) -> {
			return true;
		}, (acc, t) -> {
			return clientAccountRepository.updateBalance(acc.getBalance().add(t.getValue().abs()),
					acc.getAgencyNumber(), acc.getAccountNumber());
		});
	}

	@Override
	public List<TransactionLog> getStatement(AccountIdDTO accountId, LocalDateTime init, LocalDateTime finish) {
		if (accountId == null)
			throw new IllegalArgumentException("A account ID is required.");

		if (accountId.getAccountNumber() == null || accountId.getAccountNumber().toString().isEmpty())
			throw new IllegalArgumentException("Invalid account number.");

		if (accountId.getAgencyNumber() == null || accountId.getAgencyNumber().toString().isEmpty())
			throw new IllegalArgumentException("Invalid agency number.");

		if (init == null || finish == null)
			throw new IllegalArgumentException("Initial of Final date is null.");

		if (finish.isBefore(init))
			throw new IllegalArgumentException("Invalid time period.");

		List<Transaction> l = new ArrayList<>();
		l.add(new Transaction(TransactionTypeEnum.WITHDRAW.name(), TransactionTypeEnum.WITHDRAW.getId()));
		l.add(new Transaction(TransactionTypeEnum.DEPOSIT.name(), TransactionTypeEnum.DEPOSIT.getId()));

		User user = new User();
		user.setUserId(
				LabSitBankingUtils.getUserId(new AccountId(accountId.getAgencyNumber(), accountId.getAccountNumber())));

		List<io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog> transactions = transactionLogRepository
				.findAllByTransactionId(user, l, init, finish);

		List<TransactionLog> r = new ArrayList<>();

		transactions.forEach(t -> r.add(new TransactionLog(t)));

		return r;
	}

	public TransactionLogRepository getTransactionLogRepository() {
		return transactionLogRepository;
	}

	public void setTransactionLogRepository(TransactionLogRepository transactionLogRepository) {
		this.transactionLogRepository = transactionLogRepository;
	}

	public ClientAccountRepository getClientAccountRepository() {
		return clientAccountRepository;
	}

	public void setClientAccountRepository(ClientAccountRepository clientAccountRepository) {
		this.clientAccountRepository = clientAccountRepository;
	}

	public UserRepository getClientRepository() {
		return userRepository;
	}

	public void setClientRepository(UserRepository clientRepository) {
		this.userRepository = clientRepository;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

}
