package exercise.repository;

import static java.util.Objects.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import exercise.entity.CepDetails;

@RunWith(MockitoJUnitRunner.class)
public class CepServiceRepositoryServiceTest {

	private CepServiceRepository repository;
	
	@Before
	public void init() {
		repository = new CepServiceRepository();
	}
	
	@Test
	public void deveRetornarUmCepDetailsQuandoReceberUmCepExistente() {
		// GIVEN
		String cep = "01311000";
		
		// WHEN
		CepDetails result = repository.getDetails(cep);
		
		// THEN
		assertTrue(nonNull(result));
		assertEquals(cep, result.getCep());
	}
	
	@Test
	public void deveRetornarNullQuandoReceberUmCepInexistente() {
		// GIVEN
		String cep = "05001200";
		
		// WHEN
		CepDetails result = repository.getDetails(cep);
		
		// THEN
		assertTrue(Objects.isNull(result));
	}
}
