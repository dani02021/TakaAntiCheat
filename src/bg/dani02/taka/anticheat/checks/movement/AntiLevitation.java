package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class AntiLevitation {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_ANTI_LEVITATION))
			return false;
		
		if (Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.3, 0),
				0.3) != null) {
			p.removeViolation(HackType.MOVING_ANTI_LEVITATION, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		
		// Lag
		if(Utils.getTPS() < 15.00)
			return false;
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION)) {
				if ((to.getY() < from.getY() && Math.abs(p.getLastFromToLocations()[1].getY() - p.getLastFromToLocations()[0].getY()) <= Math.abs(to.getY() - from.getY())) ||
						to.getY() == from.getY()) {
					if (p.reportToAdmin(HackType.MOVING_ANTI_LEVITATION)) {
						p.teleportToGround(HackType.MOVING_ANTI_LEVITATION);
						return true;
					}
				} else {
					p.removeViolation(HackType.MOVING_ANTI_LEVITATION, ViolationType.CANCELMOVE, (short) 1);
				}
			}
		}

		return false;
	}
}
