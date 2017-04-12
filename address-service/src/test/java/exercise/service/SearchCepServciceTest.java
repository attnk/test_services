package exercise.service;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.exception.CouldNotSearchCepException;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;

@RunWith(MockitoJUnitRunner.class)
public class SearchCepServciceTest {
	
	private static final String HOST = "localhost";
	private static final String PORT = ":8080";
	private static final String CONTEXT = "/CEP/search/";

	@Mock
	private RestTemplate restTempalte;
	
	private SearchCepService service;
	
	@Before
	public void init() {
		FixtureFactoryLoader.loadTemplates("exercise.template.model");
		service = new SearchCepService();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "restTemplate", restTempalte);
	}
	
	@Test
	public void deveExecutarSemErroQuandoCepValidoAceitoPelosServico() 
			throws InvalidCepException, NotFoundCepException, CouldNotSearchCepException {
		// GIVEN
		String cep = "01311000";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri = new String("http://" + HOST + PORT + CONTEXT + cep);
		
        Address address = from(Address.class).gimme("paulista-sem-id");
        
        ResponseEntity<Address> responseEntity = new ResponseEntity<Address>(address, HttpStatus.OK);
        
		when(restTempalte.exchange(uri, HttpMethod.GET, entity, Address.class))
		.thenReturn(responseEntity);
		
		// WHEN
		service.searchCepDetails(cep);
		
		// THEN
		verify(restTempalte, times(1)).exchange(uri, HttpMethod.GET, entity, Address.class);
		verifyNoMoreInteractions(restTempalte);
	}
	
	@Test(expected = InvalidCepException.class)
	public void deveRetornarInvalidCepExceptionQuandoHttpStatusBadRequest() 
			throws InvalidCepException, NotFoundCepException, CouldNotSearchCepException {
		// GIVEN
		String cep = "01311000";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri = new String("http://" + HOST + PORT + CONTEXT + cep);
		
		when(restTempalte.exchange(uri, HttpMethod.GET, entity, Address.class))
		.thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		try {
			// WHEN
			service.searchCepDetails(cep);
		} catch (Exception e) {
			// THEN
			verify(restTempalte, times(1)).exchange(uri, HttpMethod.GET, entity, Address.class);
			verifyNoMoreInteractions(restTempalte);
			throw e;
		}
	}
	
	@Test(expected = NotFoundCepException.class)
	public void deveRetornarNotFoundCepExceptionQuandoHttpStatusNotFound() 
			throws InvalidCepException, NotFoundCepException, CouldNotSearchCepException {
		// GIVEN
		String cep = "01311000";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri = new String("http://" + HOST + PORT + CONTEXT + cep);
		
		when(restTempalte.exchange(uri, HttpMethod.GET, entity, Address.class))
		.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		try {
			// WHEN
			service.searchCepDetails(cep);
		} catch (Exception e) {
			// THEN
			verify(restTempalte, times(1)).exchange(uri, HttpMethod.GET, entity, Address.class);
			verifyNoMoreInteractions(restTempalte);
			throw e;
		}
	}
	
	@Test(expected = CouldNotSearchCepException.class)
	public void deveRetornarCouldNotSearchCepExceptionQuandoHttpStatusDiferenteDeBadRequestOuNotFound() 
			throws InvalidCepException, NotFoundCepException, CouldNotSearchCepException {
		// GIVEN
		String cep = "01311000";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String uri = new String("http://" + HOST + PORT + CONTEXT + cep);
		
		when(restTempalte.exchange(uri, HttpMethod.GET, entity, Address.class))
		.thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		try {
			// WHEN
			service.searchCepDetails(cep);
		} catch (Exception e) {
			// THEN
			verify(restTempalte, times(1)).exchange(uri, HttpMethod.GET, entity, Address.class);
			verifyNoMoreInteractions(restTempalte);
			throw e;
		}
	}
}
