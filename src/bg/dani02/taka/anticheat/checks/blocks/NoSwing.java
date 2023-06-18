package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class NoSwing {
	// BlockBreakEvent, EntityDamageByEntityEvent
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b, Entity damager, DamageCause cause) {
		if(!Utils.checkModule(p, HackType.WORLD_NO_SWING))
			return false;
		if (p.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			return false;
		//mcMMO
			if(System.currentTimeMillis() - p.getLastmcMMOAbilityTime() < 1500)
				return false;
		// mcMMO Sword skill
		if(System.currentTimeMillis() - p.getLastmcMMOFakeDamageEntityTime() < 2000)
			return false;
		// Some plugins interfere with the check, like mcMMO - sword skill
		if(cause == DamageCause.CUSTOM)
			return false;
		//VeinMine
		if(System.currentTimeMillis() - p.getLastVeinMineTime() < 1500)
			return false;
		if (Utils.getServerVersion() >= 2)
			if (cause != null)
				if (cause.name().equals("ENTITY_SWEEP_ATTACK")) // md_5 Fuck you! You didn't add the enum back on 1.9, you idiot!
						return false;
		if (cause == DamageCause.THORNS || p.isRiptiding())
			return false;
		if (damager == null)
			if (Utils.isInstantBreak(b.getType(), p.getPlayer().getItemInHand().getType()))
				return false;
		if (!p.getNSAnimation()) {
			return p.reportToAdmin(HackType.WORLD_NO_SWING);
		} else
			p.setNSAnimation(false);
		return false;
	}
}
