package bg.dani02.taka.anticheat.checks.inventory;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.MoveDirectionType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class InvMove {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.INVENTORY_MOVE))
			return false;
		if (p.getLastGroundLocation().clone().subtract(0, 0.2, 0).getBlock().getType().equals(Material.SLIME_BLOCK))
			return false;
		if (p.getLegitInvMoveVL() > 0) {
			p.setLegitInvMoveVL((short) (p.getLegitInvMoveVL() - 1));
			return false;
		}
		if(Math.abs(p.getLastFromToLocations()[1].getY() - p.getLastFromToLocations()[0].getY()) > Math.abs(to.getY() - from.getY())) {
			p.setLegitInvMoveVL((short) (p.getLegitInvMoveVL() - 1));
			return false;
		}
		if (p.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			return false;
		if (p.getPlayer().getLocation().getBlock().isLiquid() || p.getPlayer().isFlying()
				|| Utils.isOnIce(p.getPlayer()) || p.getPlayer().isInsideVehicle())
			return false;
		if (from.getY() <= to.getY() && System.currentTimeMillis() - p.getLastDamageTime() > 2000) {
			if (p.getOpenPlayerInventory()
					|| (!p.getPlayer().getItemOnCursor().getType().equals(Material.AIR)
							&& p.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING))
					|| !p.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING)) {
				if (Utils.getXZDisstance(from, to) > 0.15) {
					if (!p.getPlayer().getItemOnCursor().getType().equals(Material.AIR)) {
						p.getPlayer().getOpenInventory().close();
						p.getPlayer().closeInventory();
					}
					
					// Some times giving false positives, when player lagged
					// the server didnt proccesed the packet when was sent by the client, which made the client not opening the chest and hence able to walk
					
					if (p.reportToAdmin(HackType.INVENTORY_MOVE, "move_open_inv last_damage: " + (System.currentTimeMillis() - p.getLastDamageTime()) + " dist: " + Utils.getXZDisstance(from, to))) {
						// Utils.freezePlayer(p.getPlayer(), HackType.INVENTORY_MOVE);
						p.getPlayer().getOpenInventory().close();
						p.getPlayer().closeInventory();
						return true;
					}
				}
			} else {
				p.removeViolation(HackType.INVENTORY_MOVE, ViolationType.CANCELMOVE, (short) 1);
			}
		}
		return false;
	}

	public static void onCheck(CheatPlayer p, InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;

		if (!Utils.isDetectionEnabled(HackType.INVENTORY_MOVE))
			return;
		if (p.haveBypass(HackType.INVENTORY_MOVE))
			return;

		// Bug - If the player didnt made move, but still moves items!
		if (p.getLastMoveDirectionType() == null)
			return;

		if (e.getWhoClicked().getOpenInventory().getType().equals(InventoryType.CRAFTING)
				&& !e.getSlotType().equals(SlotType.QUICKBAR)) {
			if (e.isShiftClick() && (e.getSlot() < 11 || e.getSlot() > 15) && e.getSlot() != 23 && e.getSlot() != 22
					&& e.getSlot() != 21 && p.getLastMoveDirectionType().equals(MoveDirectionType.HORIZONTAL)
					&& p.getPlayer().getLocation().getY() % 1 == 0) {
				// p.getPlayer().sendMessage("SHIFT_CLICK:
				// "+(System.currentTimeMillis()-p.getLastPickupItemTime()));
				if ((System.currentTimeMillis() - p.getLastPickupItemTime()) < 700) {
					p.reportToAdmin(HackType.INVENTORY_MOVE, "fastItemMove time: " + (System.currentTimeMillis() - p.getLastPickupItemTime()));
					p.setLastFAEBlockTime(System.currentTimeMillis());
					e.setCancelled(true);
					// Probably < 500
				}
			}
		}
	}

}