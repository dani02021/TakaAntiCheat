package bg.dani02.taka.anticheat.checks.movement.fly;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class FlyDoubleJumpDown {

	private static ArrayList<String> falling = new ArrayList<String>();

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_FLY_DOUBLE_JUMP_DOWN))
			return false;
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION)) {
				falling.remove(p.getPlayer().getName());
				return false;
			}
		}
		if (System.currentTimeMillis() - p.getLastSMTime() < 1500) {
			return false;
		}
		if (Utils.isOnGroundUltimate(from)
				|| Utils.isOnGroundUltimate(from, 0.5D)
				|| p.getPlayer().getAllowFlight() || System.currentTimeMillis() - p.getLastTeleportTime() <= 1500
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 1500 || p.isGliding()
				|| Utils.isNextToBlocksByNameAround(p, p.getPlayer().getLocation().getBlock(), false, true, "FENCE", "WALL")
				|| p.getLastGroundLocation().clone().subtract(0, 0.21, 0).getBlock().getType()
						.equals(Material.SLIME_BLOCK)
				|| Utils.isOnLadder2(p.getPlayer().getLocation())
				|| p.getPlayer().isInsideVehicle() || p.isRiptiding()
				|| (System.currentTimeMillis() - p.getLastDamageTime() <= 800
						&& p.getPlayer().getLastDamageCause() != null
						&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FALL))
				|| System.currentTimeMillis() - p.getLastAllTeleportTime() < 400
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
				|| Utils.isOnSnow(p.getPlayer())
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getEyeLocation())) {
			falling.remove(p.getPlayer().getName());
			return false;
		}
		if (p.getPlayer().getLastDamageCause() != null)
			if (!p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE)
					&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)
					&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FALL)
					&& System.currentTimeMillis() - p.getLastDamageTime() < 3000) {
				p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN, ViolationType.CANCELMOVE, (short) 1);
				falling.remove(p.getPlayer().getName());
				return false;
			}
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isOnBlock(p.getPlayer(), "SHULKER")) {
				falling.remove(p.getPlayer().getName());
				return false;
			}
		}
		
		if (Utils.isNextToEntity(p.getPlayer(), (short) 3, EntityType.BOAT)
				|| Utils.isOnEntity(p.getPlayer(), EntityType.BOAT))
			return false;
				
		if (from.getY() > to.getY()) {
			if (!to.clone().subtract(0,0.01,0).getBlock().isEmpty()) {
				falling.remove(p.getPlayer().getName());
				return false;
			}
			if (!falling.contains(p.getPlayer().getName()))
				falling.add(p.getPlayer().getName());
		} else if (from.getY() == to.getY()) {
			if(!falling.contains(p.getPlayer().getName()))
				return false;
			
			if (!Utils.isOnGroundUltimate(from)) {
				p.reportToAdmin(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN);
				p.teleportToGround(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN);
			}
			falling.remove(p.getPlayer().getName());
		} else {
			if (!Utils.isOnGroundUltimate(from)
					&& falling.contains(p.getPlayer().getName())) {
				if(p.reportToAdmin(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN)) {
					// Tower
					p.setBuildBlockageTime(System.currentTimeMillis());
					// p.teleport(Utils.getGroundLocation(from)); Open the door to nofall bypasses
					p.teleportToGround(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN);
				}
				return true;
			}
			falling.remove(p.getPlayer().getName());
		}
		return false;
	}
}
