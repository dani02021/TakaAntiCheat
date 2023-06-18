package bg.dani02.taka.anticheat.checks.movement;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class Sneak {
	public static boolean onCheck(CheatPlayer p, boolean isSneaking) {
		if(!Utils.checkModule(p, HackType.MOVING_SNEAK))
			return false;

		if (isSneaking && !p.getFreezed()) {
			if (System.currentTimeMillis() - p.getSTR() > 1000) {
				p.setSTR(System.currentTimeMillis());
				p.removeSneakCount(p.getSneakCount());
			} else {
				p.addSneakCount((short) 1);
				if (checkSneak(p))
					return true;
			}
		}
		return false;
	}

	private static boolean checkSneak(CheatPlayer p) {
		if (p.getSneakCount() > 22) {
			if(p.reportToAdmin(HackType.MOVING_SNEAK)) {
				Utils.freezePlayer(p.getPlayer(), HackType.MOVING_SNEAK);
				return true;
			}
		}

		return false;
	}
}