package bg.dani02.taka.anticheat.cache;

import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.EvictingQueue;

public class CacheHolder {
	/*
	 * This class is holding all checks cache per player
	 * 
	 * IMPORTANT: In order to purge older unneded cache entries, this check is checking if the hashCode of the location
	 * is the same as the new cache enty, if it's not, then it deletes it.
	 * 
	 * All cashe entries identificators must start with the hashCode of the locatino in order to work !!!
	 */
	
	private final static byte MAX_ENTRIES = 10;
	private HashMap<CacheCheck, EvictingQueue<CacheHolderEntry>> holder = new HashMap<CacheCheck, EvictingQueue<CacheHolderEntry>>();
	
	public CacheHolder(CacheCheck check, CacheHolderEntry entry) {
		EvictingQueue<CacheHolderEntry> entries = EvictingQueue.create(MAX_ENTRIES);
		entries.add(entry);
		holder.put(check, entries);
	}
	
	public synchronized void addEntry(CacheCheck check, CacheHolderEntry entry) {
		holder.get(check).add(entry);
	}
	
	public synchronized void addHolder(CacheCheck check) {
		holder.put(check, EvictingQueue.create(MAX_ENTRIES));
	}
	
	public synchronized EvictingQueue<CacheHolderEntry> getHolders(CacheCheck check) {
		return holder.get(check);
	}
	
	/**
	 * Get entry by the identificator
	 * @param check
	 * @param identificator
	 * @return
	 */
	public synchronized CacheHolderEntry getEntry(CacheCheck check, String identificator) {
		Iterator<CacheHolderEntry> iterator = holder.get(check).iterator();
		
		while(iterator.hasNext()) {
			CacheHolderEntry entry = iterator.next();
			
			if(entry.getIdentificator().toLowerCase().equals(identificator.toLowerCase()))
				return entry;
		}
		
		return null;
	}
}
