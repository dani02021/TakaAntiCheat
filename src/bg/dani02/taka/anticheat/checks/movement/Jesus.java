package bg.dani02.taka.anticheat.checks.movement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class Jesus {
	@SuppressWarnings("deprecation")
	public static boolean onCheck(CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_JESUS))
			return false;
		if (p.getPlayer().isInsideVehicle() || p.getPlayer().isFlying() || p.isGliding() || p.isRiptiding()
				|| Utils.isNextToEntity(p.getPlayer(), (short) 4, EntityType.BOAT)
				|| Utils.isInEntity(p.getPlayer(), (short) 4, EntityType.BOAT)
				|| Utils.isOnSnow(p.getPlayer()))
			return false;
		if (System.currentTimeMillis() - p.getLastFlyTime() < 1000 || System.currentTimeMillis() - p.getLastBubbleColumnTouchTime() < 500)
			return false;
		if (from.distance(to) == 0.0) {
			return false;
		}
		if (from.getY() < to.getY()) {
			return false;
		}
		if (System.currentTimeMillis() - p.getLastJoinTime() <= 4000)
			return false;
		if (Utils.getBlocksAroundYisAroundByName(p.getPlayer().getEyeLocation(), 0.2, 0.01, "LILY")
				|| Utils.getBlocksAroundYisAroundByName(p.getPlayer().getEyeLocation(), 0.2, 0.01, "TRAPDOOR")
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "FENCE", false, true)
				|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "WALL", false, true)
				|| Utils.isNextToBlockDifferent(p.getPlayer().getEyeLocation().clone().add(0,0.3,0), XMaterial.WATER.parseMaterial(), XMaterial.AIR.parseMaterial())) // Fixes the bug, where if you have block above your head while in water, from.y==to.y, may be responcible for bypasses
			return false;

		final Location loc = p.getPlayer().getLocation().subtract(0.0, 0.35, 0.0);
		if (loc.getBlock().isLiquid()
				&& p.getPlayer().getLocation().add(0.0, 0.1, 0.0).getBlock().getType().equals(Material.AIR)
				&& checkNotOnGround(p.getPlayer())
				&& from.getY() != to.getY()) {
			if (loc.getBlock().getData() <= 2) {
				if (Utils.isNextToBlock(p, p.getPlayer().getLocation(), XMaterial.LILY_PAD.parseMaterial())
						|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "TRAPDOOR", false, false)
						|| Utils.isNextToBlockByNameAround(p, p.getPlayer().getLocation().getBlock(), "CARPET", false, false)
						|| Utils.getMaterialsAroundByName(p.getPlayer().getLocation().clone().subtract(0, 0.1, 0),
								"CARPET")
						|| Utils.getMaterialsAround(p.getPlayer().getLocation().clone().subtract(0, 0.1, 0))
								.contains(XMaterial.LILY_PAD.parseMaterial())
						|| Utils.getMaterialsAroundByName(p.getPlayer().getLocation().clone().subtract(0, 0.1, 0),
								"TRAPDOOR")) {
					if (p.getJVL() > 0)
						p.setJVL((short) (p.getJVL() - 1));
					return false;
				}
				if (Utils.getServerVersion() >= 6) {
					if (p.getPlayer().hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
						if (p.getJVL() > 0)
							p.setJVL((short) (p.getJVL() - 1));

						return false;
					}
				}

				short vl = (short) 5;
				p.setJVL((short) (p.getJVL() + 1));

				if (p.getJVL() >= vl) {
					p.reportToAdmin(HackType.MOVING_JESUS);

					// Make problems sometimes
					// p.teleportToGround();
					
					for (short s = 3; s > 0; s--)
						if (p.getPlayer().getLocation().clone().subtract(0, s, 0).getBlock().isLiquid())
							p.teleport(p.getPlayer().getLocation().clone().subtract(0, s, 0), HackType.MOVING_JESUS);

					return true;
				}
			}
		} else if(from.getY() == to.getY()) {
			if(loc.getBlock().isLiquid() && checkNotOnGround(p.getPlayer()) &&
					loc.getBlock().getData() <= 2 && !p.isSwimming()) {
				p.setJVL((short) (p.getJVL() + 1));
				
				if(p.getJVL() > 5) {
					if(p.reportToAdmin(HackType.MOVING_JESUS)) {
						for (short s = 3; s > 0; s--)
							if (p.getPlayer().getLocation().clone().subtract(0, s, 0).getBlock().isLiquid())
								p.teleport(p.getPlayer().getLocation().clone().subtract(0, s, 0), HackType.MOVING_JESUS);
					
						p.setJVL((short) 0);
						
						return true;
					}

				}
			}
		} else {
			if (p.getJVL() > 0)
				p.setJVL((short) (p.getJVL() - 1));
		}

		return false;
	}

	private static boolean checkNotOnGround(final Player player) {
		final Location block1 = player.getLocation().clone().subtract(0, 0.15, 0);
		final Block block2 = block1.clone().add(0, 0, 0.3).getBlock();
		final Block block3 = block1.getBlock().getRelative(BlockFace.NORTH_EAST);
		final Block block4 = block1.getBlock().getRelative(BlockFace.NORTH_WEST);
		final Block block5 = block1.getBlock().getRelative(BlockFace.SOUTH_EAST);
		final Block block6 = block1.getBlock().getRelative(BlockFace.SOUTH_WEST);
		final Block block7 = block1.clone().add(0.3, 0, 0).getBlock();
		final Block block8 = block1.clone().subtract(0.3, 0, 0).getBlock();
		final Block block9 = block1.clone().subtract(0, 0, 0.3).getBlock();
		return block2.isLiquid() && block3.isLiquid() && block4.isLiquid() && block5.isLiquid() && block6.isLiquid()
				&& block7.isLiquid() && block8.isLiquid() && block9.isLiquid();
	}
}
