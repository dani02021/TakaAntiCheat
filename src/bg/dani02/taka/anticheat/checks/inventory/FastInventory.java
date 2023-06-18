package bg.dani02.taka.anticheat.checks.inventory;

import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class FastInventory {
	public static boolean onCheck(CheatPlayer p, ItemStack item, SlotType type) {
		if(!Utils.checkModule(p, HackType.INVENTORY_FAST_INVENTORY))
			return false;
		if (System.currentTimeMillis() - p.getLastFAEBlockTime() < 2000) {
			p.setLastFAEBlockTime(System.currentTimeMillis());
			p.getPlayer().getWorld().dropItem(p.getPlayer().getLocation(), item);
			p.getPlayer().getInventory().remove(item);
			
			p.addViolation(HackType.INVENTORY_FAST_INVENTORY, ViolationType.THRESHOLD, (short) 1);
			p.runThreshold(HackType.INVENTORY_FAST_INVENTORY);
			
			return true;
		}
		if (type != null) {
			if (!type.equals(SlotType.QUICKBAR)) {
				// Item in not quickbar
				if (System.currentTimeMillis() - p.getLastPickupItemTime() < 800) {
					if (item.equals(p.getLastPickupItem())) {
						p.setLastFAEBlockTime(System.currentTimeMillis());
						p.reportToAdmin(HackType.INVENTORY_FAST_INVENTORY);

						return true;
					}
				}
			}
		}

		// After closing inventory
		if (System.currentTimeMillis() - p.getLastInvCloseTime() < 60) {
			if (Utils.isArmor(item.getType())) {
				p.setLastFAEBlockTime(System.currentTimeMillis());
				p.reportToAdmin(HackType.INVENTORY_FAST_INVENTORY);

				return true;
			}
		}

		// After opening player inventory - Working only for < 1.12
		if (System.currentTimeMillis() - p.getOpenPlayerInventoryTime() < 200) {
			// TODO Check the slots, there is 100% false positive, but should change the
			// void
			if (Utils.isArmor(item.getType())) {
				p.setLastFAEBlockTime(System.currentTimeMillis());
				p.reportToAdmin(HackType.INVENTORY_FAST_INVENTORY);

				return true;
			}
		}

		if (System.currentTimeMillis() - p.getLastPickupItemTime() < 300) {
			if (item.equals(p.getLastPickupItem())) {
				p.setLastFAEBlockTime(System.currentTimeMillis());
				p.reportToAdmin(HackType.INVENTORY_FAST_INVENTORY);

				if (Utils.isItem(p.getLastPickupItem().getType())) {
					p.getPlayer().getInventory().remove(p.getLastPickupItem());
					p.getPlayer().getWorld().dropItemNaturally(p.getPlayer().getLocation(), p.getLastPickupItem());
				}

				return true;
			}
		}
		return false;
	}
}