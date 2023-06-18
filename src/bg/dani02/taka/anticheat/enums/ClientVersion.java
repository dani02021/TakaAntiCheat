package bg.dani02.taka.anticheat.enums;

public enum ClientVersion {
	v1_7_X(-1),
	v1_8_X(1),
	v1_9_X(2),
	v1_10_X(3),
	v1_11_X(4),
	v1_12_X(5),
	v1_13_X(6),
	v1_14_X(7),
	v1_15_X(8),
	v1_16_X(9),
	v1_17_X(10),
	UNKNOWN(0);
	
	short ver;
	ClientVersion(int ver) {
		this.ver = (short) ver;
	}
	
	public short getVersion() { return ver; }
	public boolean isBelow(ClientVersion b) {return this.getVersion() < b.getVersion(); }
	public boolean isAbove(ClientVersion b) {return this.getVersion() > b.getVersion(); }
}