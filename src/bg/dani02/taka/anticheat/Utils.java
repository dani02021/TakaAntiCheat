package bg.dani02.taka.anticheat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import bg.dani02.taka.anticheat.api.BanWaveRunEvent;
import bg.dani02.taka.anticheat.api.PlayerBlockInteractEvent;
import bg.dani02.taka.anticheat.api.PlayerBlockInteractEvent.IteractionType;
import bg.dani02.taka.anticheat.api.PlayerReportEvent;
import bg.dani02.taka.anticheat.api.PlayerTACTeleportEvent;
import bg.dani02.taka.anticheat.cache.CacheCheck;
import bg.dani02.taka.anticheat.cache.CacheSystem;
import bg.dani02.taka.anticheat.checks.blocks.AutoSign;
import bg.dani02.taka.anticheat.checks.blocks.CreativeNuker;
import bg.dani02.taka.anticheat.checks.blocks.FastBreak;
import bg.dani02.taka.anticheat.checks.blocks.FastPlace;
import bg.dani02.taka.anticheat.checks.blocks.InvalidInteractionBlock;
import bg.dani02.taka.anticheat.checks.blocks.LiquidInteraction;
import bg.dani02.taka.anticheat.checks.blocks.NoBreakDelay;
import bg.dani02.taka.anticheat.checks.blocks.NoSwing;
import bg.dani02.taka.anticheat.checks.blocks.WrongBlock;
import bg.dani02.taka.anticheat.checks.chat.ChatBadwords;
import bg.dani02.taka.anticheat.checks.chat.ChatCaptcha;
import bg.dani02.taka.anticheat.checks.combat.AutoSoup;
import bg.dani02.taka.anticheat.checks.combat.Criticals;
import bg.dani02.taka.anticheat.checks.combat.FastBow;
import bg.dani02.taka.anticheat.checks.combat.InvalidInteractionEntity;
import bg.dani02.taka.anticheat.checks.combat.ReachCombat;
import bg.dani02.taka.anticheat.checks.exploit.UUIDSpoof;
import bg.dani02.taka.anticheat.checks.inventory.CreativeDrop;
import bg.dani02.taka.anticheat.checks.inventory.FastClick;
import bg.dani02.taka.anticheat.checks.inventory.FastEat;
import bg.dani02.taka.anticheat.checks.inventory.FastInventory;
import bg.dani02.taka.anticheat.checks.inventory.InvMove;
import bg.dani02.taka.anticheat.checks.inventory.Throw;
import bg.dani02.taka.anticheat.checks.movement.AntiLevitation;
import bg.dani02.taka.anticheat.checks.movement.Blink;
import bg.dani02.taka.anticheat.checks.movement.FastLadder;
import bg.dani02.taka.anticheat.checks.movement.GroundElytra;
import bg.dani02.taka.anticheat.checks.movement.HighJump;
import bg.dani02.taka.anticheat.checks.movement.ImpossibleJump;
import bg.dani02.taka.anticheat.checks.movement.Jesus;
import bg.dani02.taka.anticheat.checks.movement.LowJump;
import bg.dani02.taka.anticheat.checks.movement.NoFall;
import bg.dani02.taka.anticheat.checks.movement.NoPitch;
import bg.dani02.taka.anticheat.checks.movement.ScaffoldWalk;
import bg.dani02.taka.anticheat.checks.movement.Sneak;
import bg.dani02.taka.anticheat.checks.movement.Step;
import bg.dani02.taka.anticheat.checks.movement.Strafe;
import bg.dani02.taka.anticheat.checks.movement.Timer;
import bg.dani02.taka.anticheat.checks.movement.fly.FlyDoubleJumpDown;
import bg.dani02.taka.anticheat.checks.movement.fly.FlyDoubleJumpUP;
import bg.dani02.taka.anticheat.checks.movement.fly.FlyInvalidY;
import bg.dani02.taka.anticheat.checks.movement.fly.FlyModulo;
import bg.dani02.taka.anticheat.checks.movement.fly.FlyStableY;
import bg.dani02.taka.anticheat.checks.movement.invalidfall.InvalidFall;
import bg.dani02.taka.anticheat.checks.movement.speed.Speed;
import bg.dani02.taka.anticheat.enums.ClientVersion;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.LogMessage;
import bg.dani02.taka.anticheat.enums.MoveDirectionType;
import bg.dani02.taka.anticheat.enums.Numbers;
import bg.dani02.taka.anticheat.enums.PluginHookType;
import bg.dani02.taka.anticheat.enums.ServerType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import bg.dani02.taka.anticheat.enums.XMaterial;
import bg.dani02.taka.anticheat.exceptions.InvalidServerVersionException;
import bg.dani02.taka.anticheat.exceptions.TakaException;
import bg.dani02.taka.anticheat.managers.ProtocolLibManager;
import me.clip.placeholderapi.PlaceholderAPI;
import mkremins.fanciful.FancyMessage;

@SuppressWarnings("deprecation")
public class Utils {

	private static HashMap<UUID, CheatPlayer> chP = new HashMap<UUID, CheatPlayer>();

	private static ArrayList<Report> reports = new ArrayList<Report>();
	
	private static ArrayList<String> disabledWorlds = new ArrayList<String>();

	private static Thread th;

	private static com.comphenix.protocol.ProtocolManager protocolManager;

	private static boolean placeholder;
	private static boolean citizens;
	private static boolean mcmmo;
	private static boolean realdualwield;
	private static boolean veinmine;

	private static long takaST;
	public static long suspendMillis;
	
	public static String bungeeServerName = "";

	private static short version;

	private static int banWaveTaskID;

	public static double config_version = 1.7D;
	
	// For Speed check
	public static Executor spExecutor = Executors.newSingleThreadExecutor();

	// PluginUtils
	public static String prepareColors(String str) {
		return str.replaceAll("&", "§");
	}

	public static String removeColors(String str) {
		return str.replaceAll("&[1-9a-fk-or]|§[1-9a-fk-or]", "");
	}
	
	/**
	 * 1.8 = 1
	 * 1.9 = 2
	 * 1.10 = 3
	 * 1.11 = 4
	 * 1.12 = 5
	 * 1.14 = 7
	 * 1.15 = 8
	 * 1.16 = 9
	 * 
	 * @return
	 */
	public static short getServerVersion() {
		return version;
	}

	public static void setServerVersion() {
		String s = Taka.getThisPlugin().getServer().getVersion().split("MC: ")[1].replaceAll("\\)", "");
		short res = 0;
		// Checks
		if (s.contains("1.8"))
			res = 1;
		else if (s.contains("1.9"))
			res = 2;
		else if (s.contains("1.10"))
			res = 3;
		else if (s.contains("1.11"))
			res = 4;
		else if (s.contains("1.12"))
			res = 5;
		else if (s.contains("1.13"))
			res = 6;
		else if (s.contains("1.14"))
			res = 7;
		else if (s.contains("1.15"))
			res = 8;
		else if (s.contains("1.16"))
			res = 9;
		else if (s.contains("1.17"))
			res = 10;
		else if (s.contains("1.18"))
			res = 11;
		else if (s.contains("1.19"))
			res = 12;
		else if (s.contains("1.20"))
			res = 13;
		
		// Maybe too new version?
		if(res == 0) {
			version = 13;
			throw new InvalidServerVersionException("Taka dosen't support your current version: " + s);
		}

		version = res;
	}

	public static ServerType getServerType() {
		String s = Taka.getThisPlugin().getServer().getVersion();

		if (s.contains("Spigot"))
			return ServerType.SPIGOT;
		else if (s.contains("Paper"))
			return ServerType.PAPER;
		else if (s.contains("Bukkit"))
			return ServerType.CRAFTBUKKIT;

		return ServerType.UNKNOWN;
	}
	
	public static ClientVersion getClientVersion(CheatPlayer p) {
		if(p.getClientVersion() <= 5) {
			return ClientVersion.v1_7_X;
		} else if(p.getClientVersion() <= 47) {
			return ClientVersion.v1_8_X;
		} else if(p.getClientVersion() <= 110) {
			return ClientVersion.v1_9_X;
		} else if(p.getClientVersion() <= 210) {
			return ClientVersion.v1_10_X;
		} else if(p.getClientVersion() <= 316) {
			return ClientVersion.v1_11_X;
		} else if(p.getClientVersion() <= 340) {
			return ClientVersion.v1_12_X;
		} else if(p.getClientVersion() <= 404) {
			return ClientVersion.v1_13_X;
		} else if(p.getClientVersion() <= 498) {
			return ClientVersion.v1_14_X;
		} else if(p.getClientVersion() <= 578) {
			return ClientVersion.v1_15_X;
		} else if(p.getClientVersion() <= 754) {
			return ClientVersion.v1_16_X;
		}
		
		return ClientVersion.UNKNOWN;
	}

	public static String getAdminPermission() {
		return ConfigsMessages.anticheat_admin_permission;
	}

	public static String getBypassPermission() {
		return ConfigsMessages.anticheat_bypass_permission;
	}

	public static String getCommandReloadPermission() {
		return "TAC.Commands.Reload";
	}

	public static String getCommandPemrsPermission() {
		return "TAC.Commands.Permissions";
	}

	public static String getCommandChangelogPermission() {
		return "TAC.Commands.Changelog";
	}

	public static String getCommandBanWavePermission() {
		return "TAC.Commands.BanWave";
	}

	public static String getCommandGhostPermission() {
		return "TAC.Commands.Ghost";
	}

	public static String getCommandTpsPermission() {
		return "TAC.Commands.Tps";
	}

	public static String getCommandDebugPermission() {
		return "TAC.Commands.Debug";
	}

	public static String getCommandCheckPermission() {
		return "TAC.Commands.Check";
	}

	public static String getCommandClearVLPermission() {
		return "TAC.Commands.ClearVL";
	}

	public static String getCommandReportListPermission() {
		return "TAC.Commands.ReportList";
	}

	public static String getCommandTReportPermission() {
		return "TAC.Commands.Report";
	}

	public static String getCommandCommandsPermission() {
		return "TAC.Commands.Commands";
	}

	public static String getCommandLogPermission() {
		return "TAC.Commands.Log";
	}

	public static String getCommandFreezePermission() {
		return "TAC.Commands.Freeze";
	}

	public static String getCommandUnfreezePermission() {
		return "TAC.Commands.Unfreeze";
	}

	public static String getCommandMessagePermission() {
		return "TAC.Commands.Message";
	}

	public static String getCommandSuspendPermission() {
		return "TAC.Commands.Suspend";
	}
	
	public static String getCommandDisabledWorldsPermission() {
		return "TAC.Commands.DisabledWorlds";
	}
	
	public static String getCommandTimingsPermission() {
		return "TAC.Commands.Timings";
	}
	
	public static String getCommandVersionPermission() {
		return "TAC.Commands.Version";
	}

	public static String getAdminTurnStaffModeMessage(boolean staffMode) {
		return staffMode ? ConfigsMessages.anticheat_admin_staff_mode_message.replaceAll("%m%", "enable")
				: ConfigsMessages.anticheat_admin_staff_mode_message.replaceAll("%m%", "disable");
	}

	public static String getAdminTurnDebugModeMessage(boolean debugMode) {
		return debugMode ? ConfigsMessages.anticheat_admin_debug_mode_message.replaceAll("%m%", "enable")
				: ConfigsMessages.anticheat_admin_debug_mode_message.replaceAll("%m%", "disable");
	}

	public static String getNoPermissionMessage() {
		return ConfigsMessages.anticheat_no_permission_message;
	}

	public static double getLogSizeWarningLevel() {
		return ConfigsMessages.log_file_warning;
	}

	public static double getLogFolderSize() {
		return getLogFolderSize(new File("plugins/TakaAntiCheat/logs"));
	}

	// Not sure who should see it, I got a request to don't show the remove violation messages, actually no one needs them right? Except the dev(me)
	public static void clearVL(boolean message) {
		for (CheatPlayer cp : getCheatPlayers()) {
			if(message)
				if (cp.getDebugMode() && cp.getDebugModeType() == 4)
					cp.logMessage(LogMessage.VERBOSE, ConfigsMessages.anticheat_vl_cleaner_message);
			
			cp.clearViolation();
		}

//		addMessageToFileLog(null, "WARNING: " + ConfigsMessages.anticheat_vl_cleaner_message);

//		if (ConfigsMessages.log_console_enable)
//			if (ConfigsMessages.log_console_use_colors)
//				Taka.getThisPlugin().getServer().getConsoleSender()
//						.sendMessage(prepareColors(ConfigsMessages.anticheat_vl_cleaner_message));
//			else
//				Taka.getThisPlugin().getServer().getConsoleSender()
//						.sendMessage(removeColors(ConfigsMessages.anticheat_vl_cleaner_message));

	}

	public static void reloadConfig() {
		Taka.getThisPlugin().reloadConfig();
		ConfigsMessages.load();

		if (!ConfigsMessages.ban_wave_enable)
			Taka.getThisPlugin().getServer().getScheduler().cancelTask(banWaveTaskID);
	}

