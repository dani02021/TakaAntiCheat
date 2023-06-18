package bg.dani02.taka.anticheat.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import bg.dani02.taka.anticheat.Utils;

public class ElytraEventManager implements Listener {

	@EventHandler
	public static void elytra(EntityToggleGlideEvent e) {
		// Utils.setValues(Utils.getCheatPlayer(e.getEntity().getUniqueId()), e); Its
		// bad example
		if (e.isGliding())
			Utils.getCheatPlayer(e.getEntity().getUniqueId()).setLastGlideStartTime(System.currentTimeMillis());
		else
			Utils.getCheatPlayer(e.getEntity().getUniqueId()).setLastGlideEndTime(System.currentTimeMillis());
	}
}
