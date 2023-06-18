package bg.dani02.taka.anticheat.checks.movement.fly;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class FlyStableY {
	private static HashMap<String, Double> prevMoveDist = new HashMap<String, Double>();

	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_FLY_STABLE_Y))
			return false;
		
		// Checking the down block instead of isOnGround dont use much CPU power
		if (!p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().isEmpty()
				&& !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()
						.equals(XMaterial.COBWEB.parseMaterial())
				&& !p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()
						.equals(XMaterial.SNOW.parseMaterial())) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		if(Utils.getServerVersion() >= 8)
			if(Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getEyeLocation(), Material.HONEY_BLOCK)) {
				prevMoveDist.remove(p.getPlayer().getName());
				p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
				return false;
			}
		
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION)) {
				prevMoveDist.remove(p.getPlayer().getName());
				p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
				return false;
			}
		}
		if (System.currentTimeMillis() - p.getLastFlyTime() <= 2000
				|| System.currentTimeMillis() - p.getLastGlideEndTime() <= 2000) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}

		if (Utils.isOnEntity(p.getPlayer(), EntityType.BOAT)
				|| Utils.isNextToEntity(p.getPlayer(), (short) 3, EntityType.BOAT)
				|| p.getPlayer().getVehicle() != null) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		if (Utils.isNextToBlockDifferent(p.getPlayer().getLocation().clone().subtract(0, 0.0625, 0), Material.AIR,
				XMaterial.COBWEB.parseMaterial(), XMaterial.SNOW.parseMaterial())
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.CAKE.parseMaterial())
				|| Utils.isNextToBlocksByNameAround(p, p.getPlayer().getLocation().getBlock(), false, true, "FENCE", "WALL")
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(),
						XMaterial.COBBLESTONE_WALL.parseMaterial())
				|| Utils.isInWeb(p, p.getPlayer().getLocation(), true)
				|| p.isRiptiding()
				|| Utils.isOnStair(p.getPlayer()) || Utils.isOnHalfSlab(p.getPlayer().getLocation())
				|| Utils.isAroundLiquidLoc(p, p.getPlayer().getLocation()) || p.getPlayer().isFlying()
				|| Utils.getPlayerStandOnBlockLocationSnow(p.getPlayer().getLocation()).getBlock().getType().name()
						.contains("SNOW")
				/*|| p.getPlayer().getFireTicks() > 0*/ || p.isGliding()) {
			prevMoveDist.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_FLY_STABLE_Y, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		if (Math.abs(to.getY() - from.getY()) < 0.1 &&
				Math.abs(Math.abs(to.getY() - from.getY()) - 0.07840000152587834D) > 0.005D) {
			if (prevMoveDist.containsKey(p.getPlayer().getName())) {
				if (p.getPlayer().getLocation().getY() == prevMoveDist.get(p.getPlayer().getName())) {
					if (p.reportToAdmin(HackType.MOVING_FLY_STABLE_Y, "yDiff:" + Math.abs(to.getY() - from.getY()))) {
						p.teleportToGround(HackType.MOVING_FLY_STABLE_Y);
						return true;
					}
				} else {
					prevMoveDist.put(p.getPlayer().getName(), p.getPlayer().getLocation().getY());
				}
			} else {
				prevMoveDist.put(p.getPlayer().getName(), p.getPlayer().getLocation().getY());
			}
		} else {
			prevMoveDist.remove(p.getPlayer().getName());
		}

		return false;
	}
}
