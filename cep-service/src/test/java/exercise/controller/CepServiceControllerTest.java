package exercise.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import exercise.business.CepServiceBusiness;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;

@RunWith(SpringRunner.class)
@WebMvcTest(CepServiceController.class)
public class CepServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CepServiceBusiness business;
	
	@Before
	public void init() {
		FixtureFactoryLoader.loadTemplates("exercise.template.address");
	}
	
	@Test
	public void deveRetornarMensagemDeErroComHttpStatusBadRequestQuandoRetornarInvalidCepException() 
			throws Exception {
		when(business.searchCepDetails(anyString())).thenThrow(new InvalidCepException("ERRO"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/CEP/search/{CEP}", "01311000"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void deveRetornarMensagemDeErroComHttpStatusNotFoundQuandoRetornarNotFoundCepException() 
			throws Exception {
		when(business.searchCepDetails(anyString())).thenThrow(new NotFoundCepException("ERRO"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/CEP/search/{CEP}", "01311000"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void deveRetornarOsDadosDeEnderecoParaOcepInformado() throws Exception {
		when(business.searchCepDetails(anyString())).thenReturn(Fixture.from(Address.class).gimme("paulista"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/CEP/search/{CEP}", "01311000"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
}
