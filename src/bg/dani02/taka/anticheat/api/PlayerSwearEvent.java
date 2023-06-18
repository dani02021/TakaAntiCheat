package bg.dani02.taka.anticheat.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSwearEvent extends Event implements Cancellable {
	private Player hacker;
	private String msg;
	private boolean hide;
	private boolean cancel;
	
	private static final HandlerList hand = new HandlerList();
	
	public PlayerSwearEvent(Player p, String msg, boolean hide) {
		hacker = p;
		this.msg = msg;
		this.hide = hide;
	}
	
	public Player getPlayer() {
		return hacker;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
	
	public boolean isHidden() {
		return hide;
	}
	
	public void setHidden(boolean hide) {
		this.hide = hide;
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
