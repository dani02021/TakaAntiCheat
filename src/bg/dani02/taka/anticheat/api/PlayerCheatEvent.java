package bg.dani02.taka.anticheat.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import bg.dani02.taka.anticheat.enums.HackType;

public class PlayerCheatEvent extends Event implements Cancellable {
	private Player hacker;
	private HackType hack;
	private String message;
	private boolean cancel;
	
	private static final HandlerList hand = new HandlerList();

	public PlayerCheatEvent(Player p, HackType hack, String message) {
		hacker = p;
		this.hack = hack;
		this.message = message;
	}

	public Player getPlayer() {
		return hacker;
	}

	public HackType getHack() {
		return hack;
	}
	
	public String getMessage() {
		return message;
	}

	public short getViolation() {
		return API.getViolation(hacker, hack);
	}

	public short getMaxViolation() {
		return API.getMaxViolation(hack);
	}
	// TODO: Add setVL and setHack

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
}
