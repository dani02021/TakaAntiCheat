package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class FastBreak {
	// Disabled due to false positive on laggy servers (the player sends many START_DESTROY_BLOCK for 1 block)
	// and due to being unneeded;
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_FAST_BREAK))
			return false;
		//mcMMO
		if(System.currentTimeMillis() - p.getLastmcMMOAbilityTime() < 1500)
			return false;
		//VeinMine
		if(System.currentTimeMillis() - p.getLastVeinMineTime() < 1500)
			return false;
		if(System.currentTimeMillis() - p.getBreakBlockageTime() < 2000) {
			p.setBreakBlockageTime(System.currentTimeMillis());
			return false;
		}
		
		if(Utils.isInstantBreak(b.getType(), p.getPlayer().getInventory().getItemInHand().getType()) ||
				p.getPlayer().getInventory().getItemInHand().containsEnchantment(Enchantment.DIG_SPEED) ||
				p.getPlayer().getGameMode() == GameMode.CREATIVE) // Stop spamming reportAdmin
			return false;
		
		if((System.currentTimeMillis() - p.getLastStartBlockBreakTime()) <= 100) {
			p.reportToAdmin(HackType.WORLD_FAST_BREAK, "blockType: " + b.getType() + " time: " + (System.currentTimeMillis() - p.getLastStartBlockBreakTime()));
			p.setBreakBlockageTime(System.currentTimeMillis());
		}
		
		return false;
	}
}