package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class GroundElytra {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_GROUND_ELYTRA))
			return false;
		if (Utils.isAroundLiquidLoc(p, from))
			return false;
		// Some plugins like SurvivalMechanics are gliding the player without elytra
		if (from.getY() == to.getY() && !from.getBlock().getRelative(BlockFace.DOWN).isEmpty()
				&& !to.getBlock().getRelative(BlockFace.DOWN).isEmpty()) {
			if (p.isGliding() && p.getPlayer().getInventory().getChestplate() != null) {
				if (p.reportToAdmin(HackType.MOVING_GROUND_ELYTRA)) {
					p.getPlayer().getInventory().addItem(p.getPlayer().getInventory().getChestplate());
					p.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
					p.teleportToGround(HackType.MOVING_GROUND_ELYTRA);
					return true;
				}
			}
		} else {
			p.removeViolation(HackType.MOVING_GROUND_ELYTRA, ViolationType.CANCELMOVE, (short) 1);
		}

		return false;
	}
}
