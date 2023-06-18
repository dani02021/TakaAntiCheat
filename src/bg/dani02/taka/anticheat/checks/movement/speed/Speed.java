package bg.dani02.taka.anticheat.checks.movement.speed;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.MoveDirectionType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class Speed {
	public static HashMap<String, Object[]> tpBack = new HashMap<String, Object[]>();
	
	public static Vector prevVel;
	
	// SpeedVL Weight:
	// (INFO): SpeedVL reasons should have weight, which means that if new reason have less weight, it doesn't change speedVL
	// ice_block
	// ice_jump
	// ice
	// block
	// jump
	// others are not weighted

	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkPlayer(p))
			return false;
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 500)) {
				return false;
			}
		}

		if (System.currentTimeMillis() - p.getLastDamageTime() < 500 && (p.getPlayer().getLastDamageCause() != null
				&& p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK)))
			return false;
		// Somewhat fix of speed problem where it is teleporting you far far away no
		// matter if you used hack or no
		if (tpBack.containsKey(p.getPlayer().getName()))
			if (System.currentTimeMillis() - (Long) tpBack.get(p.getPlayer().getName())[1] >= 10 * 1000)
				tpBack.remove(p.getPlayer().getName());
		
		// Getting damage with high velocity can trigger some false positives
		// but can also open up the door to bypasses
		if(System.currentTimeMillis() - p.getLastVelocityTime() < 500)
			return false;
		
		if(p.getLastDamager() != null && p.getLastDamager() instanceof LivingEntity)
			if(((LivingEntity)p.getLastDamager()).getEquipment().getItemInHand().containsEnchantment(Enchantment.KNOCKBACK) ||
					((LivingEntity)p.getLastDamager()).getEquipment().getItemInHand().containsEnchantment(Enchantment.ARROW_KNOCKBACK))
				return false;

		// Not good way to fix the false positive, but a working one
		if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
			p.setLastSpeedPotionTime(System.currentTimeMillis());
		} else {
			if (System.currentTimeMillis() - p.getLastSpeedPotionTime() < 500)
				return false;
		}
		
		final double dX = from.getX() - to.getX();
		final double dZ = from.getZ() - to.getZ();
		final double dY = from.getY() - to.getY();
		final double dist = (dX * dX + dZ * dZ) / 0.1;
		final double distY = (dY * dY) / 0.1;
		
		double maxSpeed = 0D;
		HackType check = null;
		
		if(p.getPlayer().isInsideVehicle())
			SpeedVehicle.onCheck(p, from, to, dist, distY);
		
		if (p.getPlayer().isInsideVehicle() || Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", false, false)) // When piston is extended speed is called
			return false;
		
		if(System.currentTimeMillis() - p.getLastVehicleTime() < 500)
			return false;

		// FULLY IMMPOSIBLE IN NOTCH CLIENT
		if (dist > 50) {
			if (p.getPlayer().isFlying())
				if (p.getPlayer().getFlySpeed() > 0.2F)
					return false;
			if(p.getPlayer().getWalkSpeed() > 1F)
				return false;
			// Use FlyFastStartDistance one
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
		}
		
		// Should be here ???
		if (!p.haveBypass(HackType.MOVING_SPEED_ONGROUND)) {
			if (System.currentTimeMillis() - p.getLastFlyTime() < Utils.mapNumbers(p.getPlayer().getFlySpeed(), 0.2,
					1, 350, 1350))
				return false;
		}
		
		if(p.getLegitSpeedVL() > 0)
			p.setLegitSpeedVL((short) (p.getLegitSpeedVL() - 1), p.getLegitSpeedReason());

		if (from.clone().subtract(0, 0.3, 0).getBlock().getType().name().contains("ICE")
				&& to.clone().subtract(0, 0.1, 0).getBlock().isEmpty()) {
			if(!p.getLegitSpeedReason().equalsIgnoreCase("ice_jump") &&
					!p.getLegitSpeedReason().equalsIgnoreCase("ice_block") ||
					p.getLegitSpeedVL() == 0) // Giving fps otherwise
				p.setLegitSpeedVL((short) 14, "ice");
		}
		
		if(checkHasBlockOverPlayer(p.getPlayer().getEyeLocation().add(0,0.3,0)) ||
				checkHasBlockOverPlayer(p.getPlayer().getEyeLocation().add(0,0.124,0))) // 0.125 is redstone height - smallest block
			if (Utils.getBlocksAroundByName(from.getBlock().getRelative(BlockFace.DOWN).getLocation(), "ICE")) {
				p.setLegitSpeedVL((short) 25, "ice_block");
			} else {
				if(!p.getLegitSpeedReason().equalsIgnoreCase("ice_jump") &&
						!p.getLegitSpeedReason().equalsIgnoreCase("ice_block") &&
						!p.getLegitSpeedReason().equalsIgnoreCase("ice") ||
						p.getLegitSpeedVL() == 0)
					p.setLegitSpeedVL((short) 15, "block");
			}

		if (from.distance(to) == 0)
			return false;
		
		SpeedAir.onCheck(p, from, to); // Actually Speed Timer
		
		//Utils.debugMessage("SPEED: " + dist + " " + distY);
		//Utils.debugMessage("SPEED_vel: &c" + p.getPlayer().getVelocity().getZ() + " &b" + p.getPlayer().getVelocity().getY());
		//Utils.debugMessage("SPEED_vel_diff: &c" + (p.getLastVelocity().getZ() - p.getPlayer().getVelocity().getZ()));
		//Utils.debugMessage("SPEED_vel_angle: &b" + Utils.getAngle2(p.getLastVelocity(), p.getPlayer().getVelocity()));
		//Utils.debugMessage("SPEED z_slope: &e" + (p.getLastFromToLocations()[0].getZ() / to.getZ()));

		// Checks Start
		// p.getPlayer().sendMessage("A: "+(to.getY()-from.getY())+" "+dist);
		
		if (Utils.getYDisstance(from, to) <= 0.08
				&& Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0), 0.35) == null) {
			if (!p.getPlayer().getLocation().getBlock().isLiquid()
					&& p.getPlayer().getLocation().clone().subtract(0, 0.5, 0).getBlock().isLiquid()
					&& Utils.getYDisstance(from, to) > 0) {
				if(!p.haveBypass(HackType.MOVING_SPEED_LIQUID_WATER) && !p.haveBypass(HackType.MOVING_SPEED_LIQUID_LAVA)) {
					maxSpeed = 2.0;
					if (p.haveBypass(HackType.MOVING_SPEED_LIQUID_WATER) || p.haveBypass(HackType.MOVING_SPEED_LIQUID_LAVA))
						check = p.haveBypass(HackType.MOVING_SPEED_LIQUID_WATER) ? HackType.MOVING_SPEED_LIQUID_WATER
								: HackType.MOVING_SPEED_LIQUID_LAVA;
				}
			}
			if (p.getLegitSpeedVL() > 0 && !p.getLegitSpeedReason().equalsIgnoreCase("block")) {
				// p.setLegitSpeedVL((short) (p.getLegitSpeedVL() - 1), p.getLegitSpeedReason());
			} else {
				if (System.currentTimeMillis() - p.getLastAttackTime() > 2000) {
					if (Utils.checkModule(p, HackType.MOVING_SPEED_ONGROUND)) {
						check = HackType.MOVING_SPEED_ONGROUND;
						// Check OnGround
						if (!Utils.isOnStair(p.getPlayer()) && !Utils.isOnHalfSlab(from)
								&& !Utils.getBlocksAroundByName(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), "ICE")
								&& !Utils.getBlocksAroundByName(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getLocation(), "ICE")
								&& !Utils.getBlocksAroundByName(p.getPlayer().getLocation(), "ICE")
								&& !Utils.isOnHalfSlab(to) && !Utils.isNextToBlockByNameAround(p, from.getBlock().getRelative(BlockFace.DOWN), "FENCE", true, true)
								&& !p.getPlayer().getLocation().getBlock().isLiquid()
								&& !p.getPlayer().getEyeLocation().getBlock().isLiquid()
								&& !Utils.isWaterPlant(p.getPlayer().getLocation().getBlock().getType())
								&& !Utils.checkCollision_TEST_A(p)) {
							// Detecting backwards spring and such, should check only yaw angle
							if (Utils.getYawAngle(to, from, p.getPlayer().getLocation().getDirection()) > 55) {
								if (Utils.getYawAngle(to, from, p.getPlayer().getLocation().getDirection()) > 105) {
									if (!p.getPlayer().isSneaking())
										maxSpeed = 0.47;
									else
										maxSpeed = 0.12;
									
									if (p.isSprinting()) // You can't sprint while moving backwards
										maxSpeed = 0.12;

									/*
									 * // There is a bypass if (p.isSprinting()) maxSpeed = 0.83;
									 */
									 
								} else {
									if (p.isSprinting()) {
										maxSpeed = 0.83;
									} else {
										if (!p.getPlayer().isSneaking())
											maxSpeed = 0.489;
										else {
											if (System.currentTimeMillis() - p.getLastSprintEndTime() < 600)
												maxSpeed = 0.1;
										}
									}
								}
							} else {
								if (p.isSprinting()) {
									if (p.getPlayer().isSneaking())
										maxSpeed = 0.1;
									else
										maxSpeed = 0.79;
								} else {
									if (!p.getPlayer().isSneaking())
										maxSpeed = 0.468;
									else {
										if (System.currentTimeMillis() - p.getLastSprintEndTime() > 600)
											maxSpeed = 0.14;
									}
								}
							}
							
							// p.getPlayer().sendMessage("sp: "+dist+" "+p.getPlayer().isBlocking());

							if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
									&& p.getPlayer().getWalkSpeed() <= 0.2F) {
								check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
								maxSpeed = 0.489;
							}
							
							if(p.getLegitSpeedVL() > 0)
								if(p.getLegitSpeedReason().contains("ice") ||
										p.getLegitSpeedReason().contains("block"))
									maxSpeed = 0;
						}
					}
				}
				
				// p.getPlayer().sendMessage("A: "+ dist + " " + maxSpeed + " " +
				// p.isSprinting());
				// p.getPlayer().sendMessage("sp: "+Utils.getAngle(to, from,
				// p.getPlayer().getLocation()));
				if (Utils.checkModule(p, HackType.MOVING_SPEED_ITEM)) {
					if (p.getPlayer().getItemInHand().getType().equals(Material.BOW)
							|| Utils.isFood(p.getPlayer().getItemInHand().getType())
							|| p.getPlayer().getItemInHand().getType().equals(XMaterial.POTION.parseMaterial())
							|| Utils.isBlockable(p.getPlayer().getItemInHand().getType())) {
						if (p.getUseItemSpeed() || p.isBlocking()) {
							if (System.currentTimeMillis() - p.getLastDamageTime() < 1500)
								return false;
							int max = 150;
							double max1 = 0.1;
							if (p.getPlayer().getWalkSpeed() <= 1F
									&& !Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
								max = (int) Utils.mapNumbers(p.getPlayer().getWalkSpeed(), 0.2, 1, 150, 350);
								max1 = Utils.mapNumbers(p.getPlayer().getWalkSpeed(), 0.2, 1, max1, 0.5);
							}
							if (System.currentTimeMillis() - p.getLastSprintEndTime() < max)
								return false;
							check = HackType.MOVING_SPEED_ITEM;
							maxSpeed = max1;
							// Utils.debugMessage("SPEED ITEM: " + maxSpeed + " " + dist + " " + p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(HackType.MOVING_SPEED_ITEM)) + " " + p.getLegitSpeedReason() + " " + p.getLegitSpeedVL());
						}
					}
				}

				if (Utils.checkModule(p, HackType.MOVING_SPEED_ONGROUND_SLIME)) {
					if (p.getPlayer().getLocation().clone().subtract(0, 0.2, 0).getBlock().getType().equals(
							Material.SLIME_BLOCK) && p.getLastMoveDirectionType() == MoveDirectionType.HORIZONTAL) {
						maxSpeed = 0.14;
						check = HackType.MOVING_SPEED_ONGROUND_SLIME;
					}
				}

				if (Utils.checkModule(p, HackType.MOVING_SPEED_ONGROUND_ICE)) {
					if (p.getPlayer().getLocation().clone().subtract(0, 0.3, 0).getBlock().getType().name()
							.contains("ICE")) {
						if(Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0),
								0.35) == null &&
								Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.3, 0),
										0.35) == null) {
							check = HackType.MOVING_SPEED_ONGROUND_ICE;
							if(p.getLegitSpeedVL() == 0) {
								if (Utils.getAngle(to, from, p.getPlayer().getLocation().getDirection()) > 55) {
									if (Utils.getAngle(to, from, p.getPlayer().getLocation().getDirection()) > 200) {
										maxSpeed = 0.435;
									}
									if (p.isSprinting()) {
										maxSpeed = 0.772;
									} else {
										maxSpeed = 0.455;
									}
								} else {
									if (p.isSprinting()) {
										maxSpeed = 0.7318;
									} else {
										maxSpeed = 0.435;
									}
								}
							}
						}
					}
				}
				if (Utils.checkModule(p, HackType.MOVING_SPEED_ONGROUND_SOULSAND)) {
					if (p.getPlayer().getLocation().clone().subtract(0, 0.3, 0).getBlock().getType()
							.equals(Material.SOUL_SAND) ||
							p.getPlayer().getLocation().clone().subtract(0, 0.3, 0).getBlock().getType().name()
							.contains("SOUL_SOIL")
							&& !Utils.isNextToBlockByNameAround(p, from.getBlock(), "FENCE", false, true)) {
						if (p.getPlayer().getLocation().getY() % 1 == 0)
							return false;
						
						check = HackType.MOVING_SPEED_ONGROUND_SOULSAND;
						
						if (Utils.getAngle(to, from, p.getPlayer().getLocation().getDirection()) > 55) {
							if (!p.getPlayer().isSneaking())
								maxSpeed = 0.17;
							else
								maxSpeed = 0.14;
						}
						if (p.isSprinting()) {
							maxSpeed = 0.29;
						} else {
							if (!p.getPlayer().isSneaking())
								maxSpeed = 0.17;
							else
								maxSpeed = 0.12;
						}

						if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
								&& p.getPlayer().getWalkSpeed() <= 0.2F) {
							check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
							maxSpeed = 0.14;
						}

						if (p.getPlayer().isBlocking()) {
							maxSpeed = 0.11;
						}
						
						if(Utils.getServerVersion() >= 9) {
							if(p.getPlayer().getInventory().getBoots() != null) {
								if(p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED)) {
									int enchantLevel = p.getPlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.SOUL_SPEED);
									
									if(p.getPlayer().isSprinting()) {
										maxSpeed = 0.35 + (1.0 + enchantLevel * 0.44);
									} else {
										// The formula is from the wiki
										maxSpeed = 0.17 + (0.9 + enchantLevel * 0.125);
									}
								}
							}
						}
					}
				}

				if (check != HackType.MOVING_SPEED_ITEM) {
					if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
						// speed *= (PlayerUtils.getPotionEffect(p,
						// PotionEffectType.SPEED).getAmplifier() + 1) * speedPotion;
						maxSpeed *= (Utils.getPotionEffect(p.getPlayer(), PotionEffectType.SPEED).getAmplifier() + 1)
								* 1.6 + 1.3;
					} else if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SLOW)) {
						maxSpeed *= (Utils.getPotionEffect(p.getPlayer(), PotionEffectType.SLOW).getAmplifier() + 1)
								* 0.05 + 1.3;
					}
				}
			}
		} else if (from.getY() > to.getY()) {
			if (Utils.checkModule(p, HackType.MOVING_SPEED_ITEM)) {
				// TODO
				if (p.getUseItemSpeed() && (p.getPlayer().getItemInHand().getType().equals(Material.BOW)
						|| Utils.isFood(p.getPlayer().getItemInHand().getType()))) {
					double max = 150;
					if (p.getPlayer().getWalkSpeed() <= 1F
							&& !Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
						max = (int) Utils.mapNumbers(p.getPlayer().getWalkSpeed(), 0.2, 1, 150, 400);
					}
					if (System.currentTimeMillis() - p.getLastSprintEndTime() < max)
						return false;
					check = HackType.MOVING_SPEED_ITEM;
					maxSpeed = 0.43;
				}
			}
//			if (Utils.checkModule(p, HackType.MOVING_SPEED_AIR))
//				if (SpeedAir2.onCheck(p, p.getUpwardsSpeed(), false, from))
//					return true;
			// checkIt
			// p.getPlayer().sendMessage("lol "+(from.getY()-to.getY()));
			
			if (from.getY() - to.getY() != 0.4000000000000057
					&& !Utils.isNextToLiquidBlock(p.getPlayer().getLocation()))
				if (!to.clone().subtract(0, 0.2, 0).getBlock().isEmpty() &&
						(!p.getLegitSpeedReason().equalsIgnoreCase("block") &&
						!p.getLegitSpeedReason().equalsIgnoreCase("ice_jump") &&
						!p.getLegitSpeedReason().equalsIgnoreCase("ice_block") &&
						!p.getLegitSpeedReason().equalsIgnoreCase("ice") ||
						p.getLegitSpeedVL() == 0)) {
					p.setLegitSpeedVL((short) 12, "jump");// 5 is sometimes giving fps because of '0.4000000000000057'
				}
			
			// When a block is over your head, it is pretty tough to detect if he is jumping or not,
			// so if a block is above, automatically set ice_jump
			if ((from.clone().subtract(0, 0.1, 0).getBlock().isEmpty() ||
					Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.3, 0), 0.3) != null)
					&& to.clone().subtract(0, 0.2, 0).getBlock().getType().equals(Material.ICE) && dist > 0.7 &&
					(!p.getLegitSpeedReason().equalsIgnoreCase("ice_block") ||
					p.getLegitSpeedVL() == 0)) {
				p.setLegitSpeedVL((short) 16, "ice_jump");
			}
		} else if (to.getY() > from.getY()) {
			// SpeedAir2.onCheck(p, dist, true, from);
			
			// Jumping on soulsand
			if (Utils.checkModule(p, HackType.MOVING_SPEED_SOULSAND)) {
				if((p.getLastGroundLocation().clone().subtract(0,0.1,0).getBlock().getType().equals(XMaterial.SOUL_SAND.parseMaterial()) ||
						p.getPlayer().getLocation().clone().subtract(0, 0.3, 0).getBlock().getType().name().contains("SOUL_SOIL")) &&
						p.getLastGroundLocation().getY() % 1 != 0 &&
						p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().isEmpty()
						&& !Utils.isNextToBlockByNameAround(p, from.getBlock(), "FENCE", false, true)) {
					if(Utils.getServerVersion() >= 9) {
						if(p.getPlayer().getInventory().getBoots() != null) {
							if(p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED)) {
								// TODO Fix it, I am lazy
								return false;
							}
						}
					}
					
					if(p.getLegitSpeedVL() > 0 && p.getLegitSpeedReason().contains("ice")) {
						// p.setLegitSpeedVL((short) (p.getLegitSpeedVL() - 1), p.getLegitSpeedReason());
						p.removeViolation(HackType.MOVING_SPEED_SOULSAND, ViolationType.CANCELMOVE, (short) 1);
						return false;
					}
					
					if (p.getPlayer().getLocation().getY() % 1 == 0)
						return false;
					
					if(Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED) || p.getPlayer().isBlocking()) // Fix it, I am lazy : lol
						return false;
					
					if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
							&& p.getPlayer().getWalkSpeed() <= 0.2F) {
						check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
						maxSpeed = 0.14;
					}
					
					check = HackType.MOVING_SPEED_SOULSAND;
					
					if(p.isSprinting())
						maxSpeed = 0.65;
					else maxSpeed = 0.2;
				}
			}
			// Jumping on ice
			if (Utils.checkModule(p, HackType.MOVING_SPEED_ICE)) {
				if (p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("ICE")) {
					if(Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0),
							0.35) == null &&
							Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.3, 0),
									0.35) == null) {
						if(p.getLegitSpeedVL() != 0) {
							if(p.getLegitSpeedReason().equalsIgnoreCase("ice_jump")) {
								check = HackType.MOVING_SPEED_ICE;
								
								if(p.isSprinting())
									maxSpeed = 2.4D;
								else {
									if(System.currentTimeMillis() - p.getLastSprintEndTime() > 500)
										maxSpeed = 1.0D;
								}
								// The values are intentionaly lower, because vl alg doesnt add enough vl
								
								if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
										&& p.getPlayer().getWalkSpeed() <= 0.2F) {
									check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
									maxSpeed = 0.14;
								}
							}
						}
					}
				}
			}
			
			// Jumping on slime
			if (Utils.checkModule(p, HackType.MOVING_SPEED_SLIME)) {
				if (p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(XMaterial.SLIME_BLOCK.parseMaterial())) {
					if(Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0),
							0.35) == null &&
							Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.3, 0),
									0.35) == null) {
						check = HackType.MOVING_SPEED_SLIME;
						
						// Sprint: First jump is around 3.2D next ones are 1.9 and below
						// No Sprint: First jump is round 0.5D next ones are 0.35 and below
						
						if(p.isSprinting())
							maxSpeed = 2.1D;
						else
							maxSpeed = 0.8D;
					}
					else {
						check = HackType.MOVING_SPEED_SLIME;
						
						// Sprint: Can go up to 4.8D
						// No Sprint: Can go up to 0.6D
						
						if(p.isSprinting())
							maxSpeed = 2.8D;
						else
							maxSpeed = 0.5D;
					}
					
					if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
							&& p.getPlayer().getWalkSpeed() <= 0.2F) {
						check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
						maxSpeed = 0.14;
					}
				}
			}
		}
		// Liquid check
		if (to.getBlock().isLiquid() || from.getBlock().isLiquid()
				|| p.getPlayer().getLocation().clone().subtract(0, 0.5, 0).getBlock().isLiquid()) {
			if (System.currentTimeMillis() - p.getLastDamageTime() < 1000
					&& p.getPlayer().getLastDamageCause() != null &&
					!p.getPlayer().getLastDamageCause().getCause().equals(DamageCause.DROWNING))
				return false;
			if (Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.LILY_PAD.parseMaterial())
					|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "CARPET", false, false)
					|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "STEP", false, false)
					|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SLAB", false, false)
					|| Utils.isNextToBlock(p, p.getPlayer().getLocation().clone().subtract(0,0.2,0), XMaterial.FARMLAND.parseMaterial())
					|| Utils.getMaterialsAroundByName(p.getPlayer().getLocation().clone().subtract(0, 0.1, 0), "CARPET")
					|| Utils.getMaterialsAround(p.getPlayer().getLocation().clone().subtract(0, 0.1, 0))
							.contains(XMaterial.LILY_PAD.parseMaterial())) {
				if (p.getJVL() > 0)
					p.setJVL((short) (p.getJVL() - 1));
				return false;
			}
			if (!from.getBlock().isLiquid() /* && dist > 0.133 */ && p.getServerFallDistance() > 0.1)
				p.setLegitSpeedVL((short) (distY * 0.25 + 8), "liquid");
			if(p.getPlayer().getWalkSpeed() - 0.2f > 0.1f)
				p.setLegitSpeedVL((short) (dist * (3.5 + p.getPlayer().getWalkSpeed())), "liquid");
			if (p.isRiptiding() || System.currentTimeMillis() - p.getLastRiptileTime() < 500)
				return false;
			if (p.getPlayer().getLocation().getBlock().getType().name().contains("WATER")) {
				if (!Utils.checkModule(p, HackType.MOVING_SPEED_LIQUID_WATER))
					return false;
				if (p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
						.contains("FENCE")
						|| p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
								.contains("WALL"))
					return false;
				if (p.getPlayer().getLocation().getBlock().getData() > 0) {
					if (p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
						p.removeViolation(HackType.MOVING_SPEED_LIQUID_WATER, ViolationType.CANCELMOVE, (short) 2);
						return false;
					}
				}
				if (!to.getBlock().isLiquid() && !from.getBlock().isLiquid()) {
					if (Utils.isOnGround(p.getPlayer().getLocation()) != null)
						for (Block b : Utils.isOnGround(p.getPlayer().getLocation())) {
							if (!b.isLiquid() && !b.isEmpty())
								return false;
						}
				}
				check = HackType.MOVING_SPEED_LIQUID_WATER;
				if (p.getLegitSpeedVL() > 0) {
					p.setLegitSpeedVL((short) (p.getLegitSpeedVL() - 1), p.getLegitSpeedReason());
					return false;
				}
				//check Y distance
				SpeedWaterY.onCheck(p, distY, to.getY() > from.getY(), from, to);
				
				if(p.isSprinting())
					maxSpeed = 0.41;
				else maxSpeed = 0.14;
				if (p.getPlayer().getInventory().getBoots() != null)
					if (p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.DEPTH_STRIDER)) {
						maxSpeed = 0;
						if (p.getPlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.DEPTH_STRIDER) == 1) {
							if(p.isSprinting())
								maxSpeed = 0.655;
							else maxSpeed = 0.261;
						} else if (p.getPlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.DEPTH_STRIDER) == 2) {
							if(p.isSprinting())
								maxSpeed = 0.751;
							else  maxSpeed = 0.381;
						} else if (p.getPlayer().getInventory().getBoots().getEnchantmentLevel(Enchantment.DEPTH_STRIDER) == 3) {
							if(p.isSprinting())
								maxSpeed = 0.792;
							else  maxSpeed = 0.471;
						}
					}
				if(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), "DOLPHINS_GRACE")) // TODO: Test values
					maxSpeed = 0;
			} else if (p.getPlayer().getLocation().getBlock().getType().name().contains("LAVA")) {
				if(!Utils.checkModule(p, HackType.MOVING_SPEED_LIQUID_LAVA))
					return false;
				if (p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
						.contains("FENCE")
						|| p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
								.contains("WALL"))
					return false;
				check = HackType.MOVING_SPEED_LIQUID_LAVA;
				if(p.isSprinting())
					maxSpeed = 0.415;
				else maxSpeed = 0.14; // Not tested
			}
		}
		// CobWeb
		if (Utils.checkModule(p, HackType.MOVING_SPEED_COBWEB)) {
			if (p.getPlayer().getLocation().getBlock().getType().equals(XMaterial.COBWEB.parseMaterial())
					|| p.getPlayer().getEyeLocation().getBlock().getType().equals(XMaterial.COBWEB.parseMaterial())) {
				if(p.getLastFromToLocations()[0].getBlock().getType() != XMaterial.COBWEB.parseMaterial() &&
						p.getLastFromToLocations()[0].getBlock().getRelative(BlockFace.UP).getType() != XMaterial.COBWEB.parseMaterial()) {
					p.setLegitSpeedVL((short) 4, "cobweb");
				} else {
					if(p.getLegitSpeedVL() != 0 && p.getLegitSpeedReason().equalsIgnoreCase("cobweb")) {
						p.setLegitSpeedVL(p.getLegitSpeedVL(), "cobweb");
					} else {
						check = HackType.MOVING_SPEED_COBWEB;

						if (p.isSprinting()) {
							if (from.getY() == to.getY())
								maxSpeed = 0.11;
							else
								maxSpeed = 0.145;
						} else {
							maxSpeed = 0.11;
						}

						// LiquidBounce webwalk rewi bypass
						if (System.currentTimeMillis() - p.getSCT() == 350 && to.getY() != from.getY()) {
							if (p.isSprinting())
								maxSpeed = 0.04;
							else
								maxSpeed = 0.1;
						}

						p.setSCT(System.currentTimeMillis());

						if (p.getPlayer().getFoodLevel() <= 5 && !p.getPlayer().isSneaking()
								&& p.getPlayer().getWalkSpeed() <= 0.2F) {
							check = HackType.MOVING_SPEED_SPRINT_HUNGRY;
							maxSpeed = 0.11;
						}
					}
				}
			}
		}

		// p.getPlayer().sendMessage("SP: "+dist+" "+maxSpeed+"
		// "+p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(HackType.MOVING_SPEED_AIR)));
		// If there is no check which support the move, then the result will be 0
		if (maxSpeed == 0 || check == null)
			return false;

		if (p.getPlayer().isFlying() || p.isGliding() || System.currentTimeMillis() - p.getLastFlyTime() < 1200) {
			tpBack.remove(p.getPlayer().getName());
			p.removeViolation(check, ViolationType.CANCELMOVE, (short) 10);
			return false;
		}

		// Checks Stop
		if (dist > maxSpeed) {
			return law(p, maxSpeed, dist, check, from, to);
		} else {
			// tpBack.remove(p.getPlayer().getName());
			p.removeViolation(check, ViolationType.CANCELMOVE, (short) 4);
		}
		return false;
	}
	
	protected static boolean law(CheatPlayer p, double maxSpeed, double dist, HackType check, Location from, Location to) {
		return law(p, maxSpeed, dist, check, from, to, 1);
	}

	protected static boolean law(CheatPlayer p, double maxSpeed, double dist, HackType check, Location from, Location to, double vlMultiplier) {
		// Essentials speed command
		if (check.name().contains("ONGROUND")) {
			if (p.getPlayer().getWalkSpeed() > 0.2F)
				return false;
			if (check == HackType.MOVING_SPEED_SPRINT_HUNGRY)
				if (p.getPlayer().getGameMode() == GameMode.CREATIVE ||
					!Utils.checkModule(p, HackType.MOVING_SPEED_SPRINT_HUNGRY))
					return false;
		}

		// Just to get sure
		if (maxSpeed > dist)
			return false;

		if (tpBack.containsKey(p.getPlayer().getName())) {
			short vl = 0;
			if (maxSpeed > 1.2)
				vl = (short) ((double) 2 * (dist - maxSpeed) * vlMultiplier);
			else {
				if (maxSpeed > 0.1)
					vl = (short) ((double) 2 * ((dist - maxSpeed) / 0.1) * vlMultiplier);
				else if (maxSpeed > 0.01)
					vl = (short) ((double) 2 * ((dist - maxSpeed) / 0.01) * vlMultiplier);
				else if (maxSpeed > 0.001)
					vl = (short) ((double) 2 * ((dist - maxSpeed) / 0.001) * vlMultiplier);
				else
					vl = (short) ((double) 2 * ((dist - maxSpeed) / 0.1) * vlMultiplier);
			}
			
			p.addViolation(check, ViolationType.CANCELMOVE, (vl));
			
			// Utils.debugMessage("vlMP: "+vl+" All: "+p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(check)));
			
			if (p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(check)) >= Short
					.parseShort(Utils.getMoveCancelViolation(check))) {
				
				Location tpBackCopy = (Location) tpBack.get(p.getPlayer().getName())[0];
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.reportToAdmin(check, "maxSpeed: " + maxSpeed + " speed: " + dist + " tpBack: " + tpBack.containsKey(p.getPlayer().getName()) +
								" angle: " + Utils.getYawAngle(to, from, p.getPlayer().getLocation().getDirection()));
						p.setLastTacTeleportTime(System.currentTimeMillis());
						p.teleport(tpBackCopy, check);
					}
				}.runTask(Taka.getThisPlugin());
				
				tpBack.remove(p.getPlayer().getName());
				return true;
			}
		} else {
			tpBack.put(p.getPlayer().getName(), new Object[] { from, System.currentTimeMillis() });
		}
		return false;
	}

	public static void onPlayerLeave(UUID uuid) {
		tpBack.remove(Taka.getThisPlugin().getServer().getPlayer(uuid).getName());
	}
	
	// Utils
	private static boolean checkHasBlockOverPlayer(Location overPlayerHead) {
		Location loc = overPlayerHead;
		/* Not working correctly, somehow the loc is 0.55 dist from the block, it should be 0.3...
		 * Location[] b = new Location[] { loc.clone().subtract(0.35, 0, 0),
		 * loc.clone().subtract(0, 0, 0.35), loc.clone().add(0.35, 0, 0),
		 * loc.clone().add(0, 0, 0.35), loc.clone().add(1, 0, 1), loc.clone().add(-1, 0,
		 * 1), loc.clone().add(1, 0, -1), loc.clone().add(-1, 0, -1), };
		 */
		
//		Location[] b = new Location[] { loc.clone().subtract(1, 0, 0), loc.clone().subtract(0, 0, 1),
//				loc.clone().add(1, 0, 0), loc.clone().add(0, 0, 1), loc.clone().add(1, 0, 1),
//				loc.clone().add(-1, 0, 1), loc.clone().add(1, 0, -1), loc.clone().add(-1, 0, -1), };
		
		// Minecraft is somewhat inacurate, should be 0.3D but because of this things gonna say 0.31D;
		double diff = 0.31D;
		Location[] b = new Location[] { loc, loc.clone().subtract(diff, 0, 0), loc.clone().subtract(0, 0, diff),
				loc.clone().add(diff, 0, 0), loc.clone().add(0, 0, diff), loc.clone().add(diff, 0, diff),
				loc.clone().add(-diff, 0, diff), loc.clone().add(diff, 0, -diff), loc.clone().add(-diff, 0, -diff) };
		for (Location blo : b) {
			Block bl = blo.getBlock();
			// Utils.debugMessage("AA: " + bl.getType() + " " + Utils.isSolid(bl) + " " + Utils.isSolid(bl.getRelative(BlockFace.UP).getType()));
			// Check also is the block is air, because if x block is air and x+1 is not air it is not detected
			if (Utils.isSolid(bl)) { // You can also jump on climable blocks
				if (bl.getRelative(BlockFace.DOWN).isEmpty() &&
						Utils.isPassableBlock(bl.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType()))
					return true;
				else {
					return false;
				}
			}
		}
		
		return false;
	}
}