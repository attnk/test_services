package exercise.exception;

public class InvalidCepException extends Exception {

	private static final long serialVersionUID = -3759662972371833523L;

	public InvalidCepException() {
		super();
	}
	
	public InvalidCepException(String message) {
		super(message);
	}
	
}
