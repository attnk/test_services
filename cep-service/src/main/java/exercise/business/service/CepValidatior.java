package exercise.business.service;

import java.util.Objects;

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
		if(Objects.isNull(cep)) {
			throw new IllegalArgumentException("CEP não pode ser Nulo!!!");
		}
		
		return cep.matches(CEP_PATTERN);
	}

}
