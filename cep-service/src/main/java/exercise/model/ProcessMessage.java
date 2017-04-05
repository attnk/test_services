package exercise.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessMessage implements Serializable {

	private static final long serialVersionUID = 6041067810602643807L;
	
	@JsonProperty("MENSAGEM")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
