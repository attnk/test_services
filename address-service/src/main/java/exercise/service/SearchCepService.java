package exercise.service;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import exercise.exception.CouldNotSearchCepException;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;

@Component
public class SearchCepService {

	private static final String HOST = "localhost";
	private static final String PORT = ":8080";
	private static final String CONTEXT = "/CEP/search/";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 
	 * @param cep
	 * @throws InvalidCepException
	 * @throws NotFoundCepException 
	 * @throws CouldNotSearchCepException 
	 */
	public void searchCepDetails(String cep) 
			throws InvalidCepException, NotFoundCepException, CouldNotSearchCepException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri = new String("http://" + HOST + PORT + CONTEXT + cep);
        
        try {
        	restTemplate.exchange(uri, GET, entity, Address.class);
		} catch (HttpClientErrorException e) {
			if(e.getRawStatusCode() == BAD_REQUEST.value()) {
				throw new InvalidCepException(e.getResponseBodyAsString());
				
			} else if(e.getRawStatusCode() == NOT_FOUND.value()) {
				throw new NotFoundCepException(e.getResponseBodyAsString());
				
			} else {
				throw new CouldNotSearchCepException(e);
			}
		}
	}
	
}
