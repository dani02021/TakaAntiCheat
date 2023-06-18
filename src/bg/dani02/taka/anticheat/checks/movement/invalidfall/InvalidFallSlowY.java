package bg.dani02.taka.anticheat.checks.movement.invalidfall;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class InvalidFallSlowY {
	private static HashMap<String, Double> dif = new HashMap<String, Double>();

	public static boolean onCheck(CheatPlayer p, double diff, boolean onGrn) {
		if(!Utils.checkModule(p, HackType.MOVING_INVALIDFALL_SLOW_Y))
			return false;
		if (System.currentTimeMillis() - p.getLastSMTime() < 1500) {
			return false;
		}
		if (p.getPlayer().isInsideVehicle())
			return false;
		
		if (System.currentTimeMillis() - p.getLastFlyTime() <= 2000
				|| System.currentTimeMillis() - p.getLastGlideEndTime() <= 2000
				|| p.getLastGroundLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK)
				|| p.isGliding()
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "BED", false, true)
				|| Utils.isNextToBlock(p, p.getPlayer().getLocation().clone().subtract(0,0.3,0), XMaterial.DAYLIGHT_DETECTOR.parseMaterial())
				|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
				|| Utils.isClimable(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())) {
			dif.remove(p.getPlayer().getName());
			p.removeViolation(HackType.MOVING_INVALIDFALL_SLOW_Y, ViolationType.CANCELMOVE, (short) 1);
			return false;
		}
		if(Utils.getServerVersion() >= 8)
			if(Utils.isNextToBlock(p, p.getPlayer().getLocation(), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getLocation().clone().subtract(0,0.3,0), Material.HONEY_BLOCK) ||
					Utils.isNextToBlock(p, p.getPlayer().getEyeLocation(), Material.HONEY_BLOCK)) {
				return false;
			}
		if (p.getLegitSpeedVL() > 0 && p.getLegitSpeedReason().contains("block"))
			return false;
		if (Utils.getMaterialsAroundByName(p.getPlayer().getLocation(), "CARPET") || Utils.getMaterialsAroundByName(p.getPlayer().getLocation(), "TRAPDOOR")) // WHY?
			return false;

		// LIQUIDBOUNCE - highjump FALL
		// NOT GOOD SOLUTION FIND NEW ONE
		// FALSE POSITIVES DETECTED, SEARCH FOR MORE

		if (onGrn) {
			dif.put(p.getPlayer().getName(), diff);
		} else {
			if (dif.containsKey(p.getPlayer().getName())) {
				if (dif.get(p.getPlayer().getName()) > 0.07 && diff > 0.02 && diff < 0.035) {
					if(p.reportToAdmin(HackType.MOVING_INVALIDFALL_SLOW_Y, "diff: " + diff + " fallDist: " + p.getServerFallDistance())) {
						p.teleportToGround(HackType.MOVING_INVALIDFALL_SLOW_Y);
						return true;
					}
				}
				dif.remove(p.getPlayer().getName());
			}
		}

		return false;
	}

}
