 package bg.dani02.taka.anticheat.checks.movement.fly;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FlyDoubleJumpUP {

	protected static HashMap<String, Double> prevMoveDist = new HashMap<String, Double>();

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_FLY_DOUBLE_JUMP_UP))
			return false;
		if (System.currentTimeMillis() - p.getLastFlyTime() <= 2000) {
			p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
			prevMoveDist.remove(p.getPlayer().getName());
			return false;
		}
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 500)) {
				prevMoveDist.remove(p.getPlayer().getName());
				p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
				return false;
			}
		}

		if (p.getLastGroundLocation().getBlock().getType().name().contains("PLATE")) {
			if (ConfigsMessages.step_basic_jump_pads_wooden)
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

		if (System.currentTimeMillis() - p.getLastSMTime() < 1500) {
			return false;
		}

		if (Utils.isOnEntity(p.getPlayer(), EntityType.BOAT) || Utils.isOnEntity(p.getPlayer(), EntityType.BOAT)) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		// p.getPlayer().sendMessage(""+p.getPlayer().getFireTicks());
		// Hmm maybe check isNextToBlockDifferent p.getloc.subtract(0.2)?
		if (/*Utils.isNextToBlock2DifferentNoDown(
				p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getLocation(),
				org.bukkit.Material.AIR, 1)
				||*/ Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| Utils.isOnStair(p.getPlayer()) || p.isRiptiding()
				|| (!to.clone().subtract(0, 0.1, 0).getBlock().isEmpty()
						&& (to.clone().subtract(0, 0.1, 0).getBlock().getType().isSolid()
								&& !to.clone().subtract(0, 0.1, 0).getBlock().getType().name().contains("GLASS_PANE")
								&& !to.clone().subtract(0, 0.1, 0).getBlock().getType().name().contains("THIN_GLASS")
								&& !to.clone().subtract(0, 0.1, 0).getBlock().getType().name().contains("SIGN")))
				|| Utils.isOnHalfSlab(p.getPlayer().getLocation())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "WALL", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 2500
				|| (System.currentTimeMillis() - p.getLastDamageTime() < 250 &&
						p.getPlayer().getLastDamageCause() != null)
				|| Utils.isOnSnow(p.getPlayer())
				|| (System.currentTimeMillis() - p.getLastTeleportTime() < 500 && System.currentTimeMillis() - p.getLastTacTeleportTime() > 500)
				|| System.currentTimeMillis() - p.getLastFlyTime() < 1000
				/* || p.getPlayer().isInsideVehicle() */ || Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
				|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType()) || p.getPlayer().isFlying()
				|| p.isGliding() || Utils.isAroundLiquidLoc(p, p.getPlayer().getEyeLocation())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "BED", false, false)
				|| Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0), 0.3) != null
				|| Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.2, 0), 0.3) != null
				|| !p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
			p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
			prevMoveDist.remove(p.getPlayer().getName());
			return false;
		}
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isOnBlock(p.getPlayer(), "SHULKER")) {
				p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
				prevMoveDist.remove(p.getPlayer().getName());
				return false;
			}
		}
		if (Utils.getServerVersion() >= 7) {
			if (Utils.isOnScaffolding(p.getPlayer())) {
				p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
				prevMoveDist.remove(p.getPlayer().getName());
				return false;
			}
		}
		
		// When riding horse, actually the player is in the air, so every jump is like in air, so should check vehicle location instead
		if(p.getPlayer().isInsideVehicle()) {
			if(Utils.isOnGround4(p.getPlayer().getLocation()) != null || Utils.isOnGround4(p.getPlayer().getVehicle().getLocation()) != null)
				return false;
		}
		/*
		 * if (p.getPlayer().getLastDamageCause() != null) if
		 * ((System.currentTimeMillis() - p.getLastDamageTime() <= 3000 &&
		 * !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE) &&
		 * !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)
		 * && !p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FALL)))
		 * { p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, (short) 1,
		 * ViolationType.CANCELMOVE); prevMoveDist.remove(p.getPlayer().getName());
		 * return false; }
		 */
		if (from.getY() < to.getY()) {
			// Wolfram StepLegit fence
			if (Utils.isNextToBlock2ByName(
					p.getPlayer().getLocation().getBlock().getLocation().clone().subtract(0, 0.3, 0), "FENCE", 0.075)
					&& !Utils.isNextToBlockByNameAround(p, from.getBlock(), "CARPET", false, false) && !Utils.isNextToBlockByNameAround(p, to.getBlock(), "CARPET", false, false)) {
				if (to.getY() - from.getY() > 0.42) {
					p.reportToAdmin(HackType.MOVING_FLY_DOUBLE_JUMP_UP);
					p.teleportToGround(HackType.MOVING_FLY_DOUBLE_JUMP_UP);
					return true;
				} else {
					p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
					prevMoveDist.remove(p.getPlayer().getName());
				}
			}
			// Woflram END
			if ((to.getY() - from.getY()) < 0.5 && 
					from.clone().subtract(0, 0.3, 0).getBlock().isEmpty()
					&& !to.clone().subtract(0, 0.3, 0).getBlock().isEmpty()) {
				p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
				prevMoveDist.remove(p.getPlayer().getName());
				return false;
			}
			// p.getPlayer().sendMessage(""+(prevMoveDist.get(p.getPlayer().getName())+"
			// "+(to.getY()-from.getY()))+"
			// "+Double.toString(((to.getY()-from.getY())/0.1)).length());
			if (prevMoveDist.containsKey(p.getPlayer().getName())) {
				if (FlySlowY.onCheck(p, (prevMoveDist.get(p.getPlayer().getName()) - (to.getY() - from.getY()))))
					return false;

				if ((to.getY() - from.getY()) >= prevMoveDist.get(p.getPlayer().getName())) {
					// Fixes one false positive BUT find a better way :/
					if (from.clone().subtract(0, 0.1, 0).getBlock().isEmpty()
							&& Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP))
						return false;
					// Tower
					p.setBuildBlockageTime(System.currentTimeMillis());
					// p.getPlayer().sendMessage("A: "+(to.getY()-from.getY())+"
					// "+from.clone().subtract(0,0.1,0).getBlock().isEmpty());
					if(p.reportToAdmin(HackType.MOVING_FLY_DOUBLE_JUMP_UP)) {
						p.teleportToGround(HackType.MOVING_FLY_DOUBLE_JUMP_UP);
						return true;
					}
				} else {
					p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
				}
				prevMoveDist.put(p.getPlayer().getName(), (to.getY() - from.getY()));
			} else {
				prevMoveDist.put(p.getPlayer().getName(), (to.getY() - from.getY()));
			}
		} else {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_DOUBLE_JUMP_UP, ViolationType.CANCELMOVE, (short) 1);
		}
		// p.getPlayer().sendMessage("diffY: "+(to.getY()-from.getY())+"
		// "+p.getPlayer().getVelocity().getY()+"
		// "+(p.getLastFromToLocations()[1].getY()-p.getLastFromToLocations()[0].getY()));
		return false;
	}
}
