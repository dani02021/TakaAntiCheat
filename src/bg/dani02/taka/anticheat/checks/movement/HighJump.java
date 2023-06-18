package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class HighJump {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_HIGHJUMP))
			return false;
		if (System.currentTimeMillis() - p.getLastPlateTouchTime() < 2500) {
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

		if (from.getY() < to.getY()) {
			if (p.isGliding() || p.getPlayer().isInsideVehicle() || Utils.isOnStair(to)
					|| Utils.isOnSlime(p.getLastGroundLocation())
					|| p.getPlayer().isFlying() || p.isRiptiding()
					|| System.currentTimeMillis() - p.getLastVehicleEnterTime() < 200
					|| Utils.isOnSlime(p.getPlayer().getLocation())
					|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
					|| System.currentTimeMillis() - p.getLastFlyTime() < 1000
					|| System.currentTimeMillis() - p.getLastAttackTime() < 500
					|| System.currentTimeMillis() - p.getLastVehicleExitTime() < 1000
					|| (System.currentTimeMillis() - p.getLastDamageTime() < 500  &&
							p.getPlayer().getLastDamageCause() != null &&
							p.getPlayer().getLastDamageCause().getCause().name().contains("ENTITY"))
					|| (System.currentTimeMillis() - p.getLastAllTeleportTime() < 500 &&
							p.getLastAllTeleportTime() - p.getLastTacTeleportTime() > 100)
					|| Utils.isAroundLiquidLoc(p, from) || Utils.isBubbleColumn(from)
					|| Utils.isBubbleColumn(p.getLastGroundLocation())
					|| Utils.isNextToBlocksByNameAround(p, to.getBlock().getRelative(BlockFace.DOWN), false, false, "WALL", "FENCE", "BED")
					|| Utils.isNextToBlocksByNameAround(p, to.getBlock(), false, true, "HEAD", "SKULL")
					|| Utils.isOnHalfSlab(to) || Utils.isOnSnowUltimate(p.getPlayer().getLocation(), true))
				return false;
						
			if (System.currentTimeMillis() - p.getLastFlyTime() < 700 && p.getPlayer().getFlySpeed() >= 0.6)
				return false;
			
			if (Utils.getServerVersion() >= 2) {
				if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
						(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
								System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 500)) {
					return false;
				}
			}
			
			if (Utils.getServerVersion() >= 5) {
				if(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("BED"))
					return false;
			}
			
			if (/*Utils.isOnGroundUltimate(from)*/ p.getPlayer().getLocation().clone().subtract(0,0.2D,0).getBlock().getType().isSolid()) {
				double needed = Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP)
						? Utils.getPotionEffect(p.getPlayer(), PotionEffectType.JUMP).getAmplifier() * 0.1 + 0.6
						: 0.42;
				// double need =
				// p.getPlayer().getLocation().clone().subtract(0,0.3,0).getBlock().isEmpty() ?
				// needed : 0.42; -> IDK Whats that xD
				if (to.getY() - from.getY() > needed) {
					if(p.reportToAdmin(HackType.MOVING_HIGHJUMP, "yDiff: " + (to.getY() - from.getY()))) {
						p.teleportToGround(HackType.MOVING_HIGHJUMP);
						return true;
					}
				}
			}
		}

		return false;
	}
}
