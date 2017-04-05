package exercise.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.CouldNotConvertException;
import exercise.exception.CouldNotFoundAdressException;
import exercise.exception.CouldNotProcessException;
import exercise.exception.CouldNotProcessInvalidArgumentException;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.SearchError;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler({
    	InvalidCepException.class, 
    	CouldNotProcessInvalidArgumentException.class
    })
	public SearchError handleInvalidCep(Exception e) {
		SearchError response = new SearchError();
		response.setMessage(e.getMessage());
		
		return response;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  
    @ExceptionHandler({
    	NotFoundCepException.class,
    	CouldNotFoundAdressException.class
    })
	public SearchError handleNotFoundCep(Exception e) {
		SearchError response = new SearchError();
		response.setMessage(e.getMessage());
		
		return response;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ExceptionHandler({
    	CouldNotProcessException.class,
    	CouldNotConvertException.class
    })
	public SearchError handleInternalError() {
		SearchError response = new SearchError();
		response.setMessage(String.format("Erro ao realizar o processamento!"));
		
		return response;
	}
	
}