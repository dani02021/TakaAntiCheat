package bg.dani02.taka.anticheat.enums;

public enum LogMessage {
	VERBOSE, DEBUG, ERROR;

	public static String getName(LogMessage lm) {
		if (lm.equals(VERBOSE)) {
			return "&cVERBOSE: ";
		} else if (lm.equals(ERROR)) {
			return "&cERROR: ";
		} else if (lm.equals(DEBUG)) {
			return "&7DEBUG: ";
		}

		return null;
	}
}
