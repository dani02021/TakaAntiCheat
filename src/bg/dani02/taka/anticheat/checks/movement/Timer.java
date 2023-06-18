package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class Timer {
	public static final short QUEUE_SIZE = 50;
	
	public static double prev = 0D;
	
	// This check has a problem:
	// when you just start your first move, timeDiff between now and last packet is high
	// Called from ProtocolLib Manager
	public static boolean check(CheatPlayer p, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_TIMER))
			return false;
		if (p.getPlayer().isFlying() || p.isGliding()
				|| Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SPEED)) {
			resetTimer(p);
			return false;
		}

		// Well not good way to fix the laggy servers issue, but for now kinda works
		if (Utils.getTPS() <= 17) {
			resetTimer(p);
			return false;
		}
		
		// Utils.debugMessage("P: " + (System.currentTimeMillis() - Utils.getCheatPlayer(event.getPlayer().getUniqueId()).getLastPositionPacketTime()));
		
		double vl = p.getTA() / QUEUE_SIZE;
		
		if((vl < 42.0D || vl > 77.0D) &&
				p.isTaFull()) {
			if (p.getTTL() == null)
				p.setTTL(p.getPlayer().getLocation());
			
			p.addViolation(HackType.MOVING_TIMER, ViolationType.CANCELMOVE, (short) 1);
			
			if (p.getViolation(ViolationType.CANCELMOVE).get(HackType.getName(HackType.MOVING_TIMER)) >= Short.parseShort(Utils.getMoveCancelViolation(HackType.MOVING_TIMER))) {
				if(p.getTTL() != null) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							if(!Utils.checkPlayer(p))
								return;
							if(p.reportToAdmin(HackType.MOVING_TIMER, "av: " + vl + " a:" + p.getTA())) {
								if(p.getTTL() == null)
									p.teleportToGround(HackType.MOVING_TIMER);
								else p.teleport(p.getTTL(), HackType.MOVING_TIMER);
								
								resetTimer(p);
							}
						}
					}.runTask(Taka.getThisPlugin());
				}
				
				return true;
			}
		} else {
			p.removeViolation(HackType.MOVING_TIMER, ViolationType.CANCELMOVE, (short) 1);
		}
		
		double timeDiff = (System.currentTimeMillis() - p.getLastPositionPacketTime());
		
		// Utils.debugMessage(Utils.prepareColors("&aCount: " + p.getTA() + " avg: " + (p.getTA() / QUEUE_SIZE)) + " " + p.isTaFull() + " " + timeDiff + " " + to.distance(p.getPlayer().getLocation()) + " statMedian: " + Utils.getStatisticMedian(p.getTAArray()));
		
		if(System.currentTimeMillis() - p.getLastPositionPacketTime() != 0 &&
				to.distance(p.getPlayer().getLocation()) > 0.1D &&
				p.getLastFromToLocations()[0].distance(p.getLastFromToLocations()[1]) > 0.11D) {
			p.addTA(timeDiff);
		}
		
		return false;
	}
	
	public static void resetTimer(CheatPlayer p) {
		p.emptyTA();
		p.setTTL(null);
	}
}
