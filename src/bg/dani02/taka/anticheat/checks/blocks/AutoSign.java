package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.block.Block;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class AutoSign {
	public static boolean onCheck(CheatPlayer p, String[] lines, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_AUTO_SIGN))
			return false;
		
		if(System.currentTimeMillis() - p.getLastSignPlaceTime() < 600) {
			int signSize = lines[0].length() + lines[1].length() + lines[2].length() + lines[3].length();
			
			if(signSize > 5) {
				if(p.reportToAdmin(HackType.WORLD_AUTO_SIGN, "char: " + signSize + " time: " + (System.currentTimeMillis() - p.getLastSignPlaceTime()))) {
					b.breakNaturally();
					return true;
				}
			}
		}
		
		return false;
	}
}