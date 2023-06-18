package bg.dani02.taka.anticheat.exceptions;

public class InvalidServerVersionException extends TakaException {

	private static final long serialVersionUID = 1L;

	public InvalidServerVersionException() { }

	public InvalidServerVersionException(String message) {
		super(message);
	}
}