	public static void addMessageToFileLog(Player player, String message) {
		String messageC = ConfigsMessages.log_file_message
				.replaceAll("%y%", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)))
				.replaceAll("%m%", Integer.toString(Calendar.getInstance().get(Calendar.MONTH)))
				.replaceAll("%d%", Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))
				.replaceAll("%h%", Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))
				.replaceAll("%mm%", Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)))
				.replaceAll("%s%", Integer.toString(Calendar.getInstance().get(Calendar.SECOND)));

		String message1 = message;
		if (isHookedTo(PluginHookType.PLACEHOLDERAPI) && player != null)
			message1 = PlaceholderAPI.setPlaceholders(player, message);
		Taka.fileLog.append("," + messageC + message1);
	}

	private static double getLogFolderSize(File dir) {
		long length = 0;

		if (dir == null || !dir.exists()) // No idea why, but throw errors
			return 0;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				length += file.length();
			else
				length += getLogFolderSize(file);
		}

		return length >> 30; // return GB
	}

	public static void loadUpdateChecker() {
		try {
			Taka.getThisPlugin().getServer().getScheduler().scheduleAsyncRepeatingTask(Taka.getThisPlugin(),
					new Runnable() {

						@Override
						public void run() {
							String ver = checkUpdate();
							if (ver != null) {
								Taka.getThisPlugin().getServer().getConsoleSender()
										.sendMessage(prepareColors(ConfigsMessages.anticheat_prefix
												+ "&5WARNING: There is a new update for TAKA!"));
								Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(
										prepareColors(ConfigsMessages.anticheat_prefix + "&5WARNING: Current version: "
												+ Taka.getThisPlugin().getDescription().getVersion()));
								Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(prepareColors(
										ConfigsMessages.anticheat_prefix + "&5WARNING: New version: " + ver));
								Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(prepareColors(
										ConfigsMessages.anticheat_prefix + "&5Thank you for using TAKA :)"));
							}
						}
					}, 1L, 2 * 60 * 60 * 20L);
		} catch (Exception ex) {
			printError(ex.getMessage(), 1);
		}
	}

	public static String checkUpdate() {
		try {
			HttpURLConnection c = (HttpURLConnection) new URL(
					"https://api.spigotmc.org/legacy/update.php?resource=45167").openConnection();
			c.setDoOutput(true);
			c.setRequestMethod("GET");
			// c.getOutputStream()
			// .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=26911")
			// .getBytes("UTF-8"));
			String oldVersion = Taka.getThisPlugin().getDescription().getVersion();
			String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine();
			if (!newVersion.equals(oldVersion)) {
				return newVersion;
			}
		} catch (Exception e) {
			Taka.getThisPlugin().getServer().getConsoleSender()
					.sendMessage(prepareColors((getTakaPrefix() + "Error while trying to check for an update!")));
		}

		return null;
	}

	public static void loadViolationCleaner() {
		try {
			Taka.getThisPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Taka.getThisPlugin(),
					new Runnable() {

						@Override
						public void run() {
							clearVL(true);
							
							try {
								if (Taka.getThisPlugin().getServer().getOnlinePlayers().size() != chP.size()) {
									printError("Unsyncronized player count!", 21);
									
									// Attempt to fix
									for (Player p : Taka.getThisPlugin().getServer().getOnlinePlayers()) {
										if(getCheatPlayer(p.getUniqueId()) == null) {
											addCheatPlayer(p.getUniqueId());
										} else {
											// Invalid element -> Player can't be null neither offline
											if(getCheatPlayer(p.getUniqueId()).getPlayer() == null ||
													!getCheatPlayer(p.getUniqueId()).getPlayer().isOnline()) {
													removeCheatPlayer(p.getUniqueId());
												}
										}
									}
								}
							} catch (ConcurrentModificationException ex) {
								ex.printStackTrace();
							}
						}
					}, 20L * getVLCleanTime(), 20L * getVLCleanTime());
		} catch (Exception ex) {
			printError(ex.getMessage(), 2);
		}
	}

	public static void loadWTDTFile() {
		try {
			Taka.getThisPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Taka.getThisPlugin(),
					new Runnable() {

						@Override
						public void run() {
							File f = new File("plugins/TakaAntiCheat/logs/");
							File f1 = new File("plugins/TakaAntiCheat/logs/" + Calendar.getInstance().get(Calendar.YEAR)
									+ "-" + Calendar.getInstance().get(Calendar.MONTH) + "-"
									+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + ".txt");
							if (!f.exists()) {
								f.mkdirs();
								try {
									f1.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							PrintWriter ps = null;
							try {
								ps = new PrintWriter(new FileWriter(f1, true));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

							String[] s = Taka.fileLog.toString().split(",");
							for (int i = 1; i <= s.length - 1; i++) {
								ps.println(s[i]);
							}
							ps.close();

							Taka.fileLog.setLength(0);// These two should be together for better performance!
							Taka.fileLog.trimToSize();// These two should be together for better performance!
						}
					}, 20L * getWTDTFileCleanTime(), 20L * getWTDTFileCleanTime());

		} catch (Exception ex) {
			printError(ex.getMessage(), 3);
		}
	}

	public static void loadWTDTReport() {
		try {
			Taka.getThisPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Taka.getThisPlugin(),
					new Runnable() {

						@Override
						public void run() {
							if (getReports().isEmpty())
								return;
							File f = new File("plugins/TakaAntiCheat/report.txt");
							if (!f.exists()) {
								try {
									f.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							writeReports(f);
						}
					}, 20L * getWTDTReportCleanTime(), 20L * getWTDTReportCleanTime());

		} catch (Exception ex) {
			printError(ex.getMessage(), 31);
		}
	}

	public static void loadBanWave() {
		try {
			banWaveTaskID = Taka.getThisPlugin().getServer().getScheduler()
					.scheduleSyncRepeatingTask(Taka.getThisPlugin(), new Runnable() {

						@Override
						public void run() {
							BanWaveRunEvent banwave = new BanWaveRunEvent();
							Taka.getThisPlugin().getServer().getPluginManager().callEvent(banwave);
							if (!banwave.isCancelled()) {
								for (CheatPlayer p : getCheatPlayers()) {
									if (p.getBanWave()) {
										p.setBanWave(false);
										Taka.getThisPlugin().getServer().dispatchCommand(
												Taka.getThisPlugin().getServer().getConsoleSender(), getBanWaveCommand()
														.replaceAll("%p%|%player%", p.getPlayer().getName()));
									}
								}
							}
						}
					}, 20L * getBanWaveTime(), 20L * getBanWaveTime());

		} catch (Exception ex) {
			printError(ex.getMessage(), 4);
		}
	}

	public static void loadLogSizeWarner() {
		try {
			Taka.getThisPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Taka.getThisPlugin(),
					new Runnable() {

						@Override
						public void run() {
							double size = getLogFolderSize();

							if (size > getLogSizeWarningLevel()) {
								addMessageToFileLog(null, "WARNING: All logs current size: " + size + " GB!");
							}
						}
					}, 20L * getWTDTFileCleanTime() * 2, 20L * getWTDTFileCleanTime() * 2);
		} catch (Exception ex) {
			printError(ex.getMessage(), 5);
		}
	}

	public static void startFlooder() {
		th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true && !Thread.currentThread().isInterrupted()) {

					for (CheatPlayer cp : getCheatPlayers()) {
						/*
						 * if(cp.getRIPN() >= 21) {
						 * Taka.getThisPlugin().getServer().getScheduler().runTask(Taka.getThisPlugin(),
						 * new Runnable() {
						 * 
						 * @Override public void run() {
						 * cp.getPlayer().kickPlayer(ConfigsMessages.flooder_kick_message); } }); } else
						 */if (cp.getUEPN() >= 35) {
							Taka.getThisPlugin().getServer().getScheduler().runTask(Taka.getThisPlugin(),
									new Runnable() {

										@Override
										public void run() {
											// cp.getPlayer().kickPlayer(ConfigsMessages.flooder_kick_message);
										}
									});
						} else if (cp.getCPPN() >= 15) {
							Taka.getThisPlugin().getServer().getScheduler().runTask(Taka.getThisPlugin(),
									new Runnable() {

										@Override
										public void run() {
											// cp.getPlayer().kickPlayer(ConfigsMessages.flooder_kick_message);
										}
									});
						}

						cp.setRIPN((short) 0);
						cp.setUEPN((short) 0);
						cp.setCPPN((short) 0);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "Anti-Flood");

		th.start();
	}

	@SuppressWarnings({ "static-access" })
	public static void stopFlooder() {
		if (th == null)
			return;
		if (!Thread.interrupted())
			th.stop();
	}

	// Arduino algorithm
	public static double mapNumbers(double f, double in_min, double in_max, double out_min, double out_max) {
		return (f - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static String getTakaPrefix() {
		return ConfigsMessages.anticheat_prefix;
	}

	public static CheatPlayer getCheatPlayer(UUID uuid) {
		return chP.get(uuid);
	}

	public static Collection<CheatPlayer> getCheatPlayers() {
		return chP.values();
	}

	public static void addCheatPlayer(UUID uuid) {
		if(getCheatPlayer(uuid) != null)
			throw new TakaException("Trying to add cheatplayer when already exists! p: " + getCheatPlayer(uuid).getPlayer().getName());
		
		CheatPlayer a = new CheatPlayer(Taka.getThisPlugin().getServer().getPlayer(uuid));
		chP.put(uuid, a);
	}

	public static void removeCheatPlayer(UUID uuid) {
		chP.remove(uuid);
	}

	public static void removeCheatPlayer(CheatPlayer cp) {
		chP.remove(cp.getPlayer().getUniqueId());
	}

	public static int getVLCleanTime() {
		return ConfigsMessages.anticheat_vl_cleaner_time;// Seconds
	}

	public static int getWTDTFileCleanTime() {
		return ConfigsMessages.log_file_wtdt * 60;// Minutes
	}

	public static int getWTDTReportCleanTime() {
		return 120 * 60;// Minutes
	}

	public static int getBanWaveTime() {
		return ConfigsMessages.ban_wave_time * 60;// Minutes
	}

	public static String getBanWaveCommand() {
		return ConfigsMessages.ban_wave_command;
	}

	public static long getStartTime() {
		return takaST;
	}

	public static void setStartTime(long time) {
		takaST = time;
	}

	public static Map<String, Object> getThreshold(HackType h) {
		try {
			switch (h) {
			case MOVING_FLY_INVALID_Y:
				return ConfigsMessages.fly_invalid_y_instant_threshold;
			case MOVING_FLY_STABLE_Y:
				return ConfigsMessages.fly_stable_y_threshold;
			case MOVING_FLY_DOUBLE_JUMP_UP:
				return ConfigsMessages.fly_double_jump_up_threshold;
			case MOVING_FLY_DOUBLE_JUMP_DOWN:
				return ConfigsMessages.fly_double_jump_down_threshold;
			case MOVING_FLY_MODULO:
				return ConfigsMessages.fly_modulo_threshold;
			case MOVING_FLY_SLOW_Y_NON_INSTANT:
				return ConfigsMessages.fly_slow_y_non_instant_threshold;
			case MOVING_FLY_SLOW_Y_INSTANT:
				return ConfigsMessages.fly_slow_y_instant_threshold;
			case MOVING_FASTLADDER_INSTANT:
				return ConfigsMessages.fastladder_instant_threshold;
			case MOVING_FASTLADDER_NON_INSTANT:
				return ConfigsMessages.fastladder_non_instant_threshold;
			case MOVING_INVALIDFALL_STABLE_DISTANCE:
				return ConfigsMessages.invalidfall_stabledistance_threshold;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_instant_threshold;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_instant_threshold;
			case MOVING_INVALIDFALL_SLOWER_DISTANCE:
				return ConfigsMessages.invalidfall_slowerdistance_threshold;
			case MOVING_INVALIDFALL_FAST_START_DISTANCE:
				return ConfigsMessages.invalidfall_faststartdistance_threshold;
			case MOVING_INVALIDFALL_SLOW_Y:
				return ConfigsMessages.invalidfall_slow_y_threshold;
			case MOVING_SPEED_ONGROUND:
				return ConfigsMessages.speed_onground_threshold;
			case MOVING_SPEED_ONGROUND_SLIME:
				return ConfigsMessages.speed_onground_slime_threshold;
			case MOVING_SPEED_ONGROUND_SOULSAND:
				return ConfigsMessages.speed_onground_soulsand_threshold;
			case MOVING_SPEED_ONGROUND_ICE:
				return ConfigsMessages.speed_onground_ice_threshold;
			case MOVING_SPEED_AIR:
				return ConfigsMessages.speed_air_threshold;
			case MOVING_SPEED_COBWEB:
				return ConfigsMessages.speed_cobweb_threshold;
			case MOVING_SPEED_ITEM:
				return ConfigsMessages.speed_item_threshold;
			case MOVING_SPEED_LIQUID_WATER:
				return ConfigsMessages.speed_liquid_water_threshold;
			case MOVING_SPEED_LIQUID_LAVA:
				return ConfigsMessages.speed_liquid_lava_threshold;
			case MOVING_SPEED_SPRINT_HUNGRY:
				return ConfigsMessages.speed_sprint_hungry_threshold;
			case MOVING_SPEED_SOULSAND:
				return ConfigsMessages.speed_soulsand_threshold;
			case MOVING_SPEED_ICE:
				return ConfigsMessages.speed_ice_threshold;
			case MOVING_SPEED_SLIME:
				return ConfigsMessages.speed_slime_threshold;
			case MOVING_SPEED_VEHICLE:
				return ConfigsMessages.speed_vehicle_threshold;
			case MOVING_SLIME_JUMP:
				return ConfigsMessages.slime_jump_threshold;
			case MOVING_ANTI_LEVITATION:
				return ConfigsMessages.anti_levitation_threshold;
			case MOVING_NO_FALL:
				return ConfigsMessages.nofall_threshold;
			case MOVING_IMPOSSIBLE_JUMP:
				return ConfigsMessages.impossible_jump_threshold;
			case MOVING_HIGHJUMP:
				return ConfigsMessages.highjump_threshold;
			case MOVING_STEP:
				return ConfigsMessages.step_ultra_threshold;
			case MOVING_SCAFFOLDWALK_BASIC:
				return ConfigsMessages.scaffoldwalk_basic_threshold;
			case MOVING_SCAFFOLDWALK_ADVANCED:
				return ConfigsMessages.scaffoldwalk_advanced_threshold;
			case MOVING_SCAFFOLDWALK_GROUND:
				return ConfigsMessages.scaffoldwalk_ground_threshold;
			case MOVING_SCAFFOLDWALK_EXPAND:
				return ConfigsMessages.scaffoldwalk_expand_threshold;
			case MOVING_SCAFFOLDWALK_TIMER:
				return ConfigsMessages.scaffoldwalk_timer_threshold;
			case MOVING_SNEAK:
				return ConfigsMessages.sneak_threshold;
			case MOVING_JESUS:
				return ConfigsMessages.jesus_threshold;
			case MOVING_NO_PITCH:
				return ConfigsMessages.no_pitch_threshold;
			case MOVING_GROUND_ELYTRA:
				return ConfigsMessages.ground_elytra_threshold;
			case MOVING_TIMER:
				return ConfigsMessages.timer_threshold;
			case MOVING_LOW_JUMP:
				return ConfigsMessages.low_jump_threshold;
			case MOVING_BLINK:
				return ConfigsMessages.blink_threshold;
			case MOVING_STRAFE:
				return ConfigsMessages.strafe_threshold;
			case INVENTORY_FAST_INVENTORY:
				return ConfigsMessages.fastinventory_threshold;
			case INVENTORY_MOVE:
				return ConfigsMessages.inv_move_threshold;
			case INVENTORY_FAST_EAT:
				return ConfigsMessages.fast_eat_threshold;
			case INVENTORY_FAST_CLICK:
				return ConfigsMessages.fast_click_threshold;
			case INVENTORY_THROW:
				return ConfigsMessages.throw_threshold;
			case WORLD_WRONG_BLOCK_DIRECTION:
				return ConfigsMessages.wrongblock_direction_threshold;
			case WORLD_LIQUID_INTERACTION:
				return ConfigsMessages.liquid_interaction_threshold;
			case WORLD_CREATIVE_DROP:
				return ConfigsMessages.creative_drop_threshold;
			case WORLD_NO_SWING:
				return ConfigsMessages.no_swing_threshold;
			case WORLD_FAST_PLACE:
				return ConfigsMessages.fast_place_threshold;
			case WORLD_CREATIVE_NUKER:
				return ConfigsMessages.nuker_threshold;
			case WORLD_FAST_BREAK:
				return ConfigsMessages.fast_break_threshold;
			case WORLD_NO_BREAK_DELAY:
				return ConfigsMessages.no_break_delay_threshold;
			case WORLD_INVALID_INTERACTION_BLOCK:
				return ConfigsMessages.invaliditeraction_block_threshold;
			case WORLD_AUTO_SIGN:
				return ConfigsMessages.autosign_threshold;
			case COMBAT_CRITICALS:
				return ConfigsMessages.criticals_threshold;
			case COMBAT_REACH:
				return ConfigsMessages.reach_combat_threshold;
			case COMBAT_AUTOSOUP:
				return ConfigsMessages.autosoup_threshold;
			case COMBAT_FASTBOW:
				return ConfigsMessages.fastbow_threshold;
			case COMBAT_INVALID_INTERACTION_ENTITY:
				return ConfigsMessages.invaliditeraction_entity_threshold;
			default:
				break;
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 6);
		}

		return null;
	}

	public static String getMoveCancelViolation(HackType h) {
		try {
			switch (h) {
			case MOVING_FLY_INVALID_Y:
				return "0";
			case MOVING_FLY_STABLE_Y:
				return ConfigsMessages.fly_stable_y_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_FLY_DOUBLE_JUMP_UP:
				return "0";
			case MOVING_FLY_DOUBLE_JUMP_DOWN:
				return "0";
			case MOVING_FLY_MODULO:
				return "0";
			case MOVING_FLY_SLOW_Y_NON_INSTANT:
				return ConfigsMessages.fly_slow_y_non_instant_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_FLY_SLOW_Y_INSTANT:
				return "0";
			case MOVING_HIGHJUMP:
				return "0";
			case MOVING_FASTLADDER_NON_INSTANT:
				return ConfigsMessages.fastladder_non_instant_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_FASTLADDER_INSTANT:
				return "0";
			case MOVING_INVALIDFALL_STABLE_DISTANCE:
				return ConfigsMessages.invalidfall_stabledistance_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_non_instant_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
				return "0";
			case MOVING_INVALIDFALL_SLOWER_DISTANCE:
				return ConfigsMessages.invalidfall_slowerdistance_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_INVALIDFALL_FAST_START_DISTANCE:
				return "0";
			case MOVING_INVALIDFALL_SLOW_Y:
				return "0";
			case MOVING_SPEED_ONGROUND:
				return ConfigsMessages.speed_onground_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_ONGROUND_SLIME:
				return ConfigsMessages.speed_onground_slime_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_ONGROUND_SOULSAND:
				return ConfigsMessages.speed_onground_soulsand_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_ONGROUND_ICE:
				return ConfigsMessages.speed_onground_ice_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_AIR:
				return ConfigsMessages.speed_air_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_COBWEB:
				return ConfigsMessages.speed_cobweb_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_ITEM:
				return ConfigsMessages.speed_item_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_LIQUID_WATER:
				return ConfigsMessages.speed_liquid_water_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_LIQUID_LAVA:
				return ConfigsMessages.speed_liquid_lava_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_SPRINT_HUNGRY:
				return ConfigsMessages.speed_sprint_hungry_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_SOULSAND:
				return ConfigsMessages.speed_soulsand_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_ICE:
				return ConfigsMessages.speed_ice_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_SLIME:
				return ConfigsMessages.speed_slime_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SPEED_VEHICLE:
				return ConfigsMessages.speed_vehicle_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SLIME_JUMP:
				return "0";
			case MOVING_ANTI_LEVITATION:
				return ConfigsMessages.anti_levitation_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_NO_FALL:
				return "0";
			case MOVING_IMPOSSIBLE_JUMP:
				return ConfigsMessages.impossible_jump_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_STEP:
				return "0";
			case MOVING_SCAFFOLDWALK_BASIC:
				return ConfigsMessages.scaffoldwalk_basic_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SCAFFOLDWALK_ADVANCED:
				return ConfigsMessages.scaffoldwalk_advanced_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SCAFFOLDWALK_GROUND:
				return ConfigsMessages.scaffoldwalk_ground_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SCAFFOLDWALK_EXPAND:
				return ConfigsMessages.scaffoldwalk_expand_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SCAFFOLDWALK_TIMER:
				return ConfigsMessages.scaffoldwalk_timer_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_SNEAK:
				return "0";
			case MOVING_JESUS:
				return "0";
			case MOVING_GROUND_ELYTRA:
				return ConfigsMessages.ground_elytra_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_NO_PITCH:
				return "0";
			case MOVING_TIMER:
				return ConfigsMessages.timer_cancelMove.replaceAll("[^0-9]", "");
			case MOVING_LOW_JUMP:
				return ConfigsMessages.low_jump_cancelMove;
			case MOVING_BLINK:
				return ConfigsMessages.blink_cancelMove;
			case MOVING_STRAFE:
				return ConfigsMessages.strafe_cancelMove;
			case INVENTORY_FAST_INVENTORY:
				return "0";
			case INVENTORY_MOVE:
				return ConfigsMessages.inv_move_cancelMove.replaceAll("[^0-9]", "");
			case INVENTORY_FAST_EAT:
				return "0";
			case INVENTORY_FAST_CLICK:
				return ConfigsMessages.fast_click_cancelMove.replaceAll("[^0-9]", "");
			case INVENTORY_THROW:
				return "0";
			case WORLD_WRONG_BLOCK_DIRECTION:
				return "0";
			case WORLD_LIQUID_INTERACTION:
				return "0";
			case WORLD_CREATIVE_DROP:
				return "0";
			case WORLD_NO_SWING:
				return ConfigsMessages.no_swing_cancelMove.replaceAll("[^0-9]", "");
			case WORLD_FAST_PLACE:
				return ConfigsMessages.fast_place_cancelMove.replaceAll("[^0-9]", "");
			case WORLD_CREATIVE_NUKER:
				return ConfigsMessages.nuker_cancelMove.replaceAll("[^0-9]", "");
			case WORLD_FAST_BREAK:
				return "0";
			case WORLD_NO_BREAK_DELAY:
				return "0";
			case WORLD_INVALID_INTERACTION_BLOCK:
				return "0";
			case WORLD_AUTO_SIGN:
				return ConfigsMessages.autosign_cancelMove.replaceAll("[^0-9]", "");
			case COMBAT_CRITICALS:
				return ConfigsMessages.criticals_cancelMove.replaceAll("[^0-9]", "");
			case COMBAT_REACH:
				return ConfigsMessages.reach_combat_cancelMove.replaceAll("[^0-9]", "");
			case COMBAT_AUTOSOUP:
				return "0";
			case COMBAT_FASTBOW:
				return ConfigsMessages.fastbow_cancelMove.replaceAll("[^0-9]", "");
			case COMBAT_INVALID_INTERACTION_ENTITY:
				return "0";

			default:
				return "0";
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 7);
		}

		return null;
	}

	public static boolean isDetectionEnabled(HackType h) {
		try {
			switch (h) {
			case MOVING_ANTI_LEVITATION:
				return ConfigsMessages.anti_levitation_enable;
			case MOVING_FASTLADDER_INSTANT:
				return ConfigsMessages.fastladder_instant_enable;
			case MOVING_FASTLADDER_NON_INSTANT:
				return ConfigsMessages.fastladder_non_instant_enable;
			case MOVING_FLY_DOUBLE_JUMP_DOWN:
				return ConfigsMessages.fly_double_jump_down_enable;
			case MOVING_FLY_DOUBLE_JUMP_UP:
				return ConfigsMessages.fly_double_jump_up_enable;
			case MOVING_FLY_INVALID_Y:
				return ConfigsMessages.fly_invalid_y_instant_enable;
			case MOVING_FLY_MODULO:
				return ConfigsMessages.fly_modulo_enable;
			case MOVING_FLY_SLOW_Y_INSTANT:
				return ConfigsMessages.fly_slow_y_instant_enable;
			case MOVING_FLY_SLOW_Y_NON_INSTANT:
				return ConfigsMessages.fly_slow_y_non_instant_enable;
			case MOVING_FLY_STABLE_Y:
				return ConfigsMessages.fly_stable_y_enable;
			case MOVING_GROUND_ELYTRA:
				return ConfigsMessages.ground_elytra_enable;
			case MOVING_NO_PITCH:
				return ConfigsMessages.no_pitch_enable;
			case MOVING_HIGHJUMP:
				return ConfigsMessages.highjump_enable;
			case MOVING_IMPOSSIBLE_JUMP:
				return ConfigsMessages.impossible_jump_enable;
			case MOVING_INVALIDFALL_FAST_START_DISTANCE:
				return ConfigsMessages.invalidfall_faststartdistance_enable;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_instant_enable;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_non_instant_enable;
			case MOVING_INVALIDFALL_SLOW_Y:
				return ConfigsMessages.invalidfall_slow_y_enable;
			case MOVING_INVALIDFALL_SLOWER_DISTANCE:
				return ConfigsMessages.invalidfall_slowerdistance_enable;
			case MOVING_INVALIDFALL_STABLE_DISTANCE:
				return ConfigsMessages.invalidfall_stabledistance_enable;
			case MOVING_JESUS:
				return ConfigsMessages.jesus_enable;
			case MOVING_NO_FALL:
				return ConfigsMessages.nofall_enable;
			case MOVING_SCAFFOLDWALK_ADVANCED:
				return ConfigsMessages.scaffoldwalk_advanced_enable;
			case MOVING_SCAFFOLDWALK_BASIC:
				return ConfigsMessages.scaffoldwalk_basic_enable;
			case MOVING_SCAFFOLDWALK_EXPAND:
				return ConfigsMessages.scaffoldwalk_expand_enable;
			case MOVING_SCAFFOLDWALK_TIMER:
				return ConfigsMessages.scaffoldwalk_timer_enable;
			case MOVING_SCAFFOLDWALK_GROUND:
				return ConfigsMessages.scaffoldwalk_ground_enable;
			case MOVING_SLIME_JUMP:
				return ConfigsMessages.slime_jump_enable;
			case MOVING_SNEAK:
				return ConfigsMessages.sneak_enable;
			case MOVING_SPEED_AIR:
				return ConfigsMessages.speed_air_enable;
			case MOVING_SPEED_COBWEB:
				return ConfigsMessages.speed_cobweb_enable;
			case MOVING_SPEED_ITEM:
				return ConfigsMessages.speed_item_enable;
			case MOVING_SPEED_LIQUID_LAVA:
				return ConfigsMessages.speed_liquid_lava_enable;
			case MOVING_SPEED_LIQUID_WATER:
				return ConfigsMessages.speed_liquid_water_enable;
			case MOVING_SPEED_ONGROUND:
				return ConfigsMessages.speed_onground_enable;
			case MOVING_SPEED_ONGROUND_ICE:
				return ConfigsMessages.speed_onground_ice_enable;
			case MOVING_SPEED_ONGROUND_SLIME:
				return ConfigsMessages.speed_onground_slime_enable;
			case MOVING_SPEED_ONGROUND_SOULSAND:
				return ConfigsMessages.speed_onground_soulsand_enable;
			case MOVING_SPEED_SPRINT_HUNGRY:
				return ConfigsMessages.speed_sprint_hungry_enable;
			case MOVING_SPEED_SOULSAND:
				return ConfigsMessages.speed_soulsand_enable;
			case MOVING_SPEED_ICE:
				return ConfigsMessages.speed_ice_enable;
			case MOVING_SPEED_SLIME:
				return ConfigsMessages.speed_slime_enable;
			case MOVING_SPEED_VEHICLE:
				return ConfigsMessages.speed_vehicle_enable;
			case MOVING_STEP:
				return ConfigsMessages.step_ultra_enable;
			case MOVING_TIMER:
				return ConfigsMessages.timer_enable;
			case MOVING_LOW_JUMP:
				return ConfigsMessages.low_jump_enable;
			case MOVING_BLINK:
				return ConfigsMessages.blink_enable;
			case MOVING_STRAFE:
				return ConfigsMessages.strafe_enable;
			case CHAT_CAPTCHA:
				return ConfigsMessages.chat_captcha_enable;
			case CHAT_BADWORDS:
				return ConfigsMessages.chat_badwords_enable;
			case COMBAT_AUTOSOUP:
				return ConfigsMessages.autosoup_enable;
			case COMBAT_CRITICALS:
				return ConfigsMessages.criticals_enable;
			case COMBAT_FASTBOW:
				return ConfigsMessages.fastbow_enable;
			case COMBAT_REACH:
				return ConfigsMessages.reach_combat_enable;
			case COMBAT_INVALID_INTERACTION_ENTITY:
				return ConfigsMessages.invaliditeraction_entity_enable;
			case EXPLOIT_PLUGIN_LIST:
				return ConfigsMessages.plugin_list_enable;
			case EXPLOIT_UUID_SPOOF:
				return ConfigsMessages.uuid_spoof_enable;
			case EXPLOIT_AUTORESPAWN:
				return ConfigsMessages.autorespawn_enable;
			case EXPLOIT_WORLD_DOWNLOADER:
				return ConfigsMessages.world_downloader_enable;
			case INVENTORY_FAST_EAT:
				return ConfigsMessages.fast_eat_enable;
			case INVENTORY_FAST_INVENTORY:
				return ConfigsMessages.fastinventory_enable;
			case INVENTORY_MOVE:
				return ConfigsMessages.inv_move_enable;
			case INVENTORY_FAST_CLICK:
				return ConfigsMessages.fast_click_enable;
			case INVENTORY_THROW:
				return ConfigsMessages.throw_enable;
			case RENDER_HEALTHTAG:
				return ConfigsMessages.healthtag_enable;
			case RENDER_FREECAM:
				return ConfigsMessages.freecam_enable;
			case WORLD_CREATIVE_DROP:
				return ConfigsMessages.creative_drop_enable;
			case WORLD_FAST_PLACE:
				return ConfigsMessages.fast_place_enable;
			case WORLD_LIQUID_INTERACTION:
				return ConfigsMessages.liquid_interaction_enable;
			case WORLD_NO_SWING:
				return ConfigsMessages.no_swing_enable;
			case WORLD_CREATIVE_NUKER:
				return ConfigsMessages.nuker_enable;
			case WORLD_FAST_BREAK:
				return ConfigsMessages.fast_break_enable;
			case WORLD_NO_BREAK_DELAY:
				return ConfigsMessages.no_break_delay_enable;
			case WORLD_WRONG_BLOCK_DIRECTION:
				return ConfigsMessages.wrongblock_direction_enable;
			case WORLD_INVALID_INTERACTION_BLOCK:
				return ConfigsMessages.invaliditeraction_block_enable;
			case WORLD_AUTO_SIGN:
				return ConfigsMessages.autosign_enable;

			default:
				return false;
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 8);
		}

		return false;
	}
	
	public static boolean isDetectionGhosted(HackType h) {
		try {
			switch(h) {
			case MOVING_ANTI_LEVITATION:
				return ConfigsMessages.anti_levitation_ghosted;
			case MOVING_FASTLADDER_INSTANT:
				return ConfigsMessages.fastladder_instant_ghosted;
			case MOVING_FASTLADDER_NON_INSTANT:
				return ConfigsMessages.fastladder_non_instant_ghosted;
			case MOVING_FLY_DOUBLE_JUMP_DOWN:
				return ConfigsMessages.fly_double_jump_down_ghosted;
			case MOVING_FLY_DOUBLE_JUMP_UP:
				return ConfigsMessages.fly_double_jump_up_ghosted;
			case MOVING_FLY_INVALID_Y:
				return ConfigsMessages.fly_invalid_y_instant_ghosted;
			case MOVING_FLY_MODULO:
				return ConfigsMessages.fly_modulo_ghosted;
			case MOVING_FLY_SLOW_Y_INSTANT:
				return ConfigsMessages.fly_slow_y_instant_ghosted;
			case MOVING_FLY_SLOW_Y_NON_INSTANT:
				return ConfigsMessages.fly_slow_y_non_instant_ghosted;
			case MOVING_FLY_STABLE_Y:
				return ConfigsMessages.fly_stable_y_ghosted;
			case MOVING_GROUND_ELYTRA:
				return ConfigsMessages.ground_elytra_ghosted;
			case MOVING_NO_PITCH:
				return ConfigsMessages.no_pitch_ghosted;
			case MOVING_HIGHJUMP:
				return ConfigsMessages.highjump_ghosted;
			case MOVING_IMPOSSIBLE_JUMP:
				return ConfigsMessages.impossible_jump_ghosted;
			case MOVING_INVALIDFALL_FAST_START_DISTANCE:
				return ConfigsMessages.invalidfall_faststartdistance_ghosted;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_instant_ghosted;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
				return ConfigsMessages.invalidfall_fasterdistance_non_instant_ghosted;
			case MOVING_INVALIDFALL_SLOW_Y:
				return ConfigsMessages.invalidfall_slow_y_ghosted;
			case MOVING_INVALIDFALL_SLOWER_DISTANCE:
				return ConfigsMessages.invalidfall_slowerdistance_ghosted;
			case MOVING_INVALIDFALL_STABLE_DISTANCE:
				return ConfigsMessages.invalidfall_stabledistance_ghosted;
			case MOVING_JESUS:
				return ConfigsMessages.jesus_ghosted;
			case MOVING_NO_FALL:
				return ConfigsMessages.nofall_ghosted;
			case MOVING_SCAFFOLDWALK_ADVANCED:
				return ConfigsMessages.scaffoldwalk_advanced_ghosted;
			case MOVING_SCAFFOLDWALK_BASIC:
				return ConfigsMessages.scaffoldwalk_basic_ghosted;
			case MOVING_SCAFFOLDWALK_EXPAND:
				return ConfigsMessages.scaffoldwalk_expand_ghosted;
			case MOVING_SCAFFOLDWALK_TIMER:
				return ConfigsMessages.scaffoldwalk_timer_ghosted;
			case MOVING_SCAFFOLDWALK_GROUND:
				return ConfigsMessages.scaffoldwalk_ground_ghosted;
			case MOVING_SLIME_JUMP:
				return ConfigsMessages.slime_jump_ghosted;
			case MOVING_SNEAK:
				return ConfigsMessages.sneak_ghosted;
			case MOVING_SPEED_AIR:
				return ConfigsMessages.speed_air_ghosted;
			case MOVING_SPEED_COBWEB:
				return ConfigsMessages.speed_cobweb_ghosted;
			case MOVING_SPEED_ITEM:
				return ConfigsMessages.speed_item_ghosted;
			case MOVING_SPEED_LIQUID_LAVA:
				return ConfigsMessages.speed_liquid_lava_ghosted;
			case MOVING_SPEED_LIQUID_WATER:
				return ConfigsMessages.speed_liquid_water_ghosted;
			case MOVING_SPEED_ONGROUND:
				return ConfigsMessages.speed_onground_ghosted;
			case MOVING_SPEED_ONGROUND_ICE:
				return ConfigsMessages.speed_onground_ice_ghosted;
			case MOVING_SPEED_ONGROUND_SLIME:
				return ConfigsMessages.speed_onground_slime_ghosted;
			case MOVING_SPEED_ONGROUND_SOULSAND:
				return ConfigsMessages.speed_onground_soulsand_ghosted;
			case MOVING_SPEED_SPRINT_HUNGRY:
				return ConfigsMessages.speed_sprint_hungry_ghosted;
			case MOVING_SPEED_SOULSAND:
				return ConfigsMessages.speed_soulsand_ghosted;
			case MOVING_SPEED_ICE:
				return ConfigsMessages.speed_ice_ghosted;
			case MOVING_SPEED_SLIME:
				return ConfigsMessages.speed_slime_ghosted;
			case MOVING_SPEED_VEHICLE:
				return ConfigsMessages.speed_vehicle_ghosted;
			case MOVING_STEP:
				return ConfigsMessages.step_ultra_ghosted;
			case MOVING_TIMER:
				return ConfigsMessages.timer_ghosted;
			case MOVING_LOW_JUMP:
				return ConfigsMessages.low_jump_ghosted;
			case MOVING_BLINK:
				return ConfigsMessages.blink_ghosted;
			case MOVING_STRAFE:
				return ConfigsMessages.strafe_ghosted;
			case COMBAT_AUTOSOUP:
				return ConfigsMessages.autosoup_ghosted;
			case COMBAT_CRITICALS:
				return ConfigsMessages.criticals_ghosted;
			case COMBAT_FASTBOW:
				return ConfigsMessages.fastbow_ghosted;
			case COMBAT_REACH:
				return ConfigsMessages.reach_combat_ghosted;
			case COMBAT_INVALID_INTERACTION_ENTITY:
				return ConfigsMessages.invaliditeraction_entity_ghosted;
			case INVENTORY_FAST_EAT:
				return ConfigsMessages.fast_eat_ghosted;
			case INVENTORY_FAST_INVENTORY:
				return ConfigsMessages.fastinventory_ghosted;
			case INVENTORY_MOVE:
				return ConfigsMessages.inv_move_ghosted;
			case INVENTORY_FAST_CLICK:
				return ConfigsMessages.fast_click_ghosted;
			case INVENTORY_THROW:
				return ConfigsMessages.throw_ghosted;
			case WORLD_CREATIVE_DROP:
				return ConfigsMessages.creative_drop_ghosted;
			case WORLD_FAST_PLACE:
				return ConfigsMessages.fast_place_ghosted;
			case WORLD_LIQUID_INTERACTION:
				return ConfigsMessages.liquid_interaction_ghosted;
			case WORLD_NO_SWING:
				return ConfigsMessages.no_swing_ghosted;
			case WORLD_CREATIVE_NUKER:
				return ConfigsMessages.nuker_ghosted;
			case WORLD_FAST_BREAK:
				return ConfigsMessages.fast_break_ghosted;
			case WORLD_NO_BREAK_DELAY:
				return ConfigsMessages.no_break_delay_ghosted;
			case WORLD_WRONG_BLOCK_DIRECTION:
				return ConfigsMessages.wrongblock_direction_ghosted;
			case WORLD_INVALID_INTERACTION_BLOCK:
				return ConfigsMessages.invaliditeraction_block_ghosted;
			case WORLD_AUTO_SIGN:
				return ConfigsMessages.autosign_ghosted;

			default:
				return false;
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 9);
		}
		
		return false;
	}

	public static void printError(String error, int errorCode) {
		String message = "Message: " + error + ", errorCode: " + errorCode;

		System.err.println(message);

		for (CheatPlayer p1 : getCheatPlayers()) {
			if (p1.getDebugMode())
				p1.logMessage(LogMessage.ERROR, message);
		}
	}

	public static boolean isDetectionEnabled(HackType h, CheatPlayer p) {
		//Sometimes error is thrown, that the player is null
		if (p == null || p.getPlayer() == null || !p.getPlayer().isOnline())
			return false;

		try {
			return !p.getDisabledChecks().contains(h);
		} catch (Exception ex) {
			printError(ex.getMessage(), 10);
		}

		return false;
	}

	public static void setDetectionEnabled(HackType h, CheatPlayer p, boolean enabled) {
		try {
			if (enabled)
				if(!p.getDisabledChecks().contains(h))
					p.getDisabledChecks().add(h);
			else
				p.getDisabledChecks().remove(h);
		} catch (Exception ex) {
			printError(ex.getMessage(), 10);
		}
	}
	
	// ReWrite
	public static void setDetectionEnabled(HackType h, boolean enabled) {
		try {
			if (HackType.MOVING_FLY_INVALID_Y.name().equals(h.name())) {
				ConfigsMessages.fly_invalid_y_instant_enable = enabled;
			} else if (HackType.MOVING_FLY_STABLE_Y.name().equals(h.name())) {
				ConfigsMessages.fly_stable_y_enable = enabled;
			} else if (HackType.MOVING_FLY_DOUBLE_JUMP_UP.name().equals(h.name())) {
				ConfigsMessages.fly_double_jump_up_enable = enabled;
			} else if (HackType.MOVING_FLY_DOUBLE_JUMP_DOWN.name().equals(h.name())) {
				ConfigsMessages.fly_double_jump_down_enable = enabled;
			} else if (HackType.MOVING_FLY_MODULO.name().equals(h.name())) {
				ConfigsMessages.fly_modulo_enable = enabled;
			} else if (HackType.MOVING_FLY_SLOW_Y_INSTANT.name().equals(h.name())) {
				ConfigsMessages.fly_slow_y_instant_enable = enabled;
			} else if (HackType.MOVING_FLY_SLOW_Y_NON_INSTANT.name().equals(h.name())) {
				ConfigsMessages.fly_slow_y_non_instant_enable = enabled;
			} else if (HackType.MOVING_HIGHJUMP.name().equals(h.name())) {
				ConfigsMessages.highjump_enable = enabled;
			} else if (HackType.MOVING_FASTLADDER_INSTANT.name().equals(h.name())) {
				ConfigsMessages.fastladder_instant_enable = enabled;
			} else if (HackType.MOVING_FASTLADDER_NON_INSTANT.name().equals(h.name())) {
				ConfigsMessages.fastladder_non_instant_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_STABLE_DISTANCE.name().equals(h.name())) {
				ConfigsMessages.invalidfall_stabledistance_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT.name().equals(h.name())) {
				ConfigsMessages.invalidfall_fasterdistance_instant_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT.name().equals(h.name())) {
				ConfigsMessages.invalidfall_fasterdistance_non_instant_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE.name().equals(h.name())) {
				ConfigsMessages.invalidfall_slowerdistance_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_FAST_START_DISTANCE.name().equals(h.name())) {
				ConfigsMessages.invalidfall_faststartdistance_enable = enabled;
			} else if (HackType.MOVING_INVALIDFALL_SLOW_Y.name().equals(h.name())) {
				ConfigsMessages.invalidfall_slow_y_enable = enabled;
			} else if (HackType.MOVING_SPEED_ONGROUND.name().equals(h.name())) {
				ConfigsMessages.speed_onground_enable = enabled;
			} else if (HackType.MOVING_SPEED_ONGROUND_SLIME.name().equals(h.name())) {
				ConfigsMessages.speed_onground_slime_enable = enabled;
			} else if (HackType.MOVING_SPEED_ONGROUND_ICE.name().equals(h.name())) {
				ConfigsMessages.speed_onground_ice_enable = enabled;
			} else if (HackType.MOVING_SPEED_ONGROUND_SOULSAND.name().equals(h.name())) {
				ConfigsMessages.speed_onground_soulsand_enable = enabled;
			} else if (HackType.MOVING_SPEED_AIR.name().equals(h.name())) {
				ConfigsMessages.speed_air_enable = enabled;
			} else if (HackType.MOVING_SPEED_COBWEB.name().equals(h.name())) {
				ConfigsMessages.speed_cobweb_enable = enabled;
			} else if (HackType.MOVING_SPEED_ITEM.name().equals(h.name())) {
				ConfigsMessages.speed_item_enable = enabled;
			} else if (HackType.MOVING_SPEED_LIQUID_WATER.name().equals(h.name())) {
				ConfigsMessages.speed_liquid_water_enable = enabled;
			} else if (HackType.MOVING_SPEED_LIQUID_LAVA.name().equals(h.name())) {
				ConfigsMessages.speed_liquid_lava_enable = enabled;
			} else if (HackType.MOVING_SPEED_SPRINT_HUNGRY.name().equals(h.name())) {
				ConfigsMessages.speed_sprint_hungry_enable = enabled;
			} else if (HackType.MOVING_SPEED_SOULSAND.name().equals(h.name())) {
				ConfigsMessages.speed_soulsand_enable = enabled;
			} else if (HackType.MOVING_SPEED_ICE.name().equals(h.name())) {
				ConfigsMessages.speed_ice_enable = enabled;
			} else if (HackType.MOVING_SPEED_SLIME.name().equals(h.name())) {
				ConfigsMessages.speed_slime_enable = enabled;
			} else if (HackType.MOVING_SPEED_VEHICLE.name().equals(h.name())) {
				ConfigsMessages.speed_vehicle_enable = enabled;
			} else if (HackType.MOVING_SLIME_JUMP.name().equals(h.name())) {
				ConfigsMessages.slime_jump_enable = enabled;
			} else if (HackType.MOVING_ANTI_LEVITATION.name().equals(h.name())) {
				ConfigsMessages.anti_levitation_enable = enabled;
			} else if (HackType.MOVING_NO_FALL.name().equals(h.name())) {
				ConfigsMessages.nofall_enable = enabled;
			} else if (HackType.MOVING_IMPOSSIBLE_JUMP.name().equals(h.name())) {
				ConfigsMessages.impossible_jump_enable = enabled;
			} else if (HackType.MOVING_STEP.name().equals(h.name())) {
				ConfigsMessages.step_ultra_enable = enabled;
			} else if (HackType.MOVING_SCAFFOLDWALK_BASIC.name().equals(h.name())) {
				ConfigsMessages.scaffoldwalk_basic_enable = enabled;
			} else if (HackType.MOVING_SCAFFOLDWALK_ADVANCED.name().equals(h.name())) {
				ConfigsMessages.scaffoldwalk_advanced_enable = enabled;
			} else if (HackType.MOVING_SCAFFOLDWALK_GROUND.name().equals(h.name())) {
				ConfigsMessages.scaffoldwalk_ground_enable = enabled;
			} else if (HackType.MOVING_SCAFFOLDWALK_EXPAND.name().equals(h.name())) {
				ConfigsMessages.scaffoldwalk_expand_enable = enabled;
			} else if (HackType.MOVING_SCAFFOLDWALK_TIMER.name().equals(h.name())) {
				ConfigsMessages.scaffoldwalk_timer_enable = enabled;
			} else if (HackType.MOVING_SNEAK.name().equals(h.name())) {
				ConfigsMessages.sneak_enable = enabled;
			} else if (HackType.MOVING_JESUS.name().equals(h.name())) {
				ConfigsMessages.jesus_enable = enabled;
			} else if (HackType.MOVING_NO_PITCH.name().equals(h.name())) {
				ConfigsMessages.no_pitch_enable = enabled;
			} else if (HackType.MOVING_GROUND_ELYTRA.name().equals(h.name())) {
				ConfigsMessages.ground_elytra_enable = enabled;
			} else if (HackType.MOVING_TIMER.name().equals(h.name())) {
				ConfigsMessages.timer_enable = enabled;
			} else if (HackType.MOVING_LOW_JUMP.name().equals(h.name())) {
				ConfigsMessages.low_jump_enable = enabled;
			} else if (HackType.MOVING_BLINK.name().equals(h.name())) {
				ConfigsMessages.blink_enable = enabled;
			} else if (HackType.MOVING_STRAFE.name().equals(h.name())) {
				ConfigsMessages.strafe_enable = enabled;
			} else if (HackType.INVENTORY_FAST_INVENTORY.name().equals(h.name())) {
				ConfigsMessages.fastinventory_enable = enabled;
			} else if (HackType.INVENTORY_MOVE.name().equals(h.name())) {
				ConfigsMessages.inv_move_enable = enabled;
			} else if (HackType.INVENTORY_FAST_EAT.name().equals(h.name())) {
				ConfigsMessages.fast_eat_enable = enabled;
			} else if (HackType.INVENTORY_FAST_CLICK.name().equals(h.name())) {
				ConfigsMessages.fast_click_enable = enabled;
			} else if (HackType.INVENTORY_THROW.name().equals(h.name())) {
				ConfigsMessages.throw_enable = enabled;
			} else if (HackType.WORLD_WRONG_BLOCK_DIRECTION.name().equals(h.name())) {
				ConfigsMessages.wrongblock_direction_enable = enabled;
			} else if (HackType.WORLD_LIQUID_INTERACTION.name().equals(h.name())) {
				ConfigsMessages.liquid_interaction_enable = enabled;
			} else if (HackType.WORLD_CREATIVE_DROP.name().equals(h.name())) {
				ConfigsMessages.creative_drop_enable = enabled;
			} else if (HackType.WORLD_NO_SWING.name().equals(h.name())) {
				ConfigsMessages.no_swing_enable = enabled;
			} else if (HackType.WORLD_FAST_PLACE.name().equals(h.name())) {
				ConfigsMessages.fast_place_enable = enabled;
			} else if (HackType.WORLD_CREATIVE_NUKER.name().equals(h.name())) {
				ConfigsMessages.nuker_enable = enabled;
			} else if (HackType.WORLD_FAST_BREAK.name().equals(h.name())) {
				ConfigsMessages.fast_break_enable = enabled;
			} else if (HackType.WORLD_NO_BREAK_DELAY.name().equals(h.name())) {
				ConfigsMessages.no_break_delay_enable = enabled;
			} else if (HackType.WORLD_INVALID_INTERACTION_BLOCK.name().equals(h.name())) {
				ConfigsMessages.invaliditeraction_block_enable = enabled;
			} else if (HackType.WORLD_AUTO_SIGN.name().equals(h.name())) {
				ConfigsMessages.autosign_enable = enabled;
			} else if (HackType.CHAT_CAPTCHA.name().equals(h.name())) {
				ConfigsMessages.chat_captcha_enable = enabled;
			} else if (HackType.CHAT_BADWORDS.name().equals(h.name())) {
				ConfigsMessages.chat_badwords_enable = enabled;
			} else if (HackType.COMBAT_CRITICALS.name().equals(h.name())) {
				ConfigsMessages.criticals_enable = enabled;
			} else if (HackType.COMBAT_REACH.name().equals(h.name())) {
				ConfigsMessages.reach_combat_enable = enabled;
			} else if (HackType.COMBAT_AUTOSOUP.name().equals(h.name())) {
				ConfigsMessages.autosoup_enable = enabled;
			} else if (HackType.COMBAT_FASTBOW.name().equals(h.name())) {
				ConfigsMessages.fastbow_enable = enabled;
			} else if (HackType.COMBAT_INVALID_INTERACTION_ENTITY.name().equals(h.name())) {
				ConfigsMessages.invaliditeraction_entity_enable = enabled;
			} else if (HackType.RENDER_HEALTHTAG.name().equals(h.name())) {
				ConfigsMessages.healthtag_enable = enabled;
			} else if (HackType.RENDER_FREECAM.name().equals(h.name())) {
				ConfigsMessages.freecam_enable = enabled;
			} else if (HackType.EXPLOIT_PLUGIN_LIST.name().equals(h.name())) {
				ConfigsMessages.plugin_list_enable = enabled;
			} else if (HackType.EXPLOIT_UUID_SPOOF.name().equals(h.name())) {
				ConfigsMessages.uuid_spoof_enable = enabled;
			} else if (HackType.EXPLOIT_WORLD_DOWNLOADER.name().equals(h.name())) {
				ConfigsMessages.world_downloader_enable = enabled;
			} else if (HackType.EXPLOIT_AUTORESPAWN.name().equals(h.name())) {
				ConfigsMessages.autorespawn_enable = enabled;
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 11);
		}
	}
	
	public static void setDetectionGhosted(HackType h, boolean ghosted) {
		try {
			switch(h) {
			case MOVING_ANTI_LEVITATION:
				ConfigsMessages.anti_levitation_ghosted = ghosted; break;
			case MOVING_FASTLADDER_INSTANT:
				ConfigsMessages.fastladder_instant_ghosted = ghosted; break;
			case MOVING_FASTLADDER_NON_INSTANT:
				ConfigsMessages.fastladder_non_instant_ghosted = ghosted; break;
			case MOVING_FLY_DOUBLE_JUMP_DOWN:
				ConfigsMessages.fly_double_jump_down_ghosted = ghosted; break;
			case MOVING_FLY_DOUBLE_JUMP_UP:
				ConfigsMessages.fly_double_jump_up_ghosted = ghosted; break;
			case MOVING_FLY_INVALID_Y:
				ConfigsMessages.fly_invalid_y_instant_ghosted = ghosted; break;
			case MOVING_FLY_MODULO:
				ConfigsMessages.fly_modulo_ghosted = ghosted; break;
			case MOVING_FLY_SLOW_Y_INSTANT:
				ConfigsMessages.fly_slow_y_instant_ghosted = ghosted; break;
			case MOVING_FLY_SLOW_Y_NON_INSTANT:
				ConfigsMessages.fly_slow_y_non_instant_ghosted = ghosted; break;
			case MOVING_FLY_STABLE_Y:
				ConfigsMessages.fly_stable_y_ghosted = ghosted; break;
			case MOVING_GROUND_ELYTRA:
				ConfigsMessages.ground_elytra_ghosted = ghosted; break;
			case MOVING_NO_PITCH:
				ConfigsMessages.no_pitch_ghosted = ghosted; break;
			case MOVING_HIGHJUMP:
				ConfigsMessages.highjump_ghosted = ghosted; break;
			case MOVING_IMPOSSIBLE_JUMP:
				ConfigsMessages.impossible_jump_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_FAST_START_DISTANCE:
				ConfigsMessages.invalidfall_faststartdistance_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
				ConfigsMessages.invalidfall_fasterdistance_instant_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
				ConfigsMessages.invalidfall_fasterdistance_non_instant_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_SLOW_Y:
				ConfigsMessages.invalidfall_slow_y_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_SLOWER_DISTANCE:
				ConfigsMessages.invalidfall_slowerdistance_ghosted = ghosted; break;
			case MOVING_INVALIDFALL_STABLE_DISTANCE:
				ConfigsMessages.invalidfall_stabledistance_ghosted = ghosted; break;
			case MOVING_JESUS:
				ConfigsMessages.jesus_ghosted = ghosted; break;
			case MOVING_NO_FALL:
				ConfigsMessages.nofall_ghosted = ghosted; break;
			case MOVING_SCAFFOLDWALK_ADVANCED:
				ConfigsMessages.scaffoldwalk_advanced_ghosted = ghosted; break;
			case MOVING_SCAFFOLDWALK_BASIC:
				ConfigsMessages.scaffoldwalk_basic_ghosted = ghosted; break;
			case MOVING_SCAFFOLDWALK_EXPAND:
				ConfigsMessages.scaffoldwalk_expand_ghosted = ghosted; break;
			case MOVING_SCAFFOLDWALK_TIMER:
				ConfigsMessages.scaffoldwalk_timer_ghosted = ghosted; break;
			case MOVING_SCAFFOLDWALK_GROUND:
				ConfigsMessages.scaffoldwalk_ground_ghosted = ghosted; break;
			case MOVING_SLIME_JUMP:
				ConfigsMessages.slime_jump_ghosted = ghosted; break;
			case MOVING_SNEAK:
				ConfigsMessages.sneak_ghosted = ghosted; break;
			case MOVING_SPEED_AIR:
				ConfigsMessages.speed_air_ghosted = ghosted; break;
			case MOVING_SPEED_COBWEB:
				ConfigsMessages.speed_cobweb_ghosted = ghosted; break;
			case MOVING_SPEED_ITEM:
				ConfigsMessages.speed_item_ghosted = ghosted; break;
			case MOVING_SPEED_LIQUID_LAVA:
				ConfigsMessages.speed_liquid_lava_ghosted = ghosted; break;
			case MOVING_SPEED_LIQUID_WATER:
				ConfigsMessages.speed_liquid_water_ghosted = ghosted; break;
			case MOVING_SPEED_ONGROUND:
				ConfigsMessages.speed_onground_ghosted = ghosted; break;
			case MOVING_SPEED_ONGROUND_ICE:
				ConfigsMessages.speed_onground_ice_ghosted = ghosted; break;
			case MOVING_SPEED_ONGROUND_SLIME:
				ConfigsMessages.speed_onground_slime_ghosted = ghosted; break;
			case MOVING_SPEED_ONGROUND_SOULSAND:
				ConfigsMessages.speed_onground_soulsand_ghosted = ghosted; break;
			case MOVING_SPEED_SPRINT_HUNGRY:
				ConfigsMessages.speed_sprint_hungry_ghosted = ghosted; break;
			case MOVING_SPEED_SOULSAND:
				ConfigsMessages.speed_soulsand_ghosted = ghosted; break;
			case MOVING_SPEED_ICE:
				ConfigsMessages.speed_ice_ghosted = ghosted; break;
			case MOVING_SPEED_SLIME:
				ConfigsMessages.speed_slime_ghosted = ghosted; break;
			case MOVING_SPEED_VEHICLE:
				ConfigsMessages.speed_vehicle_ghosted = ghosted; break;
			case MOVING_STEP:
				ConfigsMessages.step_ultra_ghosted = ghosted; break;
			case MOVING_TIMER:
				ConfigsMessages.timer_ghosted = ghosted; break;
			case MOVING_LOW_JUMP:
				ConfigsMessages.low_jump_ghosted = ghosted; break;
			case MOVING_BLINK:
				ConfigsMessages.blink_ghosted = ghosted; break;
			case MOVING_STRAFE:
				ConfigsMessages.strafe_ghosted = ghosted; break;
			case COMBAT_AUTOSOUP:
				ConfigsMessages.autosoup_ghosted = ghosted; break;
			case COMBAT_CRITICALS:
				ConfigsMessages.criticals_ghosted = ghosted; break;
			case COMBAT_FASTBOW:
				ConfigsMessages.fastbow_ghosted = ghosted; break;
			case COMBAT_REACH:
				ConfigsMessages.reach_combat_ghosted = ghosted; break;
			case COMBAT_INVALID_INTERACTION_ENTITY:
				ConfigsMessages.invaliditeraction_entity_ghosted = ghosted; break;
			case INVENTORY_FAST_EAT:
				ConfigsMessages.fast_eat_ghosted = ghosted; break;
			case INVENTORY_FAST_INVENTORY:
				ConfigsMessages.fastinventory_ghosted = ghosted; break;
			case INVENTORY_MOVE:
				ConfigsMessages.inv_move_ghosted = ghosted; break;
			case INVENTORY_FAST_CLICK:
				ConfigsMessages.fast_click_ghosted = ghosted; break;
			case INVENTORY_THROW:
				ConfigsMessages.throw_ghosted = ghosted; break;
			case WORLD_CREATIVE_DROP:
				ConfigsMessages.creative_drop_ghosted = ghosted; break;
			case WORLD_FAST_PLACE:
				ConfigsMessages.fast_place_ghosted = ghosted; break;
			case WORLD_LIQUID_INTERACTION:
				ConfigsMessages.liquid_interaction_ghosted = ghosted; break;
			case WORLD_NO_SWING:
				ConfigsMessages.no_swing_ghosted = ghosted; break;
			case WORLD_CREATIVE_NUKER:
				ConfigsMessages.nuker_ghosted = ghosted; break;
			case WORLD_FAST_BREAK:
				ConfigsMessages.fast_break_ghosted = ghosted; break;
			case WORLD_NO_BREAK_DELAY:
				ConfigsMessages.no_break_delay_ghosted = ghosted; break;
			case WORLD_WRONG_BLOCK_DIRECTION:
				ConfigsMessages.wrongblock_direction_ghosted = ghosted; break;
			case WORLD_INVALID_INTERACTION_BLOCK:
				ConfigsMessages.invaliditeraction_block_ghosted = ghosted; break;
			case WORLD_AUTO_SIGN:
				ConfigsMessages.autosign_ghosted = ghosted; break;
			
			default:
				break;
			}
		} catch (Exception ex) {
			printError(ex.getMessage(), 12);
		}
	}

	// Hooks

	public static com.comphenix.protocol.ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public static void hookTo(PluginHookType pluginType) {
		switch (pluginType) {
		case PROTOCOLLIB:
			protocolManager = com.comphenix.protocol.ProtocolLibrary.getProtocolManager();
			ProtocolLibManager.enb();
			break;
		case PLACEHOLDERAPI:
			placeholder = true;
		case CITIZENS:
			citizens = true;
		case MCMMO:
			mcmmo = true;
		case REALDUALWIELD:
			realdualwield = true;
		case VEINMINE:
			veinmine = true;
		default:
			break;
		}
	}

	public static void unhookTo(PluginHookType pluginType) {
		switch (pluginType) {
		case PLACEHOLDERAPI:
			placeholder = false;
		case CITIZENS:
			citizens = false;
		case REALDUALWIELD:
			realdualwield = false;
		case VEINMINE:
			veinmine = false;
		default:
			break;
		}
	}

	public static boolean isHookedTo(PluginHookType pluginType) {
		switch (pluginType) {
		case PROTOCOLLIB:
			return protocolManager == null;
		case PLACEHOLDERAPI:
			return placeholder;
		case CITIZENS:
			return citizens;
		case MCMMO:
			return mcmmo;
		case REALDUALWIELD:
			return realdualwield;
		case VEINMINE:
			return veinmine;
		default:
			return false;
		}
	}

	// PlayerUtils
	// e instanceof EntityDamageEvent && !(e instanceof EntityDamageByEntityEvent) -
	// INFO
	// XD EntityDamageByEntityEvent is instance of EntityDamageEvent, so this made
	// problem before
	public static void setValues(final CheatPlayer p, Event e) {
		try {
			if (e instanceof PlayerTeleportEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerTeleportEvent) e).isCancelled())
					return;
				// p.setServerFallDistance(0.0F);
				NoFall.damage.remove(((PlayerTeleportEvent) e).getPlayer().getName());
				// For LiquidBounce AAC NoFall bypass
//				if (p.getNFWT() == 0)
//					if (!NoFall.a.contains(((PlayerTeleportEvent) e).getPlayer().getName()))
//						NoFall.a.add(p.getPlayer().getName());

				if (!((PlayerTeleportEvent) e).getCause().equals(TeleportCause.PLUGIN))
					p.setLastTeleportTime(System.currentTimeMillis());
				p.setLastAllTeleportTime(System.currentTimeMillis());
				p.setLastAllTeleportType(((PlayerTeleportEvent) e).getCause());
				p.setLastGroundLocation(((PlayerTeleportEvent) e).getTo());
				for (HackType h : HackType.values()) {
					p.removeViolation(h, ViolationType.CANCELMOVE, Short.MAX_VALUE);
				}
				
				// Timer false positive
				Timer.resetTimer(p);
			} else if (e instanceof PlayerToggleSneakEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerToggleSneakEvent) e).isSneaking()) {
					// Speed
					if (!p.getPlayer().getLocation().getBlock().isLiquid())
						p.setLegitSpeedVL((short) 4, "liquid");

					if (isDetectionEnabled(HackType.MOVING_SNEAK)) {
						if ((System.currentTimeMillis()
								- getCheatPlayer(((PlayerToggleSneakEvent) e).getPlayer().getUniqueId())
										.getLastSneakToggleTime()) < 90)
							p.addSneakCount((short) 1);
						getCheatPlayer(((PlayerToggleSneakEvent) e).getPlayer().getUniqueId())
								.setLastSneakToggleTime(System.currentTimeMillis());
					}
				}
			} else if (e instanceof PlayerChangedWorldEvent) {
				if(!checkPlayer(p))
					return;
				p.setOpenPlayerInventory(false);
				p.setLastGroundLocation(((PlayerChangedWorldEvent) e).getPlayer().getLocation());
				Speed.tpBack.put(((PlayerChangedWorldEvent) e).getPlayer().getName(), new Object[] {
						((PlayerChangedWorldEvent) e).getPlayer().getLocation(), System.currentTimeMillis() });
			} else if (e instanceof AsyncPlayerChatEvent) {
				if(!checkPlayer(p))
					return;
				if (((AsyncPlayerChatEvent) e).isCancelled())
					return;
				p.setLastChatTime(System.currentTimeMillis());
			} else if (e instanceof PlayerCommandPreprocessEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerCommandPreprocessEvent) e).isCancelled())
					return;
				p.setLastCommandTime(System.currentTimeMillis());
				// Strange problem
				if (((PlayerCommandPreprocessEvent) e).getMessage().equals("/fly")
						&& p.getPlayer().hasPermission("essentials.fly"))
					p.setLastFlyTime(System.currentTimeMillis());
			} else if (e instanceof PlayerToggleFlightEvent) {
				/*
				 * if (((PlayerToggleFlightEvent) e).isCancelled()) return;
				 */

				// In order to check doublejump you should not check if the event is cancelled
				p.setLastFlyTime(System.currentTimeMillis());
				p.setIFB(true);
				
				NoFall.damage.remove(((PlayerToggleFlightEvent) e).getPlayer().getName());

				/*
				 * if(p.getLastMoveDirectionType() == MoveDirectionType.VERTICAL)
				 * NoFall.a.add(p.getPlayer().getName());
				 */
			} else if (e instanceof PlayerDeathEvent) {
				if(!checkPlayer(p))
					return;
				p.setLastDeathTime(System.currentTimeMillis());

//				if (!NoFall.a.contains(((PlayerDeathEvent) e).getEntity().getName()))
//					NoFall.a.add(((PlayerDeathEvent) e).getEntity().getName());
//				NoFall.damage.remove(((PlayerDeathEvent) e).getEntity().getName());
				p.setNFD(false);
				p.setFDD(0.0F);
				
				// Timer false positive
				Timer.resetTimer(p);
			} else if (e instanceof PlayerRespawnEvent) {
				p.setLastRespawnTime(System.currentTimeMillis());
			} else if (e instanceof EntityDamageEvent && !(e instanceof EntityDamageByEntityEvent)) {
				if(!checkPlayer(p))
					return;
				if (!((EntityDamageEvent) e).isCancelled() && ((EntityDamageEvent) e).getDamage() > 0.0D)
					p.setLastDamageTime(System.currentTimeMillis());

				// Bypass GitHub -> coolpvp - WARN: Don't add the player in exception if the event is canceled, nofall will no work due to /god event call in the check
				if (!NoFall.damage.contains(((EntityDamageEvent) e).getEntity().getName()))
					NoFall.damage.add(((EntityDamageEvent) e).getEntity().getName());
			} else if (e instanceof PlayerVelocityEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerVelocityEvent) e).isCancelled())
					return;
				p.setLastVelocityTime(System.currentTimeMillis());
				
				// TODO: If velocity too much reset Timer
			} else if (e instanceof VehicleEnterEvent) {
				if(!checkPlayer(p))
					return;
				if (((VehicleEnterEvent) e).isCancelled())
					return;
				p.setLastVehicleEnterTime(System.currentTimeMillis());
			} else if (e instanceof VehicleExitEvent) {
				if(!checkPlayer(p))
					return;
				if (((VehicleExitEvent) e).isCancelled())
					return;
				p.setLastVehicleExitTime(System.currentTimeMillis());
			} else if (e instanceof HorseJumpEvent) {
				if(!checkPlayer(p))
					return;
				if (((HorseJumpEvent) e).isCancelled())
					return;
				p.setLastHorseJumpTime(System.currentTimeMillis());
			} else if (e instanceof PlayerAnimationEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerAnimationEvent) e).isCancelled())
					return;
				p.setNSAnimation(true);
			} else if (getServerVersion() <= 7 && e instanceof PlayerAchievementAwardedEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerAchievementAwardedEvent) e).isCancelled())
					return;
				p.setLastAchievementAwardTime(System.currentTimeMillis());

				p.setLastAchievementAwardType(((PlayerAchievementAwardedEvent) e).getAchievement());

				if (((PlayerAchievementAwardedEvent) e).getAchievement().equals(Achievement.OPEN_INVENTORY)) {
					p.setOpenPlayerInventory(true);
					p.setOpenPlayerInventoryTime(System.currentTimeMillis());
					if (!p.getOpenPlayerInventoryFT()) {
						p.setOpenPlayerInventoryFT(true);
					} else
						((PlayerAchievementAwardedEvent) e).setCancelled(true);
					if (p.getLastFromToLocations()[0].distance(p.getLastFromToLocations()[1]) > 0.15) {
						p.setLegitInvMoveVL((short) 8);
					}
				}
			} else if (e instanceof PlayerPickupItemEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerPickupItemEvent) e).isCancelled())
					return;
				p.setLastPickupItemTime(System.currentTimeMillis());

				p.setLastPickupItem(((PlayerPickupItemEvent) e).getItem().getItemStack());
			} else if (e instanceof PlayerItemBreakEvent) {
				if(!checkPlayer(p))
					return;
				p.setLastBrokeItemTime(System.currentTimeMillis());
			} else if (e instanceof InventoryCloseEvent) {
				if(!checkPlayer(p))
					return;
				if (((InventoryCloseEvent) e).getInventory().getType().equals(InventoryType.CRAFTING)) {
					if (getServerVersion() <= 4)
						if (p.hasAchievement(Achievement.OPEN_INVENTORY))
							p.removeAchievement(Achievement.OPEN_INVENTORY);
					p.setOpenPlayerInventory(false);

					if (getCheatPlayer(((InventoryCloseEvent) e).getPlayer().getUniqueId()).getFreezed()
							&& getCheatPlayer(((InventoryCloseEvent) e).getPlayer().getUniqueId()).getFreezedReason()
									.equals(HackType.INVENTORY_MOVE)) {
						unfreezePlayer((Player) ((InventoryCloseEvent) e).getPlayer());
					}
				}
				getCheatPlayer(((InventoryCloseEvent) e).getPlayer().getUniqueId())
						.setLastInvCloseTime(System.currentTimeMillis());
				getCheatPlayer(((InventoryCloseEvent) e).getPlayer().getUniqueId())
						.setLastInvCloseType(((InventoryCloseEvent) e).getInventory().getType());
			} else if (e instanceof InventoryOpenEvent) {
				if(!checkPlayer(p))
					return;
				GUI.onEvent((InventoryOpenEvent) e);

				getCheatPlayer(((InventoryOpenEvent) e).getPlayer().getUniqueId()).setUseItemSpeed(false);
			} else if (e instanceof BlockBreakEvent) {
				if (((BlockBreakEvent) e).isCancelled())
					return;

				p.setLastBlockBreakTime(System.currentTimeMillis());

				if (System.currentTimeMillis() - p.getBreakBlockageTime() < 2000)
					cancelEvent((Cancellable) e);
			} else if (e instanceof BlockPlaceEvent) {
				if(!checkPlayer(p))
					return;
				if (((BlockPlaceEvent) e).isCancelled()) {
					double diffXMax = Math.max(((BlockPlaceEvent) e).getPlayer().getLocation().getBlockX(),
							((BlockPlaceEvent) e).getBlock().getX());
					double diffXMin = Math.min(((BlockPlaceEvent) e).getPlayer().getLocation().getBlockX(),
							((BlockPlaceEvent) e).getBlock().getX());
					double diffX = diffXMax - diffXMin;
					double diffZMax = Math.max(((BlockPlaceEvent) e).getPlayer().getLocation().getBlockZ(),
							((BlockPlaceEvent) e).getBlock().getZ());
					double diffZMin = Math.min(((BlockPlaceEvent) e).getPlayer().getLocation().getBlockZ(),
							((BlockPlaceEvent) e).getBlock().getZ());
					double diffZ = diffZMax - diffZMin;
					if (((BlockPlaceEvent) e).getPlayer().getLocation().getY()
							- ((BlockPlaceEvent) e).getBlock().getY() <= 3.0D && diffX < 4.0D && diffZ < 4.0D)
						p.setLastCanceledPlaceBlockTime(System.currentTimeMillis());
					p.setServerFallDistance(0);
					return;
				}

				if (System.currentTimeMillis() - p.getBuildBlockageTime() <= 2000) {
					cancelEvent((Cancellable) e);
				}
				// ScaffoldWalk check
				if (System.currentTimeMillis() - p.getLastSWBlockTime() <= 2000)
					cancelEvent((Cancellable) e);

				if (((BlockPlaceEvent) e).getBlock().getType().name().contains("SIGN"))
					p.setLastSignPlaceTime(System.currentTimeMillis());

				p.setLastBlockPlaceTime(System.currentTimeMillis());
			} else if (e instanceof PlayerToggleSprintEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerToggleSprintEvent) e).isCancelled())
					return;
				if (((PlayerToggleSprintEvent) e).isSprinting())
					p.setLastSprintStartTime(System.currentTimeMillis());
				else
					p.setLastSprintEndTime(System.currentTimeMillis());

				p.setSprinting(((PlayerToggleSprintEvent) e).isSprinting());
			} else if (e instanceof PlayerJoinEvent) {
				p.setLastJoinTime(System.currentTimeMillis());

				// Check config-version
				if (((PlayerJoinEvent) e).getPlayer().isOp())
					if(checkConfigVersion())
						((PlayerJoinEvent) e).getPlayer().sendMessage(getConfigVersionMessage());

				if (ConfigsMessages.chat_captcha_enable && checkModule(p, HackType.CHAT_CAPTCHA)
						&& !ChatCaptcha.registered.contains(((PlayerJoinEvent) e).getPlayer().getName()))
					Taka.getThisPlugin().getServer().getScheduler().runTaskLater(Taka.getThisPlugin(),
							() -> ChatCaptcha.showMessage(p, Numbers.getRandom(), true), 5L);
			} else if (e instanceof EntityDamageByEntityEvent) {
				if(!checkPlayer(p))
					return;
				if (((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
					p.setLastAttackTime(System.currentTimeMillis());
				}
				if (((EntityDamageByEntityEvent) e).getEntity() instanceof Player) {
					p.setLastDamageTime(System.currentTimeMillis());
					p.setLastDamager(((EntityDamageByEntityEvent) e).getDamager());
				}
				if(((EntityDamageByEntityEvent) e).getDamager() instanceof Projectile &&
						((EntityDamageByEntityEvent) e).getEntity() instanceof Player) {
					if(((Projectile)((EntityDamageByEntityEvent) e).getDamager()).getShooter() instanceof LivingEntity) {
						p.setLastDamager((((LivingEntity)((Projectile)((EntityDamageByEntityEvent) e).getDamager()).getShooter())));
					}
				}
			} else if (e instanceof PlayerQuitEvent) {
				// SPEED
				Speed.onPlayerLeave(((PlayerQuitEvent) e).getPlayer().getUniqueId());
				// BanWave
				if (ConfigsMessages.ban_wave_enable) {
					if (getCheatPlayer(((PlayerQuitEvent) e).getPlayer().getUniqueId()).getBanWave()) {
						if (ConfigsMessages.ban_wave_ibol) {
							Taka.getThisPlugin().getServer().dispatchCommand(
									Taka.getThisPlugin().getServer().getConsoleSender(),
									getBanWaveCommand().replaceAll("%p%|%player%", Taka.getThisPlugin().getServer()
											.getPlayer(((PlayerQuitEvent) e).getPlayer().getUniqueId()).getName()));
						}
					}
				}

				removeCheatPlayer(p.getPlayer().getUniqueId());
				
				// Clear unneded cache space
				CacheSystem.removeCache(p);

			} else if (e instanceof PlayerBlockInteractEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerBlockInteractEvent) e).getIteraction() == IteractionType.START_DESTROY_BLOCK) {
					if(p.getLastStartBlockBreak() == null || !p.getLastStartBlockBreak().equals(((PlayerBlockInteractEvent) e).getBlock())) {
						p.setLastStartBlockBreak(((PlayerBlockInteractEvent) e).getBlock());
						p.setLastStartBlockBreakTime(System.currentTimeMillis());
					}
				}
			} else if (e instanceof PlayerTACTeleportEvent) {
				if(!checkPlayer(p))
					return;
				NoFall.onCheck(p, ((PlayerTACTeleportEvent) e).getFrom(), ((PlayerTACTeleportEvent) e).getTo());
			} else if (e instanceof PlayerBedLeaveEvent) {
				if(!checkPlayer(p))
					return;
				p.setLastBedLeaveTime(System.currentTimeMillis());
			} else if (e instanceof PlayerMoveEvent) {
				if(!checkPlayer(p))
					return;
				if (((PlayerMoveEvent) e).isCancelled())
					return;

				p.setLastMoveLookTime(System.currentTimeMillis());
				
				if(((PlayerMoveEvent) e).getTo().distance(((PlayerMoveEvent) e).getFrom()) != 0.0D)
					p.setLastMoveTime(System.currentTimeMillis());

				if (isFrozen(p.getPlayer()) && ((PlayerMoveEvent) e).getTo().distance(((PlayerMoveEvent) e).getFrom()) != 0.0D)
					p.teleportToGround();
				
				if (!((PlayerMoveEvent) e).getFrom().clone().subtract(0, 0.1, 0).getBlock().isEmpty() &&
						(((PlayerMoveEvent) e).getFrom().clone().subtract(0, 0.1, 0).getBlock().getType().isSolid() &&
						!Utils.isPassableBlock(((PlayerMoveEvent) e).getFrom().clone().subtract(0, 0.1, 0).getBlock().getType())))
					p.setLastGroundLocation(((PlayerMoveEvent) e).getFrom());

				p.setLastFromToLocations(((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());

				if (getServerVersion() >= 6)
					if (p.getPlayer().getLocation().getBlock().getType().equals(Material.BUBBLE_COLUMN))
						p.setLastBubbleColumnTouchTime(System.currentTimeMillis());

				if (p.isSwimming())
					p.setLastSwimingTime(System.currentTimeMillis());

				// SlimeJump test
				// if(p.getLastVelocity().getY() < 0.0D && p.getPlayer().getVelocity().getY() >
				// 0.0D)
				// p.getPlayer().sendMessage("LEAVE: " + p.getLastVelocity().getY());

				p.setLastVelocity(((PlayerMoveEvent) e).getPlayer().getVelocity());

				// There is no event which shows when riptiling has ended!
				if (p.isRiptiding())
					p.setLastRiptileTime(System.currentTimeMillis());

				// There is entity dismount event, but only in Spigot api
				if (p.getPlayer().isInsideVehicle())
					p.setLastVehicleTime(System.currentTimeMillis());

				if (p.getPlayer().getActivePotionEffects() != null
						&& !p.getPlayer().getActivePotionEffects().isEmpty()) {
					p.setLastActivePotionEffectTypes(p.getPlayer().getActivePotionEffects());
					p.setLastActivePotionEffectsTime(System.currentTimeMillis());
				}

				if (p.getPlayer().getLocation().getBlock().getType().name().contains("PLATE")) {
					p.setLastPlateTouchTime(System.currentTimeMillis());
					p.setLastPlateTouchType(p.getPlayer().getLocation().getBlock().getType());
				}
				if (((PlayerMoveEvent) e).getFrom().getY() == ((PlayerMoveEvent) e).getTo().getY())
					p.setLastMoveDirectionType(MoveDirectionType.HORIZONTAL);
				else
					p.setLastMoveDirectionType(MoveDirectionType.VERTICAL);
				if (((PlayerMoveEvent) e).getFrom().getY() > ((PlayerMoveEvent) e).getTo().getY()) {
					// LOL, > 7 = hack
					// p.getPlayer().sendMessage("FALLDISTGRAVITY:
					// "+((p.getServerFallDistance()+((float) (((PlayerMoveEvent) e).getTo().getY()
					// - ((PlayerMoveEvent) e).getFrom().getY()))) / p.getServerFallDistance()));

					p.addServerFallDistance(
							(float) (((PlayerMoveEvent) e).getFrom().getY() - ((PlayerMoveEvent) e).getTo().getY()));

					// Minecraft doesn't account the fall dist when on climable materials, when fly is allowed
					// Fixing Scaffolding NoFall fp

					if (isClimable(((PlayerMoveEvent) e).getFrom().getBlock().getType())
							|| isClimable(((PlayerMoveEvent) e).getTo().getBlock().getType()) || p.isGliding()
							|| p.getPlayer().getAllowFlight()
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("PLATE")
							|| p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
									.contains("PLATE")
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("DOOR")
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("SIGN")
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("DAYLIGHT")
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("BANNER")
							|| p.getPlayer().getLocation().getBlock().getType().name().contains("CAMPFIRE")
							|| p.getPlayer().getLocation().getBlock().getType() == XMaterial.END_PORTAL_FRAME.parseMaterial()
							|| p.getPlayer().getLocation().getBlock().getType() == XMaterial.CAULDRON.parseMaterial()
							|| p.getPlayer().getLocation().getBlock().getType() == XMaterial.IRON_BARS.parseMaterial()
							|| p.getPlayer().getLocation().getBlock().getType() == XMaterial.BREWING_STAND.parseMaterial())
						p.setServerFallDistance(0.0F);

					// Reset it, sometimes MC takes false dmg when falling
					if (isOnGroundUltimate(((PlayerMoveEvent) e).getTo())) {
						// LowJump
						if (isOnGround2(p.getPlayer().getLocation()) != null
								|| isOnGround(p.getPlayer().getLocation()) != null
								|| p.getPlayer().getLocation().clone().subtract(0, 0.1, 0).getBlock()
										.getType() == XMaterial.LILY_PAD.parseMaterial()
								|| p.getPlayer().getLocation().getBlock().getType().name().contains("PLATE")
								|| p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().name()
										.contains("PLATE")
								|| System.currentTimeMillis() - p.getLastDamageTime() < 500) {
							LowJump.onCheck(p, ((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());
						}
						
						// NoFall
						if (isDetectionEnabled(HackType.MOVING_NO_FALL))
							NoFall.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
									((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());

						p.setServerFallDistance(0.0F);
					}
					// if(p.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK))
					// p.getPlayer().sendMessage("LEAVE: "+p.getPlayer().getVelocity().getY()+"
					// "+p.getLastVelocity().getY());
				} else {
					// if(isDetectionEnabled(HackType.MOVING_SLIME_JUMP) &&
					// isOnGround(((PlayerMoveEvent) e).getFrom()) != null) {
					// SlimeJump.onCheck(getCheatPlayer(((PlayerMoveEvent)
					// e).getPlayer().getUniqueId()), isOnGround(p.getPlayer().getLocation()), 0.0);
					// }
					
					if(p.getServerFallDistance() > 0.0F) {
						if (isDetectionEnabled(HackType.MOVING_NO_FALL))
							NoFall.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
									((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());
					}
					
					p.setServerFallDistance(0.0F);
				}
			}
		} catch (Exception ex) {
			if (ex instanceof NullPointerException) {
				// One cause if from npc, because they don't have cheatplayer object
				ex.printStackTrace();
				return;
			}
			ex.printStackTrace();
			printError(ex.getMessage(), 13);
		}
	}
	
	private static void cancelEvent(Cancellable e) {
		cancelEvent(e, null);
	}
	
	private static void cancelEvent(Cancellable e, HackType check) {
		if (!isGhostMode())
			if(check != null && isDetectionGhosted(check))
				e.setCancelled(true);
	}

	public static void loadChecks(Event e) {
		try {
			// Check for breaking point
			if (getTPS() < ConfigsMessages.anticheat_break_point_tps) {
				clearVL(false);
				
				// Low tps causes too much lag, the checks are not reliable
				return;

			}
			if (e instanceof PlayerEvent) {
				if (getCheatPlayer(((PlayerEvent) e).getPlayer().getUniqueId()) == null) {
					if(((PlayerEvent)e).getPlayer().isOnline()) {
						addCheatPlayer(((PlayerEvent)e).getPlayer().getUniqueId());
						printError("Player has been disconected!", 14);
					}
					
					return;
				}
			}
			// Join lag causes false positives
			if (System.currentTimeMillis() - getStartTime() >= 5000) {
				if (e instanceof PlayerMoveEvent) {
					if (((PlayerMoveEvent) e).isCancelled())
						return;
					// NoPitch
					if (isDetectionEnabled(HackType.MOVING_NO_PITCH))
						if (NoPitch.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							cancelEvent((Cancellable) e, HackType.MOVING_NO_PITCH);
					
					// NoFall
					if (isDetectionEnabled(HackType.MOVING_NO_FALL))
						NoFall.checkFallDamage(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());
					
					// Fly Stable Y
					if (isDetectionEnabled(HackType.MOVING_FLY_STABLE_Y))
						if (FlyStableY.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					if (((PlayerMoveEvent) e).getTo().distance(((PlayerMoveEvent) e).getFrom()) == 0.0D)
						return;
					if (System.currentTimeMillis() - getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId())
							.getLastCanceledPlaceBlockTime() < 1000)
						return;
					if (getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()).getFreezed()) {
						if (System.currentTimeMillis()
								- getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId())
										.getLastSneakToggleTime() > 500
								&& getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()).getFreezedReason()
										.equals(HackType.MOVING_SNEAK)) {
							getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()).setFreezed(false);
							return;
						}
						((PlayerMoveEvent) e).getPlayer().teleport(((PlayerMoveEvent) e).getFrom());
					}
					
					// AntiLevitation
					if (isDetectionEnabled(HackType.MOVING_ANTI_LEVITATION))
						if (AntiLevitation.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Speed
					CheatPlayer pCopy = getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId());
					Location from = ((PlayerMoveEvent) e).getFrom();
					Location to = ((PlayerMoveEvent) e).getTo();
					
					spExecutor.execute(() -> {
						if (Speed.onCheck(pCopy, from, to))
							return;
					});
					
					// InvMove
					if (isDetectionEnabled(HackType.INVENTORY_MOVE))
						if (InvMove.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Jesus
					if (isDetectionEnabled(HackType.MOVING_JESUS))
						if (Jesus.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Step
					if (isDetectionEnabled(HackType.MOVING_STEP))
						if (Step.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Ground Elytra
					if (isDetectionEnabled(HackType.MOVING_GROUND_ELYTRA))
						if (GroundElytra.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// HighJump
					if (isDetectionEnabled(HackType.MOVING_HIGHJUMP))
						if (HighJump.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Fly Invalid Y
					if (isDetectionEnabled(HackType.MOVING_FLY_INVALID_Y))
						if (FlyInvalidY.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Fly Double Jump Up
					if (isDetectionEnabled(HackType.MOVING_FLY_DOUBLE_JUMP_UP))
						if (FlyDoubleJumpUP.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Fly Double Jump Down
					if (isDetectionEnabled(HackType.MOVING_FLY_DOUBLE_JUMP_DOWN))
						if (FlyDoubleJumpDown.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Fly Modulo
					if (isDetectionEnabled(HackType.MOVING_FLY_MODULO))
						if (FlyModulo.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// InvalidFall
					if (isDetectionEnabled(HackType.MOVING_INVALIDFALL_STABLE_DISTANCE)
							|| isDetectionEnabled(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT)
							|| isDetectionEnabled(HackType.MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT)
							|| isDetectionEnabled(HackType.MOVING_INVALIDFALL_SLOWER_DISTANCE)
							|| isDetectionEnabled(HackType.MOVING_INVALIDFALL_FAST_START_DISTANCE))
						if (InvalidFall.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// FastLadder
					if (isDetectionEnabled(HackType.MOVING_FASTLADDER_INSTANT)
							|| isDetectionEnabled(HackType.MOVING_FASTLADDER_NON_INSTANT))
						if (FastLadder.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// ImpossibleJump
					if (isDetectionEnabled(HackType.MOVING_IMPOSSIBLE_JUMP))
						if (ImpossibleJump.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
					
					// Blink
					if (isDetectionEnabled(HackType.MOVING_BLINK))
						Blink.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo());
					
					// Strafe
					if (isDetectionEnabled(HackType.MOVING_STRAFE))
						if (Strafe.onCheck(getCheatPlayer(((PlayerMoveEvent) e).getPlayer().getUniqueId()),
								((PlayerMoveEvent) e).getFrom(), ((PlayerMoveEvent) e).getTo()))
							return;
				}
			}

			if (e instanceof AsyncPlayerChatEvent) {
				/*
				 * Some idiots cancel the event to make their's thingies...oh if
				 * (((AsyncPlayerChatEvent) e).isCancelled()) return;
				 */
				if (getCheatPlayer(((AsyncPlayerChatEvent) e).getPlayer().getUniqueId()) == null)
					return;
				
				// Chat Captcha
				if (isDetectionEnabled(HackType.CHAT_CAPTCHA))
					((AsyncPlayerChatEvent) e).setCancelled(
							ChatCaptcha.onCheck(getCheatPlayer(((AsyncPlayerChatEvent) e).getPlayer().getUniqueId()),
									((AsyncPlayerChatEvent) e).getMessage()));
				
				// Some checks want sync because of event calling
				new BukkitRunnable() {
					
					@Override
					public void run() {
						// Chat Badwords
						if (isDetectionEnabled(HackType.CHAT_BADWORDS)) {
							String message = ChatBadwords.onCheck(
									getCheatPlayer(((AsyncPlayerChatEvent) e).getPlayer().getUniqueId()),
									((AsyncPlayerChatEvent) e).getMessage());
							if (message == null)
								((AsyncPlayerChatEvent) e).setCancelled(true);
							else if (!message.equalsIgnoreCase(((AsyncPlayerChatEvent) e).getMessage()))
								((AsyncPlayerChatEvent) e).setMessage(message);
						}
					}
				}.runTask(Taka.getThisPlugin());
			}
			if (e instanceof AsyncPlayerPreLoginEvent) {
				// UUIDSpoof
				if (isDetectionEnabled(HackType.EXPLOIT_UUID_SPOOF))
					UUIDSpoof.onJoin((AsyncPlayerPreLoginEvent) e);
			} else if (e instanceof PlayerCommandPreprocessEvent) {
				if (((PlayerCommandPreprocessEvent) e).isCancelled())
					return;
				if (!((PlayerCommandPreprocessEvent) e).getPlayer().isOnline())
					return;
				
				// Chat Captcha
				if (isDetectionEnabled(HackType.CHAT_CAPTCHA))
					if ((ChatCaptcha.onCheck1(
							getCheatPlayer(((PlayerCommandPreprocessEvent) e).getPlayer().getUniqueId()),
							((PlayerCommandPreprocessEvent) e).getMessage())))
						cancelEvent((Cancellable) e);
			} else if (e instanceof PlayerToggleSneakEvent) {
				// Sneak
				if (isDetectionEnabled(HackType.MOVING_SNEAK)) {
					Sneak.onCheck(getCheatPlayer(((PlayerToggleSneakEvent) e).getPlayer().getUniqueId()),
							((PlayerToggleSneakEvent) e).isSneaking());
				}
			} else if (e instanceof PlayerInteractEvent) {
				if (((PlayerInteractEvent) e).getAction().equals(Action.RIGHT_CLICK_AIR)
						|| ((PlayerInteractEvent) e).getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					// If click on door for example while having food, the player does not start to eat, but open the door
					Material click = ((PlayerInteractEvent) e).getAction().equals(Action.RIGHT_CLICK_BLOCK) ? ((PlayerInteractEvent) e).getClickedBlock().getType() : null;
					if ((click != null && !isClickableBlock(click)) || click == null) {
						if (isFood(((PlayerInteractEvent) e).getMaterial())
								|| ((PlayerInteractEvent) e).getMaterial().equals(XMaterial.POTION.parseMaterial())
								|| ((PlayerInteractEvent) e).getMaterial().equals(Material.BOW)
								|| isBlockable(((PlayerInteractEvent) e).getMaterial())) {
							if (((PlayerInteractEvent) e).getMaterial().equals(Material.BOW)) {
								boolean as = false;
								for (ItemStack a : ((PlayerInteractEvent) e).getPlayer().getInventory()
										.getContents()) {
									if (a != null && a.getType().name().contains("ARROW")) {
										as = true;
										break;
									}
								}
								
								if (!as)
									return;
								
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
								.setUseItemSpeed(true);
							} else if (isFood(((PlayerInteractEvent) e).getMaterial())) {
								if (((PlayerInteractEvent) e).getPlayer().getFoodLevel() == 20)
									return;
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
										.setLastFoodEatStartTime(System.currentTimeMillis());
								
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
								.setUseItemSpeed(true);
							} else if (((PlayerInteractEvent) e).getMaterial().equals(XMaterial.POTION.parseMaterial())) {
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
										.setLastPotionConsumeStartTime(System.currentTimeMillis());
								
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
								.setUseItemSpeed(true);
							} else if(isBlockable(((PlayerInteractEvent) e).getMaterial())) {
								if(isSword(((PlayerInteractEvent) e).getMaterial()) &&
										getClientVersion(getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())).isBelow(ClientVersion.v1_9_X)) {
									getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
									.setUseItemSpeed(true);
									
									getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
									.setBlocking(true);
								} else {
									if(getClientVersion(getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())).isAbove(ClientVersion.v1_8_X)) {
										if(Utils.getServerVersion() >= 2) {
											try {
												if(XMaterial.matchXMaterial(((PlayerInteractEvent) e).getPlayer().getInventory().getItemInOffHand().getType()).equals(XMaterial.SHIELD) ||
														XMaterial.matchXMaterial(((PlayerInteractEvent) e).getMaterial()).equals(XMaterial.SHIELD)) {
													getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
													.setUseItemSpeed(true);
													
													getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId())
													.setBlocking(true);
												}
											} catch (NoSuchMethodError ignored) { }
										}
									}
								}
							}
						}
					}
					if (((PlayerInteractEvent) e).getPlayer().getItemInHand().getType().name().contains("BOOTS")
							|| ((PlayerInteractEvent) e).getPlayer().getItemInHand().getType().name()
									.contains("LEGGINGS")
							|| ((PlayerInteractEvent) e).getPlayer().getItemInHand().getType().name()
									.contains("CHESTPLATE")
							|| ((PlayerInteractEvent) e).getPlayer().getItemInHand().getType().name()
									.contains("HELMET")) {
						// Fast Inventory
						if (isDetectionEnabled(HackType.INVENTORY_FAST_INVENTORY)) {
							if (FastInventory.onCheck(
									getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId()),
									((PlayerInteractEvent) e).getPlayer().getItemInHand(), null)) {
								cancelEvent((Cancellable) e, HackType.INVENTORY_FAST_INVENTORY);
							}
						}
					}
					
					// Auto soup
					if (isDetectionEnabled(HackType.COMBAT_AUTOSOUP))
						if (AutoSoup.onCheck1(getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId()),
								((PlayerInteractEvent) e).getAction(), ((PlayerInteractEvent) e).getMaterial()))
							cancelEvent((Cancellable) e, HackType.COMBAT_AUTOSOUP);
					
					// Fast Click
					if (isDetectionEnabled(HackType.INVENTORY_FAST_CLICK))
						FastClick.onClick(getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId()),
								((PlayerInteractEvent) e).getItem());
					
					// Invalid Interaction Block
					if (isDetectionEnabled(HackType.WORLD_INVALID_INTERACTION_BLOCK))
						if (InvalidInteractionBlock.onCheck(
								getCheatPlayer(((PlayerInteractEvent) e).getPlayer().getUniqueId()),
								((PlayerInteractEvent) e).getClickedBlock(), true))
							cancelEvent((Cancellable) e, HackType.WORLD_INVALID_INTERACTION_BLOCK);
				}
			} else if (e instanceof BlockBreakEvent) {
				if (((BlockBreakEvent) e).isCancelled())
					return;
				
				// Wrong Block Direction
				if (isDetectionEnabled(HackType.WORLD_WRONG_BLOCK_DIRECTION))
					if ((WrongBlock.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()),
							((BlockBreakEvent) e).getBlock())))
						cancelEvent((Cancellable) e, HackType.WORLD_WRONG_BLOCK_DIRECTION);
				
				// Liquid Interaction
				if (isDetectionEnabled(HackType.WORLD_LIQUID_INTERACTION))
					if (LiquidInteraction.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()),
							((BlockBreakEvent) e).getBlock(), null))
						cancelEvent((Cancellable) e, HackType.WORLD_LIQUID_INTERACTION);
				
				// No Swing
				if (isDetectionEnabled(HackType.WORLD_NO_SWING))
					if (NoSwing.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()),
							((BlockBreakEvent) e).getBlock(), null, null))
						cancelEvent((Cancellable) e, HackType.WORLD_NO_SWING);
				
				// CreativeNuker
				if (isDetectionEnabled(HackType.WORLD_CREATIVE_NUKER))
					if (CreativeNuker.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()),
							((BlockBreakEvent) e).getBlock()))
						cancelEvent((Cancellable) e, HackType.WORLD_CREATIVE_NUKER);
				
				// Invalid Interaction Block
				if (isDetectionEnabled(HackType.WORLD_INVALID_INTERACTION_BLOCK))
					if (InvalidInteractionBlock.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()),
							((BlockBreakEvent) e).getBlock(), false))
						cancelEvent((Cancellable) e, HackType.WORLD_INVALID_INTERACTION_BLOCK);
				
				// Fast Break
				if(isDetectionEnabled(HackType.WORLD_FAST_BREAK))
					FastBreak.onCheck(getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId()), ((BlockBreakEvent) e).getBlock());

				// I really wanted to put it in setValues(), but it is called before checks...
				if (!((BlockBreakEvent) e).getBlock().getType().name().contains("AIR"))
					getCheatPlayer(((BlockBreakEvent) e).getPlayer().getUniqueId())
							.setLastBlockBreakType(((BlockBreakEvent) e).getBlock().getType());
			} else if (e instanceof BlockDamageEvent) {
				// NoBreakDelay
				if (isDetectionEnabled(HackType.WORLD_NO_BREAK_DELAY))
					NoBreakDelay.onCheck(getCheatPlayer(((BlockDamageEvent) e).getPlayer().getUniqueId()),
							((BlockDamageEvent) e).getBlock());
			} else if (e instanceof BlockPlaceEvent) {
				if (((BlockPlaceEvent) e).isCancelled())
					return;
				
				// Liquid Interaction
				if (isDetectionEnabled(HackType.WORLD_LIQUID_INTERACTION))
					if (LiquidInteraction.onCheck(getCheatPlayer(((BlockPlaceEvent) e).getPlayer().getUniqueId()),
							((BlockPlaceEvent) e).getBlock(), ((BlockPlaceEvent) e).getBlockAgainst()))
						cancelEvent((Cancellable) e, HackType.WORLD_LIQUID_INTERACTION);
				
				// Wrong Block
				if (isDetectionEnabled(HackType.WORLD_WRONG_BLOCK_DIRECTION))
					if ((WrongBlock.onCheck(getCheatPlayer(((BlockPlaceEvent) e).getPlayer().getUniqueId()),
							((BlockPlaceEvent) e).getBlock())))
						cancelEvent((Cancellable) e, HackType.WORLD_WRONG_BLOCK_DIRECTION);
				
				// Haha, if canceled by previous checks, make some stupid problems
				// Fast Place
				if (!((BlockPlaceEvent) e).isCancelled())
					if (isDetectionEnabled(HackType.WORLD_FAST_PLACE))
						if (FastPlace.onCheck(getCheatPlayer(((BlockPlaceEvent) e).getPlayer().getUniqueId()),
								((BlockPlaceEvent) e).getBlock()))
							cancelEvent((Cancellable) e, HackType.WORLD_FAST_PLACE);
				
				// Scaffold Legacy
				if (isDetectionEnabled(HackType.MOVING_SCAFFOLDWALK_TIMER))
					if (ScaffoldWalk.onCheckTimer(getCheatPlayer(((BlockPlaceEvent) e).getPlayer().getUniqueId()),
							((BlockPlaceEvent) e).getBlock()))
						((BlockPlaceEvent) e).setCancelled(true);
			} else if (e instanceof PlayerDropItemEvent) {
				if (((PlayerDropItemEvent) e).isCancelled())
					return;
				
				// Creative Drop
				if (isDetectionEnabled(HackType.WORLD_CREATIVE_DROP))
					if (CreativeDrop.onCheck(getCheatPlayer(((PlayerDropItemEvent) e).getPlayer().getUniqueId()))) {
						cancelEvent((Cancellable) e, HackType.WORLD_CREATIVE_DROP);
					}
			} else if (e instanceof InventoryClickEvent) {
				if (((InventoryClickEvent) e).isCancelled())
					return;
				
				// Inv Move
				if (isDetectionEnabled(HackType.INVENTORY_MOVE))
					InvMove.onCheck(getCheatPlayer(((InventoryClickEvent) e).getWhoClicked().getUniqueId()),
							(InventoryClickEvent) e);
				
				// Fast Inventory
				if (((InventoryClickEvent) e).getCurrentItem() != null
						&& !((InventoryClickEvent) e).getCurrentItem().getType().equals(Material.AIR)) {
					if (FastInventory.onCheck(getCheatPlayer(((InventoryClickEvent) e).getWhoClicked().getUniqueId()),
							((InventoryClickEvent) e).getCurrentItem(), ((InventoryClickEvent) e).getSlotType()))
						cancelEvent((Cancellable) e, HackType.INVENTORY_FAST_INVENTORY);
				}
				
				// Auto soup
				if (isDetectionEnabled(HackType.COMBAT_AUTOSOUP))
					AutoSoup.onCheck(getCheatPlayer(((InventoryClickEvent) e).getWhoClicked().getUniqueId()),
							((InventoryClickEvent) e).getCursor(), ((InventoryClickEvent) e).getCurrentItem(),
							((InventoryClickEvent) e).getSlotType(), ((InventoryClickEvent) e).getClick(),
							((InventoryClickEvent) e).getSlot());

				// GUI
				GUI.onEvent((InventoryClickEvent) e);
				GUI.Report.reportEvents((InventoryClickEvent) e);
			} else if (e instanceof EntityDamageByEntityEvent) {
				if (((EntityDamageByEntityEvent) e).isCancelled())
					return;
				
				// No Swing
				if (isDetectionEnabled(HackType.WORLD_NO_SWING))
					if (NoSwing.onCheck(getCheatPlayer(((EntityDamageByEntityEvent) e).getDamager().getUniqueId()),
							null, ((EntityDamageByEntityEvent) e).getDamager(),
							((EntityDamageByEntityEvent) e).getCause()))
						cancelEvent((Cancellable) e, HackType.WORLD_NO_SWING);
				
				// Criticals
				if (isDetectionEnabled(HackType.COMBAT_CRITICALS))
					if (Criticals.onCheck(getCheatPlayer(((EntityDamageByEntityEvent) e).getDamager().getUniqueId()),
							((EntityDamageByEntityEvent) e).getDamage()))
						cancelEvent((Cancellable) e, HackType.COMBAT_CRITICALS);
				
				// Reach
				if (isDetectionEnabled(HackType.COMBAT_REACH))
					ReachCombat.onCheck(getCheatPlayer(((EntityDamageByEntityEvent) e).getDamager().getUniqueId()),
							((EntityDamageByEntityEvent) e).getEntity(), ((EntityDamageByEntityEvent) e).getCause()); // Don't cancel the check!
				
				// Invalid Interaction Entity
				if (isDetectionEnabled(HackType.COMBAT_INVALID_INTERACTION_ENTITY))
					if (InvalidInteractionEntity.onCheck(
							getCheatPlayer(((EntityDamageByEntityEvent) e).getDamager().getUniqueId()),
							((EntityDamageByEntityEvent) e).getEntity(), ((EntityDamageByEntityEvent) e).getCause()))
						cancelEvent((Cancellable) e, HackType.COMBAT_INVALID_INTERACTION_ENTITY);
				
			} else if (e instanceof EntityShootBowEvent) {
				if (((EntityShootBowEvent) e).isCancelled())
					return;
				
				// FastBow
				if (isDetectionEnabled(HackType.COMBAT_FASTBOW))
					if (FastBow.onCheck(getCheatPlayer(((EntityShootBowEvent) e).getEntity().getUniqueId()),
							((EntityShootBowEvent) e).getForce()))
						cancelEvent((Cancellable) e, HackType.COMBAT_FASTBOW);
			} else if (e instanceof SignChangeEvent) {
				if (((SignChangeEvent) e).isCancelled())
					return;
				
				// Auto Sign
				if (isDetectionEnabled(HackType.WORLD_AUTO_SIGN))
					if (AutoSign.onCheck(getCheatPlayer(((SignChangeEvent) e).getPlayer().getUniqueId()),
							((SignChangeEvent) e).getLines(), ((SignChangeEvent) e).getBlock()))
						cancelEvent((Cancellable) e, HackType.WORLD_AUTO_SIGN);
			} else if (e instanceof PlayerItemConsumeEvent) {
				if (((PlayerItemConsumeEvent) e).isCancelled())
					return;
				
				// Fast Eat
				if (isDetectionEnabled(HackType.INVENTORY_FAST_EAT)) {
					if (FastEat.onCheck(getCheatPlayer(((PlayerItemConsumeEvent) e).getPlayer().getUniqueId()), ((PlayerItemConsumeEvent) e).getItem()))
						cancelEvent((Cancellable) e, HackType.INVENTORY_FAST_EAT);
				}
				
				// Speed
				getCheatPlayer(((PlayerItemConsumeEvent) e).getPlayer().getUniqueId()).setUseItemSpeed(false);
			} else if (e instanceof PlayerItemHeldEvent) {
				if (((PlayerItemHeldEvent) e).isCancelled())
					return;
				
				// Fast Click
				if (isDetectionEnabled(HackType.INVENTORY_FAST_CLICK))
					FastClick.onSlotChange(getCheatPlayer(((PlayerItemHeldEvent) e).getPlayer().getUniqueId()),
							((PlayerItemHeldEvent) e).getPreviousSlot(), ((PlayerItemHeldEvent) e).getNewSlot(),
							((PlayerItemHeldEvent) e).getPlayer().getInventory()
									.getItem(((PlayerItemHeldEvent) e).getPreviousSlot()));
			} else if (e instanceof ProjectileLaunchEvent) {
				if (((ProjectileLaunchEvent) e).isCancelled()
						|| !(((ProjectileLaunchEvent) e).getEntity().getShooter() instanceof Player))
					return;
				
				// Throw
				if (isDetectionEnabled(HackType.INVENTORY_THROW))
					if (Throw.onCheck(
							getCheatPlayer(
									((Player) ((ProjectileLaunchEvent) e).getEntity().getShooter()).getUniqueId()),
							((ProjectileLaunchEvent) e).getEntity()))
						cancelEvent((Cancellable) e, HackType.INVENTORY_THROW);
			}
		} catch (Exception ex) {
			// NPC
			ex.printStackTrace();
			if (ex instanceof NullPointerException)
				return;

			printError(ex.getMessage(), 15);
		}
	}

	public static ArrayList<Report> getReports() {
		return reports;
	}

	public static Report getReport(String reported) {
		for (Report rep : getReports()) {
			if (rep.getReported().getName().equals(reported))
				return rep;
		}
		return null;
	}

	public static void addReport(Report rep, boolean serverLoad) {
		PlayerReportEvent rep1 = new PlayerReportEvent(rep, PlayerReportEvent.ReportType.ADD_PLAYER);
		Taka.getThisPlugin().getServer().getPluginManager().callEvent(rep1);
		if (!rep1.isCancelled()) {
			boolean isOverWriting = false;
			for (Report r : reports) {
				if (r.getPlayer().getName() == rep.getPlayer().getName()
						&& r.getReported().getName() == rep.getReported().getName()) {
					r.setReport(r.getReport() + ", " + rep.getReport());
					isOverWriting = true;
				}
			}
			
			/*if (rep.getPlayer().isOnline() && !serverLoad) {
				((Player) rep.getPlayer()).sendMessage(
						prepareColors(getTakaPrefix() + ConfigsMessages.anticheat_report_complete_message));
			}*/

			if (!serverLoad) {
				for (CheatPlayer p : getCheatPlayers()) {
					if (p.getDebugMode()) {
						new BukkitRunnable() {

							@Override
							public void run() {
								Taka.getThisPlugin().getServer().dispatchCommand(
										Taka.getThisPlugin().getServer().getConsoleSender(),
										"taka message " + ConfigsMessages.anticheat_report_staff_message
												.replaceAll("%p%|%player%", rep.getPlayer().getName())
												.replaceAll("%rep%", rep.getReported().getName())
												.replaceAll("%hack%|%h%", rep.getReport()));
							}
						}.runTask(Taka.getThisPlugin());
					}
				}
			}

			if (!isOverWriting)
				reports.add(rep);
		}
	}

	public static void removeReport(Report rep) {
		PlayerReportEvent rep1 = new PlayerReportEvent(rep, PlayerReportEvent.ReportType.REMOVE_PLAYER);
		Taka.getThisPlugin().getServer().getPluginManager().callEvent(rep1);
		if (!rep1.isCancelled()) {
			reports.remove(rep);
		}
	}

	public static double getLaggReachDisstance(Entity attacked, double dist, boolean isDamagerSprinting) {
		if (!(attacked instanceof Player)) {
			// Check TPS Lagg
			if (getTPS() <= 17) {
				dist += calculateTPSLaggDisstance(getTPS());
			}
		} else {
			// Checking Ping Lagg
			// Don't check NPC's, they don't have cheatplayer
			if (!attacked.hasMetadata("NPC") && getCheatPlayer(attacked.getUniqueId()).getMCPing() >= 150) {
				dist += calculatePingLaggDisstance(getCheatPlayer(attacked.getUniqueId()).getMCPing());
			}
			// Checking TPS Lagg
			if (getTPS() <= 16) {
				dist += calculateTPSLaggDisstance(getTPS());
			}
		}
		return isDamagerSprinting ? dist + 0.6 : dist;
	}

	public static double calculatePingLaggDisstance(double ping) {
		// Make unbreakable ping calculation?, possible and not so hard, but needs alot
		// of testing
		return ping / 300;
	}

	public static double calculateTPSLaggDisstance(double tps) {
		return (20 - tps) * 0.165;
	}

	public static Location getGroundLocation(Location loc) {
		Location loc1 = loc;

		for (double y = loc.getY(); y >= 0; y--) {
			loc1.setY(y);

			if (loc1.getBlock().getRelative(BlockFace.DOWN).getType().isSolid() ||
					loc1.getBlock().getRelative(BlockFace.DOWN).getType().name().contains("VOID"))
				return loc1;
		}

		return loc1;
	}

	public static boolean isInWeb(CheatPlayer p, final Location location, boolean upperBlock) {
		if (!upperBlock)
			return isNextToBlock(p, location, XMaterial.COBWEB.parseMaterial());

		return isNextToBlock(p, location, XMaterial.COBWEB.parseMaterial()) || isNextToBlock(
				p, location.getBlock().getRelative(BlockFace.UP).getLocation(), XMaterial.COBWEB.parseMaterial());
	}

	public static boolean isOnEntity(final Player p, final EntityType type) {
		for (final Entity e : p.getNearbyEntities(1.0, 1.0, 1.0)) {
			if (e.getType() == type && e.getLocation().getY() < p.getLocation().getY()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOnEntity(final Player p, final EntityType type, final double distance) {
		for (final Entity e : p.getNearbyEntities(1.5, distance, 1.5)) {
			if (e.getType() == type && e.getLocation().getY() < p.getLocation().getY()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOnEntity(final Player p) {
		for (final Entity e : p.getNearbyEntities(1.0, 1.0, 1.0)) {
			if (e.getLocation().getY() < p.getLocation().getY()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInEntity(final Player p, short diff, EntityType enType) {
		for (final Entity e : p.getNearbyEntities(diff, diff, diff)) {
			if (e.getLocation().getY() == p.getLocation().getY()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNextToEntity(final Player p, short diff, EntityType enType) {
		for (final Entity e : p.getNearbyEntities(diff, diff, diff)) {
			if (enType == e.getType()) {
				return true;
			}
		}
		return false;
	}

	// I got this from the web
	public static ArrayList<Block> getBlocksFromTwoLocations(Location loc1, Location loc2) {
		ArrayList<Block> blocks = new ArrayList<Block>();

		int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
		int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

		int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
		int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

		int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
		int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int z = bottomBlockZ; z <= topBlockZ; z++) {
				for (int y = bottomBlockY; y <= topBlockY; y++) {
					Block block = loc1.getWorld().getBlockAt(x, y, z);

					blocks.add(block);
				}
			}
		}

		return blocks;
	}

	public static boolean isOnLadder(final Player p) {
		return (p.getLocation().add(0.0, 1.0, 0.0).getBlock().getType() == Material.LADDER
				&& p.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.LADDER)
				|| (p.getLocation().add(0.0, 1.0, 0.0).getBlock().getType() == Material.VINE
						&& p.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.VINE);

	}

	public static boolean isOnLadder2(final Location l) {
		return l.getBlock().getType() == Material.LADDER || l.getBlock().getType() == Material.VINE;
	}

	public static boolean isOnScaffolding(final Player p) {
		if (getServerVersion() < 7)
			return false;

		return p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.SCAFFOLDING
				|| p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SCAFFOLDING
				|| p.getLocation().getBlock().getRelative(BlockFace.EAST).getType() == Material.SCAFFOLDING
				|| p.getLocation().getBlock().getRelative(BlockFace.WEST).getType() == Material.SCAFFOLDING
				|| p.getLocation().getBlock().getType() == Material.SCAFFOLDING;

	}

	public static boolean isOnScaffolding2(final Location l) {
		if (getServerVersion() < 7)
			return false;

		return l.getBlock().getType() == Material.SCAFFOLDING || l.getBlock().getType() == Material.SCAFFOLDING;
	}

	public static boolean isOnSlime(final Location playerLoc) {
		// Down slime block or if there is CARPET or something, check the block below it
		if (playerLoc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK || playerLoc.getBlock()
				.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK)
			return true;

		return false;
	}

	public static ArrayList<Block> isOnGround(Location location) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc4 = location.clone().subtract(0.0, 0.2, 0.0);
		if (!loc4.clone().add(0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, 0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, 0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, 0.5).getBlock());
		if (!loc4.clone().add(-0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -0.5).getBlock());
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_WEST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_WEST));
		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> getAroundBlocks(Location location) {
		return getAroundBlocks(location, 0.5);
	}

	public static ArrayList<Block> getAroundBlocks(Location location, double d) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc4 = location.clone().subtract(0.0, 0.2, 0.0);
		if (!loc4.clone().add(d, 0, 0).getBlock().isEmpty() && loc4.clone().add(d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, d).getBlock().isEmpty() && loc4.clone().add(0, 0, d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, d).getBlock());
		if (!loc4.clone().add(-d, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -d).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -d).getBlock());
		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> getAroundBlocks2(Location location) {
		return getAroundBlocks2(location, 0.5);
	}

	public static ArrayList<Block> getAroundBlocks2(Location location, double d) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc4 = location.clone().subtract(0.0, 0.2, 0.0);
		if (!loc4.clone().add(d, 0, 0).getBlock().isEmpty() && loc4.clone().add(d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, d).getBlock().isEmpty() && loc4.clone().add(0, 0, d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, d).getBlock());
		if (!loc4.clone().add(-d, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -d).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -d).getBlock());

		if (!loc4.clone().add(d, 0, 1).getBlock().isEmpty() && loc4.clone().add(d, 0, 1).getBlock().getType().isSolid())
			result.add(loc4.clone().add(d, 0, 1).getBlock());
		if (!loc4.clone().add(-1, 0, d).getBlock().isEmpty()
				&& loc4.clone().add(-1, 0, d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-1, 0, d).getBlock());
		if (!loc4.clone().add(-d, 0, 1).getBlock().isEmpty()
				&& loc4.clone().add(1, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(1, 0, -d).getBlock());
		if (!loc4.clone().add(-1, 0, -d).getBlock().isEmpty()
				&& loc4.clone().add(-1, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-1, 0, -d).getBlock());

		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> getAroundBlocks3(Location loc4, double d) {
		ArrayList<Block> result = new ArrayList<Block>();
		if (!loc4.clone().add(d, 0, 0).getBlock().isEmpty() && loc4.clone().add(d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, d).getBlock().isEmpty() && loc4.clone().add(0, 0, d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, d).getBlock());
		if (!loc4.clone().add(-d, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-d, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-d, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -d).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -d).getBlock());

		if (!loc4.clone().add(d, 0, 1).getBlock().isEmpty() && loc4.clone().add(d, 0, 1).getBlock().getType().isSolid())
			result.add(loc4.clone().add(d, 0, 1).getBlock());
		if (!loc4.clone().add(-d, 0, 1).getBlock().isEmpty()
				&& loc4.clone().add(-d, 0, 1).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-d, 0, 1).getBlock());
		if (!loc4.clone().add(-1, 0, d).getBlock().isEmpty()
				&& loc4.clone().add(-1, 0, d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-1, 0, d).getBlock());
		if (!loc4.clone().add(-1, 0, -d).getBlock().isEmpty()
				&& loc4.clone().add(-1, 0, -d).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-1, 0, -d).getBlock());

		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> isOnGround2(Location location) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc3 = location.clone().subtract(0.0, 0.8, 0.0);
		if (!loc3.clone().add(0.5, 0, 0).getBlock().isEmpty()
				&& loc3.clone().add(0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc3.clone().add(0.5, 0, 0).getBlock());
		if (!loc3.clone().add(0, 0, 0.5).getBlock().isEmpty()
				&& loc3.clone().add(0, 0, 0.5).getBlock().getType().isSolid())
			result.add(loc3.clone().add(0, 0, 0.5).getBlock());
		if (!loc3.clone().add(-0.5, 0, 0).getBlock().isEmpty()
				&& loc3.clone().add(-0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc3.clone().add(-0.5, 0, 0).getBlock());
		if (!loc3.clone().add(0, 0, -0.5).getBlock().isEmpty()
				&& loc3.clone().add(0, 0, -0.5).getBlock().getType().isSolid())
			result.add(loc3.clone().add(0, 0, -0.5).getBlock());
		if (!loc3.getBlock().isEmpty() && loc3.getBlock().getType().isSolid())
			result.add(loc3.getBlock());
		final Location loc4 = location.clone().subtract(0.0, 0.2, 0.0);
		if (loc4.clone().add(0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, 0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, 0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, 0.5).getBlock());
		if (!loc4.clone().add(-0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -0.5).getBlock());
		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> isOnGround3(Location location) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc4 = location.clone().subtract(0.0, 0.2, 0.0);
		if (!loc4.clone().add(0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, 0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, 0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, 0.5).getBlock());
		if (!loc4.clone().add(-0.5, 0, 0).getBlock().isEmpty()
				&& loc4.clone().add(-0.5, 0, 0).getBlock().getType().isSolid())
			result.add(loc4.clone().add(-0.5, 0, 0).getBlock());
		if (!loc4.clone().add(0, 0, -0.5).getBlock().isEmpty()
				&& loc4.clone().add(0, 0, -0.5).getBlock().getType().isSolid())
			result.add(loc4.clone().add(0, 0, -0.5).getBlock());
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_WEST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_WEST));
		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}

	public static ArrayList<Block> isOnGround4(Location location) {
		ArrayList<Block> result = new ArrayList<Block>();
		final Location loc4 = location.getBlock().getRelative(BlockFace.DOWN).getLocation();

		if (!loc4.getBlock().getRelative(BlockFace.SOUTH).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH));
		if (!loc4.getBlock().getRelative(BlockFace.EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.EAST));
		if (!loc4.getBlock().getRelative(BlockFace.WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.WEST));
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.SOUTH_WEST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_EAST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_EAST));
		if (!loc4.getBlock().getRelative(BlockFace.NORTH_WEST).isEmpty()
				&& loc4.getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid())
			result.add(loc4.getBlock().getRelative(BlockFace.NORTH_WEST));
		if (!loc4.getBlock().isEmpty() && loc4.getBlock().getType().isSolid())
			result.add(loc4.getBlock());

		if (result.isEmpty())
			return null;
		else
			return result;
	}
	
	public static boolean isOnGroundUltimate(Location player) {
		return isOnGroundUltimate(player, 0.2D);
	}
	
	public static boolean isOnGroundUltimate(Location player, double yDiff) {
		Location loc = player.clone().subtract(0, yDiff, 0);
		/* Not working correctly, somehow the loc is 0.55 dist from the block, it should be 0.3...
		 * Location[] b = new Location[] { loc.clone().subtract(0.35, 0, 0),
		 * loc.clone().subtract(0, 0, 0.35), loc.clone().add(0.35, 0, 0),
		 * loc.clone().add(0, 0, 0.35), loc.clone().add(1, 0, 1), loc.clone().add(-1, 0,
		 * 1), loc.clone().add(1, 0, -1), loc.clone().add(-1, 0, -1), };
		 */
		
//		Location[] b = new Location[] { loc.clone().subtract(1, 0, 0), loc.clone().subtract(0, 0, 1),
//				loc.clone().add(1, 0, 0), loc.clone().add(0, 0, 1), loc.clone().add(1, 0, 1),
//				loc.clone().add(-1, 0, 1), loc.clone().add(1, 0, -1), loc.clone().add(-1, 0, -1), };
		
		// Minecraft is somewhat inaccurate, should be 0.3D but because of this things gonna say 0.31D;
		double diff = 0.35D;
		Location[] b = new Location[] { loc, loc.clone().subtract(diff, 0, 0), loc.clone().subtract(0, 0, diff),
				loc.clone().add(diff, 0, 0), loc.clone().add(0, 0, diff), loc.clone().add(diff, 0, diff),
				loc.clone().add(-diff, 0, diff), loc.clone().add(diff, 0, -diff), loc.clone().add(-diff, 0, -diff) };
		for (Location blo : b) {
			Block bl = blo.getBlock();
			// Utils.debugMessage("AA: " + bl.getType() + " " + Utils.isSolid(bl) + " " + bl.getRelative(BlockFace.UP).getType() + " " + bl.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType());
			// Check also is the block is air, because if x block is air and x+1 is not air it is not detected
			if (isSolid(bl) || isClimable(bl.getType())) { // You can also jump on climable blocks
				if (!Utils.isSolid(bl.getRelative(BlockFace.UP).getType()) && !Utils.isSolid(bl.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType()) ||
						Utils.isPassableBlock(bl.getRelative(BlockFace.UP).getType()))
					return true;
			}
		}

		return false;
	}

	public static Location onBlockLoc(final Location locationUnderPlayer, final String mat) {
		if (locationUnderPlayer.clone().add(0.0, 0.0, 0.5).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(0.0, 0.0, 0.5).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(0.0, 0.0, 0.5);
		}
		if (locationUnderPlayer.clone().add(0.5, 0.0, 0.0).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(0.5, 0.0, 0.0).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(0.5, 0.0, 0.0);
		}
		if (locationUnderPlayer.clone().add(0.5, 0.0, 0.5).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(0.5, 0.0, 0.5).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(0.5, 0.0, 0.5);
		}
		if (locationUnderPlayer.clone().add(-0.5, 0.0, -0.5).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(-0.5, 0.0, -0.5).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(-0.5, 0.0, -0.5);
		}
		if (locationUnderPlayer.clone().add(-0.5, 0.0, 0.5).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(-0.5, 0.0, 0.5).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(-0.5, 0.0, 0.5);
		}
		if (locationUnderPlayer.clone().add(0.5, 0.0, -0.5).getBlock().getType().name().contains(mat)
				&& locationUnderPlayer.clone().add(0.5, 0.0, -0.5).getBlock().getRelative(BlockFace.UP).isEmpty()) {
			return locationUnderPlayer.clone().add(0.5, 0.0, -0.5);
		}
		return locationUnderPlayer;
	}

	private static boolean isInLiquidLoc(final Location loc) {
		return loc.getBlock().isLiquid() || loc.clone().subtract(0, 0.3, 0).getBlock().isLiquid()
				|| loc.clone().subtract(0, 0.1, 0).getBlock().isLiquid();
	}
	
	public static boolean isAroundLiquidLoc(CheatPlayer p,  Location loc) {
		boolean result = false;
		
		byte cacheResult = CacheSystem.isCached(p, CacheCheck.IS_AROUND_LIQUID_LOC, loc.getWorld().getFullTime(), Arrays.asList(loc.getBlock().hashCode()));
		boolean addCache = false;
		if(cacheResult == -1) {
			// CACHE MISS
			addCache = true;
		} else {
			// CACHE HIT
			return cacheResult == 1 ? true : false;
		}
		
		if(isInLiquidLoc(loc.getBlock().getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.EAST).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.WEST).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.NORTH).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.SOUTH).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.NORTH_EAST).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.SOUTH_EAST).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.NORTH_WEST).getLocation())
				|| isInLiquidLoc(loc.getBlock().getRelative(BlockFace.SOUTH_WEST).getLocation()))
			result = true;
		
		if(addCache)
			CacheSystem.addToCache(p, CacheCheck.IS_AROUND_LIQUID_LOC, loc.getWorld().getFullTime(), Arrays.asList(loc.getBlock().hashCode()), result);
		
		return result;
	}

	public static boolean isUnderBlock(final Player p) {
		final Location loc = p.getLocation().add(0.0, 2.5, 0.0);
		final Location b = getPlayerStandOnBlockLocationss(loc, Material.AIR);
		return b.getBlock().getType().isSolid() && !b.getBlock().getType().name().contains("LADDER")
				&& !b.getBlock().getType().name().contains("VINE");
	}

	public static Location getPlayerStandOnBlockLocationss(final Location locationUnderPlayer, final Material mat) {
		final Location b11 = locationUnderPlayer.clone().add(0.3, 0.0, -0.3);
		if (b11.getBlock().getType() != mat) {
			return b11;
		}
		final Location b12 = locationUnderPlayer.clone().add(-0.3, 0.0, -0.3);
		if (b12.getBlock().getType() != mat) {
			return b12;
		}
		final Location b13 = locationUnderPlayer.clone().add(0.3, 0.0, 0.3);
		if (b13.getBlock().getType() != mat) {
			return b13;
		}
		final Location b14 = locationUnderPlayer.clone().add(-0.3, 0.0, 0.3);
		if (b14.getBlock().getType() != mat) {
			return b14;
		}
		return locationUnderPlayer;
	}

	public static ArrayList<Material> getMaterialsAround2(final Location locationUnderPlayer) {
		ArrayList<Material> m = new ArrayList<Material>();

		if (locationUnderPlayer.getBlock().getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType());
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType() != Material.AIR)
			m.add(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType());

		return m;
	}

	public static boolean isNextToBlock(CheatPlayer p, final Location locationUnderPlayer, final Material mat) {
		byte cacheResult = CacheSystem.isCached(p, CacheCheck.IS_NEXT_TO_BLOCK, locationUnderPlayer.getWorld().getFullTime(), Arrays.asList(locationUnderPlayer.getBlock().hashCode(), mat));
		boolean addCache = false;
		if(cacheResult == -1) {
			// CACHE MISS
			addCache = true;
		} else {
			// CACHE HIT
			return cacheResult == 1 ? true : false;
		}
		
		boolean result = (locationUnderPlayer.getBlock().getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType() == mat) |
				(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType() == mat);
		
		if(addCache)
			CacheSystem.addToCache(p, CacheCheck.IS_NEXT_TO_BLOCK, locationUnderPlayer.getWorld().getFullTime(), Arrays.asList(locationUnderPlayer.getBlock().hashCode(), mat), result);
		
		return result;
	}
	
	public static boolean isNextToLiquidBlock(final Location locationUnderPlayer) {
		if (locationUnderPlayer.getBlock().isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).isLiquid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).isLiquid())
			return true;
		return locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).isLiquid();
	}

	public static boolean isNextToBlockNoDown(final Location locationUnderPlayer, final org.bukkit.Material mat) {
		Block b = locationUnderPlayer.getBlock().getRelative(BlockFace.UP);
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.EAST).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.WEST).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH_WEST).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH_WEST).isEmpty() && b.getType().isSolid())
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH_EAST).isEmpty() && b.getType().isSolid())
			return true;
		return locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH_EAST).isEmpty() && b.getType().isSolid();
	}
	
	public static boolean isNextToBlockByNameAround(CheatPlayer p, Block locationPlayer, String str, boolean up, boolean down) {
		byte cacheResult = CacheSystem.isCached(p, CacheCheck.IS_NEXT_TO_BLOCK_BY_NAME_AROUND, locationPlayer.getWorld().getFullTime(), Arrays.asList(locationPlayer.hashCode(), str, up, down));
		boolean addCache = false;
		if(cacheResult == -1) {
			// CACHE MISS
			addCache = true;
		} else {
			// CACHE HIT
			return cacheResult == 1 ? true : false;
		}
		
		boolean result = false;
		
		Block[] list = {locationPlayer};
		if(up && !down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.UP)};
		else if(!up && down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.DOWN)};
		else if(up && down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.UP), locationPlayer.getRelative(BlockFace.DOWN)};
		
		topper:
		for(Block b : list) {
			Block[] sides = {b.getRelative(BlockFace.SELF), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.WEST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.NORTH),
					b.getRelative(BlockFace.NORTH_EAST), b.getRelative(BlockFace.NORTH_WEST), b.getRelative(BlockFace.SOUTH_EAST), b.getRelative(BlockFace.SOUTH_WEST)};
			
			for(Block side : sides)
				if (side.getType().name().toLowerCase().contains(str.toLowerCase())) {
					result = true;
					break topper;
				}
		}
		
		if(addCache)
			CacheSystem.addToCache(p, CacheCheck.IS_NEXT_TO_BLOCK_BY_NAME_AROUND, locationPlayer.getWorld().getFullTime(), Arrays.asList(locationPlayer.hashCode(), str, up, down), result);
		
		return result;
	}
	
	public static boolean isNextToBlocksByNameAround(CheatPlayer p, Block locationPlayer, boolean up, boolean down, String... around) {
		byte cacheResult = CacheSystem.isCached(p, CacheCheck.IS_NEXT_TO_BLOCKS_BY_NAME_AROUND, locationPlayer.getWorld().getFullTime(), Arrays.asList(locationPlayer.hashCode(), up, down, around.hashCode()));
		boolean addCache = false;
		if(cacheResult == -1) {
			// CACHE MISS
			addCache = true;
		} else {
			// CACHE HIT
			return cacheResult == 1 ? true : false;
		}
		
		boolean result = false;
		
		Block[] list = {locationPlayer};
		if(up && !down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.UP)};
		else if(!up && down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.DOWN)};
		else if(up && down)
			list = new Block[] {locationPlayer, locationPlayer.getRelative(BlockFace.UP), locationPlayer.getRelative(BlockFace.DOWN)};
		
		topper:
		for(Block b : list) {
			Block[] sides = {b.getRelative(BlockFace.SELF), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.WEST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.NORTH),
					b.getRelative(BlockFace.NORTH_EAST), b.getRelative(BlockFace.NORTH_WEST), b.getRelative(BlockFace.SOUTH_EAST), b.getRelative(BlockFace.SOUTH_WEST)};
			
			for(Block side : sides)
				for(String str : around) {
					if (side.getType().name().toLowerCase().contains(str.toLowerCase())) {
						result = true;
						break topper;
					}
					
				}
		}
		
		if(addCache)
			CacheSystem.addToCache(p, CacheCheck.IS_NEXT_TO_BLOCKS_BY_NAME_AROUND, locationPlayer.getWorld().getFullTime(), Arrays.asList(locationPlayer.hashCode(), up, down, around.hashCode()), result);
		
		return result;
	}

	public static boolean isNextToBlock2ByName(final Location locationUnderPlayer, final String str,
			final double dist) {
		if (locationUnderPlayer.getBlock().getType().name().toLowerCase().contains(str))
			return true;
		if (locationUnderPlayer.clone().subtract(dist, 0, 0).getBlock().getType().name().toLowerCase().contains(str))
			return true;
		if (locationUnderPlayer.clone().subtract(0, 0, dist).getBlock().getType().name().toLowerCase().contains(str))
			return true;
		if (locationUnderPlayer.clone().add(dist, 0, 0).getBlock().getType().name().toLowerCase().contains(str))
			return true;
		return locationUnderPlayer.clone().add(0, 0, dist).getBlock().getType().name().toLowerCase().contains(str);
	}

	public static boolean isNextToBlockDifferent(final Location locationUnderPlayer, final org.bukkit.Material... mat) {
		List<Material> mats = Arrays.asList(mat);
		if (!mats.contains(locationUnderPlayer.getBlock().getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType()))
			return true;
		if (!mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType()))
			return true;
		return !mats.contains(locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType());
	}

	public static boolean isNextToBlockDifferentNoDown(final Location locationUnderPlayer,
			final org.bukkit.Material mat) {
		Block b = locationUnderPlayer.getBlock().getRelative(BlockFace.UP);
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.EAST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.WEST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH_WEST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH_WEST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().isSolid())
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.NORTH_EAST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType().isSolid())
			return true;
		return !locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().equals(mat)
				&& b.getRelative(BlockFace.SOUTH_EAST).isEmpty() && b.getType().isSolid()
				&& locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().isSolid();
	}

	public static boolean isNextToBlockDifferentNoDownNoChg(final Location locationUnderPlayer,
			final org.bukkit.Material mat) {
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().equals(mat))
			return true;
		if (!locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType().equals(mat))
			return true;
		return !locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().equals(mat);
	}

	public static boolean isNextToBlock2(final Location locationUnderPlayer, final Material mat, double violation) {
		if (locationUnderPlayer.getBlock().getType() == mat)
			return true;
		if (locationUnderPlayer.add(violation, 0, 0).getBlock().getType() == mat)
			return true;
		if (locationUnderPlayer.add(0, 0, violation).getBlock().getType() == mat)
			return true;
		if (locationUnderPlayer.subtract(violation, 0, 0).getBlock().getType() == mat)
			return true;
		return locationUnderPlayer.subtract(0, 0, violation).getBlock().getType() == mat;
	}

	public static boolean isNextToBlock2Different(final Location locationUnderPlayer, final Material mat, double d) {
		if (!(locationUnderPlayer.getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(d, 0, 0).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(0, 0, d).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.subtract(d, 0, 0).getBlock().getType() == mat))
			return true;
		else
			return !(locationUnderPlayer.subtract(0, 0, d).getBlock().getType() == mat);
	}

	public static boolean isNextToBlock2DifferentNoDown(final Location locationUnderPlayer, final Material mat,
			double d) {
		if (!(locationUnderPlayer.add(d, 0, 0).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(0, 0, d).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(-d, 0, 0).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(0, 0, -d).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(d, 0, d).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(-d, 0, d).getBlock().getType() == mat))
			return true;
		else if (!(locationUnderPlayer.add(d, 0, -d).getBlock().getType() == mat))
			return true;
		else
			return !(locationUnderPlayer.add(-d, 0, -d).getBlock().getType() == mat);
	}

	public static boolean isNextToBlock3CCDifferent(final Location locationUnderPlayer, final Material mat) {
		if (locationUnderPlayer.getBlock().getType() != mat)
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType() != mat && (!locationUnderPlayer
				.getBlock().getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP).getType().isSolid()))
			return false;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType() != mat
				&& (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP)
						.getType() == mat)
				|| !locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP).getType()
						.isSolid())
			return false;
		return locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType() == mat || (locationUnderPlayer
				.getBlock().getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP).getType().isSolid());
	}

	// WARNING: Not really usable, distance algorithm is bad, should be Chebishev dist, not Manhatan dist + block.getloc is not the center

	public static boolean isNextToBlock4(final Location locationUnderPlayer, final Material mat, double dist) {
		if (locationUnderPlayer.getBlock().getType() == mat
				&& getXZDisstance(locationUnderPlayer, locationUnderPlayer.getBlock().getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.EAST).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.WEST).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_WEST).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_WEST).getLocation()) <= dist)
			return true;
		if (locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.NORTH_EAST).getLocation()) <= dist)
			return true;
		return locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getType() == mat
				&& getXZDisstance(locationUnderPlayer,
						locationUnderPlayer.getBlock().getRelative(BlockFace.SOUTH_EAST).getLocation()) <= dist;
	}

	public static PotionEffect getPotionEffect(final Player p, final PotionEffectType type) {
		PotionEffect effect = null;
		final Iterator<PotionEffect> iterator = p.getActivePotionEffects().iterator();
		while (iterator.hasNext()) {
			effect = iterator.next();
			if (effect.getType() == type) {
				break;
			}
		}
		return effect;
	}

	public static boolean isUsingPotionEffect(final Player p, final PotionEffectType type) {
		for (final PotionEffect pf : p.getActivePotionEffects()) {
			if (pf.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingPotionEffect(final Collection<PotionEffect> effects, final PotionEffectType type) {
		if (effects == null)
			return false;
		for (final PotionEffect pf : effects) {
			if (pf.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingPotionEffect(final Collection<PotionEffect> effects, final String type) {
		if (effects == null)
			return false;
		for (final PotionEffect pf : effects) {
			if (pf.getType().getName().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingPotionEffect(final Player p, final String type) {
		for (final PotionEffect pf : p.getActivePotionEffects()) {
			if (pf.getType().getName().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	// {-1, 0, 1} = 9 chunks {-2,-1, 0, 1, 2} = 25 chunks
	public static ArrayList<Chunk> getChunksAroundPlayer(Player player) {
		int[] offset = { 0, 1, -1, 2, -2 };

		World world = player.getWorld();
		int baseX = player.getLocation().getChunk().getX();
		int baseZ = player.getLocation().getChunk().getZ();

		ArrayList<Chunk> chunksAroundPlayer = new ArrayList<>();
		for (int x : offset) {
			for (int z : offset) {
				Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
				chunksAroundPlayer.add(chunk);
			}
		}
		return chunksAroundPlayer;
	}

	public static boolean isOnHalfSlab(final Location to) {
		final Location loc = to.clone();
		if (onBlockLoc(loc, "STEP").getBlock().getType().name().contains("STEP")
				|| onBlockLoc(loc, "SLAB").getBlock().getType().name().contains("SLAB")
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc).getBlock().getType().name().contains(
		 * "SNOW")
		 */) {
			return true;
		}
		final Location loc2 = to.clone().subtract(0.0, 1.0, 0.0);
		if (onBlockLoc(loc2, "STEP").getBlock().getType().name().contains("STEP")
				|| onBlockLoc(loc2, "SLAB").getBlock().getType().name().contains("SLAB")
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains(
		 * "SNOW")
		 */) {
			return true;
		}
		final Location loc3 = to.clone().subtract(0.0, 0.5, 0.0);
		return onBlockLoc(loc3, "STEP").getBlock().getType().name().contains("STEP")
				|| onBlockLoc(loc3, "SLAB").getBlock().getType().name().contains("SLAB")
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains(
		 * "SNOW")
		 */;
	}

	public static boolean isOnHalfSlab2(final Location to) {
		final Location loc = to.clone();
		if (onBlockLoc(loc, "STEP").getBlock().getType().name().contains("STEP")
				|| onBlockLoc(loc, "SLAB").getBlock().getType().name().contains("SLAB"))
			return true;

		final Location loc2 = to.clone().subtract(0.0, 0.8, 0.0);
		if (getMaterialsAroundByName(loc2, "STEP") || getMaterialsAroundByName(loc2, "SLAB"))
			return true;

		final Location loc3 = to.clone().subtract(0.0, 0.5, 0.0);
		if (getMaterialsAroundByName(loc3, "STEP") || getMaterialsAroundByName(loc3, "SLAB"))
			return true;

		final Location loc4 = to.clone().subtract(0.0, 0.2, 0.0);
		return getMaterialsAroundByName(loc4, "STEP") || getMaterialsAroundByName(loc4, "SLAB");
	}

	public static boolean isOnBlock(final Player p, final String matName) {
		final Location loc2 = p.getLocation().subtract(0.0, 1.0, 0.0);
		if (onBlockLoc(loc2, matName).getBlock().getType().name().contains(matName)
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains(
		 * "SNOW")
		 */) {
			return true;
		}
		final Location loc3 = p.getLocation().subtract(0.0, 0.5, 0.0);
		if (onBlockLoc(loc3, matName).getBlock().getType().name().contains(matName)
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains(
		 * "SNOW")
		 */) {
			return true;
		}
		final Location loc4 = p.getLocation().subtract(0.0, 0.2, 0.0);
		return onBlockLoc(loc4, matName).getBlock().getType().name().contains(matName)
		/*
		 * ||
		 * getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains(
		 * "SNOW")
		 */;
	}

	public static boolean isOnSnow(final Player p) {
		final Location loc = p.getLocation();
		if (getPlayerStandOnBlockLocationSnow(loc).getBlock().getType().name().contains("SNOW")) {
			return true;
		}
		final Location loc2 = p.getLocation().subtract(0.0, 1.0, 0.0);
		if (getPlayerStandOnBlockLocationSnow(loc2).getBlock().getType().name().contains("SNOW")) {
			return true;
		}
		final Location loc3 = p.getLocation().subtract(0.0, 0.5, 0.0);
		if (getPlayerStandOnBlockLocationSnow(loc3).getBlock().getType().name().contains("SNOW")) {
			return true;
		}
		final Location loc4 = p.getLocation().subtract(0.0, 0.2, 0.0);
		return getPlayerStandOnBlockLocationSnow(loc4).getBlock().getType().name().contains("SNOW");
	}
	
	public static boolean isOnSnowUltimate(Location loc, boolean yOffset) {
		if (getPlayerStandOnBlockLocationSnow(loc).getBlock().getType().name().contains("SNOW")) {
			return true;
		}
		if(yOffset) {
			final Location loc4 = loc.clone().subtract(0.0, 0.2, 0.0);
			return getPlayerStandOnBlockLocationSnow(loc4).getBlock().getType().name().contains("SNOW");
		}
		
		return false;
	}

	public static boolean isOnStair(final Player p) {
		final Location loc4 = p.getLocation().subtract(0.0, 0.0, 0.0);
		if (onBlockLoc(loc4, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc5 = p.getLocation().subtract(0.0, 0.01, 0.0);
		if (onBlockLoc(loc5, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc6 = p.getLocation().subtract(0.0, 0.5, 0.0);
		if (onBlockLoc(loc6, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc7 = p.getLocation().subtract(0.0, 1.0, 0.0);
		return onBlockLoc(loc7, "STAIR").getBlock().getType().name().contains("STAIR");
	}

	public static boolean isOnStair(final Location l) {
		final Location loc1 = l.clone();
		final Location loc4 = loc1.subtract(0.0, 0.0, 0.0);
		if (onBlockLoc(loc4, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc5 = loc1.subtract(0.0, 0.01, 0.0);
		if (onBlockLoc(loc5, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc6 = loc1.subtract(0.0, 0.5, 0.0);
		if (onBlockLoc(loc6, "STAIR").getBlock().getType().name().contains("STAIR")) {
			return true;
		}
		final Location loc7 = loc1.subtract(0.0, 1.0, 0.0);
		return onBlockLoc(loc7, "STAIR").getBlock().getType().name().contains("STAIR");
	}

	public static Location getPlayerStandOnBlockLocationSnow(final Location locationUnderPlayer) {
		final Location b11 = locationUnderPlayer.clone().add(0.7, 0.0, -0.7);
		if (b11.getBlock().getType().name().contains("SNOW")) {
			return b11;
		}
		final Location b12 = locationUnderPlayer.clone().add(-0.7, 0.0, -0.7);
		if (b12.getBlock().getType().name().contains("SNOW")) {
			return b12;
		}
		final Location b13 = locationUnderPlayer.clone().add(0.7, 0.0, 0.7);
		if (b13.getBlock().getType().name().contains("SNOW")) {
			return b13;
		}
		final Location b14 = locationUnderPlayer.clone().add(-0.7, 0.0, 0.7);
		if (b14.getBlock().getType().name().contains("SNOW")) {
			return b14;
		}
		final Location b15 = locationUnderPlayer.clone().add(-0.7, 0.0, 0);
		if (b15.getBlock().getType().name().contains("SNOW")) {
			return b15;
		}
		final Location b16 = locationUnderPlayer.clone().add(0.7, 0.0, 0);
		if (b16.getBlock().getType().name().contains("SNOW")) {
			return b16;
		}
		final Location b17 = locationUnderPlayer.clone().add(0, 0.0, -0.7);
		if (b17.getBlock().getType().name().contains("SNOW")) {
			return b17;
		}
		final Location b18 = locationUnderPlayer.clone().add(0, 0.0, 0.7);
		if (b18.getBlock().getType().name().contains("SNOW")) {
			return b18;
		}
		return locationUnderPlayer;
	}

	public static List<Block> getBlocksInRadius(Block start, int radius) {
		int iterations = (radius * 2) + 1;
		List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					blocks.add(start.getRelative(x, y, z));
				}
			}
		}
		return blocks;
	}

	public static Entity getClosestEntity(Location center, double radius) {
		Entity closestEntity = null;
		double closestDistance = Double.MAX_VALUE;

		for (Entity entity : center.getWorld().getChunkAt(center).getEntities()) {
			double distance = entity.getLocation().distanceSquared(center);
			if (distance < closestDistance && entity instanceof Player) {
				closestDistance = distance;
				closestEntity = entity;
			}
		}

		return closestEntity;
	}

	public static boolean isOnIce(final Player p) {
		final List<Material> materials = getMaterialsAround(p.getLocation().clone().add(0.0, -0.01, 0.0));
		return materials.contains(Material.ICE) || materials.contains(Material.PACKED_ICE);
	}

	public static List<Material> getMaterialsAround(final Location loc) {
		final List<Material> result = new ArrayList<Material>();
		result.add(loc.getBlock().getType());
		for (double d = 0.2; d <= 0.8; d += 0.2) {
			result.add(loc.clone().add(d, 0.0, -d).getBlock().getType());
			result.add(loc.clone().add(-d, 0.0, -d).getBlock().getType());
			result.add(loc.clone().add(d, 0.0, d).getBlock().getType());
			result.add(loc.clone().add(-d, 0.0, d).getBlock().getType());
			result.add(loc.clone().add(0.0, 0.0, d).getBlock().getType());
			result.add(loc.clone().add(-d, 0.0, 0.0).getBlock().getType());
			result.add(loc.clone().add(d, 0.0, 0.0).getBlock().getType());
			result.add(loc.clone().add(0.0, 0.0, -d).getBlock().getType());
		}
		return result;
	}

	public static List<Material> getMaterialsAround3(final Location loc, double d) {
		final List<Material> result = new ArrayList<Material>();
		result.add(loc.getBlock().getType());
		result.add(loc.clone().add(d, 0.0, -d).getBlock().getType());
		result.add(loc.clone().add(-d, 0.0, -d).getBlock().getType());
		result.add(loc.clone().add(d, 0.0, d).getBlock().getType());
		result.add(loc.clone().add(-d, 0.0, d).getBlock().getType());
		result.add(loc.clone().add(0.0, 0.0, d).getBlock().getType());
		result.add(loc.clone().add(-d, 0.0, 0.0).getBlock().getType());
		result.add(loc.clone().add(d, 0.0, 0.0).getBlock().getType());
		result.add(loc.clone().add(0.0, 0.0, -d).getBlock().getType());

		return result;
	}

	public static boolean getMaterialsAroundByName(final Location loc, String block) {
		for (Material m : getMaterialsAround(loc)) {
			if (m.name().contains(block))
				return true;
		}
		return false;
	}

	public static boolean getMaterialsAround3ByName(final Location loc, double d, String block) {
		for (Material m : getMaterialsAround3(loc, d)) {
			if (m.name().contains(block))
				return true;
		}
		return false;
	}

	public static List<Block> getBlocksAround(final Location loc) {
		final List<Block> result = new ArrayList<Block>();
		result.add(loc.getBlock());

		result.add(loc.getBlock().getRelative(BlockFace.EAST));
		result.add(loc.getBlock().getRelative(BlockFace.WEST));
		result.add(loc.getBlock().getRelative(BlockFace.NORTH));
		result.add(loc.getBlock().getRelative(BlockFace.SOUTH));
		result.add(loc.getBlock().getRelative(BlockFace.NORTH_EAST));
		result.add(loc.getBlock().getRelative(BlockFace.NORTH_WEST));
		result.add(loc.getBlock().getRelative(BlockFace.SOUTH_EAST));
		result.add(loc.getBlock().getRelative(BlockFace.SOUTH_WEST));

		return result;
	}
	
	public static boolean getBlocksAroundByName(final Location loc, String block) {
		for (Block b: getBlocksAround(loc)) {
			if (b.getType().name().contains(block))
				return true;
		}
		return false;
	}

	public static List<Material> getBlocksAround2(final Location loc, double i) {
		final List<Material> result = new ArrayList<Material>();
		result.add(loc.getBlock().getType());

		result.add(loc.clone().add(i, 0.0, i).getBlock().getType());
		result.add(loc.clone().add(-i, 0.0, i).getBlock().getType());
		result.add(loc.clone().add(-i, 0.0, -i).getBlock().getType());
		result.add(loc.clone().add(i, 0.0, -i).getBlock().getType());
		result.add(loc.clone().add(0.0, 0.0, i).getBlock().getType());
		result.add(loc.clone().add(-i, 0.0, 0.0).getBlock().getType());
		result.add(loc.clone().add(i, 0.0, 0.0).getBlock().getType());
		result.add(loc.clone().add(0.0, 0.0, -i).getBlock().getType());

		return result;
	}

	/**
	 * Get all the blocks around the player within radius
	 *
	 * @param loc, radius
	 * @return
	 */
	public static List<Material> getBlocksAroundX(final Location loc, double radius, double d) {
		final List<Material> result = new ArrayList<Material>();
		result.add(loc.getBlock().getType());
		for (double i = 0; i <= radius; i += d) {
			result.add(loc.clone().add(i, 0.0, i).getBlock().getType());
			result.add(loc.clone().add(-i, 0.0, i).getBlock().getType());
			result.add(loc.clone().add(-i, 0.0, -i).getBlock().getType());
			result.add(loc.clone().add(i, 0.0, -i).getBlock().getType());
			result.add(loc.clone().add(0.0, 0.0, i).getBlock().getType());
			result.add(loc.clone().add(-i, 0.0, 0.0).getBlock().getType());
			result.add(loc.clone().add(i, 0.0, 0.0).getBlock().getType());
			result.add(loc.clone().add(0.0, 0.0, -i).getBlock().getType());
		}
		return result;
	}

	public static List<Material> getBlocksAroundY(final Location loc, double radius, double d) {
		final List<Material> result = new ArrayList<Material>();
		result.add(loc.getBlock().getType());
		for (double i = 0; i <= radius; i += d) {
			result.add(loc.clone().add(0, i, 0).getBlock().getType());
			result.add(loc.clone().add(0, -i, 0).getBlock().getType());
		}

		return result;
	}

	public static boolean getBlocksAroundYisAroundByName(final Location loc, double radius, double d, String material) {
		List<Material> list = getBlocksAroundY(loc, radius, d);
		for (Material m : list) {
			if (m.name().contains(material))
				return true;
		}

		return false;
	}

	// KillAuraUtils
	public static float[] getRotationsNeeded(Player p, Entity entity) {
		if (entity == null)
			return null;
		double diffX = entity.getLocation().getX() - p.getLocation().getX();
		double diffY;
		if (entity instanceof LivingEntity) {
			LivingEntity entityLivingBase = (LivingEntity) entity;
			diffY = entityLivingBase.getLocation().getY() + entityLivingBase.getEyeHeight() * 0.9
					- (p.getLocation().getY() + p.getEyeHeight());
		} else
			diffY = (entity.getLocation().getY() + entity.getLocation().getY()) / 2.0D
					- (p.getLocation().getY() + p.getEyeHeight());
		double diffZ = entity.getLocation().getZ() - p.getLocation().getZ();
		double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] { p.getLocation().getYaw() + wrapAngleTo180(yaw - p.getLocation().getYaw()),
				p.getLocation().getPitch() + wrapAngleTo180(pitch - p.getLocation().getPitch()) };

	}

	private static float wrapAngleTo180(float f) {
		f %= 360.0F;
		if (f >= 180.0F) {
			f -= 360.0F;
		}

		if (f < -180.0F) {
			f += 360.0F;
		}

		return f;
	}

	// MathUtils
	public static double getXZDisstance(Location one, Location two) {
		Location o = one.clone();
		Location t = two.clone();
		o.setY(0);
		t.setY(0);
		return o.distance(t);
	}

	public static double getYDisstance(Location one, Location two) {
		return Math.abs(Math.abs(one.getY()) - Math.abs(two.getY()));
	}

	public static double get3DDisstance(Location one, Location two) {
		double sum = 0;

		sum += Math.pow((Math.max(one.getX(), two.getX()) - Math.min(one.getX(), two.getX())), 2);
		sum += Math.pow((Math.max(one.getY(), two.getY()) - Math.min(one.getY(), two.getY())), 2);
		sum += Math.pow((Math.max(one.getZ(), two.getZ()) - Math.min(one.getZ(), two.getZ())), 2);

		return Math.sqrt(sum);
	}

	public static double get3DXZDisstance(Location one, Location two) {
		Location one1 = one.clone();
		one1.setY(0);
		Location two1 = two.clone();
		two1.setY(0);

		return get3DDisstance(one1, two1);
	}

	public static double getYawAngle(Location to, Location from, Vector p) {
		Vector v1 = getVector(from, to);
		p.setY(0);
		v1.setY(0);

		double i = v1.angle(p);
		return i * 90;
	}

	public static double getAngle(Location to, Location from, Vector p) {
		double i = getVector(from, to).angle(p);
		return i * 90;
	}

	public static Vector getVector(Location from, Location to) {
		Location v = from.clone();
		Location v1 = to.clone();
		Vector v2 = (v1.subtract(v)).toVector().normalize();

		return v2;
	}

	public static double getAngle2(Vector one, Vector two) {
		double a = Math.acos(one.dot(two));
		double result = Math.toDegrees(a);

		return result;
	}

	public static double distance3D(double x, double z, double y) {
		double x2, z2, y2;

		x2 = x * x;
		z2 = z * z;
		y2 = y * y;

		return Math.sqrt(x2 + z2 + y2);
	}

	public static double distance3D(Location p, Location o) {
		double x2, z2, y2, x3, z3, y3;

		x2 = p.getX() * p.getX();
		z2 = p.getZ() * p.getZ();
		y2 = p.getY() * p.getY();

		x3 = o.getX() * o.getX();
		z3 = o.getZ() * o.getZ();
		y3 = o.getY() * o.getY();

		double distSq = (x2 + z2 + y2) - (x3 + z3 + y3);

		return distSq < 0 ? Math.sqrt((x3 + z3 + y3) - (x2 + z2 + y2)) : Math.sqrt((x2 + z2 + y2) - (x3 + z3 + y3));
	}

	public static double max(double... a) {
		double maxValue = a[0];
		for (double i : a) {
			if (i > maxValue) {
				maxValue = i;
			}
		}

		return maxValue;

	}

	public static double min(double... a) {
		double minValue = a[0];
		for (double i : a) {
			if (i < minValue) {
				minValue = i;
			}
		}

		return minValue;

	}

	// INNACURATE !!!
	public static float calculateBowForce(long l) {
		long ticks = ((l * 20) / 950) + 3;
		float f = ticks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		f = f > 1.0F ? 1.0F : f;
		return f;
	}

	public static float getYaw(Vector vector) {
		double dx = vector.getX();
		double dz = vector.getZ();
		double yaw = 0;
		// Set yaw
		if (dx != 0) {
			// Set yaw start value based on dx
			if (dx < 0) {
				yaw = 1.5 * Math.PI;
			} else {
				yaw = 0.5 * Math.PI;
			}
			yaw -= Math.atan(dz / dx);
		} else if (dz < 0) {
			yaw = Math.PI;
		}
		return (float) (-yaw * 180 / Math.PI);
	}

	// Returns True if collision is detected
	// Returns False is no collision is detected
	public static boolean checkCollision(Player p) {
		// In 1.14 spigot has BoundingBox api
		// p.getBoundingBox()

		if (getServerVersion() < 2)
			return false;

		try {
			for (Entity e : p.getWorld().getNearbyEntities(p.getPlayer().getLocation(), 3, 3, 3)) {
				if (e == p)
					continue;

				final Class<?> craftEntity = Class.forName(getServerClass() + ".entity.CraftEntity");
				final Object converted = craftEntity.cast(p);
				final Method handle = converted.getClass().getMethod("getHandle");
				final Object entityPlayer = handle.invoke(converted);
				final Method boundingBoxMethod = entityPlayer.getClass().getMethod("getBoundingBox");
				final Object boundingBox = boundingBoxMethod.invoke(entityPlayer);

				final Class<?> craftEntity1 = Class.forName(getServerClass() + ".entity.CraftEntity");
				final Object converted1 = craftEntity1.cast(e);
				final Method handle1 = converted1.getClass().getMethod("getHandle");
				final Object entityPlayer1 = handle1.invoke(converted1);
				final Method boundingBoxMethod1 = entityPlayer1.getClass().getMethod("getBoundingBox");
				final Object boundingBox1 = boundingBoxMethod1.invoke(entityPlayer);

				final Method collide = boundingBox.getClass().getMethod("b", boundingBox.getClass());
				final Object collision = collide.invoke(boundingBox, boundingBox1);
				return (Boolean) collision;
			}
		} catch (Exception ex) {

			for (Entity e : p.getWorld().getNearbyEntities(p.getPlayer().getLocation(), 3, 3, 3)) {
				if (e == p)
					continue;

				if (getServerVersion() >= 7) {
					return p.getBoundingBox().contains(e.getBoundingBox());
				} else {
					try {
						final Class<?> craftEntity = Class.forName(getServerClass() + ".entity.CraftEntity");
						final Object converted = craftEntity.cast(p);
						final Method handle = converted.getClass().getMethod("getHandle");
						final Object entityPlayer = handle.invoke(converted);
						final Method boundingBoxMethod = entityPlayer.getClass().getMethod("getBoundingBox");
						final Object boundingBox = boundingBoxMethod.invoke(entityPlayer);

						final Class<?> craftEntity1 = Class.forName(getServerClass() + ".entity.CraftEntity");
						final Object converted1 = craftEntity1.cast(e);
						final Method handle1 = converted1.getClass().getMethod("getHandle");
						final Object entityPlayer1 = handle1.invoke(converted1);
						final Method boundingBoxMethod1 = entityPlayer1.getClass().getMethod("getBoundingBox");
						final Object boundingBox1 = boundingBoxMethod1.invoke(entityPlayer1);

						final Method collide = boundingBox.getClass().getMethod("c", boundingBox.getClass());
						final Object collision = collide.invoke(boundingBox, boundingBox1);

						return (Boolean) collision;
					} catch (Exception ex1) {
						ex1.printStackTrace();
					}
				}
			}
		}

		return false;
	}
	
	public static boolean checkCollision_TEST_A(CheatPlayer p) {
		// In 1.14 spigot has BoundingBox api
		// p.getBoundingBox()
		
		Object lockObj = new Object();
		
		synchronized (lockObj) {
			if(!Taka.getThisPlugin().getServer().isPrimaryThread()) {
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (getServerVersion() < 2)
							p.setSCH(false);

						try {
							for (Entity e : p.getPlayer().getWorld().getNearbyEntities(p.getPlayer().getLocation(), 3, 3, 3)) {
								if (e == p)
									continue;

								final Class<?> craftEntity = Class.forName(getServerClass() + ".entity.CraftEntity");
								final Object converted = craftEntity.cast(p);
								final Method handle = converted.getClass().getMethod("getHandle");
								final Object entityPlayer = handle.invoke(converted);
								final Method boundingBoxMethod = entityPlayer.getClass().getMethod("getBoundingBox");
								final Object boundingBox = boundingBoxMethod.invoke(entityPlayer);

								final Class<?> craftEntity1 = Class.forName(getServerClass() + ".entity.CraftEntity");
								final Object converted1 = craftEntity1.cast(e);
								final Method handle1 = converted1.getClass().getMethod("getHandle");
								final Object entityPlayer1 = handle1.invoke(converted1);
								final Method boundingBoxMethod1 = entityPlayer1.getClass().getMethod("getBoundingBox");
								final Object boundingBox1 = boundingBoxMethod1.invoke(entityPlayer);

								final Method collide = boundingBox.getClass().getMethod("b", boundingBox.getClass());
								final Object collision = collide.invoke(boundingBox, boundingBox1);
								p.setSCH((Boolean) collision);
							}
						} catch (Exception ex) {

							for (Entity e : p.getPlayer().getWorld().getNearbyEntities(p.getPlayer().getLocation(), 3, 3, 3)) {
								if (e == p)
									continue;

								if (getServerVersion() >= 7) {
									p.setSCH(p.getPlayer().getBoundingBox().contains(e.getBoundingBox()));
								} else {
									try {
										final Class<?> craftEntity = Class.forName(getServerClass() + ".entity.CraftEntity");
										final Object converted = craftEntity.cast(p);
										final Method handle = converted.getClass().getMethod("getHandle");
										final Object entityPlayer = handle.invoke(converted);
										final Method boundingBoxMethod = entityPlayer.getClass().getMethod("getBoundingBox");
										final Object boundingBox = boundingBoxMethod.invoke(entityPlayer);

										final Class<?> craftEntity1 = Class.forName(getServerClass() + ".entity.CraftEntity");
										final Object converted1 = craftEntity1.cast(e);
										final Method handle1 = converted1.getClass().getMethod("getHandle");
										final Object entityPlayer1 = handle1.invoke(converted1);
										final Method boundingBoxMethod1 = entityPlayer1.getClass().getMethod("getBoundingBox");
										final Object boundingBox1 = boundingBoxMethod1.invoke(entityPlayer1);

										final Method collide = boundingBox.getClass().getMethod("c", boundingBox.getClass());
										final Object collision = collide.invoke(boundingBox, boundingBox1);

										p.setSCH(((Boolean) collision));
									} catch (Exception ex1) {
										ex1.printStackTrace();
									}
								}
							}
						}
						
						Taka.getThisPlugin().getServer().broadcastMessage("SPEED SET SCH");
					}
				}.runTask(Taka.getThisPlugin());
			}
		}
		
		synchronized (lockObj) {
			Taka.getThisPlugin().getServer().broadcastMessage("SPEED GET SCH");
			return p.getSCH();
		}

		
	}

	// MaterialUtils
	public static boolean isFood(Material m) {
		switch (XMaterial.matchXMaterial(m)) {
		case APPLE:
		case BAKED_POTATO:
		case BEEF:
		case BREAD:
		case CARROT:
		case CARROTS:
		case COOKED_BEEF:
		case COOKED_COD:
		case COOKED_CHICKEN:
		case COOKED_MUTTON:
		case COOKED_PORKCHOP:
		case COOKED_RABBIT:
		case COOKED_SALMON:
		case COOKIE:
		case CHICKEN:
		case ENCHANTING_TABLE:
		case GOLDEN_APPLE:
		case GOLDEN_CARROT:
		case MELON:
		case MUSHROOM_STEW:
		case MUTTON:
		case POISONOUS_POTATO:
		case POTATO:
		case POTATOES:
		case PUFFERFISH:
		case PUMPKIN:
		case PUMPKIN_PIE:
		case RABBIT:
		case RABBIT_STEW:
		case ROTTEN_FLESH:
		case SPIDER_EYE:
			return true;
		}
		
		if (getServerVersion() >= 2) {
			switch (XMaterial.matchXMaterial(m)) {
			case BEETROOT:
			case BEETROOTS:
			case BEETROOT_SOUP:
			case CHORUS_FRUIT:
				return true;
			}
		} if(getServerVersion() >= 6) {
			switch (XMaterial.matchXMaterial(m)) {
			case DRIED_KELP:
				return true;
			}
		} if(getServerVersion() >= 7) {
			switch (XMaterial.matchXMaterial(m)) {
			case SUSPICIOUS_STEW:
			case SWEET_BERRIES:
				return true;
			}
		}
		
		return false;
	}

	public static boolean isSword(Material m) {
		try {
			switch(XMaterial.matchXMaterial(m)) {
			case WOODEN_SWORD:
			case STONE_SWORD:
			case IRON_SWORD:
			case GOLDEN_SWORD:
			case DIAMOND_SWORD:
				return true;
			}
			
			if(getServerVersion() >= 9) {
				if(m.equals(Material.NETHERITE_SWORD))
					return true;
			}
		} catch(Exception e) { return false; }
		
		return false;
	}
	
	public static boolean isBlockable(Material m) {
		switch(XMaterial.matchXMaterial(m)) {
		case WOODEN_SWORD:
		case STONE_SWORD:
		case IRON_SWORD:
		case GOLDEN_SWORD:
		case DIAMOND_SWORD:
		case NETHERITE_SWORD:
		case SHIELD:
			return true;
		}
		
		return false;
	}
	
	// TODO: Potion?
	public static boolean isUsable(Material m) {
		return isSword(m) || isFood(m) || isArmor(m) || m == Material.BOW;
	}
	
	public static boolean isClickableBlock(Material click) {
		return click.name().contains("CHEST") || click.name().contains("FENCE_GATE") || click.name().contains("DOOR")
				|| click.name().contains("REDSTONE") || click.name().contains("BUTTON") || click.name().contains("BED")
				|| click.name().contains("LEVER") || click.equals(XMaterial.FARMLAND.parseMaterial()) || click.name().contains("DAYLIGHT_DETECTOR")
				|| click.name().contains("FLOWER_POT") || click.equals(XMaterial.NOTE_BLOCK.parseMaterial());
	}

	public static boolean isSolid2(Block b) {
		return b.getY() == 1.0 && b.getType().isSolid();
	}

	public static boolean isSolid(Material m) {
		return m.isSolid() // && !m.name().contains("BED") && !m.name().contains("DOOR") && !m.name().contains("CHEST") Whyy? :O
				&& !m.equals(Material.DEAD_BUSH);
	}

	public static boolean isSolid(Block b) {
		return isSolid(b.getType());
	}

	public static boolean isClimable(Material m) {
		if (getServerVersion() > 7)
			return m.equals(Material.LADDER) || m.equals(Material.VINE) || m.equals(Material.SCAFFOLDING);
		return m.equals(Material.LADDER) || m.equals(Material.VINE);
	}

	public static boolean isArmor(Material m) {
		try {
			switch(XMaterial.matchXMaterial(m)) {
			case LEATHER_HELMET:
			case CHAINMAIL_HELMET:
			case IRON_HELMET:
			case GOLDEN_HELMET:
			case DIAMOND_HELMET:
			case LEATHER_CHESTPLATE:
			case CHAINMAIL_CHESTPLATE:
			case IRON_CHESTPLATE:
			case GOLDEN_CHESTPLATE:
			case DIAMOND_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case CHAINMAIL_LEGGINGS:
			case IRON_LEGGINGS:
			case GOLDEN_LEGGINGS:
			case DIAMOND_LEGGINGS:
			case LEATHER_BOOTS:
			case CHAINMAIL_BOOTS:
			case IRON_BOOTS:
			case GOLDEN_BOOTS:
			case DIAMOND_BOOTS:
				return true;
			}
			
			if(getServerVersion() >= 6) {
				switch (m) {
				case TURTLE_HELMET:
					return true;
				}
			}
			
			if(getServerVersion() >= 9) {
				switch (m) {
				case NETHERITE_HELMET:
				case NETHERITE_CHESTPLATE:
				case NETHERITE_LEGGINGS:
				case NETHERITE_BOOTS:
					return true;
				}
			}
		} catch(Exception e) { return false; }
		
		return false;
	}
	
	// TODO: Crit damage 1,8 - good, but what if some plugins change crit damage? 1.16 crit damage the same as 1.8 crit damage
	public static double getCritical(final Player p) {
		double critical = 1.5;
		
		switch(XMaterial.matchXMaterial(p.getItemInHand().getType())) {
		case WOODEN_SWORD:
			critical *= 5.0;
			break;
		case STONE_SWORD:
			critical *= 6.0;
			break;
		case IRON_SWORD:
			critical *= 7.0;
			break;
		case GOLDEN_SWORD:
			critical *= 5.0;
			break;
		case DIAMOND_SWORD:
			critical *= 8.0;
			break;
		case WOODEN_AXE:
			critical *= 4.0;
			break;
		case STONE_AXE:
			critical *= 5.0;
			break;
		case IRON_AXE:
			critical *= 6.0;
			break;
		case GOLDEN_AXE:
			critical *= 4.0;
			break;
		case DIAMOND_AXE:
			critical *= 7.0;
			break;
		case WOODEN_PICKAXE:
			critical *= 3.0;
			break;
		case STONE_PICKAXE:
			critical *= 4.0;
			break;
		case IRON_PICKAXE:
			critical *= 5.0;
			break;
		case GOLDEN_PICKAXE:
			critical *= 3.0;
			break;
		case DIAMOND_PICKAXE:
			critical *= 6.0;
			break;
		case WOODEN_HOE:
			critical *= 2.0;
			break;
		case STONE_HOE:
			critical *= 3.0;
			break;
		case IRON_HOE:
			critical *= 4.0;
			break;
		case GOLDEN_HOE:
			critical *= 2.0;
			break;
		case DIAMOND_HOE:
			critical *= 5.0;
			break;
		case WOODEN_SHOVEL:
			critical *= 2.0;
			break;
		case STONE_SHOVEL:
			critical *= 3.0;
			break;
		case IRON_SHOVEL:
			critical *= 4.0;
			break;
		case GOLDEN_SHOVEL:
			critical *= 2.0;
			break;
		case DIAMOND_SHOVEL:
			critical *= 5.0;
			break;
		}
		
		return critical;
	}

	// ReflectionUtils
	public static void forceOpenBook(ItemStack i, Player p) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ItemStack s = p.getItemInHand();
		p.setItemInHand(i);
		if (getServerVersion() >= 2) {
			Class<?> entityPlayer = Class.forName(getServerClass() + ".entity.CraftPlayer");
			Method a = entityPlayer.getClass().getMethod("a",
					Class.forName(getServerClass() + ".inventory.CraftItemStack"),
					Class.forName(getServerNMSClass() + ".EnumHand"));
			Class<?> craftPlayer = Class.forName(getServerClass() + ".entity.CraftPlayer");
			Object converted = craftPlayer.cast(p);
			Method getHandle = converted.getClass().getMethod("getHandle");
			Object entityplayer = getHandle.invoke(p);
			Class<?> enumHand = Class.forName(getServerClass() + ".EnumHand");
			Object[] enumArray = enumHand.getEnumConstants();
			a.invoke(entityplayer, getItemStack(i), enumArray[0]);
		} else {
			Class<?> craftPlayer = Class.forName(getServerClass() + ".entity.CraftPlayer");
			Object converted = craftPlayer.cast(p);
			Method getHandle = converted.getClass().getMethod("getHandle");
			Object entityplayer = getHandle.invoke(p);
			Method mes = entityplayer.getClass().getMethod("openBook",
					Class.forName(getServerNMSClass() + getServerVersionRF() + ".ItemStack"));
			mes.invoke(entityplayer, getItemStack(i));
		}
		p.setItemInHand(s);
	}

	private static Object getItemStack(ItemStack item) {
		try {
			Method asNMSCopy = Class.forName(getServerClass() + ".inventory.CraftItemStack").getMethod("asNMSCopy",
					ItemStack.class);
			return asNMSCopy.invoke(Class.forName(getServerClass() + ".inventory.CraftItemStack"), item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getServerClass() {
		return Taka.getThisPlugin().getServer().getClass().getPackage().getName().replaceAll("/", ".");
	}

	public static String getServerNMSClass() {
		return "net.minecraft.server.";
	}

	public static double getTPS() {
		if (System.currentTimeMillis() - getStartTime() < 30 * 1000)
			return Double.NaN;
		
		double tps = Lag.getTPS();
		
		if(tps > 20)
			tps = 20;

		return tps;
	}

	public static String getTPSFormatted() {
		double tps = getTPS();

		if (Double.isNaN(tps))
			return Utils.prepareColors("&cNaN");

		String tpsFormat = NumberFormat.getInstance().format(tps);
		String tpsColor;

		if (tps >= 18)
			tpsColor = "&a" + tpsFormat;
		else if (tps >= 12)
			tpsColor = "&6" + tpsFormat;
		else
			tpsColor = "&c" + tpsFormat;

		return Utils.prepareColors(tpsColor);
	}
	
	public static int getVerbose3ModeTiming() {
		return ConfigsMessages.anticheat_verbose_mode_3_timing;
	}

	public static Class<?> getNMSClass(String className) {
		try {
			return Class.forName("net.minecraft.server." + getServerVersionRF() + "." + className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getServerVersionRF() {
		return Taka.getThisPlugin().getServer().getClass().getPackage().getName().substring(23);
	}

	// BlockUtils
	public static BlockFace getDirection(double yaw, boolean fourDirections) {
		if (!fourDirections) {
			final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
					BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
			return radial[(int) (Math.round(yaw / 45f) & 0x7)];
		} else {
			final BlockFace[] axis = { BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST };
			return axis[(int) (Math.round(yaw / 90f) & 0x3)];
		}
	}

	public static List<Block> getLineOfSight(Player p, Set<Material> transparent, int maxDistance, int maxLength,
			boolean againstBlock) {
		if (transparent == null)
			if (getServerVersion() >= 6)
				transparent = Sets.newHashSet(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
			else
				transparent = Sets.newHashSet(Material.AIR);
		if (maxDistance > 120)
			maxDistance = 120;

		ArrayList<Block> blocks = new ArrayList<Block>();
		Iterator<Block> itr = new BlockIterator(p, maxDistance);
		boolean shouldBreak = false;

		while (itr.hasNext()) {
			Block block = itr.next();
			blocks.add(block);
			if (maxLength != 0 && blocks.size() > maxLength)
				blocks.remove(0);

			if (shouldBreak)
				break;

			Material material = block.getType();
			if (!transparent.contains(material))
				if (againstBlock)
					shouldBreak = true;
				else
					break;
		}

		return blocks;
	}

	public static boolean isTargetBlock(Player p, Set<Material> transparent, int maxDistance, int maxLength,
			boolean againstBlock, Material type) {
		List<Block> blocks = getLineOfSight(p, transparent, maxDistance, maxLength, againstBlock);
		if (blocks.get(blocks.size() - 1).getType().equals(type))
			return true;

		return false;
	}

	public static boolean isTargetBlock(Player p, boolean againstBlock, Material type) {
		return isTargetBlock(p, null, 100, 0, againstBlock, type);
	}

	public static boolean isTargetBlockLiquid(Player p, boolean againstBlock) {
		List<Block> blocks = getLineOfSight(p, null, 100, 0, againstBlock);
		if (blocks.get(blocks.size() - 1).isLiquid())
			return true;

		return false;
	}
	
	public static boolean isInstantBreak(final Material m, final Material inHand) {
		switch(m) {
		case TORCH:
		case TRIPWIRE_HOOK:
		case DEAD_BUSH:
		case REDSTONE_WIRE:
		case SLIME_BLOCK:
		case POTATO:
		case WHEAT:
		case TNT:
		case SNOW:
		case SNOW_BLOCK:
		case COCOA:
		case DAYLIGHT_DETECTOR:
		case LADDER:
		case VINE:
		case NETHERRACK:
		case BREWING_STAND:
		case TRIPWIRE:
			return true;
		}
		
		switch (XMaterial.matchXMaterial(m)) {
		case REPEATER:
		case COMPARATOR:
		case REDSTONE_TORCH:
		case GRASS:
		case CARROTS:
		case LILY_PAD:
		case NETHER_WART:
		case FERN:
			return true;
		}
		
		switch (m.name().toUpperCase()) {
		case "BEETROOT":
		case "STEM":
		case "SEEDS":
		case "MUSHROOM":
		case "SAPLING":
		case "CARPET":
		case "BED":
		case "LEAVES":
		case "BUTTON":
		case "PLATE":
		case "PUMPKIN":
		case "MELON":
		case "POT":
		case "TWISTING_VINES":
		case "SUGAR_CANE":
		case "CROPS":
		case "FERN":
			return true;
		}

		if ((getItemPower(inHand) > 5 && (m == Material.SAND || m.name().contains("SAND") || m == Material.NOTE_BLOCK
				|| m == Material.QUARTZ_BLOCK || m.name().contains("RAIL"))))
			return true;

		if ((getItemPower(inHand) > 4 && (m == XMaterial.MYCELIUM.parseMaterial() || m == Material.GRAVEL
				|| m.name().contains("GRASS") || m == XMaterial.FARMLAND.parseMaterial() || m == Material.CLAY)))
			return true;

		if ((getItemPower(inHand) > 3 && (m == Material.SOUL_SAND || m == Material.SAND || m.name().contains("PISTON")
				|| m.name().contains("ICE") || m.name().contains("MAGMA_BLOCK") || m.name().contains("CONCRETE_POWDER")
				|| m == XMaterial.COARSE_DIRT.parseMaterial() || m == XMaterial.PODZOL.parseMaterial())))
			return true;

		if (getServerVersion() >= 7)
			if (m == Material.SWEET_BERRY_BUSH)
				return true;

		if (getServerVersion() >= 8)
			if (m == Material.HONEY_BLOCK || m == Material.BEEHIVE || m == Material.BEE_NEST
					|| m.name().contains("BAMBOO"))
				return true;
		
		if(isFlower(m) || isWaterPlant(m))
			return true;

		return false;
	}

	public static boolean isFlower(Material type) {
		try {
			switch(XMaterial.matchXMaterial(type)) {
			case POPPY:
			case DANDELION:
			case BLUE_ORCHID:
			case ALLIUM:
			case AZURE_BLUET:
			case OXEYE_DAISY:
			case CORNFLOWER:
			case LILY_OF_THE_VALLEY:
			case LARGE_FERN:
			case TALL_GRASS:
			case WITHER_ROSE:
			case SUNFLOWER:
			case LILAC:
			case ROSE_BUSH:
			case PEONY:
				return true;
			}
			
			if(type.name().contains("PLANT") || type.name().contains("TULIP"))
				return true;
		} catch(Exception e) { return false; }
		
		return false;
	}

	public static boolean isItem(Material type) {
		// Trying to represent the isItem method in the Material class.

		return type.name().contains("SIGN") || type.name().contains("FIRE") || type.name().contains("POT")
				|| type.name().contains("SKULL") || type.name().contains("HEAD") || type.name().contains("WATER")
				|| type.name().contains("LAVA") || type.name().contains("BANNER") || type.name().contains("CARROT")
				|| type.name().contains("DOOR") || isArmor(type);
	}
	
	public static boolean isWaterPlant(Material type) {
		try {
			if(getServerVersion() >= 6) {
				switch (type) {
				case KELP:
				case KELP_PLANT:
				case DRIED_KELP_BLOCK:
				case DRIED_KELP:
				case SEAGRASS:
				case TALL_SEAGRASS:
				case BUBBLE_CORAL_BLOCK:
				case BRAIN_CORAL:
				case BRAIN_CORAL_BLOCK:
				case BRAIN_CORAL_FAN:
				case BRAIN_CORAL_WALL_FAN:
				case BUBBLE_CORAL:
				case BUBBLE_CORAL_FAN:
				case BUBBLE_CORAL_WALL_FAN:
				case DEAD_BRAIN_CORAL:
				case DEAD_BRAIN_CORAL_BLOCK:
				case DEAD_BRAIN_CORAL_FAN:
				case DEAD_BRAIN_CORAL_WALL_FAN:
				case DEAD_BUBBLE_CORAL:
				case DEAD_BUBBLE_CORAL_BLOCK:
				case DEAD_BUBBLE_CORAL_FAN:
				case DEAD_BUBBLE_CORAL_WALL_FAN:
				case DEAD_FIRE_CORAL:
				case DEAD_FIRE_CORAL_BLOCK:
				case DEAD_FIRE_CORAL_FAN:
				case DEAD_FIRE_CORAL_WALL_FAN:
				case DEAD_HORN_CORAL:
				case DEAD_HORN_CORAL_BLOCK:
				case DEAD_HORN_CORAL_FAN:
				case DEAD_HORN_CORAL_WALL_FAN:
				case DEAD_TUBE_CORAL:
				case DEAD_TUBE_CORAL_BLOCK:
				case DEAD_TUBE_CORAL_FAN:
				case DEAD_TUBE_CORAL_WALL_FAN:
				case FIRE_CORAL:
				case FIRE_CORAL_BLOCK:
				case FIRE_CORAL_FAN:
				case FIRE_CORAL_WALL_FAN:
				case HORN_CORAL:
				case HORN_CORAL_BLOCK:
				case HORN_CORAL_FAN:
				case HORN_CORAL_WALL_FAN:
				case TUBE_CORAL:
				case TUBE_CORAL_BLOCK:
				case TUBE_CORAL_FAN:
				case TUBE_CORAL_WALL_FAN:
				case TWISTING_VINES:
				case TWISTING_VINES_PLANT:
				case WEEPING_VINES:
				case WEEPING_VINES_PLANT:
					return true;
				}
			}
		} catch(Exception e) { return false; }
		
		return false;
	}

	public static boolean isPassableBlock(Material type) {
		return type.name().contains("SIGN") || type.name().contains("DOOR") || type.name().contains("AIR")
				|| type.name().contains("SKULL") || type.name().contains("HEAD") || type.name().contains("POT")
				|| type.name().contains("BANNER") || isFlower(type) || isWaterPlant(type)
				|| type.name().contains("AIR") || type.name().contains("WATER") || type.name().contains("LAVA")
				|| type.name().contains("WALL") || type.equals(Material.FIRE) || type.name().contains("FENCE")
				|| type.name().contains("CHEST") || type.name().contains("AIR");
	}
	
	// Only for tools
	public static short getItemPower(Material type) {
		switch(XMaterial.matchXMaterial(type)) {
		case WOODEN_HOE:
		case WOODEN_SHOVEL:
		case WOODEN_AXE:
		case WOODEN_PICKAXE:
		case WOODEN_SWORD:
			return 1;
		case STONE_HOE:
		case STONE_SHOVEL:
		case STONE_AXE:
		case STONE_PICKAXE:
		case STONE_SWORD:
			return 2;
		case IRON_HOE:
		case IRON_SHOVEL:
		case IRON_AXE:
		case IRON_PICKAXE:
		case IRON_SWORD:
			return 3;
		case DIAMOND_HOE:
		case DIAMOND_SHOVEL:
		case DIAMOND_AXE:
		case DIAMOND_PICKAXE:
		case DIAMOND_SWORD:
			return 4;
		case GOLDEN_HOE:
		case GOLDEN_SHOVEL:
		case GOLDEN_AXE:
		case GOLDEN_PICKAXE:
		case GOLDEN_SWORD:
			return 6;
		}
		
		try {
			if (getServerVersion() >= 9) {
				switch(type) {
				case NETHERITE_HOE:
				case NETHERITE_SHOVEL:
				case NETHERITE_AXE:
				case NETHERITE_PICKAXE:
				case NETHERITE_SWORD:
					return 5;
				}
			}
		} catch(Exception e) { return -1; }
		
		return -1;
	}

	public static BlockFace getDirectionPL(int value) {
		switch(value) {
		case 0: return BlockFace.DOWN;
		case 1: return BlockFace.UP;
		case 2: return BlockFace.NORTH;
		case 3: return BlockFace.SOUTH;
		case 4: return BlockFace.WEST;
		case 5: return BlockFace.EAST;
		
		default: return BlockFace.SELF;
		}
	}
	
	public static boolean isBubbleColumn(Location l) {
		try {
			if (getServerVersion() >= 6)
				return l.getBlock().getType().equals(Material.BUBBLE_COLUMN);
		} catch(Exception e) { return false; }

		return false;
	}

	public static boolean isAroundBubbleColumn(Location l) {
		if (getServerVersion() >= 6)
			return getMaterialsAroundByName(l, "BUBBLE_COLUMN");

		return false;
	}

	public static boolean isBubbleColumnDown(Location l) {
		if (getServerVersion() >= 6)
			return l.getBlock().getBlockData().getAsString().contains("bubble_column")
					&& l.getBlock().getBlockData().getAsString().contains("drag=true");

		return false;
	}

	public static boolean isBubbleColumnUp(Location l) {
		if (getServerVersion() >= 6)
			return l.getBlock().getBlockData().getAsString().contains("bubble_column")
					&& l.getBlock().getBlockData().getAsString().contains("drag=false");

		return false;
	}

	// ReportUtils
	public static void writeReports(File f) {
		PrintWriter ps = null;
		try {
			ps = new PrintWriter(new FileWriter(f, true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Report p : getReports()) {
			ps.println(p.getPlayer().getName() + " --> " + p.getReported().getName() + " for: " + p.getReport());
		}
		ps.close();
	}

	public static String setPlaceholders(Player p, String message) {
		if (isHookedTo(PluginHookType.PLACEHOLDERAPI))
			return PlaceholderAPI.setPlaceholders(p, message);
		else
			return message;
	}

	public static void loadReports(File f) {
		try {
			BufferedReader ps = new BufferedReader(new FileReader(f));
			String s;
			while ((s = ps.readLine()) != null) {
				// Parse
				String[] split = s.split(" ");
				String player = split[0];
				String reported = split[2];
				String report = "";

				for (int s1 = 4; s1 <= split.length - 1; s1++) {
					if (s1 != split.length - 1)
						report += split[s1] + " ";
					else
						report += split[s1];
				}

				addReport(new Report(Taka.getThisPlugin().getServer().getOfflinePlayer(player),
						Taka.getThisPlugin().getServer().getOfflinePlayer(reported), report), true);

			}
			ps.close();
			Files.deleteIfExists(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(Utils
					.prepareColors(ConfigsMessages.anticheat_prefix + "&cError while loading reports!(code: A1) !"));
		}
	}
	
	public static double getHackerProbability(CheatPlayer p) {
		return (p.getHackerProbabilityCount() / (isGhostMode() ? 120.0D : 75.0D)) * 100;
	}
	
	public static String getHackerProbabilityFormat(CheatPlayer p) {
		double prob = (p.getHackerProbabilityCount() / (isGhostMode() ? 120.0D : 75.0D)) * 100;
		String color;
		
		if(prob > 100)
			prob = 100D;
		
		if(prob <= 40)
			color = "&a";
		else if(prob <= 70)
			color = "&6";
		else color = "&c";
		
		return Utils.prepareColors(color+DecimalFormat.getInstance().format(prob))+"%";
	}
	
	public static FancyMessage getReportMessage(String player, String hackType, int vl, String moveCancelVL, String ping, String cVer) {
		return new FancyMessage(Utils.prepareColors(ConfigsMessages.anticheat_debug_message.replaceAll("%player%|%p%", player)
				.replaceAll("%hack%|%h%", hackType)
				.replaceAll("%vl%", "" + vl)
				.replaceAll("%moveCancelViolation%", moveCancelVL))).formattedTooltip(new FancyMessage("TPS: ").color(ChatColor.GREEN).then(Utils.getTPSFormatted()).then(" Ping: " + ping + "ms").color(ChatColor.GOLD).then(" C.Ver: " + cVer).color(ChatColor.AQUA), new FancyMessage("Hacker probability: ").color(ChatColor.GOLD).then(getHackerProbabilityFormat(Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(player).getUniqueId()))).color(ChatColor.RED), new FancyMessage("Click to teleport").color(ChatColor.DARK_PURPLE)).command("/taka tp " + player);
	}
	
	public static FancyMessage getReportMessageBungee(String player, String hackType, String vl, String moveCancelVL, String ping, String tps, String cVer, String server) {
		return new FancyMessage(Utils.prepareColors(ConfigsMessages.anticheat_debug_message.replaceAll("%player%|%p%", player)
				.replaceAll("%hack%|%h%", hackType)
				.replaceAll("%vl%", vl)
				.replaceAll("%moveCancelViolation%", moveCancelVL))).formattedTooltip(new FancyMessage("TPS: ").color(ChatColor.GREEN).then(Utils.getTPSFormatted()).then(" Ping: " + ping + "ms").color(ChatColor.GOLD).then(" C.Ver: " + cVer).color(ChatColor.AQUA), new FancyMessage("Hacker probability: ").color(ChatColor.GOLD).then(getHackerProbabilityFormat(Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(player).getUniqueId()))).color(ChatColor.RED), new FancyMessage("From Server: " + server).color(ChatColor.DARK_PURPLE));
	}

	public static void freezePlayer(Player player, HackType reason) {
		freezePlayer(player, (short) -1, reason);
	}

	public static void freezePlayer(Player player, int seconds, HackType reason) {
		getCheatPlayer(player.getUniqueId()).setFreezedReason(reason);
		getCheatPlayer(player.getUniqueId()).setFreezed(true);
		if (seconds != -1) {
			Taka.getThisPlugin().getServer().getScheduler().runTaskLater(Taka.getThisPlugin(), new Runnable() {

				@Override
				public void run() {
					if (player.isOnline())
						unfreezePlayer(player);
				}
			}, seconds * 20L);
		}
	}

	public static void unfreezePlayer(Player player) {
		getCheatPlayer(player.getUniqueId()).setFreezed(false);
	}
	
	public static boolean isFrozen(CheatPlayer player) {
		return player.getFreezed();
	}

	public static boolean isFrozen(Player player) {
		return isFrozen(getCheatPlayer(player.getUniqueId()));
	}
	
	public static void releaseItem(CheatPlayer p) {
		if(isBlockable(p.getPlayer().getItemInHand().getType()))
			p.setBlocking(false);
		
		p.setUseItemSpeed(false);
	}

	// File Utils
	public static void extractDirectory(String dir, String destDir) {
		try {
			JarFile jar = new JarFile(new File("plugins" + File.separator + getTakaJarFileName()));
			Enumeration<JarEntry> enumEntries = jar.entries();

			while (enumEntries.hasMoreElements()) {
				JarEntry file = enumEntries.nextElement();
				File f = new File(destDir + File.separator + file.getName());

				if (f.isDirectory() && f.getName().equals(dir)) {
					f.mkdir();
				} else if (f.getPath().contains(dir)) {
					InputStream is = jar.getInputStream(file);
					f.getParentFile().mkdir();
					f.createNewFile(); // ERROR: No such file or directory - To fix, look up
					FileOutputStream fos = new FileOutputStream(f); // ERROR: File not exists! - To fix, look up

					while (is.available() > 0) {
						fos.write(is.read());
					}

					fos.close();
					is.close();
				}
			}

			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTakaJarFileName() {
		try {
			return new File(Taka.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	// Suspend Utils

	// So fcking ugly :(
	// TODO: Test
	public static String formatSuspendTime(String format) {
		int time = getTimeFromFormat(format);

		if (time != -1) {
			if (time > 60) {
				int hour = time / 60;
				int minutes = time % 60;

				return hour + " hour/s and " + minutes + " minute/s";
			} else if (time > 60 * 24) {
				int day = time / (60 * 24);
				int hour = time - (day * 60 * 24) / 60;
				int minutes = ((day * 60 * 24) / 60) - (hour * 60);

				return day + "day/s, " + hour + " hour/s and " + minutes + " minute/s!";
			} else if (time > 60 * 24 * 7) {
				int week = time / (60 * 24 * 7);
				int day = (7 * 24 * 60) / (week * 24 * 60);
				int hour = ((7 * 24 * 60) / (week * 24 * 60) * 24) - (day * 24);
				int minutes = (((7 * 24 * 60) / (week * 24 * 60) * 24) - (day * 24) * 60) - (hour * 60);

				return week + " week/s, " + day + "day/s, " + hour + " hour/s and " + minutes + " minute/s!";
			}

			return time + " minute/s";
		}

		return "ERROR!!!";
	}

	// Return time in minutes
	public static int getTimeFromFormat(String format) {
		String number = format.replaceAll("[^0-9]", "");
		int time = Integer.parseInt(number);
		char timeKey = number.charAt(number.length() - 1);

		switch (timeKey) {
		case 'm':
			return time;
		case 'h':
			return time * 60;
		case 'd':
			return time * 60 * 24;
		case 'w':
			return time * 60 * 24 * 7;
		default:
			if (Character.isDigit(timeKey))
				return time;

			return -1;
		}
	}

	public static void setSuspendTime(int time) {
		long suspendMillis = System.currentTimeMillis() + (time * 60 * 1000);

		Utils.suspendMillis = suspendMillis;

		saveSuspendTime();
	}

	public static void saveSuspendTime() {
		try {
			File save = new File("plugins/TakaAntiCheat/suspend.dat");
			if (save.exists()) {
				save.delete();
				save.createNewFile();
			}

			DataOutputStream dos = new DataOutputStream(new FileOutputStream(save));

			dos.writeLong(suspendMillis);

			dos.close();
		} catch (IOException e) { }
	}

	public static void loadSuspendTime() {
		try {
			File save = new File("plugins/TakaAntiCheat/suspend.dat");
			if (!save.exists())
				return;

			DataInputStream dis = new DataInputStream(new FileInputStream(save));

			suspendMillis = dis.readLong();

			if (!isSuspended()) {
				suspendMillis = 0;
				save.delete();
			}

			dis.close();
		} catch (IOException e) { }
	}

	public static boolean isSuspended() {
		if (suspendMillis != 0) {
			if (suspendMillis - System.currentTimeMillis() > 0)
				return true;
		}

		return false;
	}
	
	public static ArrayList<String> getDisabledWorlds() {
		return disabledWorlds;
	}

	public static boolean isWorldDisabled(String world) {
		return disabledWorlds.contains(world);
	}
	
	public static void loadDisabledWorlds() {
		for(String w : ConfigsMessages.anticheat_disable_in_world)
			disabledWorlds.add(w);
	}
	
	public static void saveDisabledWorlds() {
		ConfigsMessages.disabled_worlds.set("anticheat-disable-in-world", disabledWorlds);
		
		try {
			ConfigsMessages.disabled_worlds.save(new File("plugins/TakaAntiCheat/disabled-worlds.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkConfigVersion() {
		return ConfigsMessages.config_version != config_version;
	}
	
	public static String getConfigVersionMessage() {
		return Utils.prepareColors(Utils.getTakaPrefix()
				+ " &6WARNING: &cYour config version(" + ConfigsMessages.config_version
				+ ") is different than the newest one(" + config_version + ")!"
				+ "In order to have best experience with Taka, you should delete your old config file!");
	}

	// Checks Utils
	public static boolean checkModule(CheatPlayer p, HackType type) {
		if(!checkPlayer(p))
			return false;
		
		if (isWorldDisabled(p.getPlayer().getWorld().getName()))
			return false;
		
		if (isSuspended())
			return false;

		if (HackType.isCheckDisabled(type))
			return false;

		if (!Utils.isDetectionEnabled(type) || !Utils.isDetectionEnabled(type, p))
			return false;

		if (p.haveBypass() || p.haveBypass(type))
			return false;

		return true;
	}
	
	public static boolean checkPlayer(CheatPlayer p) {
		if (p == null)
			return false;
		
		if(p.getPlayer() == null)
			return false;
		
		if(!p.getPlayer().isOnline())
			return false;
		
		return true;
	}
	
	public static boolean isGhostMode() {
		return ConfigsMessages.anticheat_ghost;
	}
	
	public static void setGhostMode(boolean ghost) {
		ConfigsMessages.anticheat_ghost = ghost;
	}

	// Debug Utils
	public static void debugMessage(String msg) {
		if (Taka.getThisPlugin().getServer().getPlayer("PoshPoser") != null) // Send message to PoshPoser or _dakata
			if(getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer("PoshPoser").getUniqueId()).getDebugModeType() == 4)
				getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer("PoshPoser").getUniqueId()).logMessage(LogMessage.DEBUG, Utils.prepareColors(msg));

		if (Taka.getThisPlugin().getServer().getPlayer("_dakata") != null) // Send message to PoshPoser or _dakata
			if(getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer("_dakata").getUniqueId()).getDebugModeType() == 4)
				getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer("_dakata").getUniqueId()).logMessage(LogMessage.DEBUG, Utils.prepareColors(msg));
	}
	
	public static boolean isDevName(CheatPlayer p) {
		return isDevName(p.getPlayer().getName());
	}
	
	public static boolean isDevName(String p) {
		return p.equals("PoshPoser") || p.equals("_dakata");
	}

	public static boolean isDevTest(CheatPlayer p) {
		if(p == null)
			return false;
		
		return (isDevName(p) && Taka.DEV_TEST && p.getDebugModeType() == 4);
	}
	
	// Bungee Utils
	public static void sendReportMessageToPluginChanel(String hacker, String hackType, String vl, String moveCancelVL, String server, String ping, String tps, String cVer) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("TAKA_Report"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(hacker); // You can do anything you want with msgout
			msgout.writeUTF(hackType);
			msgout.writeUTF(vl);
			msgout.writeUTF(moveCancelVL);
			msgout.writeUTF(ping);
			msgout.writeUTF(tps);
			msgout.writeUTF(cVer);
			msgout.writeUTF(server);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		
		if(!Taka.getThisPlugin().getServer().getOnlinePlayers().isEmpty())
			Iterables.getFirst(Taka.getThisPlugin().getServer().getOnlinePlayers(), null).sendPluginMessage(Taka.getThisPlugin(), "BungeeCord", out.toByteArray());
	}

	public static void sendBungeeGetServer() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("GetServer"); // So BungeeCord knows to forward it
		
		if(!Taka.getThisPlugin().getServer().getOnlinePlayers().isEmpty())
			Iterables.getFirst(Taka.getThisPlugin().getServer().getOnlinePlayers(), null).sendPluginMessage(Taka.getThisPlugin(), "BungeeCord", out.toByteArray());
	}
	
	// Math Utils
	public static double getStatisticMedian(double[] elements) {
		if(elements == null || elements.length < 2)
			return -1;
		
		Arrays.sort(elements); // Sort the elements
		
		if(elements.length % 2 == 0) {
			return elements[elements.length / 2];
		} else {
			return (elements[elements.length / 2] + elements[(elements.length + 1) / 2]) / 2;
		}
	}
}
