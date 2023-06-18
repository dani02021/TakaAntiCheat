package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class Blink {
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_BLINK))
			return false;
		
		if(Utils.isUsingPotionEffect(p.getPlayer(), "DOLPHINS_GRACE"))
			return false;
		
		if(System.currentTimeMillis() - p.getLastBlinkTime() <= 500) {
			p.setMovePacket((short) (p.getMovePacket()+1));
			
			if(p.getMovePacket() > 65) {
				if(p.reportToAdmin(HackType.MOVING_BLINK, "packets: " + p.getMovePacket() + " serverLag: " + (System.currentTimeMillis()-p.getLastBlinkTime())))
					p.teleport(p.getBlinkPrevLocation(), HackType.MOVING_BLINK);
				p.setMovePacket((short) 0);
			}
			
		} else {
			p.setLastBlinkTime(System.currentTimeMillis());
			
			p.setBlinkPrevLocation(p.getPlayer().getLocation());
			
			p.setMovePacket((short) 0);
		}
		
		return false;
	}
}