package bg.dani02.taka.anticheat.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.evill4mer.RealDualWield.Api.PlayerOffhandAnimationEvent;

import bg.dani02.taka.anticheat.Utils;

public class RealDualWieldManager implements Listener {
	@EventHandler
	public static void onOffhand(PlayerOffhandAnimationEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setNSAnimation(true);
	}
}