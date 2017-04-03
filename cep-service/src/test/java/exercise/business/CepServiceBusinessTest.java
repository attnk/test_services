package exercise.business;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import exercise.repository.CepServiceRepository;
import exercise.service.CepValidatior;

public class CepServiceBusinessTest {

	@Mock
	private CepValidatior validator;
	
	@Mock
	private CepServiceRepository repository;
	
	@InjectMocks
	private CepServiceBusiness business;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(business);
	}
	
}
