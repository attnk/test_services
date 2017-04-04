package exercise.repository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.entity.CepDetails;

@Repository
public class CepServiceRepository {

	public CepServiceRepository() {
		FixtureFactoryLoader.loadTemplates("exercise.repository.factory.template");
	}
	
	public CepDetails getDetails(String cep) {
		CepDetails cepDetails = search(cep);
		
		return cepDetails;
	}

	/**
	 * Método mock para busca do CEP
	 * @param cep
	 * @return {@link CepDetails}
	 */
	private CepDetails search(String cep) {
		List<CepDetails> cepList = Fixture.from(CepDetails.class)
				.gimme(3, Arrays.asList("paulista", "candido-de-abreu", "estadio-maracana"));
		
		try {
			return cepList.stream().filter(cepDetails -> cep.equals(cepDetails.getCep())).findFirst().get();
		} catch (NullPointerException | NoSuchElementException e) {
			return null;
		}
	}
	
}
