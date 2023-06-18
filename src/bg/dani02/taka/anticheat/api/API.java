package bg.dani02.taka.anticheat.api;

import org.bukkit.entity.Player;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Report;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.ClientVersion;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;

public class API {

	// Player

	/**
	 * Freeze or unfreeze the player for a specified time
	 * 
	 * @param p
	 *            The player
	 * @param freeze
	 *            Will the player be freezed or unfreezed
	 * @param time
	 *            For what time will the player be freezed(Set to -1 for infinity)
	 */
	public static void setFreezed(Player p, boolean freeze, short time, HackType reason) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		if (freeze)
			Utils.freezePlayer(p, time, reason);
		else
			Utils.unfreezePlayer(p);
	}

	/**
	 * Returns boolean depending of is the player frozen or not
	 * 
	 * @param p
	 *            The player
	 * @return True if the player frozen and False otherwise
	 */
	public static boolean isFrozen(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getCheatPlayer(p.getUniqueId()).getFreezed();
	}

	/**
	 * Returns boolean depending of is the player in banwave or not
	 * 
	 * @param p
	 *            The player
	 * @exception NullPointerException
	 *                If the player is not found
	 * @return True if the player is in banwave and False otherwise
	 */
	public static boolean isPlayerInBanWave(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");

		for (CheatPlayer pa : Utils.getCheatPlayers()) {
			if (pa.getPlayer().equals(p)) {
				if (pa.getBanWave())
					return true;
			}
		}
		return false;
	}

	/**
	 * Adds or removes the player in banwave
	 * 
	 * @param p
	 *            The player
	 * @param banned
	 *            Will the player be added in the banwave or not
	 */
	public static void setPlayerInBanWave(Player p, boolean banned) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		for (CheatPlayer pa : Utils.getCheatPlayers()) {
			if (pa.getPlayer().equals(p)) {
				pa.setBanWave(banned);
			}
		}
	}

	/**
	 * Does the player have bypass for the specified HackType
	 * 
	 * @param p
	 *            The player
	 * @param h
	 *            The hack type
	 * @return boolean Does the player bypass the check
	 */
	public static boolean haveBypass(Player p, HackType h) {
		if(p == null || h == null)
			throw new NullPointerException("Player or HackType cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		
		return Utils.getCheatPlayer(p.getUniqueId()).haveBypass(h);
	}
	
	/**
	 * 
	 * @param p
	 * 			The player
	 * @param h
	 * 			The hack type
	 * @return short Get the current vl of the player for the hack type
	 */
	public static short getViolation(Player p, HackType h) {
		if(p == null || h == null)
			throw new NullPointerException("Player or HackType cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		
		return Utils.getCheatPlayer(p.getUniqueId()).getViolation(ViolationType.CANCELMOVE).get(HackType.getName(h));
	}
	
	/**
	 * 
	 * @param p
	 * 			The player
	 * @param h
	 * 			The hack type
	 * @param vl
	 * 			Violation level
	 * 
	 * Set the player's violation for the hack type
	 */

	public static void setViolation(Player p, HackType h, short vl) {
		if(p == null || h == null)
			throw new NullPointerException("Player or HackType cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		
		Utils.getCheatPlayer(p.getUniqueId()).getViolation(ViolationType.CANCELMOVE).put(HackType.getName(h), vl);
	}
	
	/**
	 * 
	 * @param p
	 * 			The player
	 * @param h
	 * 			The hack type
	 * @param vl
	 * 			Violation level
	 * 
	 * Add violation to the player for the hack type
	 */
	public static void addViolation(Player p, HackType h, short vl) {
		if(p == null || h == null)
			throw new NullPointerException("Player or HackType cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		
		Utils.getCheatPlayer(p.getUniqueId()).addViolation(h, ViolationType.CANCELMOVE, vl);
	}

	/**
	 * Returns the ping that is reported from the client
	 * 
	 * @param p
	 *            The player
	 */
	public static short getClientPing(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getCheatPlayer(p.getUniqueId()).getMCPing();
	}
	
	public static ClientVersion getClientVersion(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getClientVersion(Utils.getCheatPlayer(p.getUniqueId()));
	}

	public static boolean isGliding(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getCheatPlayer(p.getUniqueId()).isGliding();
	}
	
	/**
	 * 
	 * @param p
	 * 			The player
	 * @return boolean Does the player have verbose enabled?
	 */

	public static boolean isVerboseModeEnabled(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getCheatPlayer(p.getUniqueId()).getDebugMode();
	}

	public static short getDebugModeType(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player not found");
		return Utils.getCheatPlayer(p.getUniqueId()).getDebugModeType();
	}

	// Taka
	/**
	 * Reload Taka's config
	 */
	public static void reloadConfig() {
		ConfigsMessages.load();
	}
	
	public static void resetVL() {
		Utils.clearVL(false);
	}

	/**
	 * Resets all the violations of all players
	 * 
	 * @param message
	 * 					Should the admins be notified about vl reset?
	 */
	public static void resetVL(boolean message) {
		Utils.clearVL(message);
	}
	
	public static boolean isGhostMode() {
		return Utils.isGhostMode();
	}
	
	public static void setGhostMode(boolean ghost) {
		Utils.setGhostMode(ghost);
	}
	
	public static boolean isCheckGhosted(HackType h) {
		if (h == null)
			throw new NullPointerException("Hack Type cannot be null");
		return Utils.isDetectionGhosted(h);
	}

	/**
	 * Get the report list
	 * 
	 * @return A array of all reports
	 */
	public static Report[] getReports() {
		Report[] reps = new Report[Utils.getReports().size() - 1];
		Utils.getReports().toArray(reps);
		return reps;
	}

	public static void removeReport(Report rep) {
		if (rep.getPlayer() == null || rep.getReport() == null || rep.getReported() == null || rep == null)
			throw new NullPointerException("Report cannot contain null element or be null");
		Utils.getReports().remove(rep);
	}

	public static void addReport(Report rep) {
		if (rep.getPlayer() == null || rep.getReport() == null || rep.getReported() == null || rep == null)
			throw new NullPointerException("Report cannot contain null element or be null");
		Utils.addReport(rep, false);
	}

	public static void disableCheck(HackType h) {
		if (h == null)
			throw new NullPointerException("Hack Type cannot be null");
		Utils.setDetectionEnabled(h, false);
	}

	public static void disableCheckForPlayer(HackType h, Player p) {
		if (p == null || h == null)
			throw new NullPointerException("Player or Hack Type cannot be null");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player cannot be found!");
		Utils.setDetectionEnabled(h, Utils.getCheatPlayer(p.getUniqueId()), false);
	}

	public static void enableCheck(HackType h) {
		if (h == null)
			throw new NullPointerException("Hack Type cannot be null");
		Utils.setDetectionEnabled(h, true);
	}

	public static void enableCheckForPlayer(HackType h, Player p) {
		if (p == null || h == null)
			throw new NullPointerException("Player or Hack Type cannot be null");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player cannot be found!");
		Utils.setDetectionEnabled(h, Utils.getCheatPlayer(p.getUniqueId()), true);
	}
	
	public static boolean isCheckEnabled(HackType h) {
		if (h == null)
			throw new NullPointerException("Hack Type cannot be null");
		return Utils.isDetectionEnabled(h);
	}
	
	public static boolean isCheckEnabledForPlayer(HackType h, Player p) {
		if (p == null || h == null)
			throw new NullPointerException("Player or Hack Type cannot be null");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player cannot be found!");
		return Utils.getCheatPlayer(p.getUniqueId()).getDisabledChecks().contains(h);
	}

	public static short getMaxViolation(HackType h) {
		if (h == null)
			throw new NullPointerException("Hack Type cannot be null");
		return Short.parseShort(Utils.getMoveCancelViolation(h));
	}
	
	public static double getHackerProbability(Player p) {
		if(p == null)
			throw new NullPointerException("Player cannot be null!");
		if (Utils.getCheatPlayer(p.getUniqueId()) == null)
			throw new NullPointerException("Player cannot be found!");
		
		return Utils.getHackerProbability(Utils.getCheatPlayer(p.getUniqueId()));
	}

	/**
	 * Returns boolean depending of is there new Taka update
	 * 
	 * @return boolean Is there new Taka update
	 */
	public static boolean checkUpdate() {
		return Utils.checkUpdate() != null;
	}
}
