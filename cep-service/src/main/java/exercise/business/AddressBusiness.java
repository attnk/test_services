package exercise.business;

import java.util.Objects;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exercise.builder.AddressModelBuilder;
import exercise.exception.BuilderExcepiotn;
import exercise.exception.CouldNotConvertException;
import exercise.exception.CouldNotFoundAdressException;
import exercise.exception.CouldNotProcessException;
import exercise.exception.CouldNotProcessInvalidArgumentException;
import exercise.model.Address;
import exercise.repository.AddressRepository;

@Service
public class AddressBusiness {

	private static final Logger LOG = LogManager.getLogger(AddressBusiness.class);
	
	@Autowired
	private AddressRepository repository;
	
	@Autowired
	private AddressModelBuilder builder;
	
	@Autowired
	private CepServiceBusiness cepBusiness;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws CouldNotProcessInvalidArgumentException
	 * @throws CouldNotConvertException
	 * @throws CouldNotFoundAdressException 
	 * @throws CouldNotProcessException 
	 */
	public Address getAddress(Long id) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotConvertException, 
			CouldNotFoundAdressException, 
			CouldNotProcessException {

		if(Objects.isNull(id)) {
			throw new CouldNotProcessInvalidArgumentException("Address ID não pode ser nulo!");
		}
		
		try {
			return builder.buildModel(repository.findOne(id));
		} catch (BuilderExcepiotn e) {
			throw new CouldNotConvertException(e);
		} catch (NonUniqueResultException | NoResultException e) {
			throw new CouldNotFoundAdressException(
					String.format("Dados não encontrados para o ID: %d", id), e);
		} catch (Exception e) {
			LOG.error(e);
			throw new CouldNotProcessException(e);
		}
	}
	
	// POST - validar o CEP usando o business do serviço de Busca CEP
	
	
	// UPDATE - validar o CEP usando o business do serviço de Busca CEP
	
	
	/**
	 * 
	 * @param id
	 * @throws CouldNotProcessInvalidArgumentException
	 * @throws CouldNotProcessException 
	 */
	public void delete(Long id) 
			throws CouldNotProcessInvalidArgumentException, CouldNotProcessException {
		
		if(Objects.isNull(id)) {
			throw new CouldNotProcessInvalidArgumentException("Address ID não pode ser nulo!");
		}
		
		try {
			repository.delete(id);
		} catch (Exception e) {
			LOG.error(e);
			throw new CouldNotProcessException(e);
		}
	}
	
}
