package bg.dani02.taka.anticheat.enums;

public enum Numbers {
	ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

	public static String[] getName(int i) {
		switch (i) {
		case 0:
			return new String[] { "XXXX", "X__X", "X__X", "X__X", "XXXX" };
		case 1:
			return new String[] { "___X", "___X", "___X", "___X", "___X" };
		case 2:
			return new String[] { "XXXX", "___X", "XXXX", "X___", "XXXX" };
		case 3:
			return new String[] { "XXXX", "___X", "XXXX", "___X", "XXXX" };
		case 4:
			return new String[] { "X__X", "X__X", "XXXX", "___X", "___X" };
		case 5:
			return new String[] { "XXXX", "X___", "XXXX", "___X", "XXXX" };
		case 6:
			return new String[] { "XXXX", "X___", "XXXX", "X__X", "XXXX" };
		case 7:
			return new String[] { "XXXX", "X__X", "___X", "___X", "___X" };
		case 8:
			return new String[] { "XXXX", "X__X", "XXXX", "X__X", "XXXX" };
		case 9:
			return new String[] { "XXXX", "X__X", "XXXX", "___X", "XXXX" };
		default:
			break;
		}

		return null;
	}

	public static String[] getRandom() {
		int i1 = (int) (Math.random() * 9);
		int i2 = (int) (Math.random() * 9);
		int i3 = (int) (Math.random() * 9);
		int i4 = (int) (Math.random() * 9);

		StringBuilder full = new StringBuilder();

		for (short i = 0; i <= 4; i++) {
			full.append(getName(i1)[i]);
			full.append(" ");
			full.append(getName(i2)[i]);
			full.append(" ");
			full.append(getName(i3)[i]);
			full.append(" ");
			full.append(getName(i4)[i]);

			full.append("\n");
		}

		return new String[] { full.toString(),
				Integer.toString(i1) + Integer.toString(i2) + Integer.toString(i3) + Integer.toString(i4) };
	}
}
