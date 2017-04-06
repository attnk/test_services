package exercise.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exercise.business.AddressBusiness;
import exercise.exception.CouldNotConvertException;
import exercise.exception.CouldNotFoundAdressException;
import exercise.exception.CouldNotProcessException;
import exercise.exception.CouldNotProcessInvalidArgumentException;
import exercise.exception.InvalidCepException;
import exercise.exception.NotFoundCepException;
import exercise.model.Address;
import exercise.model.ProcessMessage;

@RestController
@RequestMapping(value = "/address", produces = APPLICATION_JSON)
public class AddressController {
	
	@Autowired
	private AddressBusiness buisiness;
	
	@RequestMapping(value = "/find/{ID}", method = GET)
	public Address find(@PathVariable(name="ID") Long id) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotConvertException, 
			CouldNotFoundAdressException, 
			CouldNotProcessException {
		
		return buisiness.getAddress(id);
	}
	
	@RequestMapping(value = "/create", method = POST)
	public Address create(@RequestBody Address address) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotConvertException, 
			CouldNotFoundAdressException, 
			CouldNotProcessException, 
			InvalidCepException, 
			NotFoundCepException {
		
		return buisiness.persist(address, false);
	}
	
	@RequestMapping(value = "/update", method = POST)
	public Address update(@RequestBody Address address) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotConvertException, 
			CouldNotFoundAdressException, 
			CouldNotProcessException, 
			InvalidCepException, 
			NotFoundCepException {
		
		return buisiness.persist(address, true);
	}
	
	@RequestMapping(value = "/remove/{ID}", method = DELETE)
	public ProcessMessage remove(@PathVariable(name="ID") Long id) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotProcessException  {
		
		buisiness.delete(id);
		
		ProcessMessage message = new ProcessMessage();
		message.setMessage("Processado!");
		
		return message;
	}
}