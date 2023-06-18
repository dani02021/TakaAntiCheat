package bg.dani02.taka.anticheat.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FlyInvalidY {

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		// This check have some fps, but if is removed then some bypasses
		// will comes out :/ - Like:
		// Walking on cobwebs
		// Climbing stairs or half slabs
		
		if(!Utils.checkModule(p, HackType.MOVING_FLY_INVALID_Y))
			return false;
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 1000)) {
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

		if (System.currentTimeMillis() - p.getLastFlyTime() <= 2000
				|| System.currentTimeMillis() - p.getLastDamageTime() <= 500
				|| Utils.isOnEntity(p.getPlayer(), EntityType.BOAT)
				|| p.isGliding()
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 1000
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| p.getLastGroundLocation().clone().subtract(0, 0.8, 0).getBlock().getType()
						.equals(Material.SLIME_BLOCK)
				|| p.getPlayer().getLocation().getBlock().isLiquid()
				|| p.getPlayer().getEyeLocation().getBlock().isLiquid() || p.haveBypass(HackType.MOVING_FLY_INVALID_Y)
				|| p.getPlayer().getLocation().clone().subtract(0, 0.6, 0).getBlock().isLiquid()
				|| ((Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.WATER)
						|| Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.WATER.parseMaterial()))
						&& !p.getPlayer().getLocation().getBlock().isLiquid()
						&& !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid())) {
			// flyVL.remove(p.getPlayer().getName());
			return false;
		}
		
		if(Utils.getServerVersion() >= 8)
			if(Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getEyeLocation(), Material.HONEY_BLOCK))
				return false;

		if (to.getY() >= from.getY() && p.getPlayer().getVelocity().clone().getY() < -0.08
				&& (p.getPlayer().getVelocity().clone().getY() != -0.1552320045166016
						&& p.getPlayer().getVelocity().clone().getY() != -0.230527368912964
						&& p.getPlayer().getVelocity().clone().getY() != 0.30431682745754424
						&& p.getPlayer().getVelocity().clone().getY() != -0.44749789698341763
						&& p.getPlayer().getVelocity().clone().getY() != -0.32374665784961826
						&& p.getPlayer().getVelocity().clone().getY() != -0.3031682745754424
						&& p.getPlayer().getVelocity().clone().getY() != -0.09090330585848257)
				&& !Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
				&& !Utils.isClimable(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())) {
			// p.getPlayer().sendMessage("INVALIDFLY:" + p.getPlayer().getVelocity().clone().getY());
			if (p.getPlayer().getVelocity().clone().getY() < -1.5) {
				if(p.reportToAdmin(HackType.MOVING_FLY_INVALID_Y, "yVel: " + p.getPlayer().getVelocity().clone().getY() + " ")) {
					p.teleportToGround(HackType.MOVING_FLY_INVALID_Y);
					return true;
				}
			}
			// TODO: Continue
			// if(Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP)) {
			// a(p, (short) (5+Utils.getPotionEffect(p.getPlayer(),
			// PotionEffectType.JUMP).getAmplifier()));
			// } else a(p, (short) 5);

		}

		return false;
	}

	// private static void a(CheatPlayer p, short vl) {
	// if(Utils.isDetectionEnabled(HackType.MOVING_FLY_INVALID_Y))
	// return;
	// if(flyVL.get(p.getPlayer().getName()) >= vl)
	// {
	// //TODO: Fix FLYVL
	// if(p.haveBypass(HackType.MOVING_FLY_INVALID_Y))
	// return;
	// if(Utils.isDetectionEnabled(HackType.MOVING_FLY_INVALID_Y))
	// return;
	// p.reportToAdmin(HackType.MOVING_FLY_INVALID_Y,
	// flyVL.get(p.getPlayer().getName()));
	// p.teleportToGround();
	// flyVL.remove(p.getPlayer().getName());
	// }
	// }
}