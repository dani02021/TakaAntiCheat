package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.block.Block;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class CreativeNuker {
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_CREATIVE_NUKER))
			return false;
		if (p.getPlayer().getGameMode() != GameMode.CREATIVE)
			return false;
		if (Utils.isInstantBreak(b.getType(), p.getPlayer().getItemInHand().getType()))
			return false;
		
		if((System.currentTimeMillis() - p.getCNT()) < 350) {
			p.setCNBB((short) (p.getCNBB() + 1));
			
			if(p.getCNBB() > 5) {
				p.reportToAdmin(HackType.WORLD_CREATIVE_NUKER, "blocks: " + p.getCNBB());
				p.setLastSWBlockTime(System.currentTimeMillis());
				return true;
			}
		} else {
			p.setCNT(System.currentTimeMillis());				
			p.setCNBB((short) 1);
		}

		return false;
	}
}
