package bg.dani02.taka.anticheat.managers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.fake.FakeEntityDamageByEntityEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityEvent;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;

public class McMMOManager implements Listener {
	@EventHandler
	public static void onAbility(McMMOPlayerAbilityEvent e) {
		CheatPlayer p = Utils.getCheatPlayer(e.getPlayer().getUniqueId());
		
		// disable invaliditeractionblock, wrongblock, fastbreak, nobreakdelay
		if(e.getAbility().name().contains("TREE_FELLER")) {
			  p.setLastmcMMOAbilityTime(System.currentTimeMillis());
		} else if(e.getAbility().name().contains("SUPER_BREAKER")) {
			  p.setLastmcMMOAbilityTime(System.currentTimeMillis());
		} else if(e.getAbility().name().contains("BLAST_MINING")) {
			  p.setLastmcMMOAbilityTime(System.currentTimeMillis());
		} else if(e.getAbility().name().contains("GIGA_DRILL_BREAKER")) {
			  p.setLastmcMMOAbilityTime(System.currentTimeMillis());
		}
		 
	}
	
	@EventHandler
	public static void onSkill(FakeEntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player))
			return;
		
		Utils.getCheatPlayer(e.getDamager().getUniqueId()).setLastmcMMOFakeDamageEntityTime(System.currentTimeMillis());
		
		// disable reach(combat) and noswing
	}
}