package bg.dani02.taka.anticheat.checks.inventory;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class FastClick {
	/**
	 * Too fast item change/click relation time Example: Slot 1 -> Slot 3 -> Click
	 * item on Slot 3 -> Slot 1 ---- for 30 ms For AutoPot
	 */
	// toSlotTime, fromSlot, fromSlotItem, clicked
	private static HashMap<String, Object[]> slots = new HashMap<String, Object[]>();

	public static boolean onClick(CheatPlayer p, ItemStack item) {
		if(!Utils.checkModule(p, HackType.INVENTORY_FAST_CLICK))
			return false;
		if(!slots.containsKey(p.getPlayer().getName()) || item == null)
			return false;
		if (slots.get(p.getPlayer().getName()).length == 3 && isClickable(item)) {
			// Clicked while in the mid slot
			Object[] old = slots.get(p.getPlayer().getName());
			slots.put(p.getPlayer().getName(), new Object[] { old[0], old[1], old[2], true });
		}

		return false;
	}

	public static void onSlotChange(CheatPlayer p, int fromSlot, int toSlot, ItemStack item) {
		if (slots.get(p.getPlayer().getName()) == null || slots.get(p.getPlayer().getName()).length == 0) {
			// Slot 1 -> Slot 3
			slots.put(p.getPlayer().getName(), new Object[] { System.currentTimeMillis(), fromSlot, item });
		} else if (slots.get(p.getPlayer().getName()).length == 3) {
			// If the player changed it's slot from 1 to 4, therefore Slot 3 -> Slot 4
			slots.put(p.getPlayer().getName(), new Object[] { System.currentTimeMillis(), fromSlot, item });
		} else if (slots.get(p.getPlayer().getName()).length == 4) {
			// Slot 4 -> Slot 3
			Object[] old = slots.get(p.getPlayer().getName());
			
			// Although 120 gives false positive, I don't have other option, adding cancelMove would hopefully fix the problem
			if (toSlot == (int) old[1] &&
					System.currentTimeMillis()-(long)slots.get(p.getPlayer().getName())[0] < 120)
				if(p.reportToAdmin(HackType.INVENTORY_FAST_CLICK))
					slots.remove(p.getPlayer().getName());

			// TODO: Test this out + BowAimbot idea - Arrrowing on the same place related to
			// the entity hitten
		}
	}

	public static boolean isClickable(ItemStack item) {
		return item.getType().name().contains("POTION") || item.getType().name().contains("SOUP")
				|| item.getType().name().contains("HELMET") || item.getType().name().contains("CHESTPLATE")
				|| item.getType().name().contains("LEGGINGS") || item.getType().name().contains("BOOT");
	}
}
