package io.labsit.interview.challenge.eduardo.web.api.errorhandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.labsit.interview.challenge.eduardo.web.api.model.client.FaultModel;
/**
 * Handlers de erro globais da API.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private Logger logger = Logger.getLogger(RestResponseEntityExceptionHandler.class.getName());
	
	@ExceptionHandler(value = { NullPointerException.class, IOException.class, InterruptedException.class,
			ArrayIndexOutOfBoundsException.class, UnsupportedOperationException.class })
	public ResponseEntity<FaultModel> handleNullPointer(Exception ex, WebRequest request) {
		logger.log(Level.SEVERE, ex.getMessage(), ex);
		
		FaultModel fault = new FaultModel();

		fault.setStatus(500);
		fault.setException(ex.getClass().getName());
		fault.setMessage("Ocorreu uma falha durante o processamento.");
		fault.setError(ex.getMessage());

		return new ResponseEntity<FaultModel>(fault, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { ResourceAccessException.class })
	public ResponseEntity<FaultModel> handleConnectionException(ResourceAccessException ex, WebRequest request) {
		logger.log(Level.SEVERE, ex.getMessage(), ex);

		FaultModel fault = new FaultModel();

		fault.setStatus(500);
		fault.setException("ResouceAccessException");
		fault.setMessage("Ocorreu uma falha durante o processamento.");
		fault.setError(ex.getMessage());

		return new ResponseEntity<FaultModel>(fault, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<FaultModel> handleResponseStatusException (ResponseStatusException ex, WebRequest request) {
		logger.log(Level.SEVERE, ex.getMessage(), ex);
		logger.log(Level.SEVERE, ex.getCause() != null ? ex.getCause().getClass().getName() : "Causa: null");
		FaultModel fault = new FaultModel();

		fault.setStatus(ex.getStatus().value());
		fault.setException(ex.getCause() != null ? ex.getCause().getClass().getSimpleName() : ex.getMostSpecificCause().getClass().getName());
		fault.setMessage(ex.getReason());
		fault.setError(ex.getMessage());

		return new ResponseEntity<FaultModel>(fault, ex.getStatus());
	}

}
