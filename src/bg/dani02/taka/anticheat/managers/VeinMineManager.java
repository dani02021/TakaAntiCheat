package bg.dani02.taka.anticheat.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import bg.dani02.taka.anticheat.Utils;
import wtf.choco.veinminer.api.event.PlayerVeinMineEvent;

public class VeinMineManager implements Listener {
	@EventHandler
	public static void onVeinMine(PlayerVeinMineEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setLastVeinMineTime(System.currentTimeMillis());
	}
}