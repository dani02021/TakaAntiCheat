package bg.dani02.taka.anticheat.cache;

public class CacheHolderEntry {
	private String identificator;
	private Object result;
	private long worldTicks;

	public CacheHolderEntry(String identificator, Object result, long worldTicks) {
		this.identificator = identificator;
		this.result = result;
		this.worldTicks = worldTicks;
	}

	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public long getWorldTicks() {
		return worldTicks;
	}

	public void setWorldTicks(long worldTicks) {
		this.worldTicks = worldTicks;
	}
}
