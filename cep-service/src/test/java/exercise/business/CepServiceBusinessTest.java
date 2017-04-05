package exercise.business;

import static java.util.Objects.nonNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.entity.CepDetails;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;
import exercise.repository.CepServiceRepository;
import exercise.service.CepValidatior;

@RunWith(MockitoJUnitRunner.class)
public class CepServiceBusinessTest {

	@Mock
	private CepValidatior validator;
	
	@Mock
	private CepServiceRepository repository;
	
	@InjectMocks
	private CepServiceBusiness business;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("exercise.template.cep");
	}
	
	@Test(expected = InvalidCepException.class)
	public void deveRetornarInvalidCepExceptionQuandoCepInvalido() throws InvalidCepException, NotFoundCepException {
		// GIVEN
		String cep = "a1c5d5d0";
		
		when(validator.isValid(cep)).thenReturn(false);
		
		try {
			// WHEN
			business.searchCepDetails(cep);
		} catch (Exception e) {
			// THEN - throw InvalidCepException
			verify(validator, times(1)).isValid(cep);
			verify(repository, never()).findAll();
			verifyNoMoreInteractions(validator, repository);
			
			throw e;
		}
	}

	@Test(expected = NotFoundCepException.class)
	public void deveRetornarNotFoundCepExceptionQuandoCepNaoEncontrado() throws InvalidCepException, NotFoundCepException {
		// GIVEN
		String cep = "04070000";
		
		when(validator.isValid(cep)).thenReturn(true);
		when(repository.findAll()).thenReturn(getTemplates());
		
		try {
			// WHEN
			business.searchCepDetails(cep);
		} catch (Exception e) {
			// THEN - throw NotFoundCepException
			verify(validator, times(1)).isValid(cep);
			verify(repository, times(1)).findAll();
			verifyNoMoreInteractions(validator, repository);
			
			throw e;
		}
	}
	
	@Test
	public void deveRetornarAddressQuandoREcebidoCepValidoEExistente() throws InvalidCepException, NotFoundCepException {
		// GIVEN
		String cep = "01311000";
		
		when(validator.isValid(cep)).thenReturn(true);
		when(repository.findAll()).thenReturn(getTemplates());
		
		// WHEN
		Address result = business.searchCepDetails(cep);
		
		// THEN - throw NotFoundCepException
		assertTrue(nonNull(result));
		
		verify(validator, times(1)).isValid(cep);
		verify(repository, times(1)).findAll();
		verifyNoMoreInteractions(validator, repository);
	}
	
	private List<CepDetails> getTemplates() {
		List<String> templateNames = Arrays.asList(
				"paulista", 
				"candido-de-abreu", 
				"estadio-maracana");
		
		return Fixture.from(CepDetails.class).gimme(3, templateNames);
	}
}
