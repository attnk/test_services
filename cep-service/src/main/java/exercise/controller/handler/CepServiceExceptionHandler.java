package exercise.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;

@ControllerAdvice
@RestController
public class CepServiceExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler({
    	InvalidCepException.class,
    	NotFoundCepException.class
    })
	public Address handleInvalidCep(InvalidCepException e) {
		Address response = new Address();
		response.setError(e.getMessage());
		
		return response;
	}
}
