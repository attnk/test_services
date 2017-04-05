package exercise.builder;

import java.util.Objects;

import org.springframework.stereotype.Component;

import exercise.entity.AddressEntity;
import exercise.entity.CepDetails;
import exercise.exception.BuilderExcepiotn;
import exercise.model.Address;

@Component
public class AddressModelBuilder {

	/**
	 * Converte uma entidade {@link AddressEntity} em um modelo de resposta
	 * {@link Address}
	 * @param entity
	 * @return
	 * @throws BuilderExcepiotn 
	 */
	public Address buildModel(AddressEntity entity) throws BuilderExcepiotn {
		if(Objects.isNull(entity)) {
			throw new BuilderExcepiotn(new IllegalArgumentException("Parâmetro AddressEntity não deve ser nulo!"));
		}
		
		CepDetails cepEntity = entity.getCep();
		Address address = new Address();
		
		address.setStreet(cepEntity.getCity());
		address.setState(cepEntity.getState());
		address.setDistrict(cepEntity.getDistrict());
		address.setCity(cepEntity.getCity());
		address.setCep(cepEntity.getCep());
		address.setNumber(entity.getNumber());
		address.setComplement(entity.getComplement());
		
		return address;
	}
	
}
