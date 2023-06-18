package bg.dani02.taka.anticheat.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class SpeedVehicle {
	// Called from Speed
	public static void onCheck(CheatPlayer p, Location from, Location to, double dist, double distY) {
		if(from.getY() > to.getY()) // Should check falling?
			return;
		
		if(System.currentTimeMillis() - p.getLastVehicleEnterTime() < 300)
			return;
		
		double maxSpeed = 0D;
		
		if (Utils.checkModule(p, HackType.MOVING_SPEED_VEHICLE)) {
			if (p.getPlayer().isInsideVehicle()) {
				if(p.getPlayer().getVehicle() instanceof Horse) {
					if(p.isSprinting())
						maxSpeed = 2.5D;
					else maxSpeed = 2.5D;
					
					if(System.currentTimeMillis() - p.getLastHorseJumpTime() < 1000)
						maxSpeed = 7.5D;
				} else if(p.getPlayer().getVehicle() instanceof Pig) {
					if(p.isSprinting())
						maxSpeed = 0.4D;
					else maxSpeed = 0.4D;
				} else if(p.getPlayer().getVehicle() instanceof Boat) {
					if(p.isSprinting())
						maxSpeed = 3.0D;
					else maxSpeed = 3.0D;
				} else {
					// Are all entities riddable ?
					// Llama? Minecart?
				}
				
				// Utils.debugMessage("VEHICLE: " + p.getPlayer().getVehicle().getType() + " " + dist + " " + distY + " " + maxSpeed);
				
				if(dist > maxSpeed)
					Speed.law(p, maxSpeed, dist, HackType.MOVING_SPEED_VEHICLE, Speed.tpBack.containsKey(p.getPlayer().getName()) ? (Location) Speed.tpBack.get(p.getPlayer().getName())[0] : from, to);
			}
		}
	}

}
