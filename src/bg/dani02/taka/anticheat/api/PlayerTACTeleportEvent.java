package bg.dani02.taka.anticheat.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
// setTo func?
public class PlayerTACTeleportEvent extends Event implements Cancellable {
	private Player p;
	private Location from, to;
	private boolean cancel;
	
	private static final HandlerList hand = new HandlerList();

	public PlayerTACTeleportEvent(Player p, Location from, Location to) {
		this.p = p;
		this.from = from;
		this.to = to;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public Location getFrom() {
		return from;
	}
	
	public Location getTo() {
		return to;
	}

	@Override
	public HandlerList getHandlers() {
		return hand;
	}

	public static HandlerList getHandlerList() {
		return hand;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}