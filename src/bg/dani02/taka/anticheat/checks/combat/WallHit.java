package bg.dani02.taka.anticheat.checks.combat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.BlockIterator;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

// DONT ACCURATE !!!
public class WallHit {
	public static boolean onCheck(CheatPlayer damager, Entity damaged, DamageCause cause) {
		if(!Utils.checkModule(damager, HackType.WORLD_WRONG_BLOCK_DIRECTION))
			return false;
		if (damager.haveBypass(HackType.WORLD_WRONG_BLOCK_DIRECTION)
				|| damager.getPlayer().getEyeLocation().getBlock().isLiquid())
			return false;
		if (!cause.equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		Location loc1 = damager.getPlayer().getLocation().clone();
		Location loc2 = damaged.getLocation().clone();
		
		loc1.setY(0);
		loc2.setY(0);
		
		double dist = loc1.distance(loc2);

		BlockIterator iter = new BlockIterator(damager.getPlayer(), (int) dist);
		Block lastBlock;
		
		while (iter.hasNext()) {
			lastBlock = iter.next();
			
			if(!lastBlock.getChunk().isLoaded()) // ERROR ???
				continue;

			if (isPassable(lastBlock))
				continue;
			
			Utils.debugMessage("BLOK: " + lastBlock.getType());
			// report to Admin

			break;
		}

		return false;
	}

	public static boolean isPassable(Block lastBlock) {
		return lastBlock.isEmpty() || lastBlock.getType() == Material.SNOW || lastBlock.getType() == Material.LADDER
				|| lastBlock.getType() == Material.VINE
				|| lastBlock.getType().name().contains("SIGN") || lastBlock.getType() == XMaterial.FERN.parseMaterial()
				|| lastBlock.getType() == XMaterial.TALL_GRASS.parseMaterial()
				|| lastBlock.getType().name().contains("GLASS_PANE") || lastBlock.getType().name().contains("CARPET")
				|| lastBlock.getType().name().contains("FENCE") || lastBlock.getType().name().contains("DOOR")
				|| lastBlock.getType().name().contains("THIN_GLASS") || lastBlock.getType().equals(XMaterial.IRON_BARS.parseMaterial())
				|| lastBlock.getType().name().contains("BED") || lastBlock.getType().name().contains("LADDER")
				|| lastBlock.getType().name().contains("SLAB") || lastBlock.getType().name().contains("STAIRS")
				|| lastBlock.getType().name().contains("CHEST")
				|| lastBlock.isLiquid() || Utils.isInstantBreak(lastBlock.getType(), Material.AIR);
	}
}