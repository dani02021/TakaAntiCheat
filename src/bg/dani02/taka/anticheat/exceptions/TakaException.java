package bg.dani02.taka.anticheat.exceptions;

public class TakaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TakaException() { }

	public TakaException(String message) {
		super(message);
	}
}