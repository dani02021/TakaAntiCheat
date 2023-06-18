package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class NoBreakDelay {
	// Disabled check, it is working fine, but there are many blocks which are instant break or jut like instant break
	// if you add them in a list, the check will be fine
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_NO_BREAK_DELAY))
			return false;
		if(System.currentTimeMillis() - p.getBreakBlockageTime() < 2000) {
			p.setBreakBlockageTime(System.currentTimeMillis());
			return false;
		}
		//mcMMO
		if(System.currentTimeMillis() - p.getLastmcMMOAbilityTime() < 1500)
			return false;
		//VeinMine
		if(System.currentTimeMillis() - p.getLastVeinMineTime() < 1500)
			return false;
		
		// Utils.debugMessage("AA: " + Utils.isInstantBreak(b.getType(), p.getPlayer().getInventory().getItemInHand().getType()) + " " + b.getType() + " " + (System.currentTimeMillis() - p.getLastBlockBreakTime()));
		
		if(Utils.isInstantBreak(b.getType(), p.getPlayer().getInventory().getItemInHand().getType()) || Utils.isInstantBreak(p.getLastBlockBreakType(), p.getPlayer().getInventory().getItemInHand().getType()) ||
				p.getPlayer().getInventory().getItemInHand().containsEnchantment(Enchantment.DIG_SPEED) ||
				b.getType().equals(Material.AIR) ||
				p.getPlayer().getGameMode() == GameMode.CREATIVE) // Stop spamming reportAdmin
			return false;
		
		if((System.currentTimeMillis() - p.getLastBlockBreakTime()) < 100) {
			p.reportToAdmin(HackType.WORLD_NO_BREAK_DELAY, "time: " + (System.currentTimeMillis() - p.getLastBlockBreakTime()) + " blockType: " + b.getType());
			p.setBreakBlockageTime(System.currentTimeMillis());
		}
		
		return false;
	}
}