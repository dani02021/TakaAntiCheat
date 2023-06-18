package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class ScaffoldWalk {
	
	public static boolean onCheck(CheatPlayer p, Block b, float xDiff, float yDiff, float zDiff, Integer face) {
		// For some reason it shows false positive
		if(b.getType().name().contains("BED") ||
				p.getPlayer().isFlying() || p.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			return false;
		// Down block
		if (p.getPlayer().getLocation().getY() - b.getY() <= 2) {
			if (System.currentTimeMillis() - p.getLastSWBlockTime() < 1500) {
				if (Utils.checkModule(p, HackType.MOVING_SCAFFOLDWALK_BASIC)) {
					if (b.getRelative(Utils.getDirectionPL(face)).equals(p.getPlayer().getLocation().getBlock())) {
						p.removeViolation(HackType.MOVING_SCAFFOLDWALK_BASIC, ViolationType.CANCELMOVE, (short) 1);
						p.removeViolation(HackType.MOVING_SCAFFOLDWALK_EXPAND, ViolationType.CANCELMOVE, (short) 1);
						return false;
					}
					p.setLastSWBlockTime(System.currentTimeMillis());
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_BASIC))
						return true;
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_EXPAND))
						return true;
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_GROUND))
						return true;
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_ADVANCED))
						return true;
				}
			}
			float dir = (float) Math.toDegrees(Math.atan2(p.getPlayer().getLocation().getBlockX() - b.getX(),
					b.getZ() - p.getPlayer().getLocation().getBlockZ()));
			
			//Expand
			if (b.getLocation().getBlockX() == p.getPlayer().getLocation().getBlockX()
					|| b.getLocation().getBlockZ() == p.getPlayer().getLocation().getBlockZ()) {
				if (Utils.getDirectionPL(face) == BlockFace.SOUTH && getClosestFace(dir) == BlockFace.WEST) {
					checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_EXPAND);
					return false;
				} else if (Utils.getDirectionPL(face) == BlockFace.WEST && getClosestFace(dir) == BlockFace.NORTH) {
					checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_EXPAND);
					return false;
				} else if (Utils.getDirectionPL(face) == BlockFace.NORTH && getClosestFace(dir) == BlockFace.EAST) {
					checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_EXPAND);
					return false;
				} else if (Utils.getDirectionPL(face) == BlockFace.EAST && getClosestFace(dir) == BlockFace.SOUTH) {
					checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_EXPAND);
					return false;
				}
			}
			// Basic
			if (Utils.checkModule(p, HackType.MOVING_SCAFFOLDWALK_BASIC)) {
				if (b.getRelative(Utils.getDirectionPL(face)).equals(p.getPlayer().getLocation().getBlock())) {
					p.removeViolation(HackType.MOVING_SCAFFOLDWALK_BASIC, ViolationType.CANCELMOVE, (short) 1);
				} else {
					if (!p.getLastFromToLocations()[0].getBlock().getRelative(BlockFace.DOWN).isEmpty()
							&& p.getLastFromToLocations()[1].getBlock().getRelative(BlockFace.DOWN).isEmpty()
							&& b.equals(p.getLastFromToLocations()[0].getBlock().getRelative(BlockFace.DOWN))) {
						if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_BASIC))
							return true;
					}
				}
			}

			// Ground
			if (Utils.checkModule(p, HackType.MOVING_SCAFFOLDWALK_GROUND)) {
				if (b.getRelative(Utils.getDirectionPL(face)).equals(p.getPlayer().getLocation().getBlock())) {
					p.removeViolation(HackType.MOVING_SCAFFOLDWALK_GROUND, ViolationType.CANCELMOVE, (short) 1);
					return true;
				}
				if (!p.getLastFromToLocations()[0].getBlock().getRelative(BlockFace.DOWN).isEmpty()
						&& !p.getLastFromToLocations()[1].getBlock().getRelative(BlockFace.DOWN).isEmpty()
						&& b.equals(p.getLastFromToLocations()[0].getBlock().getRelative(BlockFace.DOWN))) {
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_GROUND))
						return true;
				}
			}

			// Advanced
			if (Utils.checkModule(p, HackType.MOVING_SCAFFOLDWALK_ADVANCED)) {
				if (conditions(xDiff, yDiff, zDiff, face) || xDiff > 1.0 || yDiff > 1.0
						|| zDiff > 1.0 && (p.getPlayer().getLocation().getY() - b.getLocation().getY()) < 4) /* Fixes some possible* false positives, but open the gate to bypasses */ {
					if(p.getPlayer().isSneaking())
						if(System.currentTimeMillis() - p.getLastSneakToggleTime() < 350)
							return false;
					if (checkIt(p, b, face, HackType.MOVING_SCAFFOLDWALK_ADVANCED))
						return true;
				}
			}
		}
		return false;
	}
	
	// Scaffold timer check, called from blockplaceevent
	public static boolean onCheckTimer(CheatPlayer p, Block b)
	{
		if(!Utils.checkModule(p, HackType.MOVING_SCAFFOLDWALK_TIMER))
			return false;
		
		if(b.getLocation().getY() < p.getPlayer().getLocation().getY() &&
				Math.abs(b.getLocation().getX() - p.getPlayer().getLocation().getX()) <= 1.8 &&
				Math.abs(b.getLocation().getY() - p.getPlayer().getLocation().getY()) <= 1.8 &&
				Math.abs(b.getLocation().getZ() - p.getPlayer().getLocation().getZ()) <= 1.8 &&
				b.getRelative(BlockFace.DOWN).isEmpty() &&
				p.getPlayer().getLocation().getPitch() >= 80) {
			if (System.currentTimeMillis() - p.getLastSWBlockTime() < 3000)
				p.setLastSWBlockTime(System.currentTimeMillis());
			
			if((System.currentTimeMillis() - p.getSWLT()) < 2000) {
				p.setSWPB((short) (p.getSWPB() + 1));
				
				if(p.getSWPB() > 3) {
					p.reportToAdmin(HackType.MOVING_SCAFFOLDWALK_TIMER, "blocks: " + p.getSWPB());
					p.setLastSWBlockTime(System.currentTimeMillis());
					return true;
				}
			} else {
				p.setSWLT(System.currentTimeMillis());				
				p.setSWPB((short) 1);
			}
		}
		
		return false;
	}

	public static BlockFace getClosestFace(float direction) {

		direction = direction % 360;

		if (direction < 0)
			direction += 360;

		direction = Math.round(direction / 45);

		switch ((int) direction) {

		case 0:
			return BlockFace.WEST;
		case 2:
			return BlockFace.NORTH;
		case 4:
			return BlockFace.EAST;
		case 6:
			return BlockFace.SOUTH;
		default:
			return BlockFace.SELF;
		}
	}

	public static boolean conditions(float xDiff, float yDiff, float zDiff, Integer face) {
		if (Utils.getDirectionPL(face) == BlockFace.EAST) {
			if (xDiff == 1.0F && yDiff == 0.5F && zDiff == 0.5F) {
				return true;
			}
		} else if (Utils.getDirectionPL(face) == BlockFace.WEST) {
			if (xDiff == 0.0F && yDiff == 0.5F && zDiff == 0.5F) {
				return true;
			}
		} else if (Utils.getDirectionPL(face) == BlockFace.SOUTH) {
			if (xDiff == 0.5F && yDiff == 0.5F && zDiff == 1.0F) {
				return true;
			}
		} else if (Utils.getDirectionPL(face) == BlockFace.NORTH) {
			if (xDiff == 0.5F && yDiff == 0.5F && zDiff == 0.0F) {
				return true;
			}
		}

		return false;
	}

	public static boolean checkIt(final CheatPlayer p, final Block b, final Integer face, final HackType h) {
		if(p.haveBypass(h) || !Utils.isDetectionEnabled(h, p) || !Utils.isDetectionEnabled(h))
			return false;
		// Fixed bug in 7.2.4
		// In newer version, the server ignores block place packets in which the block is already placed(non AIR)
		// but in older servers, this can be done, so if you place a block on non-air location and
		// scaffold is triggered, it makes the block go air
		if (p.reportToAdmin(h)) {
			// One or the other
			p.setLastSWBlockTime(System.currentTimeMillis());
			p.setBuildBlockageTime(System.currentTimeMillis());
			return true;
		}
		
		return false;
	}
}
