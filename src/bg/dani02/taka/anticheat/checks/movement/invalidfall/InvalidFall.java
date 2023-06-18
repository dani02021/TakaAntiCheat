package bg.dani02.taka.anticheat.checks.movement.invalidfall;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class InvalidFall {
	private static HashMap<String, Double> prevMoveDist = new HashMap<>();

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkPlayer(p))
			return false;
		
		if (p.getPlayer().getAllowFlight()
				|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP).isEmpty()
				|| System.currentTimeMillis() - p.getLastTeleportTime() <= 2000 /*
																				 * || System.currentTimeMillis()-p.
																				 * getLastTacTeleportTime() <= 2000
																				 */
				|| p.getPlayer().isInsideVehicle() || Utils.isInWeb(p, p.getPlayer().getLocation(), true)
				|| Utils.isOnLadder(p.getPlayer()) || (System.currentTimeMillis() - p.getLastDamageTime() < 300 &&
						p.getPlayer().getLastDamageCause() != null)
				|| p.getPlayer().getLocation().getY() <= 0 || p.isGliding() || Utils.isOnScaffolding(p.getPlayer())
				/*|| (!to.clone().subtract(0, 0.12, 0).getBlock().isEmpty()
						&& to.clone().subtract(0, 0.12, 0).getBlock().getType().isSolid()) May have fp's, but is blocking ReverseStep*/
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation()) || System.currentTimeMillis() - p.getLastVehicleExitTime() < 1000) {
			
			// ReverseStep
			prevMoveDist.remove(p.getPlayer().getName());

			return false;
		}
		if(p.getLastGroundLocation().getBlock().getType().name().contains("PLATE")) {
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
					Utils.isNextToBlock(p,p.getPlayer().getEyeLocation(), Material.HONEY_BLOCK)) {
				return false;
			}
		if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP)) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.setLastFallTime(0);

			return false;
		}
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 1000)) {
				prevMoveDist.remove(p.getPlayer().getName());
				return false;
			}
		}
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isOnBlock(p.getPlayer(), "SHULKER")) {
				prevMoveDist.remove(p.getPlayer().getName());
				p.setLastFallTime(0);

				return false;
			}
		}
		if (Utils.isNextToEntity(p.getPlayer(), (short) 3, EntityType.BOAT)
				|| Utils.isOnEntity(p.getPlayer(), EntityType.BOAT))
			return false;
		
		// TODO: Fix one bypass:
		// Gliding - BUT not the same distance a.k.a prevMoveDist <
		// (from.getY()-to.getY())
		if (from.getY() > to.getY()) {
			if (prevMoveDist.containsKey(p.getPlayer().getName())) {
				// First check - Slowed fly
				// Turned glide after some fall distance
				// TODO: See why isNextToBlockDifferentNoDown is not working, so even
				// if the player is falling next to block, he should again be detected
				if (prevMoveDist.get(p.getPlayer().getName()) > (from.getY() - to.getY())
						&& p.getServerFallDistance() >= 2.0
						&& !Utils.isNextToBlockDifferent(
								p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
								Material.AIR)
						&& !Utils.isNextToBlockDifferent(to.getBlock().getRelative(BlockFace.DOWN).getLocation(),
								Material.AIR)
						&& p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().isEmpty()
						&& to.clone().subtract(0, 0.1, 0).getBlock().isEmpty() && !p.getPlayer().isInsideVehicle()
						&& p.getPlayer().getFireTicks() <= 0
						&& System.currentTimeMillis() - p.getLastDeathTime() > 1000
						&& Utils.checkModule(p, HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE)
						&& !(Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getLocation(), Material.LADDER)
								|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getLocation(),
										Material.VINE)
										&& p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty())
						&& !p.haveBypass(HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE)) {
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

					// Instant
					p.reportToAdmin(HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE);
					p.teleportToGround(HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE);
					return true;
				}

				// Third check - Fasted fly
				if (((from.getY() - to.getY()) - prevMoveDist.get(p.getPlayer().getName())) > 0.087 && !(Utils
						.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getLocation(), Material.LADDER)
						|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getLocation(), Material.VINE)
								&& p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty())) {
					// NonInstant
					if (Utils.checkModule(p, HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT)) {
						if (Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation().clone().subtract(0, 0.2, 0))
								|| Utils.isNextToBlock(p, from, Material.WATER) || Utils.isNextToBlock(p, from, Material.LAVA)
								|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
								|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())
								|| (p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType()
										.equals(Material.SOUL_SAND)
										&& p.getLastGroundLocation().distance(p.getPlayer().getLocation()) < 2.5D))
							return false;
						if (!p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.WEST).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.EAST).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.SOUTH).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.NORTH).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.SOUTH_EAST).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.SOUTH_WEST).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.NORTH_EAST).isEmpty()
								|| !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP)
										.getRelative(BlockFace.NORTH_WEST).isEmpty())
							return false;

						if (p.reportToAdmin(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT)) {
							p.teleportToGround(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT);
							return true;
						}
					}
					if (((from.getY() - to.getY()) - prevMoveDist.get(p.getPlayer().getName())) > 0.2
							&& Utils.checkModule(p, HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT)) {
						if (Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation().clone().subtract(0, 0.2, 0))
								|| Utils.isNextToBlock(p, from, Material.WATER)
								|| Utils.isNextToBlock(p, from, Material.LAVA))
							return false;
						// Instant
						p.reportToAdmin(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT);
						p.teleportToGround(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT);
						return true;
					}
				} else {
					p.removeViolation(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT, ViolationType.CANCELMOVE,
							(short) 1);
				}
				// Fourth check - Stable distance
				if (((from.getY() - to.getY()) - prevMoveDist.get(p.getPlayer().getName())) == 0
						&& !Utils.isNextToBlock(
								p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
								XMaterial.COBWEB.parseMaterial())
						&& !Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.COBWEB.parseMaterial())
						&& !Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
						&& System.currentTimeMillis() - p.getLastTeleportTime() > 800
						&& System.currentTimeMillis() - p.getLastFlyTime() > 500
						&& System.currentTimeMillis() - p.getLastGlideEndTime() > 500
						&& !p.isGliding() // I don't like this thing, but I should use it, sometimes fp's with elytras
						&& !Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
						&& !Utils.isClimable(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())
						&& p.getPlayer().getLocation().subtract(0,0.5,0).getBlock().isEmpty()
						&& Utils.checkModule(p, HackType.MOVING_INVALIDFALL_STABLE_DISTANCE)) {
					// Instant
					p.reportToAdmin(HackType.MOVING_INVALIDFALL_STABLE_DISTANCE);
					p.teleportToGround(HackType.MOVING_INVALIDFALL_STABLE_DISTANCE);
					return true;
				}
				
				// Seventh check
				InvalidFallSlowY.onCheck(p, (from.getY() - to.getY()) - prevMoveDist.get(p.getPlayer().getName()),
						false);
				prevMoveDist.put(p.getPlayer().getName(), from.getY() - to.getY());
			} else {
				InvalidFallSlowY.onCheck(p, (from.getY() - to.getY()), true);

				// WARING: 0.5 sometimes is giving false positives, if they are too much
				// Change the value to min 0.7

				prevMoveDist.put(p.getPlayer().getName(), from.getY() - to.getY());
				if (System.currentTimeMillis() - p.getLastGlideEndTime() < 1500
						|| System.currentTimeMillis() - p.getLastGlideStartTime() < 1500) {
					return false;
				}
				if (p.isGliding())
					return false;

				if (p.getIFB()) {
					p.setIFB(false);
					return false;
				}
				
				// Fifth check
				if (from.getY() - to.getY() >= 0.55
						&& Utils.checkModule(p, HackType.MOVING_INVALIDFALL_FAST_START_DISTANCE)
						&& System.currentTimeMillis() - p.getLastTeleportTime() > 1000
						&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
						&& !Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
						&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
						&& !Utils.isClimable(p.getLastFromToLocations()[0].getBlock().getType())
						&& (p.getServerFallDistance() - (from.getY() - to.getY())) <= 1) {
					// Instant
					p.reportToAdmin(HackType.MOVING_INVALIDFALL_FAST_START_DISTANCE);
					p.teleportToGround(HackType.MOVING_INVALIDFALL_FAST_START_DISTANCE);
					return true;
				}
				// p.getPlayer().sendMessage("firstFall: "+(from.getY()-to.getY()));
			}
		} else {
			prevMoveDist.remove(p.getPlayer().getName());
		}

		return false;
	}
}
