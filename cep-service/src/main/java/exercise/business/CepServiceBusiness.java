package exercise.business;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exercise.entity.CepDetails;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;
import exercise.repository.CepServiceRepository;
import exercise.service.CepValidatior;

@Service
public class CepServiceBusiness {

	@Autowired
	private CepValidatior validator; 
	
	@Autowired
	private CepServiceRepository repository;
	
	/**
	 * 
	 * @param cep
	 * @return {@link Address} contendo os dados referente ao CEP informado
	 * @throws InvalidCepException Quando os dados de CEP informado não possuem 
	 * o formato correto.
	 * @throws NotFoundCepException Quando não é encontrado os dados referente ao CEP
	 * informado.
	 */
	public Address searchCepDetails(String cep) throws InvalidCepException, NotFoundCepException {

		if(validator.isValid(cep)) {
			List<CepDetails> cepList = repository.getAll();
			
			return getValidAddress(cep, cepList);
		} else {
			throw new InvalidCepException("CEP inválido");
		}
		
	}

	private Address getValidAddress(String cep, List<CepDetails> cepList) throws NotFoundCepException {
		Address address = findAddresss(cep, cepList);
		
		if(Objects.isNull(address)) {
			throw new NotFoundCepException("CEP inválido");
		}
		
		return address;
	}

	private Address findAddresss(String cep, List<CepDetails> cepList) {
		CepDetails cepDetailsAux;
		char[] cepAux = cep.toCharArray();
		Address address = new Address();
		
		for(int i = cep.length(); i > 0; i--) {
			// caso esteja certo buscar os dados do CEP correspondente
			cepDetailsAux = find(new String(cepAux), cepList);
			
			if (Objects.isNull(cepDetailsAux)) {
				// caso esteja certo mas não encontre o CEP incluir 0 (zeros) a esquerda até encontrar (isso em qto houver itens)
				cepAux[i-1] = '0';
			} else {
				address.setStreet(cepDetailsAux.getStreet());
				address.setDistrict(cepDetailsAux.getDistrict());
				address.setCity(cepDetailsAux.getCity());
				address.setState(cepDetailsAux.getState());
				break;
			}
		}
		
		if(isNull(address.getStreet()) || isNull(address.getDistrict()) || isNull(address.getCity()) || isNull(address.getState())) 
			address = null;
		
		return address;
	}
	
	private CepDetails find(String cep, List<CepDetails> cepList) {
		try {
			return cepList.stream().filter(cepDetails -> cep.equals(cepDetails.getCep())).findFirst().get();
		} catch (NullPointerException | NoSuchElementException e) {
			return null;
		}
	}

}
