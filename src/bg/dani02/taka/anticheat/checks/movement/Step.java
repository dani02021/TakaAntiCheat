package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class Step {
	/*
	 * Checking the y disstance, the player can't just straight 1 block up, its
	 * ~1.25 y diff on jump *Working only for 1 block step!
	 */
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_STEP))
			return false;
		if (Utils.isOnHalfSlab(from) || Utils.isNextToBlockByNameAround(p, from.getBlock(), "STAIRS", false, false)
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation()) || p.getPlayer().isFlying() || p.isGliding()
				|| Utils.isAroundLiquidLoc(p,
						p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation())
				|| System.currentTimeMillis() - p.getLastDamageTime() < 1500
				|| System.currentTimeMillis() - p.getLastVehicleTime() < 1000
				|| System.currentTimeMillis() - p.getLastVehicleExitTime() < 1000
				|| p.getPlayer().isInsideVehicle() || Utils.isOnScaffolding(p.getPlayer())
				|| !p.getPlayer().getNearbyEntities(1, 1, 1).isEmpty()
				|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType()) || Utils.isClimable(p.getPlayer().getEyeLocation().getBlock().getType())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "BED", false, false)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.DAYLIGHT_DETECTOR.parseMaterial())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "PISTON", true, true)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| Utils.isNextToBlock2(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						Material.LADDER, 0.4)
				|| Utils.isNextToBlock2(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						Material.VINE, 0.4)
				|| Utils.isNextToBlock2(p.getPlayer().getLocation().getBlock().getLocation(),
						Material.LADDER, 0.4)
				|| Utils.isNextToBlock2(p.getPlayer().getLocation().getBlock().getLocation(),
						Material.VINE, 0.4)
				|| Utils.isNextToBlock2(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						Material.SLIME_BLOCK, 0.4)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, true)
				|| Utils.isNextToBlock(p, from, Material.BREWING_STAND) || Utils.isNextToBlock(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.BREWING_STAND)
				|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(XMaterial.SLIME_BLOCK.parseMaterial())
				|| Utils.getAroundBlocks3(p.getPlayer().getEyeLocation().clone().add(0, 0.2, 0), 0.35) != null
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 1000
				|| !p.getPlayer().getEyeLocation().getBlock().isEmpty()) {
			p.removeSMoves(p.getSMoves());
			return false;
		}
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 1000)) {
				return false;
			}
		}
		
		boolean hack = false;
		if (from.getY() < to.getY()) {
			p.addSMoves(to.getY() - from.getY());
		} else {
			if ((p.getSMoves() >= 1 && p.getSMoves() < 1.13)) {
				if (p.reportToAdmin(HackType.MOVING_STEP))
					p.teleportToGround(HackType.MOVING_STEP);
				hack = true;

				// Tower
				p.setBuildBlockageTime(System.currentTimeMillis());
			}
			
			if (p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getLocation().getY()
					- p.getPlayer().getLocation().getY() == -1.0 ||
					p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getLocation().getY()
					- p.getPlayer().getLocation().getY() == -0.5)
				p.removeSMoves(p.getSMoves());
		}

		return hack;
	}
}
