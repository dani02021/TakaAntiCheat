package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class NoPitch {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_NO_PITCH))
			return false;
		
		if (Math.abs(to.getPitch()) > 90) {
			if(p.reportToAdmin(HackType.MOVING_NO_PITCH))
				return true;
		}
		
		return false;
	}
}
