package io.labsit.interview.challenge.eduardo.web.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.labsit.interview.challenge.eduardo.web.api.exception.AccountNotFoundException;
import io.labsit.interview.challenge.eduardo.web.api.exception.UserNotFoundException;
import io.labsit.interview.challenge.eduardo.web.api.model.AccountIdDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.ClientAccountTransactionRequest;
import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientAccount;
import io.labsit.interview.challenge.eduardo.web.api.model.client.FaultModel;
import io.labsit.interview.challenge.eduardo.web.api.model.security.TransactionLog;

import io.labsit.interview.challenge.eduardo.web.api.service.ClientAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Client Account Controller")
@RestController
@RequestMapping(path = "/client-account", produces = { "application/json; charset=utf-8" })
public class ClientAccountController {

	@Autowired
	private ClientAccountService clientAccountService;

	@PostMapping("/transaction/withdraw")
	@ApiOperation(value = "Withdraw money from the given account.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 200, response = TransactionLog.class, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "Bad Request."),
			@ApiResponse(code = 404, response = FaultModel.class, message = "Account not exists"),
			@ApiResponse(code = 409, response = FaultModel.class, message = "If the account has no enough money in the balance."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public TransactionLog withdraw(@RequestBody(required = true) ClientAccountTransactionRequest withdrawRequest) {
		try {
			return clientAccountService.doWithdraw(withdrawRequest);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (AccountNotFoundException | UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/{cpfCnpj}")
	@ApiOperation(value = "Returns the Account of the given Client.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 200, response = AccountIdDTO.class, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "Invalid cpfCnpj"),
			@ApiResponse(code = 404, response = FaultModel.class, message = "Client Not Found."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public AccountIdDTO getAccountByCpfCnpj(@PathVariable("cpfCnpj") String cpfCnpj) {
		try {
			return clientAccountService.getAccountIdByCpfCnpj(cpfCnpj);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (UserNotFoundException | AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PostMapping("/transaction/deposit")
	@ApiOperation(value = "Deposit money in the given account.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 200, responseContainer = "List", response = TransactionLog.class, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "Is the account do not exists."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public TransactionLog deposit(@RequestBody(required = true) ClientAccountTransactionRequest depositRequest) {
		try {
			return clientAccountService.doDeposit(depositRequest);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (AccountNotFoundException | UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/")
	@ApiOperation(value = "Get Informations for the given account, like the current balance.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 200, response = ClientAccount.class, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "If the account information is invalid."),
			@ApiResponse(code = 404, response = FaultModel.class, message = "If the account does not exists."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public ClientAccount getBalance(@RequestHeader(value = "accountNumber") Long account,
			@RequestHeader(value = "agencyNumber") Integer agency) {
		try {
			return clientAccountService.geClientAccountById(new AccountIdDTO(agency, account));
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/transaction/statement")
	@ApiOperation(value = "Returns the statement of this account, based on filters.")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 200, response = ClientAccount.class, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "If the account information is invalid or the dates are invalid."),
			@ApiResponse(code = 404, response = FaultModel.class, message = "If the account does not exists."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public List<TransactionLog> getAccountStatement(@RequestHeader(value = "accountNumber") Long account,
			@RequestHeader(value = "agencyNumber") Integer agency,
			@RequestParam(value = "initialTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime init,
			@RequestParam(value = "finalTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime finish) {
		try {
			return clientAccountService.getStatement(new AccountIdDTO(agency, account), init, finish);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	public ClientAccountService getClientAccountService() {
		return clientAccountService;
	}

	public void setClientAccountService(ClientAccountService clientAccountService) {
		this.clientAccountService = clientAccountService;
	}
}
