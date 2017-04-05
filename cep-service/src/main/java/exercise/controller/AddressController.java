package exercise.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.ws.rs.DELETE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import exercise.business.AddressBusiness;
import exercise.exception.CouldNotConvertException;
import exercise.exception.CouldNotFoundAdressException;
import exercise.exception.CouldNotProcessException;
import exercise.exception.CouldNotProcessInvalidArgumentException;
import exercise.model.Address;

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
	
	// POST
	
	// UPDATE
	
	@RequestMapping(value = "/remove/{ID}", method = DELETE)
	public void remove(@PathVariable(name="ID") Long id) 
			throws CouldNotProcessInvalidArgumentException, 
			CouldNotProcessException  {
		
		buisiness.delete(id);
	}
}
