package exercise.builder;

import static java.util.Objects.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.entity.AddressEntity;
import exercise.entity.CepDetails;
import exercise.exception.BuilderExcepiotn;
import exercise.model.Address;

@RunWith(MockitoJUnitRunner.class)
public class AddressModelBuilderTest {

	private AddressModelBuilder builder;
	
	@Before
	public void init() {
		FixtureFactoryLoader.loadTemplates("exercise.template.entity");
		FixtureFactoryLoader.loadTemplates("exercise.template.model");
		builder = new AddressModelBuilder();
	}
	
	@Test
	public void deveConverterAddressEntityEmAddress() throws BuilderExcepiotn {
		// GIVEN
		AddressEntity entity = Fixture.from(AddressEntity.class).gimme("address-paulista");
		CepDetails cepDetails = entity.getCep();
		
		// WHEN
		Address result = builder.buildModel(entity);
		
		// THEN
		assertTrue(nonNull(result));
		assertEquals(entity.getId(), result.getId());
		assertEquals(cepDetails.getStreet(), result.getStreet());
		assertEquals(cepDetails.getState(), result.getState());
		assertEquals(cepDetails.getDistrict(), result.getDistrict());
		assertEquals(cepDetails.getCity(), result.getCity());
		assertEquals(cepDetails.getCep(), result.getCep());
		assertEquals(entity.getNumber(), result.getNumber());
		assertEquals(entity.getComplement(), result.getComplement());
	}
	
	@Test(expected = BuilderExcepiotn.class)
	public void deveRetornarBuilderExceptionQuandoAddressEntityForNula() throws BuilderExcepiotn {
		// GIVEN
		AddressEntity entity = null;
		
		// WHEN
		builder.buildModel(entity);
		
		// THEN - Throw BuilderExcepiotn
	}
	
	@Test
	public void deveConverterAddressEmAddressEntityComId() throws BuilderExcepiotn {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		
		// WHEN
		AddressEntity result = builder.buildEntity(address, cepDetails);
		
		// THEN
		assertTrue(nonNull(result));
		assertEquals(address.getId(), result.getId());
		assertEquals(cepDetails, result.getCep());
		assertEquals(address.getComplement(), result.getComplement());
		assertEquals(address.getNumber(), result.getNumber());
	}
	
	@Test
	public void deveConverterAddressEmAddressEntitySemId() throws BuilderExcepiotn {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-sem-id");
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		
		// WHEN
		AddressEntity result = builder.buildEntity(address, cepDetails);
		
		// THEN
		assertTrue(nonNull(result));
		assertEquals(address.getId(), result.getId());
		assertEquals(cepDetails, result.getCep());
		assertEquals(address.getComplement(), result.getComplement());
		assertEquals(address.getNumber(), result.getNumber());
	}
	
	@Test(expected = BuilderExcepiotn.class)
	public void deveRetornarBuilderExceptionQuandoModeloAddressNulo() throws BuilderExcepiotn {
		// GIVEN
		Address address = null;
		CepDetails cepDetails = Fixture.from(CepDetails.class).gimme("paulista");
		
		// WHEN
		builder.buildEntity(address, cepDetails);
		
		// THEN - Throw BuilderException
	}
	
	@Test(expected = BuilderExcepiotn.class)
	public void deveRetornarBuilderExceptionQuandoEntidadeCepDetailsNulo() throws BuilderExcepiotn {
		// GIVEN
		Address address = Fixture.from(Address.class).gimme("paulista-com-id");
		CepDetails cepDetails = null;
		
		// WHEN
		builder.buildEntity(address, cepDetails);
		
		// THEN - Throw BuilderException
	}
}
