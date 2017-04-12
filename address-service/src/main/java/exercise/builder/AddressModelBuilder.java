package exercise.builder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
		if(isNull(entity)) {
			throw new BuilderExcepiotn(
					new IllegalArgumentException("Parâmetro AddressEntity não pode ser nulo!"));
		}
		
		CepDetails cepEntity = entity.getCep();
		Address address = new Address();
		
		address.setId(entity.getId());
		address.setStreet(cepEntity.getStreet());
		address.setState(cepEntity.getState());
		address.setDistrict(cepEntity.getDistrict());
		address.setCity(cepEntity.getCity());
		address.setCep(cepEntity.getCep());
		address.setNumber(entity.getNumber());
		address.setComplement(entity.getComplement());
		
		return address;
	}

	/**
	 * Converte um modelo {@link Address} e um {@link CepDetails} 
	 * Sem uma entidade {@link AddressEntity}
	 * @param address
	 * @param cepDetails
	 * @return
	 * @throws BuilderExcepiotn
	 */
	public AddressEntity buildEntity(Address address, CepDetails cepDetails) throws BuilderExcepiotn {
		if(isNull(address) || isNull(cepDetails)) {
			throw new BuilderExcepiotn(
					new IllegalArgumentException("Parâmetro Address ou CepDetails não pode ser nulo!"));
		}
		
		AddressEntity entity = new AddressEntity();
		if(nonNull(address.getId())) {
			entity.setId(address.getId());
		}
		entity.setComplement(address.getComplement());
		entity.setCep(cepDetails);
		entity.setNumber(address.getNumber());
		
		return entity;
	}
	
}
