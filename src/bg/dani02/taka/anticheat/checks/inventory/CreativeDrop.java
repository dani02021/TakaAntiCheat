package bg.dani02.taka.anticheat.checks.inventory;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class CreativeDrop {
	public static boolean onCheck(CheatPlayer p) {
		if(!Utils.checkModule(p, HackType.WORLD_CREATIVE_DROP))
			return false;
		if (System.currentTimeMillis() - p.getLastCDBlockTime() < 500) {
			p.setLastCDBlockTime(System.currentTimeMillis());
			return true;
		}
		if (System.currentTimeMillis() - p.getLDITCD() > 1000) {
			p.addDICD((short) 1);
			boolean retur = checkA(p);
			p.removeDICD(p.getDICD());
			p.setLDITCD(System.currentTimeMillis());
			return retur;
		} else {
			p.addDICD((short) 1);
			return checkA(p);
		}
	}

	private static boolean checkA(CheatPlayer p) {
		if (p.getDICD() > 13) {
			p.reportToAdmin(HackType.WORLD_CREATIVE_DROP);
			p.setLastCDBlockTime(System.currentTimeMillis());
			return true;
		}
		return false;

	}
}