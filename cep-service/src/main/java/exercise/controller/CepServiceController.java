package exercise.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Address;

@RestController
@RequestMapping(value = "/CEP/search", produces = APPLICATION_JSON)
public class CepServiceController {
	
	public Address search(String cep) {
		return null;
	}

}
