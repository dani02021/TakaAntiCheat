package bg.dani02.taka.anticheat.api;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBlockInteractEvent extends Event {
	private Player p;
	private Block block;
	private BlockFace face;
	private IteractionType iteract;
	
	private static final HandlerList hand = new HandlerList();

	public PlayerBlockInteractEvent(Player p, Block block, BlockFace face, IteractionType iteract) {
		this.p = p;
		this.iteract = iteract;
		this.block = block;
		this.face = face;
	}
	
	public Player getPlayer() {
		return p;
	}

	public IteractionType getIteraction() {
		return iteract;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public BlockFace getIteractFace() {
		return face;
	}

	@Override
	public HandlerList getHandlers() {
		return hand;
	}

	public static HandlerList getHandlerList() {
		return hand;
	}
	
	public enum IteractionType {
		START_DESTROY_BLOCK, STOP_DESTROY_BLOCK, ABORT_DESTROY_BLOCK
	}
}