package bg.dani02.taka.anticheat.checks.combat;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class AutoSoup {
	public static void onCheck(CheatPlayer p, ItemStack currentItem, ItemStack clickedItem, SlotType type,
			ClickType cType, int slot) {
		if(!Utils.checkModule(p, HackType.COMBAT_AUTOSOUP))
			return;
		// The same algorithm can be used for AutoPotion
		if (slot >= 11 && slot <= 15)
			return;
		if(currentItem == null || clickedItem == null)
			return;
		
		if (currentItem.getType().equals(Material.AIR)) {
			if (clickedItem.getType().equals(XMaterial.MUSHROOM_STEW.parseMaterial()) && type.equals(SlotType.CONTAINER)) {
				p.setLastAutoSoupTime(System.currentTimeMillis());
				p.setLastAutoSoupLegal(true);
			}
		} else {
			if (type.equals(SlotType.QUICKBAR)) {
				if (currentItem.getType().equals(XMaterial.MUSHROOM_STEW.parseMaterial())) {
					p.setLastAutoSoupLegal(true);
				}
			}
		}
	}

	public static boolean onCheck1(CheatPlayer p, Action action, Material hand) {
		if (!Utils.isDetectionEnabled(HackType.COMBAT_AUTOSOUP)
				|| !Utils.isDetectionEnabled(HackType.COMBAT_AUTOSOUP, p))
			return false;
		if (p.haveBypass(HackType.COMBAT_AUTOSOUP))
			return false;

		if (action.name().contains("RIGHT") && p.getLastAutoSoupLegal()) {
			// Some stupid plugins make the people hand material bowl
			if (hand.equals(XMaterial.MUSHROOM_STEW.parseMaterial()) || hand.equals(Material.BOWL)) {
				p.setLastAutoSoupLegal(false);
				if (System.currentTimeMillis() - p.getLastAutoSoupTime() < 220) {
					p.reportToAdmin(HackType.COMBAT_AUTOSOUP);
					return true;
				}
			}
		}

		return false;
	}
}
