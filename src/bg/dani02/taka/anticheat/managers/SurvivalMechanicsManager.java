package bg.dani02.taka.anticheat.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import bg.dani02.taka.anticheat.Utils;
import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleClimbEvent;
import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleMobkickEvent;
import me.DeeCaaD.SurvivalMechanics.Events.PlayerToggleWallkickEvent;

public class SurvivalMechanicsManager implements Listener {

	@EventHandler
	public static void mobKick(PlayerToggleMobkickEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setLastSMTime(System.currentTimeMillis());
	}

	@EventHandler
	public static void mobKick(PlayerToggleWallkickEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setLastSMTime(System.currentTimeMillis());
	}

	@EventHandler
	public static void mobKick(PlayerToggleClimbEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setLastSMTime(System.currentTimeMillis());
	}
}
