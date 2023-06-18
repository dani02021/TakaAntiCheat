package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class TimerBACKUP {
	public static boolean check(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_TIMER))
			return false;
		if (p.getPlayer().isFlying() || p.isGliding()
				|| Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
			resetTimer(p);
			return false;
		}

		// Well not good way to fix the laggy servers issue, but for now kinda works
		if (Utils.getTPS() <= 15) {
			resetTimer(p);
			return false;
		}

		if (System.currentTimeMillis() - p.getTTime() >= 500) {
			if(p.getTTL() == null) {
				resetTimer(p);
				return false;
			}
			
			// Dealing with lagg - TEST
			if(System.currentTimeMillis() - p.getTTime() >= 800) {
				resetTimer(p);
				return false;
			}
			
			byte retVal = checkIt(p);

			if (retVal == 1) {
				if (p.reportToAdmin(HackType.MOVING_TIMER, "timer: " + p.getMovePacketTimer() + " " + Utils.get3DXZDisstance(p.getTTL() == null ? from : p.getTTL(), from) + " t: "
						+ (System.currentTimeMillis() - p.getTTime())))
					p.teleport(p.getTTL(), HackType.MOVING_TIMER);
			} else if (retVal == 2)
				if(p.reportToAdmin(HackType.MOVING_TIMER, "speed: " + Utils.get3DXZDisstance(p.getTTL() == null ? from : p.getTTL(), from) + " t: "
						+ (System.currentTimeMillis() - p.getTTime()) + " g: " + p.getPlayer().isOnGround() + " s: " + p.isSprinting()))
					p.teleport(p.getTTL(), HackType.MOVING_TIMER);
			
			resetTimer(p);

			return true;
		} else {
			// TTL - Timer Teleport Location
			if (p.getTTL() == null)
				p.setTTL(p.getPlayer().getLocation());
			
			// When player lags, usually he pushes all packets at once, which can cause problems with the check
			if(System.currentTimeMillis() - p.getLastPositionPacketTime() == 0) {
				return false;
			}
			
			// TD - Timer Distance
			p.setTD(p.getTD() + Utils.get3DXZDisstance(from, to));
			
			p.addMovePacketTimer((short) 1);
			
			//Utils.debugMessage("TIMER: add" + p.getTD() + " p: " + p.getMovePacketTimer());
		}

		return false;
	}

	private static byte checkIt(CheatPlayer p) {
		if(p.getTTL() == null)
			return 0;
		
		// Timer packet check
		//Utils.debugMessage("TIMER: " + p.getTD());
		if(p.isSwimming() || System.currentTimeMillis() - p.getLastSwimingTime() < 1000) { // Swimming does more packets and movement
			if (p.getMovePacketTimer() >= 24) {
				if (Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) > 5.2)
					return 1;
			}
		} else {
			if (p.getMovePacketTimer() >= 19) {
				if (Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) > 2.8)
					return 1;
			}
		}
		
		if (Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) > 2.2) { // Timer speed check
			
			//Utils.debugMessage("TIMER: " + Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) + " " + 
			//		p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("ICE") + " " +
			//		p.isSprinting() + " " + (System.currentTimeMillis() - p.getLastSprintEndTime()) + " " +
			//		p.getLegitSpeedVL() + " " + p.getLegitSpeedReason());
			
			if(p.getPlayer().isOnGround()) {
				if(p.isSprinting()) {
					if(p.getLegitSpeedVL() > 0) {
						if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 4.0)
							return 0;
					} else if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 4.0)
							return 0;
				} else {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 3.5 ||
							System.currentTimeMillis() - p.getLastSprintEndTime() < 600)
						return 0;
				}
			} else {
				if(p.isSprinting()) {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 4.3)
						return 0;
				} else {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 4.3)
						return 0;
				}
			}
			
			if(p.getLegitSpeedVL() > 0 &&
					p.getLegitSpeedReason().contains("ice")) {
				if(Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0), 0.35) != null ||
						p.getLegitSpeedReason().equalsIgnoreCase("ice_block")) {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 9.0)
						return 0;
				} else if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 5.2)
					return 0;
			}
			
			if(p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("ICE"))
				if(p.isSprinting()) {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 5.2)
						return 0;
				} else {
					if(Utils.get3DXZDisstance(p.getTTL(), p.getPlayer().getLocation()) < 5.0)
						return 0;
				}
			
			if (p.getLastGroundLocation().getBlock().getType().name().contains("PLATE")) {
				if (ConfigsMessages.step_basic_jump_pads_wooden)
					if (p.getLastPlateTouchType().equals(XMaterial.ACACIA_PRESSURE_PLATE.parseMaterial())
							|| p.getLastPlateTouchType().equals(XMaterial.BIRCH_PRESSURE_PLATE.parseMaterial())
							|| p.getLastPlateTouchType().equals(XMaterial.JUNGLE_PRESSURE_PLATE.parseMaterial())
							|| p.getLastPlateTouchType().equals(XMaterial.DARK_OAK_PRESSURE_PLATE.parseMaterial())
							|| p.getLastPlateTouchType().equals(XMaterial.OAK_PRESSURE_PLATE.parseMaterial())
							|| p.getLastPlateTouchType().equals(XMaterial.SPRUCE_PRESSURE_PLATE.parseMaterial()))
						return 0;
				if (ConfigsMessages.step_basic_jump_pads_stone)
					if (p.getLastPlateTouchType().equals(XMaterial.STONE_PRESSURE_PLATE.parseMaterial()))
						return 0;
				if (ConfigsMessages.step_basic_jump_pads_iron)
					if (p.getLastPlateTouchType().equals(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial()))
						return 0;
				if (ConfigsMessages.step_basic_jump_pads_gold)
					if (p.getLastPlateTouchType().equals(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial()))
						return 0;
			}
			
			if(p.getPlayer().getLocation().getBlock().isLiquid())
				return 0;
			
			if (Utils.getServerVersion() >= 2) {
				if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
						(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
								System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 500)) {
					return 0;
				}
			}
			
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
				p.setLastSpeedPotionTime(System.currentTimeMillis());
			} else {
				if (System.currentTimeMillis() - p.getLastSpeedPotionTime() < 500)
					return 0;
			}
			
			if (p.getPlayer().isFlying())
				if (p.getPlayer().getFlySpeed() > 0.2F)
					return 0;
			
			if(p.getPlayer().getWalkSpeed() > 1F)
				return 0;
			
			if (Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
					XMaterial.CAKE.parseMaterial())
			|| Utils.isOnStair(p.getPlayer())
			|| Utils.isOnHalfSlab(p.getPlayer().getLocation())
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "WALL", false, false)
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, false)
			|| System.currentTimeMillis() - p.getLastGlideEndTime() < 2500
			|| System.currentTimeMillis() - p.getLastDamageTime() < 800 || Utils.isOnSnow(p.getPlayer())
			|| (System.currentTimeMillis() - p.getLastTeleportTime() < 500 && System.currentTimeMillis() - p.getLastTacTeleportTime() > 500)
			|| System.currentTimeMillis() - p.getLastFlyTime() < 1000
			|| p.getPlayer().isInsideVehicle() || Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation())
			|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType()) || p.getPlayer().isFlying()
			|| p.isGliding() || Utils.isAroundLiquidLoc(p, p.getPlayer().getEyeLocation())
			|| p.isSwimming() || p.getPlayer().getAllowFlight() // Flying with speed and then stop and falling on the ground can cause big speed
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, true)
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, true)
			|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().name().contains("SLIME") // Player is faster on slime
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
			|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "BED", false, false)
			|| !p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
				// TODO: HAS NOT BEEN TESTED
				return 0;
			}
			
			return 2;
		}
		
		return 0;
	}
	
	public static void resetTimer(CheatPlayer p) {
		p.setMovePacketTimer((short) 0);
		p.setTTime(System.currentTimeMillis());
		p.setTTL(null);
		p.setTD(0.0D);
	}
}
