package exercise;

import static java.util.Objects.nonNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.NoResultException;

import org.hibernate.NonUniqueResultException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.builder.AddressModelBuilder;
import exercise.business.AddressBusiness;
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

@RunWith(MockitoJUnitRunner.class)
public class AddressBusinessTest {
	
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private CepServiceRepository cepRepository;
	
	@Mock
	private AddressModelBuilder builder;
	
	@Mock
	private SearchCepService service;
	
	@InjectMocks
	private AddressBusiness business;
	
	@Before
	public void init() {
		FixtureFactoryLoader.loadTemplates("exercise.template.entity");
		FixtureFactoryLoader.loadTemplates("exercise.template.model");
		MockitoAnnotations.initMocks(this);
	}
	
	// -- GET --
	@Test
	public void deveRetornarAddressParaIdInformado() throws BuilderExcepiotn, 
															CouldNotProcessInvalidArgumentException, 
															CouldNotConvertException, 
															CouldNotFoundAdressException, 
															CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		
		when(addressRepository.findOne(addressId)).thenReturn(addressEntity);
		when(builder.buildModel(addressEntity)).thenReturn(address);
		
		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		// WHEN
		Address result = business.getAddress(addressId);
		
		// THEN
		assertTrue(nonNull(result));
		
		order.verify(addressRepository, times(1)).findOne(addressId);
		order.verify(builder, times(1)).buildModel(addressEntity);
		order.verifyNoMoreInteractions();
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarCouldNotProcessInvalidArgumentExceptionQuandoGetReceberIdNulo() 
			throws BuilderExcepiotn, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotFoundAdressException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = null;

		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		try {
			// WHEN
			business.getAddress(addressId);
		} catch (Exception e) {
			// THEN
			order.verify(addressRepository, never()).findOne(anyLong());
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotFoundAdressException.class)
	public void deveRetornarCouldNotFoundAdressExceptionQuandoAddressRepositoryThrowNonUniqueResultException() 
			throws BuilderExcepiotn, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotFoundAdressException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;

		when(addressRepository.findOne(addressId)).thenThrow(new NonUniqueResultException(2));
		
		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		try {
			// WHEN
			business.getAddress(addressId);
		} catch (Exception e) {
			// THEN
			order.verify(addressRepository, times(1)).findOne(addressId);
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotFoundAdressException.class)
	public void deveRetornarCouldNotFoundAdressExceptionQuandoAddressRepositoryThrowNoResultException() 
			throws BuilderExcepiotn, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotFoundAdressException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;

		when(addressRepository.findOne(addressId)).thenThrow(new NoResultException("ERRO"));
		
		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		try {
			// WHEN
			business.getAddress(addressId);
		} catch (Exception e) {
			// THEN
			order.verify(addressRepository, times(1)).findOne(addressId);
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessException.class)
	public void deveRetornarCouldNotFoundAdressExceptionQuandoOcorrerQualquerExceptionDiferenteDeBuilderExceptionOuNonUniqueResultExceptionOuNoResultException() 
			throws BuilderExcepiotn, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotFoundAdressException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;

		when(addressRepository.findOne(addressId)).thenThrow(new IllegalArgumentException("ERRO"));
		
		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		try {
			// WHEN
			business.getAddress(addressId);
		} catch (Exception e) {
			// THEN
			order.verify(addressRepository, times(1)).findOne(addressId);
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotConvertException.class)
	public void deveRetornarCouldNotConvertExceptionQuandoBuilderThrowBuilderException() 
			throws BuilderExcepiotn, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotFoundAdressException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		when(addressRepository.findOne(addressId)).thenReturn(addressEntity);
		when(builder.buildModel(addressEntity)).thenThrow(new BuilderExcepiotn());
		
		InOrder order = Mockito.inOrder(addressRepository, builder);
		
		try {
			// WHEN
			business.getAddress(addressId);
		} catch (Exception e) {
			// THEN
			order.verify(addressRepository, times(1)).findOne(addressId);
			order.verify(builder, times(1)).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}

