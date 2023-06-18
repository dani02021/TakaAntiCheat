package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class FastPlace {
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_FAST_PLACE))
			return false;
		if (System.currentTimeMillis() - p.getFPB() < 400) {
			p.setFPB(System.currentTimeMillis());
			return true;
			}
		if (p.getPlayer().getItemInHand().getType().equals(Material.FLINT_AND_STEEL)
				|| p.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			return false;

		// p.getPlayer().sendMessage("a: " + (System.currentTimeMillis() -
		// p.getFPTime()));

		if (System.currentTimeMillis() - p.getFPTime() < 90) {
			if (p.reportToAdmin(HackType.WORLD_FAST_PLACE, "time: " + (System.currentTimeMillis() - p.getFPTime()) + " blockType: " + b.getType())) {
				p.setFPB(System.currentTimeMillis());
				
				p.removeViolation(HackType.WORLD_FAST_PLACE, ViolationType.CANCELMOVE, Short.parseShort(Utils.getMoveCancelViolation(HackType.WORLD_FAST_PLACE)));
				return true;
			}
		} else
			p.removeViolation(HackType.WORLD_FAST_PLACE, ViolationType.CANCELMOVE, (short) 1);

		p.setFPTime(System.currentTimeMillis());

		return false;
	}
}
