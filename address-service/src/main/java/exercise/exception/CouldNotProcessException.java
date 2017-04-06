package exercise.exception;

public class CouldNotProcessException extends Exception {

	private static final long serialVersionUID = 4998845042909791606L;

	public CouldNotProcessException() {
		super();
	}

	public CouldNotProcessException(Throwable cause) {
		super(cause);
	}

}
