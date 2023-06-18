package bg.dani02.taka.anticheat.checks.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class InvalidInteractionBlock {
	public static boolean onCheck(CheatPlayer p, Block b, boolean iteraction) {
		if(!Utils.checkModule(p, HackType.WORLD_INVALID_INTERACTION_BLOCK))
			return false;
		
		// Clicked block can be null
		if(iteraction)
			if(b == null)
				return false;
		
		//mcMMO
		if(System.currentTimeMillis() - p.getLastmcMMOAbilityTime() < 1500)
			return false;
		//VeinMine
		if(System.currentTimeMillis() - p.getLastVeinMineTime() < 1500)
			return false;
		// If the player head is in the ground causes false positive
		if(p.getPlayer().getEyeLocation().getBlock().getType().isSolid())
			return false;
		// If the player is teleported fp's could occur
		if(System.currentTimeMillis() - p.getLastAllTeleportTime() < 500)
			return false;
		
		ghostHand(p, b);
		maxDistance(p, b);
		
		return false;
	}
	
	static boolean maxDistance(CheatPlayer p, Block b) {
		double dist = p.getPlayer().getEyeLocation().distance(b.getLocation());
		if(dist > 9.0) {
			p.reportToAdmin(HackType.WORLD_INVALID_INTERACTION_BLOCK, "d: " + dist);
			return true;
		}
		
		return false;
	}
	
	static boolean ghostHand(CheatPlayer p, Block b) {
		// Bed is special!
		if(b.getType().name().contains("BED")) {
					// I am sad I am weak using bit operation ;( *sob*
					// First 2 bits represent the head face: 00 -> north 01 -> east 10 -> south 11 -> west
					// The 4th bit represent if it's head or not
					@SuppressWarnings("deprecation")
					byte data = b.getData();
					
					boolean head = data >= 8;
					
					byte face = (byte) (head ? data - 8 : data);
					
					BlockFace bFace = null;
					if(face == 0)
						bFace = BlockFace.NORTH;
					else if(face == 1)
						bFace = BlockFace.EAST;
					else if(face == 2)
						bFace = BlockFace.SOUTH;
					else if(face == 3)
						bFace = BlockFace.WEST;
					
					Block otherFace = head ? b.getRelative(bFace) : b.getRelative(bFace.getOppositeFace());
					
					if(isNonBypassable(b.getRelative(BlockFace.EAST), otherFace) &&
							isNonBypassable(b.getRelative(BlockFace.WEST), otherFace) &&
							isNonBypassable(b.getRelative(BlockFace.SOUTH), otherFace) &&
							isNonBypassable(b.getRelative(BlockFace.NORTH), otherFace) &&
							isNonBypassable(b.getRelative(BlockFace.UP), otherFace) &&
							isNonBypassable(b.getRelative(BlockFace.DOWN), otherFace) &&
							isNonBypassable(otherFace.getRelative(BlockFace.EAST), b) &&
							isNonBypassable(otherFace.getRelative(BlockFace.WEST), b) &&
							isNonBypassable(otherFace.getRelative(BlockFace.SOUTH), b) &&
							isNonBypassable(otherFace.getRelative(BlockFace.NORTH), b) &&
							isNonBypassable(otherFace.getRelative(BlockFace.UP), b) &&
							isNonBypassable(otherFace.getRelative(BlockFace.DOWN), b)) {
						p.reportToAdmin(HackType.WORLD_INVALID_INTERACTION_BLOCK, "bed, c: 1");
						return true;
					}
					
					if(isNonBypassable2(b.getRelative(BlockFace.EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.SOUTH), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.NORTH), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.SOUTH_EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.NORTH_EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.SOUTH_WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.NORTH_WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.UP), otherFace) &&
							isNonBypassable2(b.getRelative(BlockFace.DOWN), otherFace) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.SOUTH), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.NORTH), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.SOUTH_EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.NORTH_EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.SOUTH_WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.NORTH_WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.UP), b) &&
							isNonBypassable2(otherFace.getRelative(BlockFace.DOWN), b)) {
						p.reportToAdmin(HackType.WORLD_INVALID_INTERACTION_BLOCK, "bed, c: 2");
						return true;
					}
		} else {
			//Check 1: If east, west, north, south, up, down blocks are solid
			if(isNonBypassable(b.getRelative(BlockFace.EAST)) &&
					isNonBypassable(b.getRelative(BlockFace.WEST)) &&
					isNonBypassable(b.getRelative(BlockFace.SOUTH)) &&
					isNonBypassable(b.getRelative(BlockFace.NORTH)) &&
					isNonBypassable(b.getRelative(BlockFace.UP)) &&
					isNonBypassable(b.getRelative(BlockFace.DOWN))) {
				p.reportToAdmin(HackType.WORLD_INVALID_INTERACTION_BLOCK, "c: 1");
				return true;
			}
			
			//Check 2: every block around -> 19 blocks are not bypassable
			if(isNonBypassable2(b.getRelative(BlockFace.EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.SOUTH)) &&
					isNonBypassable2(b.getRelative(BlockFace.NORTH)) &&
					isNonBypassable2(b.getRelative(BlockFace.SOUTH_EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.NORTH_EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.SOUTH_WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.NORTH_WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST)) &&
					isNonBypassable2(b.getRelative(BlockFace.UP)) &&
					isNonBypassable2(b.getRelative(BlockFace.DOWN))) {
				p.reportToAdmin(HackType.WORLD_INVALID_INTERACTION_BLOCK, "c: 2");
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isNonBypassable(Block b) {
		return isNonBypassable(b, null);
	}

	private static boolean isNonBypassable(Block b, Block otherFace) {
		return b.getType().isSolid() && !b.getType().name().contains("PLATE") && !b.getType().name().contains("DOOR") &&
				!b.getType().name().contains("SIGN") && !b.getType().name().contains("BANNER") && !b.getType().name().contains("PLATE")
				&& !b.getType().name().contains("STAINED_GLASS") /*
															 * && (otherFace == null &&
															 * !b.getType().name().contains("BED"))
															 */
				&& !b.getType().name().contains("SNOW") && !b.getType().name().contains("DAYLIGHT")
				&& !b.getType().name().contains("FENCE") && !b.getType().equals(XMaterial.COBBLESTONE_WALL.parseMaterial())
				&& !b.getType().name().contains("IRON_BARS") && !b.getType().equals(XMaterial.DRAGON_EGG.parseMaterial());
	}
	
	private static boolean isNonBypassable2(Block b) {
		return isNonBypassable2(b, null);
	}

	private static boolean isNonBypassable2(Block b, Block otherFace) {
		return b.getType().isSolid() && !b.getType().name().contains("PLATE") && !b.getType().name().contains("DOOR") &&
				!b.getType().name().contains("SIGN") && !b.getType().name().contains("BANNER") && !b.getType().name().contains("PLATE")
				&& !b.getType().name().contains("SNOW") && !b.getType().name().contains("DAYLIGHT");
	}
}