package exercise.model;

import static java.util.Objects.isNull;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Address implements Serializable{
	
	private static final long serialVersionUID = 7076401315847366826L;
	
	@JsonProperty("RUA")
	private String street;
	
	@JsonProperty("BAIRRO")
	private String district;
	
	@JsonProperty("CIDADE")
	private String city;
	
	@JsonProperty("ESTADO")
	private String state;

	@JsonProperty("NUMERO")
	private Long number;
	
	@JsonProperty("COMPLEMENTO")
	private String complement;
	
	@JsonProperty("CEP")
	private String cep;
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public boolean isValidRequiredFields() {
		boolean result = true;
		
		if (isNull(this.cep) || isNull(this.street) || isNull(this.number) || isNull(this.city) || isNull(this.state)) {
			result = false;
		}
		return result;
	}
	
}
