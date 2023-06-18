package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.Location;

import bg.dani02.taka.anticheat.CheatPlayer;

public class Tower {
	public static boolean onCheck(CheatPlayer p) {
		// if (p.haveBypass(HackType.WORLD_TOWER))
		// return false;
		// if (!Utils.isDetectionEnabled(HackType.WORLD_TOWER)
		// || !Utils.isDetectionEnabled(HackType.WORLD_TOWER, p))
		// return false;

		// Find closest block
		Location closest = null;
		for (int i = (int) p.getPlayer().getLocation().getY(); i > 0; i--) {
			Location blockLoc = p.getPlayer().getLocation().clone();
			blockLoc.setY(i);
			if (!blockLoc.getBlock().isEmpty()) {
				closest = blockLoc;
				break;
			}
		}

		// p.getPlayer().sendMessage("DIST FROM BLOCK: " +
		// p.getPlayer().getLocation().distance(closest));

		// Improved InvalidFall(Gravity) - AHHH Check for better SlimeJump hack + Check
		// that fucking Tower hack

		return false;
	}
}