package exercise.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.SearchError;

@ControllerAdvice
@RestController
public class CepServiceExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler({
    	InvalidCepException.class
    })
	public SearchError handleInvalidCep(Exception e) {
		SearchError response = new SearchError();
		response.setMessega(e.getMessage());
		
		return response;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  
    @ExceptionHandler({
    	NotFoundCepException.class
    })
	public SearchError handleNotFoundCep(Exception e) {
		SearchError response = new SearchError();
		response.setMessega(e.getMessage());
		
		return response;
	}
	
}
