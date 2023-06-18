package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class LowJump {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_LOW_JUMP))
			return false;
		// Well the player can't jump less than 1.2 blocks
		if (Utils.isInWeb(p, p.getPlayer().getLocation(), true)
				|| p.isGliding() || p.getPlayer().isFlying() || p.getPlayer().getAllowFlight()
				|| p.getPlayer().isInsideVehicle() || Utils.isOnLadder(p.getPlayer()) || Utils.isOnHalfSlab(from)
				|| Utils.isOnStair(from) || Utils.isOnHalfSlab(to) || System.currentTimeMillis() - p.getLastDeathTime() < 1000
				|| System.currentTimeMillis() - p.getLastBedLeaveTime() < 1000 || System.currentTimeMillis() - p.getLastVehicleExitTime() < 1000
				|| p.getLastGroundLocation().clone().subtract(0, 0.1, 0).getBlock().getType()
						.equals(Material.SLIME_BLOCK)
				|| p.getLastGroundLocation().clone().subtract(0, 0.21, 0).getBlock().getType()
						.name().contains("BED")
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation(),
						XMaterial.ENCHANTING_TABLE.parseMaterial())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "GRASS_PATH", false, true)
				|| p.getPlayer().getLocation().clone().subtract(0,0.2,0).getBlock().getType() == XMaterial.FARMLAND.parseMaterial()
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().clone().subtract(0, 0.06250, 0).getBlock(), "CARPET", false, false)
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation().clone()) || Utils.isOnIce(p.getPlayer())
				|| Utils.isOnLadder2(p.getPlayer().getLocation()) || Utils.isOnLadder2(p.getPlayer().getEyeLocation())
				|| System.currentTimeMillis() - p.getLastDamageTime() < 1500 || from.clone().subtract(0,0.2,0).getBlock().getType().equals(Material.SOUL_SAND)
				|| System.currentTimeMillis() - p.getLastRespawnTime() < 1500
				|| Utils.isNextToBlock(p, from, Material.BREWING_STAND) || Utils.isNextToBlock(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.BREWING_STAND)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, false)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, false)
				
				|| Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.5, 0), 0.3) != null
				|| Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.2, 0), 0.3) != null
				|| !p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
			p.setLJC(p.getLJC() + 1);
			return false;
		}

		if (Utils.getServerVersion() >= 2)
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION))
				return false;
		
		if(Utils.isUsingPotionEffect(p.getPlayer(), "SLOW_FALLING"))
			return false;

		if (p.getLastGroundLocation().distance(from) == 0) {
			if (p.getServerFallDistance() > 0 && p.getServerFallDistance() < 0.9F) {
				if (!p.getLJP()) {
					p.setLJP(true);

					return false;
				}

				if (p.reportToAdmin(HackType.MOVING_LOW_JUMP)) {
					p.teleportToGround(HackType.MOVING_LOW_JUMP);
					return true;
				}
			} else {
				// Flat move on ground, no hack
				
				p.setLJC(p.getLJC() + 1);

				if (p.getLJC() >= 10) {
					p.removeViolation(HackType.MOVING_LOW_JUMP,
							ViolationType.CANCELMOVE,
							p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(HackType.MOVING_LOW_JUMP)));

					p.setLJC(0);
				}
			}
		} else {
			p.setLJP(false);
		}

		return false;
	}
}
