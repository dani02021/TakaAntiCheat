package bg.dani02.taka.anticheat.checks.combat;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class InvalidInteractionEntity {
	public static boolean onCheck(CheatPlayer damager, Entity attacked, DamageCause damageCause) {
		if(!Utils.checkModule(damager, HackType.COMBAT_INVALID_INTERACTION_ENTITY))
			return false;
		
		if (!damageCause.equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		if((damager.getPlayer().getOpenInventory().getType() != InventoryType.CRAFTING &&
		   damager.getPlayer().getOpenInventory().getType() != InventoryType.CREATIVE) ||
				attacked.equals(damager.getPlayer().getVehicle()) || maxDistance(damager, attacked)) {
			damager.reportToAdmin(HackType.COMBAT_INVALID_INTERACTION_ENTITY);
			return true;
		}
		
		return false;
	}
	
	static boolean maxDistance(CheatPlayer p, Entity attacked) {
		double dist = p.getPlayer().getEyeLocation().distance(attacked.getLocation());
		if(dist >= 8.0) {
			p.reportToAdmin(HackType.COMBAT_INVALID_INTERACTION_ENTITY, "d: " + dist);
			return true;
		}
		
		return false;
	}
}
