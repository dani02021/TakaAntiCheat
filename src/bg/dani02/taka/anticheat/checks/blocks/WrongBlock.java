package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class WrongBlock {
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Block b) {
		if(!Utils.checkModule(p, HackType.WORLD_WRONG_BLOCK_DIRECTION))
			return false;
		if (Utils.isInstantBreak(b.getType(), p.getPlayer().getItemInHand().getType())
				|| p.haveBypass(HackType.WORLD_WRONG_BLOCK_DIRECTION)
				|| p.getPlayer().getGameMode() == GameMode.CREATIVE
				|| p.getPlayer().getEyeLocation().getBlock().isLiquid())
			return false;
		if (specialType(b))
			return false;
		//mcMMO
		if(System.currentTimeMillis() - p.getLastmcMMOAbilityTime() < 1500)
			return false;
		
		// if(!Utils.isInstantBreak(b.getType())) {
		// if(p.getPlayer().getLocation().getDirection().angle(p.getPlayer().getLocation().getDirection().subtract(b.getLocation().toVector()))
		// > 44 &&
		// p.getPlayer().getLocation().getDirection().angle(p.getPlayer().getLocation().getDirection().subtract(b.getLocation().toVector()))
		// < 50) {
		// return true;
		// }
		// }
		/*
		 * Vector a =
		 * p.getPlayer().getLocation().getDirection().subtract(b.getLocation().toVector(
		 * )).normalize(); Vector a1 = p.getPlayer().getLocation().getDirection();
		 * double angle = Math.acos(a.dot(a1)); angle = Math.toDegrees(angle); angle =
		 * 180-angle; if (p.getPlayer().getLocation().distance(b.getLocation()) < 1.5)
		 * return false; p.getPlayer().sendMessage("VEC: "+angle); BlockIterator iter =
		 * new BlockIterator(p.getPlayer().getEyeLocation(), 1, 15);
		 */

		BlockIterator iter = new BlockIterator(p.getPlayer().getEyeLocation(), p.getPlayer().isSneaking() ? 0 : -0.3, 15);
		Block lastBlock;
		
		while (iter.hasNext()) {
			lastBlock = iter.next();

			if (specialType(lastBlock))
				break;

			if (lastBlock.getLocation().distance(b.getLocation()) > 1.3) {
				p.reportToAdmin(HackType.WORLD_WRONG_BLOCK_DIRECTION);
				return true;
			}

			break;
		}

		return false;
	}

	public static boolean specialType(Block lastBlock) {
		return lastBlock.isEmpty() || lastBlock.getType() == Material.SNOW || lastBlock.getType() == Material.LADDER
				|| lastBlock.getType() == Material.VINE
				|| lastBlock.getType().name().contains("SIGN") || lastBlock.getType() == XMaterial.FERN.parseMaterial()
				|| lastBlock.getType() == XMaterial.TALL_GRASS.parseMaterial()
				|| lastBlock.getType().name().contains("GLASS_PANE") || lastBlock.getType().name().contains("CARPET")
				|| lastBlock.getType().name().contains("FENCE") || lastBlock.getType().name().contains("DOOR")
				|| lastBlock.getType().name().contains("THIN_GLASS") || lastBlock.getType().equals(XMaterial.IRON_BARS.parseMaterial())
				|| lastBlock.getType().name().contains("BED") || lastBlock.getType().name().contains("LADDER")
				|| (Utils.getServerVersion() >= 7 && lastBlock.getType() == Material.SCAFFOLDING)
				|| lastBlock.isLiquid() || Utils.isInstantBreak(lastBlock.getType(), Material.AIR);
	}
}
