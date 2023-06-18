package bg.dani02.taka.anticheat.checks.movement;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Vine;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

@SuppressWarnings("deprecation")
public class FastLadder {

	public static HashMap<String, Location> backLoc = new HashMap<String, Location>();

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkPlayer(p))
			return false;
		
		if (p.getPlayer().isFlying() || from.getY() >= to.getY() || p.getPlayer().isInsideVehicle() ||
				System.currentTimeMillis() - p.getLastDamageTime() < 500) {
			p.removeViolation(HackType.MOVING_FASTLADDER_NON_INSTANT, ViolationType.CANCELMOVE, (short) 1);
			backLoc.remove(p.getPlayer().getName());
			return false;
		}

		if (Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())) {
			if (p.getPlayer().getLocation().getBlock().getType().equals(Material.VINE)) {
				Vine va = (Vine) p.getPlayer().getLocation().getBlock().getState().getData();
				if (!va.isOnFace(BlockFace.SOUTH) && !va.isOnFace(BlockFace.NORTH) && !va.isOnFace(BlockFace.EAST)
						&& !va.isOnFace(BlockFace.WEST)) {
					return false;
				}
			}
			
			if (!backLoc.containsKey(p.getPlayer().getName()))
				backLoc.put(p.getPlayer().getName(), p.getPlayer().getLocation());
			if (!Utils.isClimable(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())) {
				// WARNING: If the player have jump potion will be a problem
				if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP))
					return false;
				// First move
				if (to.getY() - from.getY() > 0.45) {
					// Stop instantly
					if(!Utils.checkModule(p, HackType.MOVING_FASTLADDER_INSTANT))
						return false;
					p.reportToAdmin(HackType.MOVING_FASTLADDER_INSTANT, "first_move yDiff: " + (to.getY() - from.getY()));
					if (backLoc.get(p.getPlayer().getName()) != null)
						p.teleport(backLoc.get(p.getPlayer().getName()), HackType.MOVING_FASTLADDER_INSTANT);
					else
						p.teleport(from, HackType.MOVING_FASTLADDER_INSTANT);
					backLoc.remove(p.getPlayer().getName(), HackType.MOVING_FASTLADDER_INSTANT);
					return true;
				}
			} else {
				if (to.getY() - from.getY() > 0.155) {
					if (to.getY() - from.getY() > 0.8) { // Stop instantly even if down block is ladder
						if(!Utils.checkModule(p, HackType.MOVING_FASTLADDER_INSTANT))
							return false;
						p.reportToAdmin(HackType.MOVING_FASTLADDER_INSTANT, "yDiff: " + (to.getY() - from.getY()));
						if (backLoc.get(p.getPlayer().getName()) != null)
							p.teleport(backLoc.get(p.getPlayer().getName()), HackType.MOVING_FASTLADDER_INSTANT);
						else
							p.teleport(from, HackType.MOVING_FASTLADDER_INSTANT);
						backLoc.remove(p.getPlayer().getName());
						return true;
					} else {
						if(!Utils.checkModule(p, HackType.MOVING_FASTLADDER_NON_INSTANT))
							return false;
						if (p.reportToAdmin(HackType.MOVING_FASTLADDER_NON_INSTANT, "yDiff: " + (to.getY() - from.getY()))) {
							if (backLoc.get(p.getPlayer().getName()) != null)
								p.teleport(backLoc.get(p.getPlayer().getName()), HackType.MOVING_FASTLADDER_NON_INSTANT);
							else
								p.teleport(from, HackType.MOVING_FASTLADDER_NON_INSTANT);
							backLoc.remove(p.getPlayer().getName());
							return true;
						}
					}
				}
			}
		} else {
			backLoc.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FASTLADDER_NON_INSTANT, ViolationType.CANCELMOVE, (short) 1);
		}

		return false;
	}
}
