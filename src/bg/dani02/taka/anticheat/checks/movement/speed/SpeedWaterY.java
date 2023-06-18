package bg.dani02.taka.anticheat.checks.movement.speed;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class SpeedWaterY {
	public static boolean onCheck(CheatPlayer p, double distY, boolean isUp, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_SPEED_LIQUID_WATER))
			return false;
		if(p.isGliding() || p.getPlayer().isFlying() || p.isRiptiding() ||
				System.currentTimeMillis() - p.getLastRiptileTime() < 500)
			return false;
		
		if(Utils.isUsingPotionEffect(p.getPlayer(), "DOLPHINS_GRACE"))
			return false;
		
		if (Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.LILY_PAD.parseMaterial())
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "CARPET", false, false)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "STEP", false, false)
				|| Utils.getMaterialsAroundByName(p.getPlayer().getLocation(), "CARPET")
				|| Utils.getMaterialsAround(p.getPlayer().getLocation())
						.contains(XMaterial.LILY_PAD.parseMaterial()))
			return false;
		
		double maxSpeed = 0;
		
		List<Block> around = Utils.getBlocksAround(p.getPlayer().getLocation());
		if(around != null) {
			for(Block b : around) {
				if(!b.isLiquid() || !b.getRelative(BlockFace.UP).isLiquid() || b.getRelative(BlockFace.UP).getType() == XMaterial.LILY_PAD.parseMaterial() ||
						b.getRelative(BlockFace.UP).getType().name().contains("CARPET")) {
					// In the perfect case, when the player is on the water surface and is trying to get off the
					// water, the y speed is above 1.2 blocks, so the player can get off the water, but this
					// causes false positive, the check should be disabled, but in the non perfect case
					// he is just around a wall in the water, so just make sure his speed is enough to don't
					// trigger false positive
					
					maxSpeed = 1.5;
					
					if(distY > maxSpeed) {
						Speed.law(p, maxSpeed, distY, HackType.MOVING_SPEED_LIQUID_WATER, from, to);
						return true;
					}
					
					return false;
				}
			}
		}
		
		if(isUp) {
			if(p.isSprinting())
				maxSpeed = 1.6;
			else maxSpeed = 0.315;
			
			if(distY > maxSpeed) {
				Speed.law(p, maxSpeed, distY, HackType.MOVING_SPEED_LIQUID_WATER, from, to);
				return true;
			}
		} else {
			if(!p.getPlayer().getEyeLocation().getBlock().isLiquid())
				return false;
			if(p.getLegitSpeedVL() > 0)
				return false;
			
			if(p.isSprinting())
				maxSpeed = 2.2;
			else maxSpeed = 0.515;
			
			if(distY > maxSpeed) {
				Speed.law(p, maxSpeed, distY, HackType.MOVING_SPEED_LIQUID_WATER, from, to);
				return true;
			}
		}
		
		return false;
		
	}
}