package bg.dani02.taka.anticheat.checks.combat;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class FastBow {
	// TODO: Improve it, detect arrows per 5 secs not just 1
	public static boolean onCheck(CheatPlayer p, float force) {
		if(!Utils.checkModule(p, HackType.COMBAT_FASTBOW))
			return false;
		//if(force < 0.5)
		//	return false;
		
		if(System.currentTimeMillis() - p.getLastBowTime() > 1000) {
			if(System.currentTimeMillis() - p.getLastBowTime() > 5000)
				p.setBowBlock(false);
			
			if(checkIt(p))
				return true;
			
			p.setLastBowTime(System.currentTimeMillis());
			p.setBowCount(1);
		} else {
			if(checkIt(p))
				return true;
			
			p.setBowCount(p.getBowCount() + 1);
			
			if(p.getBowCount() >= 6) {
				if(p.reportToAdmin(HackType.COMBAT_FASTBOW, "force: " + force + " time: " + (System.currentTimeMillis() - p.getLastBowTime())))
					p.setBowBlock(true);
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkIt(CheatPlayer p) {
		if(p.getBowBlock()) {
			p.setLastBowTime(System.currentTimeMillis());
			return true;
		}
		
		return false;
	}
}
