package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class ImpossibleJump {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_IMPOSSIBLE_JUMP))
			return false;
		if (from.getY() >= to.getY())
			return false;
		if (p.getPlayer().isFlying() || p.isGliding() || System.currentTimeMillis() - p.getLastDamageTime() < 1000
				|| System.currentTimeMillis() - p.getLastGlideEndTime() < 1000 || System.currentTimeMillis() - p.getLastBubbleColumnTouchTime() < 500)
			return false;
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
					(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
							System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 1000)) {
				return false;
			}
		}
		if (Utils.isNextToEntity(p.getPlayer(), (short) 4, EntityType.BOAT)
				|| Utils.isInEntity(p.getPlayer(), (short) 4, EntityType.BOAT))
			return false;
		if (from.getBlock().isLiquid() || to.getBlock().isLiquid())
			return false;
		if(p.getPlayer().getLocation().getBlock().getType().name().contains("PISTON_HEAD") ||
				p.getPlayer().getLocation().getBlock().getType().name().contains("MOVING_PISTON"))
			return false; // Gives problems in Loyisa's Test Server
		if (Utils.isOnSnow(p.getPlayer()) || Utils.isNextToBlock(p, p.getLastGroundLocation(), Material.SLIME_BLOCK)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "HEAD", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SKULL", false, true))
			return false;
		if (Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", true, true)
				|| Utils.isNextToBlock(p, from, Material.BREWING_STAND) || Utils.isNextToBlock(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.BREWING_STAND))
			return false;
		// Detect only jump, if values are > 0.4 and < 0.43 -> Sigma Spider bypass
		if (to.getY() - from.getY() > 0.38 && to.getY() - from.getY() < 0.44) {
			if (Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation())
					|| Utils.isNextToLiquidBlock(
							p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation())) {
				return false;
			}

			// CheckerClimb
			if (from.getBlock().getRelative(BlockFace.DOWN).isEmpty()) {
				/*
				 * Block bo = b(from); ALGORITHM A - Contains false positives if(bo != null) {
				 * if(Utils.isSolid(bo.getRelative(BlockFace.UP))) { if(!b1(from,
				 * bo.getLocation())) { p.teleportToGround();
				 * p.reportToAdmin(HackType.MOVING_IMPOSSIBLE_JUMP); } } }
				 */
				// ALGORITHM B - Test needed
				if (cClimb(from)) {
					if (p.reportToAdmin(HackType.MOVING_IMPOSSIBLE_JUMP)) {
						p.teleportToGround(HackType.MOVING_IMPOSSIBLE_JUMP);
						return true;
					}
				}
			}
			// WebWalk
			if (!p.getPlayer().getLocation().getBlock().getType().equals(XMaterial.COBWEB.parseMaterial())
					&& p.getPlayer().getLocation().clone().subtract(0, 0.2, 0).getBlock().getType()
							.equals(XMaterial.COBWEB.parseMaterial())) {
				if (p.reportToAdmin(HackType.MOVING_IMPOSSIBLE_JUMP, "webwalk down_0.05: " + p.getPlayer().getLocation().clone().subtract(0, 0.05, 0).getBlock().getType() + " down_0.1:" + p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getType())) {
					Location loc = p.getLastGroundLocation().clone().subtract(0, 0.5, 0);
					if (loc.getBlock().getType().equals(XMaterial.COBWEB.parseMaterial()) || loc.getBlock().isEmpty())
						p.teleport(p.getLastGroundLocation().clone().subtract(0, 0.5, 0), HackType.MOVING_IMPOSSIBLE_JUMP);
					else
						p.teleportToGround(HackType.MOVING_IMPOSSIBLE_JUMP);
					return true;
				}
			}
		}
		// Jesus
		if (to.getY() - from.getY() > 0.16 && !p.isSwimming() && System.currentTimeMillis() - p.getLastSwimingTime() > 1000) {
			if ((!p.getPlayer().getLocation().clone().add(0, 0.1, 0).getBlock().isLiquid()
					//&& p.getPlayer().getLocation().clone().subtract(0, 0.2 + (to.getY() - from.getY()), 0).getBlock().isLiquid()
					&& isGroundAround(from.clone().subtract(0, to.getY() - from.getY(), 0)))
					&& !p.isRiptiding()
					&& System.currentTimeMillis() - p.getLastRiptileTime() > 500
					&& !Utils.isNextToBlock(p, from, XMaterial.LILY_PAD.parseMaterial())
					&& !Utils.isNextToBlockByNameAround(p, from.getBlock(), "CARPET", false, false)
					&& !Utils.isNextToBlockByNameAround(p, from.getBlock(), "SLIME", false, false)
					&& !Utils.isNextToBlockByNameAround(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation().getBlock(), "SLIME", false, true)
					&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "SLAB", true, false)
					&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "STAIRS", true, false)
					&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "STEP", true, false)
					&& !Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "TRAPDOOR", true, false)) {

				// Check if there is a solid block and return.
				for (Material block : Utils
						.getBlocksAroundX(from.clone().subtract(0, 0.2 + (to.getY() - from.getY()), 0), 1, 1))
					if (block.isSolid())
						return false;
								
				// Check if there is a solid block for the head, because in MC you can jump then
				for (Material block : Utils
						.getBlocksAroundX(p.getPlayer().getEyeLocation(), 1, 1))
					if (block.isSolid())
						return false;

				// p.getPlayer().sendMessage("A
				// "+from.getBlock().getRelative(BlockFace.DOWN).getType() + " "+
				// from.clone().subtract(0,(to.getY()-from.getY()),0).getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getType()+"
				// "+
				// from+" "+
				// from.clone().subtract(0,(to.getY()-from.getY()),0).getBlock().getRelative(BlockFace.EAST).getType());
				if (p.reportToAdmin(HackType.MOVING_IMPOSSIBLE_JUMP, "jesus yDiff: " + (to.getY() - from.getY()) + " s:" + p.isSwimming())) {
					p.teleportToGround(HackType.MOVING_IMPOSSIBLE_JUMP);
					return true;
				}
			}
		}

		return false;
	}

	private static boolean isGroundAround(Location from) {
		double diff = 0.31D;
		
		return from.clone().add(diff, -diff, 0).getBlock().isLiquid() &&
				from.clone().add(-diff, -diff, 0).getBlock().isLiquid() &&
				from.clone().add(0, -diff, diff).getBlock().isLiquid() &&
				from.clone().add(0, -diff, -diff).getBlock().isLiquid() &&
				from.clone().add(diff, -diff, diff).getBlock().isLiquid() &&
				from.clone().add(diff, -diff, -diff).getBlock().isLiquid() &&
				from.clone().add(-diff, -diff, diff).getBlock().isLiquid() &&
				from.clone().add(-diff, -diff, -diff).getBlock().isLiquid();
	}

	/*
	 * private static Block b(Location from) { double dist = 10; Block bu = null;
	 * BlockFace[] b =
	 * {BlockFace.EAST,BlockFace.WEST,BlockFace.NORTH,BlockFace.SOUTH};
	 * for(BlockFace bJ : b) { double distA =
	 * from.getBlock().getRelative(BlockFace.DOWN).getRelative(bJ).getLocation().
	 * distance(from); if(distA < dist &&
	 * Utils.isSolid(from.getBlock().getRelative(bJ))) { dist = distA;
	 * bu=from.getBlock().getRelative(bJ); } } //if(bu.getLocation().distance(from)
	 * > 1.1) // return null; return bu; }
	 * 
	 * private static boolean b1(Location from, Location bo) {
	 * //f(from.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).
	 * getType().isSolid()) BlockFace[] b =
	 * {BlockFace.EAST,BlockFace.WEST,BlockFace.NORTH,BlockFace.SOUTH};
	 * for(BlockFace bJ : b) {
	 * if(from.getBlock().getRelative(bJ).getLocation().distance(bo) != 0) {
	 * if(!from.getBlock().getRelative(bJ).getType().isSolid() &&
	 * from.getBlock().getRelative(bJ).getRelative(BlockFace.DOWN).getType().isSolid
	 * ()) { return true; } } } return false; }
	 */

	private static boolean cClimb(Location player) {
		Location loc = player.clone().subtract(0, 0.5, 0);
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
		Location[] b = new Location[] { loc.clone().subtract(diff, 0, 0), loc.clone().subtract(0, 0, diff),
				loc.clone().add(diff, 0, 0), loc.clone().add(0, 0, diff), loc.clone().add(diff, 0, diff),
				loc.clone().add(-diff, 0, diff), loc.clone().add(diff, 0, -diff), loc.clone().add(-diff, 0, -diff) };
		boolean ret = false;
		for (Location blo : b) {
			Block bl = blo.getBlock();
			//Utils.debugMessage("AA: " + bl.getType() + " " + Utils.isSolid(bl) + " " + Utils.isSolid(bl.getRelative(BlockFace.UP).getType()) + " " + bl.getRelative(BlockFace.UP).getType()
			//		+ " " + Utils.isPassableBlock(bl.getType()) + " " + Utils.isSolid(bl.getRelative(BlockFace.UP).getRelative(BlockFace.UP)));
			// Check also is the block is air, because if x block is air and x+1 is not air it is not detected
			if (Utils.isPassableBlock(bl.getType()) || Utils.isSolid(bl) || Utils.isClimable(bl.getType()) || bl.isEmpty()) { // You can also jump on climable blocks
				if (Utils.isSolid(bl.getRelative(BlockFace.UP).getType()) && !Utils.isPassableBlock(bl.getRelative(BlockFace.UP).getType()))
					ret = true;
				else {
					if (Utils.isSolid(bl.getRelative(BlockFace.UP).getRelative(BlockFace.UP)))
						ret = true;
					else if(!bl.isEmpty())
						return false;
				}
			}
		}

		return ret;
	}
}