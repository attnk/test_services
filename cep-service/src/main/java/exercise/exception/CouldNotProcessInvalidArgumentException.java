package exercise.exception;

public class CouldNotProcessInvalidArgumentException extends Exception {

	private static final long serialVersionUID = 3643646502734540303L;

	public CouldNotProcessInvalidArgumentException() {
		super();
	}

	public CouldNotProcessInvalidArgumentException(String message) {
		super(message);
	}
	
}
