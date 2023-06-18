package bg.dani02.taka.anticheat.checks.movement;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class NoFall {
	public static ArrayList<String> damage = new ArrayList<String>();

	// Called when serverFallDistance of the player reset
	// ERROR: groundBlock is null
	// inst-3 is giving false positives, i think sometimes the fall distance don't
	// reset itself!
	
	// The check should wait a bunch of ticks, before reporting nofall, because when checking fall damage
	// sometimes the event occurs after the player has fallen, my check is really messy !!!
	
	public static void onCheck(final CheatPlayer p, Location from, Location to) {
		if(!Utils.checkModule(p, HackType.MOVING_NO_FALL))
			return;
		
		if (p.getNFWT() == 0 && damage.contains(p.getPlayer().getName())) {
			damage.remove(p.getPlayer().getName());
			return;
		}
		
		// Utils.debugMessage("FALL: dist: " + p.getServerFallDistance() + p.getPlayer().isOnGround());
		
		if(p.getServerFallDistance() < 4.0F) // Don't want to check these movements
			return;
		
		if ((Utils.isNextToBlock(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.SLIME_BLOCK)
				|| Utils.isNextToBlock(p, from, Material.SLIME_BLOCK) && !p.getPlayer().isSneaking())
				|| Utils.isOnScaffolding(p.getPlayer())
				|| p.getPlayer().getLocation().getBlock().isLiquid() || to.getBlock().isLiquid()
				|| p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getType().name()
						.contains("CARPET")
				|| p.getPlayer().isInsideVehicle() || Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
				|| p.isGliding() || p.getPlayer().getAllowFlight()
				|| Utils.isClimable(p.getPlayer().getEyeLocation().getBlock().getType())) {
			p.setFDD(0.0F);
			p.setNFD(false);
			return;
		}
		
		if (Utils.getServerVersion() >= 2) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.LEVITATION))
				return;
		}
		if (Utils.getServerVersion() >= 6) {
			if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.SLOW_FALLING))
				return;
		}
		if (p.getPlayer().getInventory().getBoots() != null)
			if (p.getPlayer().getInventory().getBoots().containsEnchantment(Enchantment.PROTECTION_FALL))
				return;

		int inst = (int) Math.ceil(p.getServerFallDistance());
		if (Utils.isUsingPotionEffect(p.getPlayer(), PotionEffectType.JUMP)) {
			inst -= Utils.getPotionEffect(p.getPlayer(), PotionEffectType.JUMP).getAmplifier();
		}
		
		if (inst - 3 > 0) {
			// This SHOULD be -3
			// If player fall 7m he will get 4 dmg, but if its inst-4 he will get 3 dmg
			p.setFDD(inst);
			// Damage should be taken on first PacketPlayInFlying(which is move, it may be just position, look, or positionlook packet)
			// in which the player is onGround!!!
			p.setNFD(true);
			// Set damage waiting event times
			p.setNFWT((short) 6);
		}
	}
	
	/*
	 * When the player has fallen, check if he has got any danage.
	 * Damage should be taken on first PacketPlayInFlying(which is move, it may be just position, look, or positionlook packet)
	 * in which the player is onGround!!!
	 */
	
	public static void checkFallDamage(CheatPlayer p, Location from, Location to) {		
		if ((Utils.isNextToBlock(p, from.getBlock().getRelative(BlockFace.DOWN).getLocation(), Material.SLIME_BLOCK)
				|| Utils.isNextToBlock(p, from, Material.SLIME_BLOCK) && !p.getPlayer().isSneaking())
				|| Utils.isOnScaffolding(p.getPlayer())
				|| p.getPlayer().getLocation().getBlock().isLiquid() || to.getBlock().isLiquid()
				|| p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock().getType().name()
						.contains("CARPET")
				|| p.getPlayer().isInsideVehicle() || Utils.isClimable(p.getPlayer().getLocation().getBlock().getType())
				|| p.isGliding() || p.getPlayer().getAllowFlight()
				|| Utils.isClimable(p.getPlayer().getEyeLocation().getBlock().getType())) {
			p.setFDD(0.0F);
			p.setNFD(false);
			return;
		}
				
		//if(!Utils.isOnGroundUltimate(to.clone().subtract(0,0.15,0)))
		//	return;
		
		// Utils.debugMessage("FALL: ckeck fall dmg: " + p.getFDD() + " " + p.getNFWT() + " " + damage.contains(p.getPlayer().getName()) + " " + (Math.abs(p.getPlayer().getLastDamage() - ((int) Math.ceil(p.getFDD() - 3))) > 1));
		
		// Just to get sure
		int dmg = (int) Math.ceil(p.getFDD() - 3);
		
		if(p.getNFWT() > 0) {
			p.setNFWT((short) (p.getNFWT() - 1));
			
			if (damage.contains(p.getPlayer().getName())) {
				// Check if the last damage is near the calculated damage, max 1 heart tolerance
				if(p.getPlayer().getLastDamageCause() != null &&
						p.getPlayer().getLastDamageCause().getCause() == DamageCause.FALL &&
						System.currentTimeMillis() - p.getLastDamageTime() <= 800)
					if(Math.abs(p.getPlayer().getLastDamage() - dmg) > 1.0D) {
						damage.remove(p.getPlayer().getName());
					} else {
						p.setNFD(false);
						p.setFDD(0.0F);
					}
			}
			
			return;
		}
				
		if(p.getNFD()) {
			p.setNFD(false);
			
			if (damage.contains(p.getPlayer().getName())) {
				// Check if the last damage is near the calculated damage, max 1 heart tolerance
				if(p.getPlayer().getLastDamageCause() != null &&
						p.getPlayer().getLastDamageCause().getCause() == DamageCause.FALL &&
						System.currentTimeMillis() - p.getLastDamageTime() <= 800)
					if(Math.abs(p.getPlayer().getLastDamage() - dmg) > 1.0D)
						damage.remove(p.getPlayer().getName());
			}
						
			if (p.getPlayer().isInsideVehicle() || Utils.isAroundLiquidLoc(p, to)
					|| Utils.isAroundLiquidLoc(p, from))
				return;
			
			if(damage.contains(p.getPlayer().getName()))
				return;
			
			// Check if the last damage is near the calculated damage, max 1 heart tolerance
			if(p.getPlayer().getLastDamageCause() != null &&
					p.getPlayer().getLastDamageCause().getCause() == DamageCause.FALL &&
					System.currentTimeMillis() - p.getLastDamageTime() <= 800)
				if(Math.abs(p.getPlayer().getLastDamage() - dmg) <= 1)
					return;
			
			// Essentials /god
			EntityDamageEvent ed = new EntityDamageEvent(p.getPlayer(), DamageCause.FALL,
					p.getPlayer().getHealth() - dmg);
			
			Taka.getThisPlugin().getServer().getPluginManager().callEvent(ed);
			if (!ed.isCancelled() && ed.getDamage() != 0.0D) {
				if(p.reportToAdmin(HackType.MOVING_NO_FALL, "f: " + p.getServerFallDistance() + " f1: " + p.getFDD() + " d:" + dmg))
					if(!Utils.isGhostMode())
						p.getPlayer().damage(dmg);
				
				p.setFDD(0.0F);
				
				return;
			}
			
			p.setFDD(0.0F);
		}
	}
}