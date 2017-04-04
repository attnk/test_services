package exercise.repository;

import static java.util.Objects.nonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
	public void deveRetornarUmaListaDeCep() {
		// GIVEN
		List<CepDetails> result;
		
		// WHEN
		result = repository.getAll();
		
		// THEN
		assertTrue(nonNull(result));
		assertFalse(result.isEmpty());
	}

}
