package bg.dani02.taka.anticheat.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;

public class CacheSystem {
	/*
	 * Some of the utilities are used too much, it is unnecessary to check for example, isAroundLiquidLoc2 3-4 times,
	 * when the player is still on the same block!
	 * 
	 * 
	 * Purge the cache if too much instances occur
	 */

	private final static short TICKS_DIFFERENCE = 15;
	private final static short MAX_SIZE = 150;
	
	private static ConcurrentHashMap<CheatPlayer, CacheHolder> cache = new ConcurrentHashMap<CheatPlayer, CacheHolder>();
	
	public static void addToCache(CheatPlayer p, CacheCheck check, long worldTime, Collection<?> values, boolean result) {
		checkCacheSize();
		
		String identificator = generateIdentificator(values);
		
		if(cache.get(p) == null) {
			cache.put(p, new CacheHolder(check, new CacheHolderEntry(identificator, result, worldTime)));
		} else {
			if(cache.get(p).getHolders(check) == null)
				cache.get(p).addHolder(check);
			
			cache.get(p).addEntry(check, new CacheHolderEntry(identificator, result, worldTime));
		}
	}
	
	public static byte isCached(CheatPlayer p, CacheCheck check, long worldTime, Collection<?> values) {
		String identificator = generateIdentificator(values);
		
		CacheHolderEntry che = null;
		
		try {
			che = cache.get(p).getEntry(check, identificator);
		} catch (NullPointerException ignored) { }
		
		if(che == null)
			return -1;
		
		if(che.getWorldTicks() > TICKS_DIFFERENCE)
			return -1;
		
		return (byte) ((boolean) che.getResult() ? 1 : 0);
	}
	
	/**
	 * Checks how many players are cached at the same time
	 */
	private static void checkCacheSize() {
		if(cache.size() >= MAX_SIZE)
			cache.clear();
	}
	
	/**
	 * Free cache space for unneeded players
	 * @param p
	 */
	public static void removeCache(CheatPlayer p) {
		cache.remove(p);
	}
	
	/**
	 * Utility, which generates identificator based on the passed values
	 * @param values
	 * @return
	 */
	public static String generateIdentificator(Collection<?> values) {
		StringBuilder str = new StringBuilder();
		
		for(Object o : values)
			str.append(o.toString() + ":");
		
		return str.toString();
	}
}
