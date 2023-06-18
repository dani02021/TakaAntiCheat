package bg.dani02.taka.anticheat.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class SpeedAir2 {
	// Called in Speed check
	public static boolean onCheck(CheatPlayer p, double dist, boolean isUp, Location from) {
		if(!Utils.checkModule(p, HackType.MOVING_SPEED_AIR))
			return false;
		if(Utils.getServerVersion() >= 9)
			if(p.getPlayer().getInventory().getBoots() != null)
				if(p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED))
					if(p.getLastGroundLocation().clone().subtract(0,0.3,0).getBlock().getType().name().contains("SOUL"))
						return false;
		if (p.getPlayer().getLastDamageCause() != null)
			if ((System.currentTimeMillis() - p.getLastDamageTime() <= 3000
					&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE)
					&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)
					&& !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FALL))) {
				p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);
				return false;
			}
		if (p.getLastGroundLocation().clone().getBlock().getRelative(BlockFace.DOWN).getType().name()
				.contains("FENCE")) {
			p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);
			return false;
		}
		if (System.currentTimeMillis() - p.getLastSMTime() < 1500) {
			p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);
			return false;
		}
		if (System.currentTimeMillis() - p.getLastPlateTouchTime() < 2500) {
			p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);

			if (ConfigsMessages.step_basic_jump_pads_gold)
				if (p.getLastPlateTouchType().equals(XMaterial.ACACIA_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.BIRCH_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.JUNGLE_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.DARK_OAK_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.OAK_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.SPRUCE_PRESSURE_PLATE.parseMaterial()))
					return false;
			if (ConfigsMessages.step_basic_jump_pads_stone)
				if (p.getLastPlateTouchType().equals(XMaterial.STONE_PRESSURE_PLATE.parseMaterial()))
					return false;
			if (ConfigsMessages.step_basic_jump_pads_iron)
				if (p.getLastPlateTouchType().equals(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial()))
					return false;
			if (ConfigsMessages.step_basic_jump_pads_gold)
				if (p.getLastPlateTouchType().equals(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial()))
					return false;
		}
		if (System.currentTimeMillis() - p.getLastGlideEndTime() < 1500
				|| System.currentTimeMillis() - p.getLastFlyTime() < 1500
				|| (System.currentTimeMillis() - p.getLastAllTeleportTime() < 1500
						&& p.getLastAllTeleportTime() != p.getLastTacTeleportTime())) {
			p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);
			return false;
		}
		if (p.isGliding() || p.getPlayer().getAllowFlight() || p.getPlayer().getWalkSpeed() > 0.3F || p.isRiptiding() ||
				System.currentTimeMillis() - p.getLastRiptileTime() < 500)
			return false;
		if (Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())) {
			p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) 500);
			return false;
		}
		if (Utils.isOnHalfSlab(from) || Utils.isOnStair(from)) {
			p.removeUpwardsSpeed(p.getUpwardsSpeed());
			return false;
		}
		
		if(p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType()
				.equals(Material.SOUL_SAND) && p.getLastGroundLocation().getY() % 1 != 0) {
			
		} else if(p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType().equals(Material.ICE)
				|| p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType().equals(Material.PACKED_ICE)) {
			
		} else {
			if(!p.getPlayer().isOnGround()) {
				
			}
		}

//		if (!isUp) {
//			if (p.getUpwardsSpeed() > 0) {
//				double need = 13;
//				// If the player has touched the soul sand, you can stand on it, without
//				// touching it!
//				if (p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType()
//						.equals(Material.SOUL_SAND) && p.getLastGroundLocation().getY() % 1 != 0)
//					need = p.isSprinting() ? 4.0 : 1.6;
//				else if (p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType().equals(Material.ICE)
//						|| p.getLastGroundLocation().clone().subtract(0, 0.5, 0).getBlock().getType()
//								.equals(Material.PACKED_ICE))
//					need = 15;
//				if (dist > need) {
//					Speed.law(p, need, dist, HackType.MOVING_SPEED_AIR, from);
//					p.removeUpwardsSpeed(p.getUpwardsSpeed());
//					return true;
//				} else {
//					p.removeViolation(HackType.MOVING_SPEED_AIR, (short) 1, ViolationType.CANCELMOVE);
//				}
//				// p.getPlayer().sendMessage("disT: "+dist);
//				// p.getPlayer().sendMessage("SP: "+dist+" "+need);
//				p.removeUpwardsSpeed(p.getUpwardsSpeed());
//			}
//		} else {
//			// p.getPlayer().sendMessage("AB: "+dist);
//			// if(Utils.isOnGround(p.getPlayer().getLocation()) == null)
//			p.addUpwardsSpeed(dist);
//		}

		return false;
	}
}
