package bg.dani02.taka.anticheat.checks.inventory;

import org.bukkit.entity.Projectile;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class Throw {
	public static boolean onCheck(CheatPlayer p, Projectile thrown) {
		if(!Utils.checkModule(p, HackType.INVENTORY_THROW))
			return false;
		
		if(System.currentTimeMillis() - p.getLastThrowTime() > 100) {
			if(System.currentTimeMillis() - p.getLastThrowTime() > 1000)
				p.setThrowBlock(false);
			
			if(checkIt(p))
				return true;
			
			p.setLastThrowTime(System.currentTimeMillis());
			p.setThrowCount(1);
		} else {
			if(checkIt(p))
				return true;
			
			p.setThrowCount(p.getThrowCount() + 1);
			
			if(p.getThrowCount() >= 12) {
				if(p.reportToAdmin(HackType.INVENTORY_THROW, "throw: " + p.getThrowCount()))
					p.setThrowBlock(true);
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkIt(CheatPlayer p) {
		if(p.getThrowBlock()) {
			p.setLastThrowTime(System.currentTimeMillis());
			return true;
		}
		
		return false;
	}
}