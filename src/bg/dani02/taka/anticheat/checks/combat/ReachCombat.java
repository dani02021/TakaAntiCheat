package bg.dani02.taka.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class ReachCombat {
	// Max 4.5m disstance WITHOUT LAG on same y :( CREATIVE
	// Max 3.4m disstance WITHOUT LAG on samy y :( SURVIVAL
	public static boolean onCheck(CheatPlayer damager, Entity attacked, DamageCause damageCause) {
		if(!Utils.checkModule(damager, HackType.COMBAT_REACH))
			return false;
		
		if (!damageCause.equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		// Some plugins interfere with the check, like mcMMO - sword skill
		if(damageCause == DamageCause.CUSTOM)
			return false;
		// mcMMO Sword skill
		if(System.currentTimeMillis() - damager.getLastmcMMOFakeDamageEntityTime() < 2000)
			return false;

		// Some special entities have problems, its better to dont check them
		if (attacked.getType().equals(EntityType.SLIME) || attacked.getType().equals(EntityType.GUARDIAN)
				|| attacked.getType().equals(EntityType.MAGMA_CUBE))
			return false;
		if (Utils.getServerVersion() >= 2)
			if (attacked.getType().name().contains("ELDER_GUARDIAN")) // Fuck md_5...again...didn't add the mob back in 1.8, added in fricking 1.11 are you serious???
				return false;
		if (Utils.getServerVersion() >= 6)
			if (attacked.getType().equals(EntityType.TURTLE))
				return false;
		if(attacked.getType().equals(EntityType.GIANT) || attacked.getType().equals(EntityType.ENDER_DRAGON) || attacked.getType().equals(EntityType.WITHER))
			return false;

		Location loc1 = attacked.getLocation();
		loc1.setY(0);
		Location loc2 = damager.getPlayer().getLocation();
		loc2.setY(0);
		double dist = loc1.distance(loc2);

		if (damager.getPlayer().getGameMode().equals(GameMode.SURVIVAL)
				|| damager.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
			// Improve the algorithm
			if (dist > Utils.getLaggReachDisstance(attacked, 3.5,
					damager.isSprinting())) {
				damager.reportToAdmin(HackType.COMBAT_REACH, "dist: " + dist + " lagReachDist: " + Utils.getLaggReachDisstance(attacked, 3.5,
						damager.isSprinting()));
				return true;
			}
		} else if (damager.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			// Improve the algorithm
			if (dist > Utils.getLaggReachDisstance(attacked, 4.6, damager.isSprinting())) {
				damager.reportToAdmin(HackType.COMBAT_REACH, "dist: " + dist + " lagReachDist: " + Utils.getLaggReachDisstance(attacked, 4.6,
						damager.isSprinting()));
				return true;
			}
		}
		return false;
	}
}
