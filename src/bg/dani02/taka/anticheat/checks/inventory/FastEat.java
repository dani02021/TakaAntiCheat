package bg.dani02.taka.anticheat.checks.inventory;

import org.bukkit.inventory.ItemStack;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FastEat {
	public static boolean onCheck(CheatPlayer p, ItemStack consumable) {
		if(!Utils.checkModule(p, HackType.INVENTORY_FAST_EAT))
			return false;
		if(consumable.getType().name().equalsIgnoreCase("DRIED_KELP"))
			return false;
		if(Utils.isFood(consumable.getType())) {
			if (System.currentTimeMillis() - p.getLastFoodEatStartTime() < 1100) {
				p.reportToAdmin(HackType.INVENTORY_FAST_EAT, "food t: " + (System.currentTimeMillis() - p.getLastFoodEatStartTime()));
				return true;
			}
		} else if(consumable.getType().equals(XMaterial.POTION.parseMaterial())) {
			if (System.currentTimeMillis() - p.setLastPotionConsumeStartTime() < 1100) {
				p.reportToAdmin(HackType.INVENTORY_FAST_EAT, "potion t: " + (System.currentTimeMillis() - p.getLastFoodEatStartTime()));
				return true;
			}
		}

		return false;
	}
}
