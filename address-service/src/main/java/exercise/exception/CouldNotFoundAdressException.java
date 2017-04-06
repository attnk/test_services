package exercise.exception;

public class CouldNotFoundAdressException extends Exception {

	private static final long serialVersionUID = 2704915186189625817L;

	public CouldNotFoundAdressException() {
		super();
	}

	public CouldNotFoundAdressException(String message, Throwable cause) {
		super(message, cause);
	}

}
