package bg.dani02.taka.anticheat.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FlyModulo {

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_FLY_MODULO))
			return false;
		if (Utils.isNextToBlock(p, from, XMaterial.ENCHANTING_TABLE.parseMaterial()))
			return false;
		if (Utils.getServerVersion() > 1) {
			if (Utils.isNextToBlock(p, from, Material.END_ROD))
				return false;
		}
		if (p.getPlayer().isFlying() || System.currentTimeMillis() - p.getLastAllTeleportTime() < 500
				|| System.currentTimeMillis() - p.getLastFlyTime() < 1000)
			return false;
		if (System.currentTimeMillis() - p.getLastTeleportTime() <= 2000)
			return false;
		if (p.getPlayer().isInsideVehicle() || p.isRiptiding())
			return false;
		if (from.getY() < to.getY()) {
			// p.getPlayer().sendMessage("step "+(to.getY()-from.getY()) % 1);

			if ((to.getY() - from.getY()) % 1 == 0 || ((to.getY() - from.getY()) % 1 == 0.5
					&& to.getY() - from.getY() != 0.5 && !Utils.isOnSnow(p.getPlayer()))) {
				if(p.reportToAdmin(HackType.MOVING_FLY_MODULO)) {
					// Tower
					p.setBuildBlockageTime(System.currentTimeMillis());
					p.teleportToGround(HackType.MOVING_FLY_MODULO);
					return true;
				}
			}
		}

		return false;
	}
}
