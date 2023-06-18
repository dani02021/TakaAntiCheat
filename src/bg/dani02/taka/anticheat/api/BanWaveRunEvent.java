package bg.dani02.taka.anticheat.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BanWaveRunEvent extends Event implements Cancellable {
	private boolean cancel;
	
	private static final HandlerList hand = new HandlerList();

	public BanWaveRunEvent() { }

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return hand;
	}

	public static HandlerList getHandlerList() {
		return hand;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancel = arg0;
	}

	public boolean containsPlayer(Player p) {
		return API.isPlayerInBanWave(p);
	}

	public void setPlayerInBanWave(Player p, boolean banned) {
		API.setPlayerInBanWave(p, banned);
	}
}
