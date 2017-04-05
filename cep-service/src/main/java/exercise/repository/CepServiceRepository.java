package exercise.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.entity.CepDetails;

/**
 * Repository "mocakdo"
 * @author Adriano Tanaka
 *
 */
@Repository
public class CepServiceRepository {

	public CepServiceRepository() {
		FixtureFactoryLoader.loadTemplates("exercise.repository.factory.template");
	}
	
	/**
	 * 
	 * @return {@link List} de todos os {@link CepDetails}
	 */
	public List<CepDetails> getAll() {
		return search();
	}

	/**
	 * Método mock para buscar CEP
	 * @param cep
	 * @return
	 */
	private List<CepDetails> search() {
		return Fixture.from(CepDetails.class)
				.gimme(3, Arrays.asList("paulista", "candido-de-abreu", "estadio-maracana"));
	}
	
}
