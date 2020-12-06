package io.labsit.interview.challenge.eduardo.web.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.labsit.interview.challenge.eduardo.web.api.exception.UserNotFoundException;
import io.labsit.interview.challenge.eduardo.web.api.model.client.ClientDTO;
import io.labsit.interview.challenge.eduardo.web.api.model.client.FaultModel;
import io.labsit.interview.challenge.eduardo.web.api.model.client.FullClientDTO;
import io.labsit.interview.challenge.eduardo.web.api.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Client Controller")
@RestController
@RequestMapping(path = "/clients", produces = { "application/json; charset=utf-8" })
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/")
	@ApiOperation(value = "Returns all clients in the database.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, responseContainer = "List", response = ClientDTO.class, message = "Ok"),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public List<ClientDTO> getClients() {
		return clientService.getAllClients();
	}

	@GetMapping("/{cpfCnpj}")
	@ApiOperation(value = "Returns a client given an CPF or CNPJ.")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ClientDTO.class, message = "Ok"),
			@ApiResponse(code = 404, response = FaultModel.class, message = "Client Not Found"),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public ClientDTO getClient(@PathVariable(name = "cpfCnpj", required = true) String cpfCnpj) {
		ClientDTO client = clientService.getClient(cpfCnpj);

		if (client == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client Not Found", new UserNotFoundException());

		return client;
	}
	
	@PatchMapping("/{cpfCnpj}")
	@ApiOperation(value = "Uptades a given client.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Ok"),
			@ApiResponse(code = 404, response = FaultModel.class, message = "Client Not Found"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "Bad Request."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public void updateClient(@PathVariable(name = "cpfCnpj", required = true) String cpfCnpj, @RequestBody(required = true) ClientDTO client) {
		try {
			clientService.updateClient(cpfCnpj, client);
		}  catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "Creates a given client in the system.")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ok"),
			@ApiResponse(code = 400, response = FaultModel.class, message = "Is the user sent a invalid information."),
			@ApiResponse(code = 409, response = FaultModel.class, message = "If some informantion is conflicting on the system."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public FullClientDTO createClient(@RequestBody(required = true) FullClientDTO client) {
		try {
			return clientService.createClient(client);
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cpfCnpj}")
	@ApiOperation(value = "Removes logically a given client from the system. Notice that the resouces of the client cant be used by new one, like the account, that cant be shared beteween users, even if they were removed.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Ok"),
			@ApiResponse(code = 404, response = FaultModel.class, message = "If the client do not exists."),
			@ApiResponse(code = 400, response = FaultModel.class, message = "If the CPF or CNPJ is Invalid."),
			@ApiResponse(code = 500, response = FaultModel.class, message = "Internal Server Error") })
	public void deleteClient(@PathVariable(name = "cpfCnpj", required = true) String cpfCnpj) {
		try {
			 clientService.deleteClient(cpfCnpj);
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

}