	// -- PERSIST --
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoAddressNulo() throws Exception {
		// GIVEN
		Address address = null;
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoEstadoNula() throws Exception {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-estado");
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoCidadeNula() throws Exception {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-cidade");
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoNumeroNulo() throws Exception {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-numero");
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoRuaNula() throws Exception {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-rua");
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarInvalidCepExceptionQuandoCepNulo() throws Exception {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-cep");
		
		InOrder order = inOrder(cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw InvalidCepException
			order.verify(cepRepository, never()).findByCep(anyString());
			order.verify(builder, never()).buildEntity(any(Address.class), any(CepDetails.class));
			order.verify(addressRepository, never()).save(any(AddressEntity.class));
			order.verify(builder, never()).buildModel(any(AddressEntity.class));
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotConvertException.class)
	public void deveRetornarCouldNotConvertExceptionQuandoBuilderEntityJogarBuilderExcepiotn() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(address.getCep());
		when(cepRepository.findByCep(address.getCep())).thenReturn(cepDetails);
		when(builder.buildEntity(address, cepDetails)).thenThrow(new BuilderExcepiotn());
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw CouldNotConvertException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, times(1)).findByCep(address.getCep());
			order.verify(builder, times(1)).buildEntity(address, cepDetails);
			order.verify(addressRepository, never()).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	 }
	
	@Test(expected = CouldNotConvertException.class)
	public void deveRetornarCouldNotConvertExceptionQuandoBuilderModelJogarBuilderExcepiotn() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(address.getCep());
		when(cepRepository.findByCep(address.getCep())).thenReturn(cepDetails);
		when(builder.buildEntity(address, cepDetails)).thenReturn(addressEntity);
		when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
		when(builder.buildModel(addressEntity)).thenThrow(new BuilderExcepiotn());
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - Throw CouldNotConvertException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, times(1)).findByCep(address.getCep());
			order.verify(builder, times(1)).buildEntity(address, cepDetails);
			order.verify(addressRepository, times(1)).save(addressEntity);
			order.verify(builder, times(1)).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
		
	 }
	
	@Test(expected = InvalidCepException.class)
	public void deveRetornarInvalidCepExceptionQuandoServiceJogarInvalidCepException() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, 
					CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doThrow(new InvalidCepException()).when(service).searchCepDetails(address.getCep());
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - throw InvalidCepException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, never()).findByCep(address.getCep());
			order.verify(builder, never()).buildEntity(address, cepDetails);
			order.verify(addressRepository, never()).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	 }
	
