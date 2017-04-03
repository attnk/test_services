package exercise.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exercise.model.Address;
import exercise.repository.CepServiceRepository;
import exercise.service.CepValidatior;

@Service
public class CepServiceBusiness {

	@Autowired
	private CepValidatior validator; 
	
	@Autowired
	private CepServiceRepository repository;
	
	public Address searchCepDetails(String cep) {
		// validar se o cep esta certo
		
		// caso não seja valido retornar erro - CEP inválido
		
		// caso esteja certo buscar os dados do CEP correspondente
		// caso esteja certo mas não encontre o CEP incluir 0 (zeros) a esquerda até encontrar (isso em qto houver itens)
		
		
		return null;
	}

}
