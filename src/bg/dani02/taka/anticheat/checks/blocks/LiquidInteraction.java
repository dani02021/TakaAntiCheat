package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.block.Block;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class LiquidInteraction {
	public static boolean onCheck(CheatPlayer p, Block b, Block againstBlock) {
		if(!Utils.checkModule(p, HackType.WORLD_LIQUID_INTERACTION))
			return false;
		if (againstBlock == null) {
			if (b.isLiquid()) {
				p.reportToAdmin(HackType.WORLD_LIQUID_INTERACTION);
				return true;
			} else
				return false;
		} else {
			if (b.getType().equals(XMaterial.LILY_PAD.parseMaterial()) ||
					p.getPlayer().getLocation().getBlock().isLiquid() ||
					p.getPlayer().getEyeLocation().getBlock().isLiquid() ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("DOOR") ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("SIGN") ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("FENCE") ||
					p.getPlayer().getEyeLocation().getBlock().getType().equals(XMaterial.IRON_BARS.parseMaterial()) ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("GLASS") ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("BED") ||
					p.getPlayer().getEyeLocation().getBlock().getType().name().contains("LADDER"))
				return false;
			if (againstBlock.isLiquid() /*|| Utils.isTargetBlockLiquid(p.getPlayer(), true) FP'S*/) {
				p.reportToAdmin(HackType.WORLD_LIQUID_INTERACTION);
				return true;
			}
		}

		return false;
	}
}