package exercise.business;

import static java.util.Objects.isNull;

import java.util.Objects;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exercise.builder.AddressModelBuilder;
import exercise.entity.AddressEntity;
import exercise.entity.CepDetails;
import exercise.exception.BuilderExcepiotn;
import exercise.exception.CouldNotConvertException;
import exercise.exception.CouldNotFoundAdressException;
import exercise.exception.CouldNotProcessException;
import exercise.exception.CouldNotProcessInvalidArgumentException;
import exercise.exception.CouldNotSearchCepException;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;
import exercise.repository.AddressRepository;
import exercise.repository.CepServiceRepository;
import exercise.service.SearchCepService;

@Service
public class AddressBusiness {

	private static final Logger LOG = LogManager.getLogger(AddressBusiness.class);
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CepServiceRepository cepRepository;
	
	@Autowired
	private AddressModelBuilder builder;
	
	@Autowired
	private SearchCepService service;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws CouldNotProcessInvalidArgumentException
	 * @throws CouldNotConvertException
	 * @throws CouldNotFoundAdressException 
	 * @throws CouldNotProcessException 
	 */
	public Address getAddress(Long id) throws CouldNotProcessInvalidArgumentException, 
												CouldNotConvertException, 
												CouldNotFoundAdressException, 
												CouldNotProcessException {

		if(isNull(id)) {
			throw new CouldNotProcessInvalidArgumentException("Address ID não pode ser nulo!");
		}
		
		try {
			return builder.buildModel(addressRepository.findOne(id));
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
	
	/**
	 * 
	 * @param address
	 * @return
	 * @throws InvalidCepException
	 * @throws NotFoundCepException
	 * @throws CouldNotProcessInvalidArgumentException
	 * @throws CouldNotConvertException
	 * @throws CouldNotProcessException
	 * @throws CouldNotSearchCepException 
	 */
	public Address persist(Address address) throws InvalidCepException, 
													NotFoundCepException, 
													CouldNotProcessInvalidArgumentException, 
													CouldNotConvertException, 
													CouldNotProcessException, 
													CouldNotSearchCepException {
		
		validateParams(address);

		// valida o CEP
		service.searchCepDetails(address.getCep());
		
		try {
			CepDetails cepDetails = cepRepository.findByCep(address.getCep());
			AddressEntity entity = builder.buildEntity(address, cepDetails);
			
			return builder.buildModel(addressRepository.save(entity));
		} catch (BuilderExcepiotn e) {
			throw new CouldNotConvertException(e);
		} catch (Exception e) {
			LOG.error(e);
			throw new CouldNotProcessException(e);
		}
	}
	
	private void validateParams(Address address) throws CouldNotProcessInvalidArgumentException {
		if(isNull(address)) {
			throw new CouldNotProcessInvalidArgumentException("Address não pode ser nulo!");
		} else if(!address.isValidRequiredFields()) {
			throw new CouldNotProcessInvalidArgumentException("RUA, NÚMERO, CEP, CIDADE, ou ESTADO não pode ser nulo!");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @throws CouldNotProcessInvalidArgumentException
	 * @throws CouldNotProcessException 
	 */
	public void delete(Long id) throws CouldNotProcessInvalidArgumentException, 
										CouldNotProcessException {
		
		if(Objects.isNull(id)) {
			throw new CouldNotProcessInvalidArgumentException("Address ID não pode ser nulo!");
		}
		
		try {
			addressRepository.delete(id);
		} catch (Exception e) {
			LOG.error(e);
			throw new CouldNotProcessException(e);
		}
	}
	
}
