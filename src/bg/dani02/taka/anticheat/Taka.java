package bg.dani02.taka.anticheat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import bg.dani02.taka.anticheat.api.PlayerBlockInteractEvent;
import bg.dani02.taka.anticheat.checks.chat.ChatBadwords;
import bg.dani02.taka.anticheat.checks.chat.ChatCaptcha;
import bg.dani02.taka.anticheat.checks.exploit.WorldDownloader;
import bg.dani02.taka.anticheat.checks.render.Freecam;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.PluginHookType;
import bg.dani02.taka.anticheat.managers.AchievementEventManager;
import bg.dani02.taka.anticheat.managers.ElytraEventManager;
import bg.dani02.taka.anticheat.managers.McMMOManager;
import bg.dani02.taka.anticheat.managers.RealDualWieldManager;
import bg.dani02.taka.anticheat.managers.SurvivalMechanicsManager;
import bg.dani02.taka.anticheat.managers.VeinMineManager;
import mkremins.fanciful.FancyMessage;

@SuppressWarnings("deprecation")
public class Taka extends JavaPlugin implements Listener, PluginMessageListener {

	private static Plugin pl = null;

	public static StringBuilder fileLog = new StringBuilder();

	public static Metrics metrics;

	public static boolean DEV_TEST = true;

