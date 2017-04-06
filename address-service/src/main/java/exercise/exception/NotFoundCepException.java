package exercise.exception;

public class NotFoundCepException extends Exception {

	private static final long serialVersionUID = 2092735549261324472L;

	public NotFoundCepException() {
		super();
	}

	public NotFoundCepException(String message) {
		super(message);
	}
	
}
