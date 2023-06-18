package bg.dani02.taka.anticheat.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FlySlowY {
	// The method is called from FlyDoubleJumpUP
	
	// False positive when placing block below while jumping, not important
	public static boolean onCheck(CheatPlayer p, double diff) {
		if(!Utils.checkPlayer(p))
			return false;
		
		if (p.getLastGroundLocation().clone().subtract(0, 0.8, 0).getBlock().getType().equals(Material.SLIME_BLOCK)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().clone().subtract(0, 0.2, 0),
						XMaterial.COBWEB.parseMaterial())
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
				|| Utils.isOnLadder2(p.getPlayer().getLocation())
				|| System.currentTimeMillis() - p.getLastVehicleTime() < 1000
				|| p.getPlayer().isInsideVehicle()
				|| p.isRiptiding()
				|| System.currentTimeMillis() - p.getLastFlyTime() < 500
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.BREWING_STAND) || Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.BREWING_STAND)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.COBWEB.parseMaterial()))
			return false;
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION)) {
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
		if(Utils.getServerVersion() >= 8)
			if(Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getLocation().clone().subtract(0,0.3,0), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getEyeLocation(), Material.HONEY_BLOCK))
				return false;
		if (Utils.isNextToBlockDifferent(
				p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.UP).getLocation(), Material.AIR))
			return false;
		
		// placing a block below you gives yDif = 0.06237590503906176 most of the time
		if (diff < 0.07 &&
				diff != 0.06237590503906176  &&
				diff != 0.049658572225752096) {
			if (diff < 0.02 && Utils.checkModule(p, HackType.MOVING_FLY_SLOW_Y_INSTANT)) {
				// Instant
				// Fire false positive
				if (p.getPlayer().getLastDamageCause() != null) {
					if (p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE)
							|| p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)
							|| p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.STARVATION)
									&& (System.currentTimeMillis() - p.getLastDamageTime()) < 200)
						return false;
				}
				if(p.reportToAdmin(HackType.MOVING_FLY_SLOW_Y_INSTANT, "yDiff: " + diff)) {
					// Tower
					p.setBuildBlockageTime(System.currentTimeMillis());
					p.teleportToGround(HackType.MOVING_FLY_SLOW_Y_INSTANT);
					return true;
				}
			}

			// NonInstant
			if (Utils.checkModule(p, HackType.MOVING_FLY_SLOW_Y_NON_INSTANT)) {
				if (p.reportToAdmin(HackType.MOVING_FLY_SLOW_Y_NON_INSTANT, "yDiff: " + diff)) {
					// Tower
					p.setBuildBlockageTime(System.currentTimeMillis());
					p.teleportToGround(HackType.MOVING_FLY_SLOW_Y_NON_INSTANT);
					return true;
				}
			}
		} else
			p.removeViolation(HackType.MOVING_FLY_SLOW_Y_NON_INSTANT, ViolationType.CANCELMOVE, (short) 1);

		return false;
	}
}