	@Override
	public void onEnable() {
		long old = System.currentTimeMillis();

		pl = this;

		// Load config files
		ConfigsMessages.load();
		
		// Create messages file
		if (!new File("plugins/TakaAntiCheat/messages.yml").exists()) {
			this.saveResource("messages.yml", false);
		}
		
		// Create disabled-worlds file
		if (!new File("plugins/TakaAntiCheat/disabled-worlds.yml").exists()) {
			this.saveResource("disabled-worlds.yml", false);
		}
		
		// Load messages file
		ConfigsMessages.messages = YamlConfiguration.loadConfiguration(new File("plugins/TakaAntiCheat/messages.yml"));
		ConfigsMessages.loadMessages();
		
		// Load disabled worlds
		ConfigsMessages.disabled_worlds = YamlConfiguration.loadConfiguration(new File("plugins/TakaAntiCheat/disabled-worlds.yml"));
		ConfigsMessages.loadDisabledWorlds();
		Utils.loadDisabledWorlds();

		// Set some things
		Utils.setServerVersion();
		
		// Check if 1.7, 1.6 or 1.5 version
		if(Utils.getServerVersion() == 0) {
			getServer().getConsoleSender().sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&cERROR: THIS PLUGIN DOESN'T WORK WITH <1.7 SERVER VERSIONS! Disabling..."));
			getServer().getPluginManager().disablePlugin(this);
		}

		// Load metrics
		metrics = new Metrics(this, 2494);

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "******************************"));
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&4Welcome to Taka Anti Cheat"));
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&4Report bugs to &3@&2dani02"));
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "******************************"));

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Register Events..."));
		getServer().getPluginManager().registerEvents(this, this);
		if (Utils.getServerVersion() >= 2) {
			getServer().getPluginManager().registerEvents(new ElytraEventManager(), this);
		}
		if (Utils.getServerVersion() <= 4) {
			getServer().getPluginManager().registerEvents(new AchievementEventManager(), this);
		}
		if (getServer().getPluginManager().isPluginEnabled("SurvivalMechanics"))
			getServer().getPluginManager().registerEvents(new SurvivalMechanicsManager(), this);

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Events Registered!"));
		
		getServer().getConsoleSender()
			.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading Configs..."));
		if (!new File("plugins/TakaAntiCheat/config.yml").exists()) {
			getServer().getConsoleSender().sendMessage(
					Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Can't find config, new one is created!"));
			this.saveResource("config.yml", false);
		}
		if (!new File("plugins/TakaAntiCheat/badwords").exists()) {
			getServer().getConsoleSender().sendMessage(Utils.prepareColors(
					ConfigsMessages.anticheat_prefix + "&5Can't find chat settings, new one's are created"));
			Utils.extractDirectory("badwords", "plugins/TakaAntiCheat/");
		}
		if (new File("plugins/TakaAntiCheat/report.txt").exists()) {
			getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Report config loaded!"));
			Utils.loadReports(new File("plugins/TakaAntiCheat/report.txt"));
		}
		getServer().getConsoleSender()
			.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Configs Loaded!"));

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Starting VL Cleaner..."));
		Utils.loadViolationCleaner();
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5VL Cleaner Started!"));

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading Players..."));

		for (Player p : getServer().getOnlinePlayers()) {
			Utils.addCheatPlayer(p.getUniqueId());
		}

		for (CheatPlayer p : Utils.getCheatPlayers()) {
			// Remove the inventory achievement, otherwise, the invmove check won't work
			if (Utils.getServerVersion() <= 4)
				if (p.hasAchievement(Achievement.OPEN_INVENTORY))
					p.removeAchievement(Achievement.OPEN_INVENTORY);
			// The player which are already in the server, are registered in captcha
			ChatCaptcha.registered.add(p.getPlayer().getName());
		}

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Players Loaded!"));
		
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to ProtocolLib..."));
		try {
			Utils.hookTo(PluginHookType.PROTOCOLLIB);
		} catch (NoClassDefFoundError ex) {
			getServer().getConsoleSender().sendMessage(
					Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&cERROR: No ProtocolLib installed!"));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to ProtocolLib!"));
		if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			getServer().getConsoleSender().sendMessage(
					Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to PlaceholderAPI..."));
			Utils.hookTo(PluginHookType.PLACEHOLDERAPI);
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to PlaceholderAPI!"));
		}

		if (getServer().getPluginManager().isPluginEnabled("Citizens")) {
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to Citizens..."));
			Utils.hookTo(PluginHookType.CITIZENS);
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to Citizens!"));
		}

		if (getServer().getPluginManager().isPluginEnabled("mcMMO")) {
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to mcMMO..."));
			Utils.hookTo(PluginHookType.MCMMO);

			getServer().getPluginManager().registerEvents(new McMMOManager(), this);

			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to mcMMO!"));
		}

		if (getServer().getPluginManager().isPluginEnabled("RealDualWield")) {
			getServer().getConsoleSender().sendMessage(
					Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to RealDualWield..."));
			Utils.hookTo(PluginHookType.REALDUALWIELD);

			getServer().getPluginManager().registerEvents(new RealDualWieldManager(), this);

			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to RealDualWield!"));
		}

		if (getServer().getPluginManager().isPluginEnabled("VeinMine")
				|| getServer().getPluginManager().isPluginEnabled("VeinMiner")) {
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooking to VeinMine..."));
			Utils.hookTo(PluginHookType.VEINMINE);

			getServer().getPluginManager().registerEvents(new VeinMineManager(), this);

			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Hooked to VeinMine!"));
		}

		if (ConfigsMessages.log_file_enable) {
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading WTDT..."));
			Utils.loadWTDTFile();
		}

		Utils.loadWTDTReport();

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5WTDT Loaded!"));

		if (ConfigsMessages.log_file_warning != -1) {
			getServer().getConsoleSender()
					.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading LSW..."));
			Utils.loadLogSizeWarner();
		}
		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5LSW Loaded!"));

		Utils.loadUpdateChecker();

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading Badwords..."));

		ChatBadwords.loadBadwords();

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Badwords Loaded!"));

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading Timer..."));

		if (Utils.isDetectionEnabled(HackType.RENDER_FREECAM))
			Freecam.onCheck();

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Timer Loaded!"));

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading custom TPS module..."));

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);

		getServer().getConsoleSender()
				.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Custom TPS module Loaded!"));
		
		if(new File("plugins/TakaAntiCheat/suspend.dat").exists()) {
			
			getServer().getConsoleSender().sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading Last Saved Suspend Time..."));
			
			Utils.loadSuspendTime();
			
			getServer().getConsoleSender().sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Last Saved Suspend Time Loaded!"));
		}

		if (Utils.getServerVersion() >= 6)
			getServer().getMessenger().registerIncomingPluginChannel(getThisPlugin(), "wdl:init", this);
		
		getServer().getConsoleSender()
		.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Loading bungeecord integration..."));

	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

	    getServer().getConsoleSender()
			.sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix + "&5Bungeecord integrated!"));
	    
		if(Utils.checkConfigVersion())
			getServer().getConsoleSender().sendMessage(Utils.getConfigVersionMessage());

		Utils.setStartTime(System.currentTimeMillis());
		getServer().getConsoleSender().sendMessage(Utils.prepareColors(ConfigsMessages.anticheat_prefix
				+ "&5Taka has been started(" + (System.currentTimeMillis() - old) + "ms)"));

		/* Fixed:
		 * improved api - added new checks
		 * 
		 * 
		 * PROBLEMS: TO BE FIXED TODO 1. Speed ice when pushed vertically by slime block
		 * 2. InvalidFall(Slow Y) when pushed upwards by a slime block 3. Strafe false
		 * positive when pushed upwards by a slime block 4. Treport GUI
		 * 5. Speed ONground false positive when jumping on fences in water TEST - OK
		 * 6. Speed Air jump on ice and block on head gives false positive TEST
		 * 7. Timer jump on ice and block on head gives false positive TEST - OK
		 * 8. Timer speed many false positives when just jumping TEST - OK
		 * 9. Timer speed false positives on ice,slime and of block is on the head TEST - OK
		 * 10 ImpossibleJump false positives jumping on slimeblocks in water TEST
		 * 11. Speed when hungry bypass ???
		 * 12. fastuse aac bypass - CANNOT BE FIXED
		 * 13 invaliditeractionblock false positive bed with stone walls TEST - OK
		 * 14. Speed false positive on teleport in Loyisa server KIllAuraAim targets
		 * 15. You can use Fly to bypass nofall check TEST - OK
		 * 16. Scaffoldwalk bypass just sneaked TEST
		 * 17. place/break block and attack entity thru block
		 * 18. timer teleport false positive TEST - OK
		 * 19. speed air fp with speed potion TEST - OK
		 * 20. Nofall bypass in LB client - Always non on ground TEST - OK
		 * 21. nofall not working correctly when player dealt with damage
		 * 22. Highjump can be bypassed when player dealt damage
		 * 23. InvMove not working correctly?
		 * 24. Soulsand jumping when other block around false positive TEST - OK
		 * 25. add autoblock check
		 * 26. speed liquid when jumping in water false positive
		 * 27. speed item false positive spamming bow
		 * 28. invalidfall can be bypassed by taking damage and teleport time
		 * 		and if i go over and over with fastfall lol
		 * 29. nofall bypass damage/teleport time? and noGround spoof TEST - OK
		 * 30. step bypass if block is above head TEST - OK
		 * 31. lowjump false positive near stone wall TEST - OK
		 * 32. speed false positive jumping when trapdoor over head TEST - OKwor
		 * 33. speed cobweb bypass when jumping in web TEST - OK
		 * 34. running under glass block or trapdoor does not trigger block speed legit reason TEST - OK
		 * 35. Timer + riding entity bypass
		 * 
		 * 
		 * XX. SCAFFOLD TIMER FALSE POSITIVE !!!
		 * XX. HIGHJUMP FALSE POSITIVE ON SLAB - LOYISA - OK
		 * XX. HIGHTJUMP FALSE POSITIVE ON SNOW - LOYISA - OK
		 * XX. FLY DOUBLE JUMP FALSE POSITIVE ON SNOW - LOYISA - OK
		 * XX. SCAFFOLD ALL FALSE POSITIVE PLACE BLOCK HEAD LEVEL - LOYISA
		 * XX. ERRORS AND JESUS DONT WORK ON SOME WATER LEVELS - LOYISA - OK
		 * XX. FLY DOUBLE JUMP DOWN BUG WITH ICE ON DAC TEST SERVER - OK
		 * XX. ICE SPEED(AIR) + SPEED(ONGROUND) ON LOYISA
		 * 
		 * 36. fly double jump false positive when jumping on scaffolding and ladders TEST - OK
		 * 37. speed air when jumping under cobblestone wall and iron bar
		 * 
		 *  
		 * 
		 * 
		 * Fly double jump up/ImpossibleJump bypass on
		 * cactus daylight sensor bed stairs fence chest iron bar head cake snow
		 * - You can use spider there
		 * 
		 * ADD SPEED(ENTITY) CHECK, MOREKB CHECK, AUTOBLOCK CHECK
		 * 
		 * Random Speed(Air) false positive when jumping and block is above head
		 * Random Fly(Double jump down) fp on damage
		 * Random Fly(Double jump up) fp on damage
		 * Random Fly(slow y instant) fp on damage
		 * Random NoFall fp on damage
		 * 
		 * 
		 * 
		 * 
		 * TODO: ADD VIDEO CLIP, CHESTAURA, MULTIAURA
		 * , EXTRAELYTRA(LOADS MUCH CHUNGS, CAN CRASH SERVER)
		 * 
		 * Performance? See XMaterial too slow? Material.matchMaterial method...
		 * 
		 * Taka new report system: - Remove old GUI, add Books report - Add new anvil
		 * for picture or video link - All reports will have DATE FORMAT: - DEFAULT:
		 * YYYY/MM/DD hh:mm:ss - Can be changed in config - All reports will show the
		 * report history(how many times the player has been reported) - Command and GUI
		 * option to see how many reports a player has made + to see the report; so all
		 * reports will be saved forever; - Report can be closed. which means they will
		 * not be visible, except using the above command - Report will have 3 states:
		 * OPEN; IN PROGRESS; CLOSED
		 */
	}

	@Override
	public void onDisable() {
		if (Utils.isHookedTo(PluginHookType.PROTOCOLLIB))
			Utils.getProtocolManager().removePacketListeners(this);
		
		getServer().getScheduler().cancelTasks(this);
		
		getServer().getMessenger().unregisterIncomingPluginChannel(this);
		getServer().getMessenger().unregisterOutgoingPluginChannel(this);
		
		Utils.saveDisabledWorlds();
		
		if (!Utils.getReports().isEmpty()) {
			File f = new File("plugins/TakaAntiCheat/report.txt");
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Utils.writeReports(f);
		}

		Utils.stopFlooder();

		if (Utils.getServerVersion() >= 6)
			getServer().getMessenger().unregisterIncomingPluginChannel(this);

		// XRay
		// TODO: Can cause server to freeze, see if the hiddenBlocks are too TOO much
		// if so there is are BIG problem somewhere
		/*
		 * for(CheatPlayer p : Utils.getCheatPlayers()) { if(p.getHiddenBlocks() !=
		 * null) { for(BlockState b : p.getHiddenBlocks().keySet()) {
		 * p.removeHiddenBlock(b); } } }
		 */
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("taka")) {
			if (sender instanceof Player)
				if (!sender.hasPermission(Utils.getAdminPermission()) &&
						!Utils.isDevName(sender.getName())) { // Checking dev name because of /taka debug
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return true;
				}
			if (args.length == 0) {
				if (sender instanceof Player) {
					GUI.openMain((Player) sender);
				} else {
					sender.sendMessage(Utils.prepareColors("&7/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Permissions &9- &3 Show all permissions"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Checks &9- &3 Show all checks"));
					sender.sendMessage(Utils.prepareColors(
							"&7/  Taka Check <enable/disable> <checkName> &9- &3 Disable or Enable check"));
					sender.sendMessage(Utils.prepareColors(
							"&7/  Taka BanWave <add/remove/list/toggle> [playerName] &9- &3 Disable or Enable check"));
					sender.sendMessage(
							Utils.prepareColors("&7/  Taka Freeze <playerName> [time]&9- &3 Freeze the player"));
					sender.sendMessage(
							Utils.prepareColors("&7/  Taka Unfreeze <playerName> &9- &3 Unfreeze the player"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Ghost &9- &3 Enter ghost mode"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Tps &9- &3 Show server tps"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka world <enable/disable> <world> &9- &3 Toggle taka in specific world"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka version &9- &3 See Taka version"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Suspend <time> &9- &3 Suspend all checks for a temporary time"));
					sender.sendMessage(Utils.prepareColors("&7/  Taka Reload &9- &3 Reload the config file"));
					sender.sendMessage(Utils.prepareColors("&7/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/"));
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("permissions")) {
					if (!sender.hasPermission(Utils.getCommandPemrsPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&3Permissions:"));
					for (HackType h : HackType.values()) {
						sender.sendMessage(Utils.prepareColors("&7- &5" + HackType.getName(h) + "&7 - &5"
								+ (Utils.getBypassPermission() + "." + HackType.removeCheckType(h))));
					}
				} else if (args[0].equalsIgnoreCase("checks")) {
					if (!sender.hasPermission(Utils.getCommandCheckPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					String abb = "";
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&3Enabled checks:"));
					for (HackType h : HackType.values()) {
						if (Utils.isDetectionEnabled(h)) {
							sender.sendMessage(Utils.prepareColors("&7- &5" + HackType.getName(h)));
						} else if (abb != "")
							abb += "," + HackType.getName(h);
						else
							abb += HackType.getName(h);
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&3Disabled checks:"));
					if (abb != "") {
						for (String ss : abb.split(",")) {
							sender.sendMessage(Utils.prepareColors("&7- &5" + ss + ""));
						}
					} else {
						sender.sendMessage(Utils.prepareColors("&3No disabled checks!"));
					}
				} else if (args[0].equalsIgnoreCase("ghost")) {
					if (!sender.hasPermission(Utils.getCommandGhostPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					Utils.setGhostMode(!Utils.isGhostMode());
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "Ghost mode "
							+ (Utils.isGhostMode() ? "&aenabled" : "&cdisabled")));
				} else if (args[0].equalsIgnoreCase("tps")) {
					if (!sender.hasPermission(Utils.getCommandTpsPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					String tps = Utils.getTPSFormatted();

					if (Utils.removeColors(tps).equals("NaN"))
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()
								+ "The TPS system is still in calibration, please check again in 30 seconds!"));
					else
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "Server tps: " + tps));
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission(Utils.getCommandReloadPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors("&6Reloading config..."));
					Utils.reloadConfig();
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&aConfig reloaded"));
				} else if (args[0].equalsIgnoreCase("verbose")) {
					if (sender instanceof ConsoleCommandSender) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid Argument"));
						return false;
					}
					if (!sender.hasPermission(Utils.getCommandDebugPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					Player p = (Player) sender;
					Utils.getCheatPlayer(p.getUniqueId())
							.setDebugMode(!Utils.getCheatPlayer(p.getUniqueId()).getDebugMode());
					p.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils
							.getAdminTurnDebugModeMessage(Utils.getCheatPlayer(p.getUniqueId()).getDebugMode())));
				} else if (args[0].equalsIgnoreCase("banwave")) {
					if (!sender.hasPermission(Utils.getCommandBanWavePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(
							Utils.getTakaPrefix() + "&c/taka banwave <list/add/remove/toggle> <player>"));
				} else if (args[0].equalsIgnoreCase("check")) {
					if (!sender.hasPermission(Utils.getCommandCheckPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(
							Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka check <enable/disable <check>"));
				} else if (args[0].equalsIgnoreCase("freeze")) {
					if (!sender.hasPermission(Utils.getCommandFreezePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka freeze <player>"));
				} else if (args[0].equalsIgnoreCase("unfreeze")) {
					if (!sender.hasPermission(Utils.getCommandUnfreezePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka unfreeze <player>"));
				} else if (args[0].equalsIgnoreCase("suspend")) {
					if (!sender.hasPermission(Utils.getCommandSuspendPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka suspend <time>"));
				} else if (args[0].equalsIgnoreCase("world")) {
					if (!sender.hasPermission(Utils.getCommandDisabledWorldsPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka world <enable/disable> <world>"));
				} else if (args[0].equalsIgnoreCase("version")) {
					if (!sender.hasPermission(Utils.getCommandVersionPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					
					sender.sendMessage(Utils.prepareColors("&aTaka Version: " + Utils.getTakaPrefix() + Taka.getThisPlugin().getDescription().getVersion()));
				} else {
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid Argument"));
				}
			} else if (args.length >= 2) {
				// REMOVE
				/*
				 * if(sender instanceof Player) { if(args[0].equalsIgnoreCase("testAim")) {
				 * Player p = getServer().getPlayer(args[1]); if(p != null) {
				 * if(!Aimbot.players.contains(p)) Aimbot.players.add(p); else
				 * Aimbot.players.remove(p); } } }
				 */
				if (args[0].equalsIgnoreCase("check") && args.length > 2) {
					if (!sender.hasPermission(Utils.getCommandCheckPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					if (args.length != 3) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid SubArgument"));
					} else {
						if (!args[1].equalsIgnoreCase("enable") && !args[1].equalsIgnoreCase("disable")) {
							sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid SubArgument"));
						} else {
							HackType accept = null;
							for (HackType t : HackType.values()) {
								String all = "";
								for (short s = 2; s <= args.length - 1; s++)
									all += args[s];
								if (HackType.getName(t).replaceAll(" ", "").equalsIgnoreCase(all))
									accept = t;
							}
							if (accept != null) {
								if (args[1].equalsIgnoreCase("enable")) {
									if (!Utils.isDetectionEnabled(accept)) {
										Utils.setDetectionEnabled(accept, true);
										sender.sendMessage(
												Utils.prepareColors(Utils.getTakaPrefix() + "&aThe check is enabled!"));
									} else {
										sender.sendMessage(Utils.prepareColors(
												Utils.getTakaPrefix() + "&cThe check is already enabled!"));
									}
								} else if (args[1].equalsIgnoreCase("disable")) {
									if (Utils.isDetectionEnabled(accept)) {
										Utils.setDetectionEnabled(accept, false);
										sender.sendMessage(Utils
												.prepareColors(Utils.getTakaPrefix() + "&aThe check is disabled!"));
									} else {
										sender.sendMessage(Utils.prepareColors(
												Utils.getTakaPrefix() + "&cThe check is already disabled!"));
									}
								}

								if (ConfigsMessages.log_file_enable) {
									Utils.addMessageToFileLog(null, sender.getName() + " set "
											+ HackType.getName(accept) + " to: " + Utils.isDetectionEnabled(accept));
								}
							} else {
								sender.sendMessage(Utils
										.prepareColors(Utils.getTakaPrefix() + "&cCheck not found, try /taka checks"));
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("banwave")) {
					if (!sender.hasPermission(Utils.getCommandBanWavePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					if (args.length > 3 || args.length < 2) {
						sender.sendMessage(Utils.prepareColors(
								Utils.getTakaPrefix() + "&c/taka banwave <list/add/remove/toggle> <player>"));
					} else {
						if (args.length == 3 || args.length == 2) {
							if (!args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("remove")
									&& !args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("toggle")) {
								sender.sendMessage(Utils.prepareColors(
										Utils.getTakaPrefix() + "&c/taka banwave <list/add/remove/toggle> <player>"));
							} else {
								if (args[1].equalsIgnoreCase("list")) {
									sender.sendMessage(
											Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayers in BanWave:"));

									boolean isEmpty = true;
									for (CheatPlayer p : Utils.getCheatPlayers())
										if (p.getBanWave()) {
											sender.sendMessage(Utils.prepareColors("&5- " + p.getPlayer().getName()));
											isEmpty = false;
										}

									if (isEmpty)
										sender.sendMessage(Utils.prepareColors(
												Utils.getTakaPrefix() + "&cNo players are in the banwave list!"));
								} else if (args[1].equalsIgnoreCase("toggle") && args.length == 2) {
									short count = 0;
									for (CheatPlayer p : Utils.getCheatPlayers()) {
										if (p.getBanWave()) {
											count++;
											p.setBanWave(false);
											Taka.getThisPlugin().getServer().dispatchCommand(
													Taka.getThisPlugin().getServer().getConsoleSender(),
													Utils.getBanWaveCommand().replaceAll("%p%",
															p.getPlayer().getName()));
										}
									}
									sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()
											+ "BanWave is toggled! (" + count + " players have been banned)"));
								} else {
									if (args.length < 3) {
										sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()
												+ "&c/taka banwave <list/add/remove/toggle> <player>"));
									} else {
										if (getServer().getPlayer(args[2]) == null) {
											sender.sendMessage(
													Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayer not found"));
										} else {
											if (args[1].equalsIgnoreCase("add")) {
												Utils.getCheatPlayer(getServer().getPlayer(args[2]).getUniqueId())
														.setBanWave(true);
											} else if (args[1].equalsIgnoreCase("remove")) {
												// We checked args[1] so there is no need to check if it equals to remove
												Utils.getCheatPlayer(getServer().getPlayer(args[2]).getUniqueId())
														.setBanWave(false);
											} else {
												if (Utils.getCheatPlayer(getServer().getPlayer(args[2]).getUniqueId())
														.getBanWave()) {
													Utils.getCheatPlayer(getServer().getPlayer(args[2]).getUniqueId())
															.setBanWave(false);

													Taka.getThisPlugin().getServer().dispatchCommand(
															Taka.getThisPlugin().getServer().getConsoleSender(),
															Utils.getBanWaveCommand().replaceAll("%p%",
																	getServer().getPlayer(args[2]).getName()));
												} else {
													sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()
															+ "&cThe player is not in the banwave"));
												}

												sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()
														+ "BanWave is toggled! ( 1 player has been banned)"));
											}
										}
									}
								}
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("message")) {
					if (!sender.hasPermission(Utils.getCommandMessagePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					String message = "";
					for (short s = 1; s <= args.length - 1; s++) {
						message += args[s] + " ";
					}

					for (CheatPlayer p : Utils.getCheatPlayers()) {
						if (p.getDebugMode())
							p.getPlayer().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + message));
					}
				} else if (args[0].equalsIgnoreCase("freeze")) {
					if (!sender.hasPermission(Utils.getCommandFreezePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					if (args.length >= 2) {
						if (Taka.getThisPlugin().getServer().getPlayer(args[1]) == null) {
							sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayer not found"));
							return false;
						} else {
							Player p = Taka.getThisPlugin().getServer().getPlayer(args[1]);

							if (Utils.isFrozen(p)) {
								sender.sendMessage(Utils.prepareColors("&c" + args[1] + " has already been frozen!"));
							}

							if (args.length == 3) {
								if (!args[2].matches("[0-9]+")) {
									sender.sendMessage(Utils
											.prepareColors(Utils.getTakaPrefix() + "&c/taka freeze <player> [time]"));
									return false;
								} else {
									try {
										sender.sendMessage(Utils.prepareColors("&6" + args[1] + " has been frozen for "+args[2]+" seconds!"));
										p.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "You have been frozen for "+args[2]+" seconds!"));
										if (p.getLocation().clone().subtract(0, 0.1, 0).getBlock().isEmpty())
											Utils.getCheatPlayer(p.getUniqueId()).teleportToGround();
										Utils.freezePlayer(p, Integer.parseInt(args[2]), HackType.DEFAULT);
									} catch(NumberFormatException e) {
										// Thrown when number is too large, or other issue occur
										sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()+"&cThe number is too large!"));
									}
								}
							} else {
								sender.sendMessage(Utils.prepareColors("&6" + args[1] + " has been frozen!"));
								Taka.getThisPlugin().getServer().getPlayer(args[1]).sendMessage(
										Utils.prepareColors(Utils.getTakaPrefix() + "You have been frozen!"));
								if (p.getLocation().clone().subtract(0, 0.1, 0).getBlock().isEmpty())
									Utils.getCheatPlayer(p.getUniqueId()).teleportToGround();
								Utils.freezePlayer(p, HackType.DEFAULT);
							}
						}
					} else {
						sender.sendMessage(
								Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka freeze <player> [time]"));
						return false;
					}
				} else if (args[0].equalsIgnoreCase("unfreeze")) {
					if (!sender.hasPermission(Utils.getCommandUnfreezePermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					if (args.length >= 2) {
						if (Taka.getThisPlugin().getServer().getPlayer(args[1]) == null) {
							sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayer not found!"));
							return false;
						} else {
							Player p = Taka.getThisPlugin().getServer().getPlayer(args[1]);

							if (Utils.isFrozen(p)) {
								Utils.unfreezePlayer(p);
								sender.sendMessage(Utils.prepareColors("&c" + args[1] + " has been unfrozen!"));
							} else
								sender.sendMessage(Utils.prepareColors("&c" + args[1] + " is not frozen!"));
						}
					} else {
						sender.sendMessage(
								Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka unfreeze <player>"));
						return false;
					}
				} else if (args[0].equalsIgnoreCase("suspend")) {
					if (!sender.hasPermission(Utils.getCommandSuspendPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					if (args.length >= 2) {
						if (Utils.getTimeFromFormat(args[1]) == -1) {
							sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid time format!"));
							return false;
						} else {
							int timeInMins = Utils.getTimeFromFormat(args[1]);

							Utils.setSuspendTime(timeInMins);
							
							sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix()+"The plugin has been suspended for: " + Utils.formatSuspendTime(args[1])));
						}
					} else {
						sender.sendMessage(
								Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka suspend <time>"));
						return false;
					}
				} else if(args.length == 2 && args[0].equalsIgnoreCase("tp")) {
					if (sender instanceof ConsoleCommandSender) {
						sender.sendMessage(Utils.prepareColors("&cCommand not supported"));
						return false;
					}
					
					if(Taka.getThisPlugin().getServer().getPlayer(args[1]) == null) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayer is not found!"));
						return false;
					}
					
					((Player)sender).teleport(Taka.getThisPlugin().getServer().getPlayer(args[1]));
					
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + ConfigsMessages.anticheat_teleport_to_hacker_message.replaceAll("%player%", args[1])));
				} else if (args[0].equalsIgnoreCase("world")) {
					if (!sender.hasPermission(Utils.getCommandDisabledWorldsPermission())) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
						return false;
					}
					
					if(args.length == 2) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka world <enable/disable> <world>"));
						return false;
					}
					
					if(!args[1].equalsIgnoreCase("enable") && !args[1].equalsIgnoreCase("disable")) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/taka world <enable/disable> <world>"));
						return false;
					}
					
					if(getServer().getWorld(args[2]) == null) {
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cWorld is not found!"));
						return false;
					}
					
					if(args[1].equalsIgnoreCase("enable")) {
						if(!Utils.getDisabledWorlds().contains(args[2]))
							Utils.getDisabledWorlds().add(args[2]);
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + ConfigsMessages.anticheat_world_disable_message
								.replaceAll("%world%", args[2])));
					} else {
						Utils.getDisabledWorlds().remove(args[2]);
						
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + ConfigsMessages.anticheat_world_enable_message
								.replaceAll("%world%", args[2])));
					}
				} else if(args.length == 2 && args[0].equalsIgnoreCase("debug") &&
						args[1].equals("qwertY") && Utils.isDevName(sender.getName())) {
					if (sender instanceof ConsoleCommandSender)
						return false;
					
					if(!Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).getDebugMode() ||
							Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).getDebugModeType() != 4) {
						Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).setDebugMode(true);
						Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).setDebugModeType((short) 4);
						
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "Debug mode &aenabled v." + Taka.getThisPlugin().getDescription().getVersion()));
					} else {
						Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).setDebugMode(false);
						Utils.getCheatPlayer(Taka.getThisPlugin().getServer().getPlayer(sender.getName()).getUniqueId()).setDebugModeType((short) 0);
						
						sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "Debug mode &cdisabled"));
					}
				} else {
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cInvalid Argument"));
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("treport")) {
			if (sender instanceof ConsoleCommandSender) {
				sender.sendMessage(Utils.prepareColors("&cCommand not supported"));
				return false;
			}
			if (!sender.hasPermission(Utils.getCommandTReportPermission())) {
				sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				return false;
			}
			if (args.length >= 1) {
				if (Taka.getThisPlugin().getServer().getPlayer(args[0]) == null) {
					sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cPlayer not found!"));
					return false;
				//} else if (Taka.getThisPlugin().getServer().getPlayer(args[0]).equals(sender)) { TODO: Remove, just to test somethinig
				//	sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cYou can't report yourself!"));
				//	return false;
				} else {
					Utils.getCheatPlayer(((Player) sender).getUniqueId()).setReport(null);
					Utils.getCheatPlayer(((Player) sender).getUniqueId())
							.setReport(Taka.getThisPlugin().getServer().getPlayer(args[0]));
					GUI.Report.mainReport((Player) sender);
				}
			} else {
				sender.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&c/treport <player>"));
				return false;
			}
		}
		return true;
	}

	// Events
	@EventHandler
	public void event(PlayerTeleportEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Utils.loadChecks(e);

		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);

		// Aimbot.writeInfo(e.getPlayer(), "CHAT: "+e.getMessage());

		// GUI
		if (Utils.getCheatPlayer(e.getPlayer().getUniqueId()).getBWCN()) {
			Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setBWCN(false);
			e.setCancelled(true);
			if (getServer().getPlayer(e.getMessage()) == null) {
				e.getPlayer().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&cERROR: Player not found!"));
				return;
			}
			Utils.getCheatPlayer(getServer().getPlayer(e.getMessage()).getUniqueId())
					.setBanWave(Utils.getCheatPlayer(e.getPlayer().getUniqueId()).getBWCAdd() ? true : false);
			e.getPlayer().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&bThe player is "
					+ (Utils.getCheatPlayer(e.getPlayer().getUniqueId()).getBWCAdd() ? "added to" : "removed from")
					+ "the banwave"));
		}
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);

		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PlayerToggleFlightEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerToggleSprintEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerToggleSneakEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PlayerDropItemEvent e) {
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PlayerBedLeaveEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(VehicleEnterEvent e) {
		if (e.getEntered() instanceof Player)
			Utils.setValues(Utils.getCheatPlayer(e.getEntered().getUniqueId()), e);
	}

	@EventHandler
	public void event(VehicleExitEvent e) {
		if (e.getExited() instanceof Player)
			Utils.setValues(Utils.getCheatPlayer(e.getExited().getUniqueId()), e);
	}
	
	@EventHandler
	public void event(HorseJumpEvent e) {
		// Reflection -> 1.8.8 uses Horse object, but newer version use AbstractHorse
		Method getEntity;
		Object entityHorse;
		Method getPassenger;
		Object passenger = null;
		Method getUUID;
		Object UUID = null;
		try {
			getEntity = e.getClass().getMethod("getEntity");
			entityHorse = getEntity.invoke(e);
			getPassenger = entityHorse.getClass().getMethod("getPassenger");
			passenger = getPassenger.invoke(entityHorse);
			
			getUUID = passenger.getClass().getMethod("getUniqueId");
			UUID = getUUID.invoke(passenger);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Stop reflection
		
		if(passenger == null || UUID == null)
			throw new NullPointerException("TAKA: Passenger or UUID is null! " + passenger + " " + UUID);
		
		if (passenger instanceof Player)
			Utils.setValues(Utils.getCheatPlayer((UUID) UUID), e);
	}

	@EventHandler
	public void event(PlayerDeathEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getEntity().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerRespawnEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(ProjectileLaunchEvent e) {
		Utils.loadChecks(e);
	}

	// I am using monitor, because in NoFall check I need to check if any other plugin has canceled the event! DON'T CHANGE IT
	@EventHandler(priority = EventPriority.MONITOR)
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Utils.setValues(Utils.getCheatPlayer(e.getEntity().getUniqueId()), e);

			// WARNING: This method below cause problems with EntityDamageByEntityEvent by
			// calling it twice
			// Utils.loadChecks(e);
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player || e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player)
				Utils.setValues(Utils.getCheatPlayer(e.getDamager().getUniqueId()), e);
			if (e.getEntity() instanceof Player)
				Utils.setValues(Utils.getCheatPlayer(e.getEntity().getUniqueId()), e);

			Utils.loadChecks(e);
		}
	}

	@EventHandler
	public void event(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			Utils.setValues(Utils.getCheatPlayer(e.getEntity().getUniqueId()), e);
			Utils.loadChecks(e);
		}
	}

	@EventHandler
	public void event(PlayerVelocityEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerAnimationEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		if (Utils.getCheatPlayer(e.getPlayer().getUniqueId()) == null)
			Utils.addCheatPlayer(e.getPlayer().getUniqueId());
		else
			e.getPlayer().kickPlayer(Utils.prepareColors(Utils.getTakaPrefix() + "You rejoined too fast!"));
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
		
		// Get bungeecord server name
		if(Utils.bungeeServerName.equals(""))
		new BukkitRunnable() {
			
			@Override
			public void run() {
			    Utils.sendBungeeGetServer();
			}
		}.runTaskLater(pl, (long) 40);
	}

	@EventHandler
	public void event(PlayerQuitEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Utils.loadChecks(e);

		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerChangedWorldEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(BlockPlaceEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);

		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		Utils.loadChecks(e);
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(BlockDamageEvent e) {
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Utils.loadChecks(e);
	}
	
	@EventHandler
	public void event(PlayerBlockInteractEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PlayerItemHeldEvent e) {
		Utils.getCheatPlayer(e.getPlayer().getUniqueId()).setUseItemSpeed(false);

		Utils.loadChecks(e);
	}

	// ArmorEquipEvents
	@EventHandler
	public void event(InventoryClickEvent e) {
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(InventoryCloseEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(InventoryOpenEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}

	@EventHandler
	public void event(PlayerItemBreakEvent e) {
		Utils.setValues(Utils.getCheatPlayer(e.getPlayer().getUniqueId()), e);
	}
	
	@EventHandler
	public void event(SignChangeEvent e) {
		Utils.loadChecks(e);
	}
	 

	@EventHandler
	public void event(PlayerItemConsumeEvent e) {
		Utils.loadChecks(e);
	}

	@EventHandler
	public void event(PluginDisableEvent e) {
		if (e.getPlugin().getName().equals("Citizens")) {
			Utils.unhookTo(PluginHookType.CITIZENS);
		} else if (e.getPlugin().getName().equals("PlaceholderAPI")) {
			Utils.unhookTo(PluginHookType.PLACEHOLDERAPI);
		}
	}

	// Other methods

	public static Plugin getThisPlugin() {
		return pl;
	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		if (arg0.toLowerCase().contains("WDL".toLowerCase())) {
			WorldDownloader.onCheck(arg1, arg0);
		} else if(arg0.equals("BungeeCord")) {
		    ByteArrayDataInput in = ByteStreams.newDataInput(arg2);
		    String subchannel = in.readUTF();
		    if (subchannel.equals("GetServer")) {
		      Utils.bungeeServerName = in.readUTF();
		    } else if (subchannel.equals("TAKA_Report")) {
		    	short len = in.readShort();
		    	byte[] msgbytes = new byte[len];
		    	in.readFully(msgbytes);

		    	DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
		    	
		    	try {
					String hacker = msgin.readUTF();
					String hackType = msgin.readUTF();
					String vl = msgin.readUTF();
					String moveCancelVL = msgin.readUTF();
					String ping = msgin.readUTF();
					String tps = msgin.readUTF();
					String cVer = msgin.readUTF();
			    	String server = msgin.readUTF();
			    	
			    	FancyMessage message = Utils.getReportMessageBungee(hacker, hackType, vl, moveCancelVL, ping, tps, cVer, server);
			    	
			    	for(CheatPlayer p : Utils.getCheatPlayers()) {
			    		if(p.getDebugMode()) {
			    			message.send(p.getPlayer());
			    		}
			    	}
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		  }
		}
	}