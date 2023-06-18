package bg.dani02.taka.anticheat.managers;

import org.bukkit.Achievement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import bg.dani02.taka.anticheat.Utils;

@SuppressWarnings("deprecation")
public class AchievementEventManager implements Listener {
	@EventHandler
	public static void achievement(PlayerAchievementAwardedEvent e) {
		if (e.getAchievement().equals(Achievement.OPEN_INVENTORY)) {
			if (e.getPlayer().isInsideVehicle())
				return;

			Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
		}
	}
}
