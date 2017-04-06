package exercise.service;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	private RestTemplate rt;
	
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
        	rt.exchange(uri, HttpMethod.GET, entity, Address.class);
		} catch (HttpClientErrorException e) {
			if(e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
				throw new InvalidCepException(e.getMessage());
			} else if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundCepException(e.getMessage());
			} else {
				throw new CouldNotSearchCepException(e);
			}
		}
	}
	
}
