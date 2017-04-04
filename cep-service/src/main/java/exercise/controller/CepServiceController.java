package exercise.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exercise.business.CepServiceBusiness;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;

@RestController
@RequestMapping(value = "/CEP", produces = APPLICATION_JSON)
public class CepServiceController {
	
	@Autowired
	private CepServiceBusiness business;
	
	@RequestMapping(value = "/search/{CEP}", method = GET)
	public Address search(@PathVariable String cep) throws InvalidCepException, NotFoundCepException {
		Address address = business.searchCepDetails(cep);
		return address;
	}

}
