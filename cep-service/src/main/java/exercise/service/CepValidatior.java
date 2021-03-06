package exercise.service;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;

@Component
public class CepValidatior {

	private static final String CEP_PATTERN = "\\d{8}"; // "\\d{5}-\\d{3}"
	
	/**
	 * 
	 * @param cep
	 * @throws IllegalArgumentException
	 * @return 
	 */
	public boolean isValid(String cep) {
		if(isNull(cep)) {
			throw new IllegalArgumentException("CEP não pode ser Nulo!!!");
		}
		
		return cep.matches(CEP_PATTERN);
	}

}
