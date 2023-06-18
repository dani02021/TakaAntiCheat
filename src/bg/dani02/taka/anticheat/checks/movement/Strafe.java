package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class Strafe {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_STRAFE))
			return false;
		// DoubleJump false positive
		if(System.currentTimeMillis() - p.getLastFlyTime() < 5000)
			return false;
		
		if(Utils.getServerVersion() >= 9)
			if(p.getPlayer().getInventory().getBoots() != null)
				if(p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED))
					if(p.getLastGroundLocation().clone().subtract(0,0.3,0).getBlock().getType().name().contains("SOUL"))
						return false;
		if(from.getY() < to.getY()) {
			if(p.getStrafePrevVector() == null) {
				p.setStrafePrevVector(Utils.getVector(from, to));
				return false;
			}
			
			if(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.EAST).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.WEST).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.NORTH).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.SOUTH).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid() ||
					p.getPlayer().getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.EAST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.WEST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.NORTH).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.SOUTH).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid() ||
					p.getPlayer().getEyeLocation().getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid() ||
					!p.getPlayer().getNearbyEntities(1, 1, 1).isEmpty() || Utils.isInWeb(p, p.getPlayer().getLocation(), true) ||
					p.getPlayer().isFlying() || p.isGliding() || p.getPlayer().isInsideVehicle() || Utils.isOnStair(from)
					|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK)
					|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
					|| Utils.isAroundLiquidLoc(p, from) || Utils.isBubbleColumn(from)

					|| Utils.isBubbleColumn(p.getLastGroundLocation())) {
				p.setStrafePrevVector(null);
				return false;
			}
			
			if (Utils.getServerVersion() >= 2) {
				if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION) ||
						(Utils.isUsingPotionEffect(p.getLastActivePotionEffectTypes(), PotionEffectType.LEVITATION) &&
								System.currentTimeMillis() - p.getLastActivePotionEffectsTime() < 1000)) {
					return false;
				}
			}
			
			if(System.currentTimeMillis()- p.getLastDamageTime() < 500) {
				p.setStrafePrevVector(null);
				return false;
			}
			
			Vector test1 = Utils.getVector(from, to);
			test1.setY(0);
			
			Vector test2 = p.getStrafePrevVector();
			test2.setY(0);
						
			if(test1.angle(test2)*90 > 100 && Utils.get3DXZDisstance(from, to) > 0.15) {
				if(p.reportToAdmin(HackType.MOVING_STRAFE)) {
					p.teleportToGround(HackType.MOVING_STRAFE);
					
					return true;
				}
			}
		} else {
			p.setStrafePrevVector(null);
		}
		
		return false;
	}
}