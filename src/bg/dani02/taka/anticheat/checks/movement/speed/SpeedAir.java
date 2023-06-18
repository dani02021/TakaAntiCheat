package bg.dani02.taka.anticheat.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class SpeedAir {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_SPEED_AIR))
			return false;
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 500)) {
				return false;
			}
		}
		
		if (from.distance(to) == 0)
			return false;
		
		if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
			p.setLastSpeedPotionTime(System.currentTimeMillis());
		} else {
			if (System.currentTimeMillis() - p.getLastSpeedPotionTime() < 500)
				return false;
		}
		
		if (p.getPlayer().isFlying())
			if (p.getPlayer().getFlySpeed() > 0.2F)
				return false;
		// Use FlyFastStartance one
		if(p.getLastGroundLocation().getBlock().getType().name().contains("PLATE")) {
			if (ConfigsMessages.step_basic_jump_pads_wooden)
				if (p.getLastPlateTouchType().equals(XMaterial.ACACIA_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.BIRCH_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.JUNGLE_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.DARK_OAK_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.OAK_PRESSURE_PLATE.parseMaterial())
						|| p.getLastPlateTouchType().equals(XMaterial.SPRUCE_PRESSURE_PLATE.parseMaterial())
								&& System.currentTimeMillis() - p.getLastPlateTouchTime() < 5000)
					return false;
			if (ConfigsMessages.step_basic_jump_pads_stone)
				if (XMaterial.STONE_PRESSURE_PLATE.parseMaterial().equals(p.getLastPlateTouchType())
						&& System.currentTimeMillis() - p.getLastPlateTouchTime() < 5000)
					return false;
			if (ConfigsMessages.step_basic_jump_pads_iron)
				if (XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial().equals(p.getLastPlateTouchType())
						&& System.currentTimeMillis() - p.getLastPlateTouchTime() < 5000)
					return false;
			if (ConfigsMessages.step_basic_jump_pads_gold)
				if (XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial().equals(p.getLastPlateTouchType())
						&& System.currentTimeMillis() - p.getLastPlateTouchTime() < 5000)
					return false;
		}
		
		if (Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| Utils.isOnStair(p.getPlayer())
				|| Utils.isOnHalfSlab(p.getPlayer().getLocation())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "WALL", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 2500
				|| System.currentTimeMillis() - p.getLastDamageTime() < 800
				|| (System.currentTimeMillis() - p.getLastTeleportTime() < 500 && System.currentTimeMillis() - p.getLastTacTeleportTime() > 500)
				|| System.currentTimeMillis() - p.getLastFlyTime() < Utils.mapNumbers(p.getPlayer().getFlySpeed(), 0.2,
						1, 2500, 10000)
				|| p.getPlayer().isInsideVehicle() || Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
				|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType()) || p.getPlayer().isFlying()
				|| p.isGliding() || Utils.isAroundLiquidLoc(p, p.getPlayer().getEyeLocation())
				|| p.getPlayer().isFlying() || p.isSwimming() || Utils.isOnSnowUltimate(p.getPlayer().getLocation(), false)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, true)
				|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("ICE")
				|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("SLIME") // Player is faster on slime
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
				|| p.getPlayer().getWalkSpeed() > 0.2F
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "BED", false, false)
				|| p.getLegitSpeedVL() > 0 && p.getLegitSpeedReason().contains("ice")
				|| !p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
			
			p.setSTP((short) (p.getSTP() + 1));
			
			return false;
		}
		
		if(System.currentTimeMillis() - p.getSTLT() > 1000) {
			p.setSTLT(System.currentTimeMillis());
			
			p.setSTP((short) 0);
			p.setSTD(0.0D);
		} else {
			p.setSTP((short) (p.getSTP() + 1));
			p.setSTD(p.getSTD() + Utils.get3DXZDisstance(from, to));
			
			// // Utils.debugMessage("SPEED: " + p.getSTP() + " &b" + Utils.get3DXZDisstance(from, to) + " " + !(from.getY() > to.getY()));
			// // Utils.debugMessage("SPEED: " + p.getSTP() + " " + p.getSTD() + " " + (System.currentTimeMillis() - p.getLastSprintEndTime()) + " " + p.isSprinting());
			
			double maxSpeed = 0.0D;
			
			if(p.isSprinting()) {
				if(p.getSTP() == 1) {
					maxSpeed = 0.6;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 0.6 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 2) {
					maxSpeed = 1.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 3) {
					maxSpeed = 1.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 4) {
					maxSpeed = 1.6;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.6 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 5) {
					maxSpeed = 2.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 6) {
					maxSpeed = 2.4;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.4 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 7) {
					maxSpeed = 2.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 8) {
					maxSpeed = 3.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 9) {
					maxSpeed = 3.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 10) {
					maxSpeed = 3.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 11) {
					maxSpeed = 4.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 12) {
					maxSpeed = 4.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 13) {
					maxSpeed = 4.8;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.8 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 14) {
					maxSpeed = 5.2;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 5.2 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 15) {
					maxSpeed = 5.6;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 5.6 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 16) {
					maxSpeed = 6.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 6.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 17) {
					maxSpeed = 6.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 6.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 18) {
					maxSpeed = 6.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 6.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 19) {
					maxSpeed = 7.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 7.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 20) {
					maxSpeed = 7.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 7.3 ? "&c " : "&a ") + p.getSTD());
				}
			} else if(System.currentTimeMillis() - p.getLastSprintEndTime() > 700) {
				if(p.getSTP() == 1) {
					maxSpeed = 0.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 0.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 2) {
					maxSpeed = 0.5;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 0.5 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 3) {
					maxSpeed = 0.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 0.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 4) {
					maxSpeed = 0.9;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 0.9 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 5) {
					maxSpeed = 1.2;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.2 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 6) {
					maxSpeed = 1.4;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.4 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 7) {
					maxSpeed = 1.6;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.6 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 8) {
					maxSpeed = 1.8;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 1.8 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 9) {
					maxSpeed = 2.0;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.0 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 10) {
					maxSpeed = 2.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 11) {
					maxSpeed = 2.5;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.5 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 12) {
					maxSpeed = 2.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 2.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 13) {
					maxSpeed = 3.1;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.1 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 14) {
					maxSpeed = 3.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 15) {
					maxSpeed = 3.5;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.5 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 16) {
					maxSpeed = 3.7;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.7 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 17) {
					maxSpeed = 3.9;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 3.9 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 18) {
					maxSpeed = 4.1;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.1 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 19) {
					maxSpeed = 4.3;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.3 ? "&c " : "&a ") + p.getSTD());
				} else if(p.getSTP() == 20) {
					maxSpeed = 4.5;
					// Utils.debugMessage("SPEED: " + p.getSTP() + (p.getSTD() > 4.5 ? "&c " : "&a ") + p.getSTD());
				}
			}
			
			// STP > 20
			if (maxSpeed <= 0)
				return false;
			
			// Block above makes player faster
			if(p.isSprinting() && p.getLegitSpeedVL() > 0 && p.getLegitSpeedReason().equalsIgnoreCase("block"))
				maxSpeed += 1.2D;
			
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
				// speed *= (PlayerUtils.getPotionEffect(p,
				// PotionEffectType.SPEED).getAmplifier() + 1) * speedPotion;
				maxSpeed *= (Utils.getPotionEffect(p.getPlayer(), PotionEffectType.SPEED).getAmplifier() + 1)
						* 1.6 + 1.3;
			} else if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SLOW)) {
				maxSpeed *= (Utils.getPotionEffect(p.getPlayer(), PotionEffectType.SLOW).getAmplifier() + 1)
						* 0.05 + 1.3;
			}
			
			// Utils.debugMessage("BL: " + maxSpeed + " " + p.getLegitSpeedVL() + " " + p.getLegitSpeedReason() + (p.getSTD() > maxSpeed ? "&c " : "&a ") + p.getSTD() + " " + p.getSTP());
			
			// Utils.debugMessage("SPEED VL: " + p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(HackType.MOVING_SPEED_AIR)) + " " + p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.UP).getType() + " " + maxSpeed + " " + p.getSTD());
			
			if(p.getSTD() > maxSpeed) {
				Speed.law(p, maxSpeed, p.getSTD(), HackType.MOVING_SPEED_AIR, from, to, 3.5);
			} else if(p.getSTD() < maxSpeed) {
				// TODO: Make better removeVL algorithm
				double rem = (maxSpeed - p.getSTD()) * 1.5D;
				if(rem > 1)
					rem = 1;
				
				p.removeViolation(HackType.MOVING_SPEED_AIR, ViolationType.CANCELMOVE, (short) rem);
			}
		}
		
		return false;
	}
	
}