	@Test(expected = NotFoundCepException.class)
	public void deveRetornarNotFoundCepExceptionQuandoServiceJogarNotFoundCepException() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, 
					CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doThrow(new NotFoundCepException()).when(service).searchCepDetails(address.getCep());
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - throw InvalidCepException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, never()).findByCep(address.getCep());
			order.verify(builder, never()).buildEntity(address, cepDetails);
			order.verify(addressRepository, never()).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	 }
	
	@Test(expected = CouldNotSearchCepException.class)
	public void deveRetornarCouldNotSearchCepExceptionQuandoServiceJogarCouldNotSearchCepException() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, 
					CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doThrow(new CouldNotSearchCepException()).when(service).searchCepDetails(address.getCep());
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - throw InvalidCepException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, never()).findByCep(address.getCep());
			order.verify(builder, never()).buildEntity(address, cepDetails);
			order.verify(addressRepository, never()).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	 }
	
	@Test(expected = CouldNotProcessException.class)
	public void deveRetornarCouldNotProcessExceptionQuandoOcorrerQualquerExceptionComCepRepository() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, 
					CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(address.getCep());
		when(cepRepository.findByCep(address.getCep())).thenThrow(new IllegalArgumentException("ERRO"));
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - throw CouldNotProcessException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, times(1)).findByCep(address.getCep());
			order.verify(builder, never()).buildEntity(address, cepDetails);
			order.verify(addressRepository, never()).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
		
	 }
	
	@Test(expected = CouldNotProcessException.class)
	public void deveRetornarCouldNotProcessExceptionQuandoAddressRepositoryJogarQualquerException() 
			throws InvalidCepException, 
					NotFoundCepException, 
					CouldNotProcessInvalidArgumentException, 
					CouldNotConvertException, 
					CouldNotProcessException, 
					BuilderExcepiotn, 
					CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(address.getCep());
		when(cepRepository.findByCep(address.getCep())).thenReturn(cepDetails);
		when(builder.buildEntity(address, cepDetails)).thenReturn(addressEntity);
		when(addressRepository.save(addressEntity)).thenThrow(new IllegalArgumentException("ERRO"));
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		try {
			// WHEN
			business.persist(address);
		} catch (Exception e) {
			// THEN - throw CouldNotProcessException
			order.verify(service, times(1)).searchCepDetails(address.getCep());
			order.verify(cepRepository, times(1)).findByCep(address.getCep());
			order.verify(builder, times(1)).buildEntity(address, cepDetails);
			order.verify(addressRepository, times(1)).save(addressEntity);
			order.verify(builder, never()).buildModel(addressEntity);
			order.verifyNoMoreInteractions();
			
			throw e;
		}
	 }
	
	// 		-- INSERT --
	@Test
	public void deveRealizarInsertQuandoAddressNaoPossuiId() throws BuilderExcepiotn, 
																	InvalidCepException, 
																	NotFoundCepException, 
																	CouldNotProcessInvalidArgumentException, 
																	CouldNotConvertException, 
																	CouldNotProcessException, 
																	CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		Address addressWithoutId = Fixture.from(Address.class).gimme("paulista-sem-id");
		
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		
		AddressEntity addressEntityWithoutId = Fixture.from(AddressEntity.class).gimme("address-paulista-sem-id");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(addressWithoutId.getCep());
		when(cepRepository.findByCep(addressWithoutId.getCep())).thenReturn(cepDetails);
		when(builder.buildEntity(addressWithoutId, cepDetails)).thenReturn(addressEntityWithoutId);
		when(addressRepository.save(addressEntityWithoutId)).thenReturn(addressEntity);
		when(builder.buildModel(addressEntity)).thenReturn(address);
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		// WHEN
		Address result = business.persist(addressWithoutId);
		
		// THEN
		assertTrue(nonNull(result));
		
		order.verify(service, times(1)).searchCepDetails(addressWithoutId.getCep());
		order.verify(cepRepository, times(1)).findByCep(addressWithoutId.getCep());
		order.verify(builder, times(1)).buildEntity(addressWithoutId, cepDetails);
		order.verify(addressRepository, times(1)).save(addressEntityWithoutId);
		order.verify(builder, times(1)).buildModel(addressEntity);
		order.verifyNoMoreInteractions();
	}

	//		-- UPDATE --
	@Test
	public void deveRealizarUpdateQuandoAddressPossuiId() throws InvalidCepException, 
 																NotFoundCepException, 
 																CouldNotProcessInvalidArgumentException, 
 																CouldNotConvertException, 
 																CouldNotProcessException, 
 																BuilderExcepiotn, 
 																CouldNotSearchCepException {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		AddressEntity addressEntity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		
		doNothing().when(service).searchCepDetails(address.getCep());
		when(cepRepository.findByCep(address.getCep())).thenReturn(cepDetails);
		when(builder.buildEntity(address, cepDetails)).thenReturn(addressEntity);
		when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
		when(builder.buildModel(addressEntity)).thenReturn(address);
		
		InOrder order = inOrder(service, cepRepository, builder, addressRepository);
		
		// WHEN
		Address result = business.persist(address);
		
		// THEN
		assertTrue(nonNull(result));
		
		order.verify(service, times(1)).searchCepDetails(address.getCep());
		order.verify(cepRepository, times(1)).findByCep(address.getCep());
		order.verify(builder, times(1)).buildEntity(address, cepDetails);
		order.verify(addressRepository, times(1)).save(addressEntity);
		order.verify(builder, times(1)).buildModel(addressEntity);
		order.verifyNoMoreInteractions();
	 }
	
	// -- DELETE --
	@Test
	public void deveDeletarRegistroComIdInformado() throws CouldNotProcessInvalidArgumentException, 
															CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;
		
		doNothing().when(addressRepository).delete(addressId);
		
		/// WHEN
		business.delete(addressId);
		
		// THEN
		verify(addressRepository, times(1)).delete(addressId);
		verifyNoMoreInteractions(addressRepository);
	}
	
	@Test(expected = CouldNotProcessException.class)
	public void deveRetornarCouldNotProcessExceptionQuandoRepositoryDeleteJogarQualquerException() 
			throws CouldNotProcessInvalidArgumentException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = 1L;
		
		doThrow(new IllegalArgumentException()).when(addressRepository).delete(addressId);
		
		try {
			/// WHEN
			business.delete(addressId);
		} catch (Exception e) {
			// THEN
			verify(addressRepository, times(1)).delete(addressId);
			verifyNoMoreInteractions(addressRepository);
			
			throw e;
		}
	}
	
	@Test(expected = CouldNotProcessInvalidArgumentException.class)
	public void deveRetornarCouldNotProcessInvalidArgumentExceptionQuandoDeleteReceberIdNulo() 
			throws CouldNotProcessInvalidArgumentException, 
					CouldNotProcessException {
		// GIVEN
		Long addressId = null;
		
		try {
			/// WHEN
			business.delete(addressId);
		} catch (Exception e) {
			// THEN
			verify(addressRepository, never()).delete(anyLong());
			verifyNoMoreInteractions(addressRepository);
			
			throw e;
		}
	}

}
