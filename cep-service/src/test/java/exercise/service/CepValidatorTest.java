package exercise.service;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CepValidatorTest {

	private CepValidatior validator;

	@Before
	public void init(){
		validator = new CepValidatior();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveRetornarIllegalArgumentExceptionQuandoCepEstaNNulo() {
		// GIVEN
		String cep = null;
		
		// WWHEN
		validator.isValid(cep);
		
		// THEN - Throw IllegalArgumentException
	}
	
	@Test
	public void deveRetornarFalseQuandoCepInvalidoDevidoTamanho() {
		// GIVEN
		String cep = "1234567890";
		
		// WHEN
		boolean result = validator.isValid(cep);
		
		// THEN
		assertFalse(result);
	}
	
	@Test
	public void deveRetornarFalseQuandoCepInvalidoPorNaoConterSomenteDigitos() {
		// GIVEN
		String cep = "61v5*#8@";
		
		// WHEN
		boolean result = validator.isValid(cep);
		
		// THEN
		assertFalse(result);
	}
	
	@Test
	public void deveRetornarTrueQuandoCepValido() {
		// GIVEN
		String cep = "123456789";
		
		// WHEN
		boolean result = validator.isValid(cep);
		
		// THEN
		assertFalse(result);		
	}
	
}
