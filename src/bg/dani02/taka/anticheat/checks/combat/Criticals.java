package bg.dani02.taka.anticheat.checks.combat;

import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class Criticals {
	// The conditions are from mc-wiki
	public static boolean onCheck(CheatPlayer damager, double dmg) {
		if(!Utils.checkModule(damager, HackType.COMBAT_CRITICALS))
			return false;
		
		// Utils.debugMessage("CRIT: " + dmg + " " + damager.getPlayer().getItemInHand().getType());
		if (Utils.getCritical(damager.getPlayer()) == dmg) {
			// Conditions
			if ((damager.getPlayer().getVelocity().getY() < -0.07 && damager.getPlayer().getVelocity().getY() > -0.08 &&
					damager.getPlayer().getVelocity().getY() != -0.07544406518948656)
					|| damager.getPlayer().getVelocity().getY() > 0
					|| Utils.isOnLadder2(damager.getPlayer().getLocation())
					|| Utils.isAroundLiquidLoc(damager, damager.getPlayer().getLocation()) || damager.getPlayer().isInsideVehicle()
					|| Utils.isUsingPotionEffect(damager.getPlayer(), PotionEffectType.BLINDNESS)
					|| damager.isSprinting()) // Should be damager.getPlayer() shitty combat mechanics... xd
				if (damager.reportToAdmin(HackType.COMBAT_CRITICALS, "s: " + damager.isSprinting() + " vY: " + damager.getPlayer().getVelocity().getY()))
					return true;
		} else {
			damager.removeViolation(HackType.COMBAT_CRITICALS, ViolationType.CANCELMOVE, (short) 1);
		}

		return false;
	}
}
