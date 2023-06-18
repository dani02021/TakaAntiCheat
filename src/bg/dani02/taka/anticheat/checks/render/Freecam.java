package bg.dani02.taka.anticheat.checks.render;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class Freecam {
	public static void onCheck() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(CheatPlayer p : Utils.getCheatPlayers()) {
					if(!Utils.checkModule(p, HackType.RENDER_FREECAM))
						continue;
					
					// Keep-Alive packet may not be sent if there is player lag, removing false positive related to lag
					if(System.currentTimeMillis() - p.getLastKeepAlivePacketTime() > 30000 ||
							p.getPlayer().getOpenInventory().getType() != InventoryType.CRAFTING ||
							p.getLastMoveLookTime() == 0 ||
							p.isGliding() || p.getPlayer().isFlying())
						return;
					if(System.currentTimeMillis() - p.getLastMoveTime() > 2000 &&
							(!ConfigsMessages.freecam_better_check && (System.currentTimeMillis() - p.getLastPositionPacketTime() > 1000))) {
						p.teleport(p.getPlayer().getLocation());
					}
				}
			}
		}.runTaskTimer(Taka.getThisPlugin(), 10L, 10L);
	}
}