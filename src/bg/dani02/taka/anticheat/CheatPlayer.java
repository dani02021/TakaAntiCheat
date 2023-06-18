package bg.dani02.taka.anticheat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.EvictingQueue;
import com.google.common.primitives.Doubles;

import bg.dani02.taka.anticheat.api.PlayerCheatEvent;
import bg.dani02.taka.anticheat.api.PlayerCheatThresholdEvent;
import bg.dani02.taka.anticheat.api.PlayerTACTeleportEvent;
import bg.dani02.taka.anticheat.api.PlayerViolationEvent;
import bg.dani02.taka.anticheat.checks.movement.Timer;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.LogMessage;
import bg.dani02.taka.anticheat.enums.MoveDirectionType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import mkremins.fanciful.FancyMessage;

@SuppressWarnings("deprecation")
public class CheatPlayer implements Cloneable {

	private Player p;

	private long str = 0L;
	private long ldt = 0L;
	private long lat = 0L;
	private long lst = 0L;
	private long lft = 0L;
	private long lct = 0L;
	private long lcct = 0L;
	private long lvet = 0L;
	private long lvext = 0L;
	private long ltt = 0L;
	private long lrt = 0L;
	private long lvt = 0L;
	private long ljt = 0L;
	private long lbt = 0L;
	private long cnt = 0L;
	private long lpit = 0L;
	private long opit = 0L;
	private long lbit = 0L;
	private long lret = 0L;
	private long stlt = 0L;
	private long lmt = 0L;
	private long lfaebt = 0L;
	private long lswbt = 0L;
	private long lfest = 0L;
	private long lsst = 0L;
	private long lset = 0L;
	private long fpb = 0L;
	private long lget = 0L;
	private long lgst = 0L;
	private long laawt = 0L;
	private long lveit = 0L;
	private long lblt = 0L;
	private long lditcd = 0L;
	private long lcdbt = 0L;
	private long lhjt = 0L;
	private long lttt = 0L;
	private long lptt = 0L;
	private long lbpt = 0L;
	private long lpcst = 0L;
	private long lbbt = 0L;
	private long lppt = 0L;
	private long licp = 0L;
	private long lkapt = 0L;
	private long lcpbt = 0L;
	private long lstt = 0L;
	private long lswt = 0L;
	private long lspt = 0L;
	private long ltht = 0L;
	private long lbot = 0L;
	private long ldet = 0L;
	private long kmitt = 0L;
	private long kmit = 0L;
	private long last = 0L;
	private long lsmt = 0L;
	private long lbmt = 0L;
	private long lpet = 0L;
	private long lsbbt = 0L;
	private long ttime = 0L;
	private long lbctt = 0L;
	private long lmlt = 0L;
	private long latt = 0L;
	private long fpbt = 0L;
	private long llmt = 0L;
	private long lfat = 0L;
	private long swlt = 0L;
	private long lvmt = 0L;
	private long cbt = 0L;
	private long cbrt = 0L;
	private long lsplt = 0L;
	private long lssvt = 0L;
	private long lmcmmofdet = 0L;
	private long lmcmmoat = 0;

	private short faescvl = 0;
	private short lsvl = 0;
	private short dmt = 3;
	private short nfwt = 0;
	private long sct = 0;
	private short ljc = 0;
	private short jvl = 0;
	private short lscwvl = 0;
	private short limvl = 0;
	private short dicd = 0;
	private short swpb = 0;
	private short sc = 0;
	private short tc = 0;
	private short bc = 0;
	private short stp = 0;
	private short ripn = 0;
	private short uepn = 0;
	private short cppn = 0;
	private short cnbb = 0;
	private short mp = 0;
	private short mpt = 0;

	private Material lptty = Material.AIR;
	private Material lbbty;
	
	private Block lsbb;

	private Achievement laawty;

	private InventoryType licty;

	private ItemStack lpi;

	private Vector lv = new Vector(0, 0, 0);
	private Vector lssvv = new Vector(0, 0, 0);
	private Vector spv = new Vector(0, 0, 0);

	private HackType fr;

	private TeleportCause latty;

	private MoveDirectionType lmdty;

	private HashMap<String, Short> vl1 = new HashMap<String, Short>();

	private HashMap<String, Short> vl2 = new HashMap<String, Short>();

	private HashSet<HackType> checksDisabled = new HashSet<HackType>();

	private Entity ld;
	private Player gr;

	private String guilct = null;
	private String lsr = "";
	
	private Collection<PotionEffect> lapety = null;
	
	private EvictingQueue<Double> ta = EvictingQueue.create(Timer.QUEUE_SIZE);

	private Location lgl;
	private Location lfl;
	private Location ttl;
	private Location bpl;
	private Location[] lftl;

	private boolean f;
	private boolean dm;
	private boolean bw;
	private boolean tb;
	private boolean jk;
	private boolean opi;
	private boolean ifb;
	private boolean ljp;
	private boolean nfd;
	private boolean nsa;
	private boolean uis;
	private boolean sch;
	private boolean kmi;
	private boolean bob;
	private boolean bwcn;
	private boolean lastl;
	private boolean opift;
	private boolean bwcadd;
	
	private boolean sprinting;
	private boolean blocking;
	private boolean cantSwim;
	private boolean cantRiptide;

	private double sm;
	private double ups;
	private double fdd;
	private double std;
	private double td;
	private double samd;

	private float sfd;
	
	private short hackerProbCount;

	public CheatPlayer(Player pl) {
		p = pl;

		sfd = 0F;

		licty = InventoryType.CRAFTING;
		
		lbbty = pl.getLocation().getBlock().getType();

		lgl = p.getLocation();

		lftl = new Location[2];

		lftl[0] = pl.getLocation();
		lftl[1] = pl.getLocation();

		if (pl.getItemOnCursor() != null)
			lpi = pl.getItemOnCursor();
		else
			lpi = new ItemStack(Material.AIR);

		for (HackType type : HackType.values()) {
			addViolation(type, ViolationType.CANCELMOVE, (short) 0);
			addViolation(type, ViolationType.THRESHOLD, (short) 0);
		}
		
		if (pl.hasPermission(Utils.getAdminPermission())) {
			dm = ConfigsMessages.anticheat_debug_default;
			dmt = (short) ConfigsMessages.anticheat_debug_mode_default;
		}
		
	}

	public Player getPlayer() {
		return p;
	}

	public boolean reportToAdmin(HackType type) {
		// If cancelMove is reached then add VL to the threshold
		// Probably add reset option when cancelMove is reached?
		
		// Just to be sure
		if (this.haveBypass(type))
			return false;
		
		boolean passMoveCancelVL = false;

		if (HackType.isSpecialCheck(type)) {
			// Add vl here, because of the api, if event is canceled remove the vl
			if (Short.parseShort(Utils.getMoveCancelViolation(type)) >= getViolation(ViolationType.CANCELMOVE).get(HackType.getName(type)))
				addViolation(type, ViolationType.CANCELMOVE, (short) 1);
			
			FancyMessage message = Utils.getReportMessage(p.getName(), HackType.getName(type), (getViolation(ViolationType.CANCELMOVE).get(HackType.getName(type))), Utils.getMoveCancelViolation(type), Short.toString(getMCPing()), Utils.getClientVersion(this).name());
			
			PlayerViolationEvent cheat = new PlayerViolationEvent(p, type, message.toOldMessageFormat());
			Taka.getThisPlugin().getServer().getPluginManager().callEvent(cheat);
			if (cheat.isCancelled()) {
				removeViolation(type, ViolationType.CANCELMOVE, (short) 1);
				
				return false;
			}
			
			if (Short.parseShort(Utils.getMoveCancelViolation(type)) <= getViolation(ViolationType.CANCELMOVE)
					.get(HackType.getName(type))) {
				PlayerCheatEvent c = new PlayerCheatEvent(p, type, message.toOldMessageFormat());
				Taka.getThisPlugin().getServer().getPluginManager().callEvent(c);
				
				if(c.isCancelled()) {
					removeViolation(type, ViolationType.CANCELMOVE, (short) 1);
					
					return false;
				}
				
				addViolation(type, ViolationType.THRESHOLD, (short) 1);
				removeViolation(type, ViolationType.CANCELMOVE, getViolation(ViolationType.CANCELMOVE).get(HackType.getName(type)));
				
				passMoveCancelVL = true;
				
				// Hacker probability
				hackerProbCount++;
			}
			
			// Show message only if the check is not ghosted, see more on HackType::isCheckGhosted
			if(!HackType.isCheckGhosted(type)) {
				for (CheatPlayer p : Utils.getCheatPlayers()) {
					if (p.getDebugMode()) {
						if (p.getDebugModeType() == 1 || p.getDebugModeType() == 4) { // 4 = DEV TEST
							p.logMessage(LogMessage.VERBOSE, message);
						} else if (p.getDebugModeType() == 2) {
							if (passMoveCancelVL) {
								p.logMessage(LogMessage.VERBOSE, message);
							}
						} else if (p.getDebugModeType() == 3) {
							if (passMoveCancelVL &&
									System.currentTimeMillis() - p.getLastLogMessageTime() > Utils.getVerbose3ModeTiming())
								p.logMessage(LogMessage.VERBOSE, message);

							p.setLastLogMessageTime(System.currentTimeMillis());
						}
					}
				}
				
				Utils.sendReportMessageToPluginChanel(p.getName(), HackType.getName(type), Integer.toString((getViolation(ViolationType.CANCELMOVE).get(HackType.getName(type)))), Utils.getMoveCancelViolation(type), Short.toString(getMCPing()), Utils.getTPSFormatted(), Utils.getClientVersion(this).name(), Utils.bungeeServerName);
				
				if (ConfigsMessages.log_file_enable)
						Utils.addMessageToFileLog(p, message.toOldMessageFormat());
				
				if (ConfigsMessages.log_console_enable &&
							Short.parseShort(
									Utils.getMoveCancelViolation(type)) <= getViolation(ViolationType.CANCELMOVE).get(HackType.getName(type)))
						if(ConfigsMessages.log_console_use_colors)
							Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(Utils.prepareColors(message.toOldMessageFormat()));
						else Taka.getThisPlugin().getServer().getConsoleSender().sendMessage(Utils.removeColors(message.toOldMessageFormat()));
			}
			
			runThreshold(type);
			
			// For test purposes beyond cancelmove violation reached
			if(Utils.isDevTest(this))
				return false;
			
			if(ConfigsMessages.ban_wave_enable && Utils.getHackerProbability(this) > ConfigsMessages.ban_wave_hack_prob)
				setBanWave(true);
			
			return passMoveCancelVL;
		}
		
		return false;
	}

	public boolean reportToAdmin(HackType type, String debugInformation) {
		if(Utils.isDevTest(this))
			Utils.debugMessage(Utils.prepareColors("&6DEV TEST: " + debugInformation));
		
		return reportToAdmin(type);
	}

	public boolean reportToAdmin(HackType type, short vl) {
		if (haveBypass(type))
			return false;
		
		if (HackType.isSpecialCheck(type)) {
			if (Integer.parseInt(Utils.getMoveCancelViolation(type)) <= getViolation(ViolationType.CANCELMOVE)
					.get(HackType.getName(type))) // TODO: Add reset
				addViolation(type, ViolationType.THRESHOLD, (short) 1);
			else
				addViolation(type, ViolationType.CANCELMOVE, vl);
		}
		
		return reportToAdmin(type);
	}
	
	@SuppressWarnings("unchecked")
	public void runThreshold(HackType type) {
		final Iterator<Map.Entry<String, Object>> iterator = Utils.getThreshold(type).entrySet().iterator();
		// LOL THE PROBLEM COMES WHEN TRY TO GET VALUES
		// p.getPlayer().sendMessage(""+Taka.getThisPlugin().getConfig().getConfigurationSection("Fly.StartStableY.threshold").getValues(true)+"
		// DIFF TO:
		// "+Taka.getThisPlugin().getConfig().getConfigurationSection("Fly.DoubleJump.Down.threshold").getValues(true));
		while (iterator.hasNext()) {
			Map.Entry<String, Object> h = iterator.next();
			// Multi command support
			String s = h.getKey();
			
			if (s.equalsIgnoreCase(
					Short.toString(getViolation(ViolationType.THRESHOLD).get(HackType.getName(type))))) {
				ArrayList<String> commands = new ArrayList<String>();

				if (h.getValue() instanceof String) {
					commands.add((String) h.getValue());
				} else if (h.getValue() instanceof ArrayList<?>) {
					commands.addAll((List<String>) h.getValue());
				}
				// Some commands don't works on async
				new BukkitRunnable() {

					@Override
					public void run() {
						for (String com : commands) {
							PlayerCheatThresholdEvent event = new PlayerCheatThresholdEvent(p, type, com);
							Taka.getThisPlugin().getServer().getPluginManager().callEvent(event);
							
							if(event.isCancelled())
								continue;
							
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
									Utils.setPlaceholders(p,
											com.replaceAll("%player%|%p%", p.getName())
													.replaceAll("%hack%|%h%", HackType.getName(type)).replaceAll("%vl%",
															Short.toString(getViolation(ViolationType.CANCELMOVE)
																	.get(HackType.getName(type))))));
							if (ConfigsMessages.log_file_enable) {
								Utils.addMessageToFileLog(p,
										"Command performed: " + com.replaceAll("%player%|%p%", p.getName())
												.replaceAll("%hack%|%h%", HackType.getName(type)).replaceAll("%vl%",
														Short.toString(getViolation(ViolationType.CANCELMOVE)
																.get(HackType.getName(type)))));
							}
						}
					}
				}.runTask(Taka.getThisPlugin());
			}
		}
	}

	public void logMessage(LogMessage lm, String msg) {
		p.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + LogMessage.getName(lm) + "&7" + msg));
	}
	
	public void logMessage(LogMessage lm, FancyMessage message) {
		Taka.getThisPlugin().getServer().dispatchCommand(Taka.getThisPlugin().getServer().getConsoleSender(), "tellraw "
				+ p.getName() + " [{\"text\":\"" + Utils.prepareColors(Utils.getTakaPrefix() + LogMessage.getName(lm) + "&7\"},") + " " + message.toJSONString() + "]");
	}

	public short getMCPing() {
		try {
			short ping = 0;
			final Class<?> craftPlayer = Class.forName(Utils.getServerClass() + ".entity.CraftPlayer");
			final Object converted = craftPlayer.cast(p);
			final Method handle = converted.getClass().getMethod("getHandle");
			final Object entityPlayer = handle.invoke(converted);
			final Field pingField = entityPlayer.getClass().getField("ping");
			ping = (short) pingField.getInt(entityPlayer);
			return ping;
		} catch (Exception ex) {
			return -1;
		}
	}
	
	public int getClientVersion() {
		return Utils.getProtocolManager().getProtocolVersion(p);
	}

	public boolean haveBypass() {
		return p.hasPermission(Utils.getBypassPermission());
	}

	public boolean haveBypass(HackType h) {
		return (p.hasPermission(Utils.getBypassPermission() + "." + HackType.removeCheckType(h))
				|| p.hasPermission(Utils.getBypassPermission()));
	}

	public Player getReport() {
		return gr;
	}

	public void setReport(Player p2) {
		gr = p2;
	}

	public boolean getFreezed() {
		return f;
	}

	public void setFreezed(boolean freeze) {
		f = freeze;
	}

	public HackType getFreezedReason() {
		return fr;
	}

	public void setFreezedReason(HackType reason) {
		fr = reason;
	}

	public boolean hasAchievement(Achievement ac) {
		try {
			if (Utils.getServerVersion() <= 4) {
				Method hasAchievement = Player.class.getMethod("hasAchievement", Achievement.class);
				
				return (boolean) hasAchievement.invoke(this.getPlayer(), ac);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void removeAchievement(Achievement ac) {
		try {
			if (Utils.getServerVersion() <= 4) {
				Method removeAchievement = Player.class.getMethod("removeAchievement", Achievement.class);
				
				removeAchievement.invoke(this.getPlayer(), ac);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getLastChatTime() {
		return lct;
	}

	public void setLastChatTime(long time) {
		lct = time;
	}

	public long getLastCommandTime() {
		return lcct;
	}

	public void setLastCommandTime(long time) {
		lcct = time;
	}

	public long getLastTeleportTime() {
		return ltt;
	}

	public void setLastTeleportTime(long time) {
		ltt = time;
	}

	public long getLastDamageTime() {
		return ldt;
	}

	public void setLastDamageTime(long time) {
		ldt = time;
	}
	
	public Entity getLastDamager() {
		return ld;
	}

	public void setLastDamager(Entity damager) {
		ld = damager;
	}

	public long getLastDeathTime() {
		return ldet;
	}

	public void setLastDeathTime(long time) {
		ldet = time;
	}
	
	public long getLastRespawnTime() {
		return lret;
	}

	public void setLastRespawnTime(long time) {
		lret = time;
	}

	public long getLastAttackTime() {
		return lat;
	}

	public void setLastAttackTime(long time) {
		lat = time;
	}

	public long getLastFlyTime() {
		return lft;
	}

	public void setLastFlyTime(long time) {
		lft = time;
	}

	public long getLastSpawnTime() {
		return lst;
	}

	public void setLastSpawnTime(long time) {
		lst = time;
	}

	public long getLastSprintStartTime() {
		return lsst;
	}

	public void setLastSprintStartTime(long time) {
		lsst = time;
	}

	public long getLastSprintEndTime() {
		return lset;
	}

	public void setLastSprintEndTime(long time) {
		lset = time;
	}

	public long getLastVelocityTime() {
		return lvt;
	}

	public void setLastVelocityTime(long time) {
		lvt = time;
	}

	public Vector getLastVelocity() {
		return lv;
	}

	public void setLastVelocity(Vector vec) {
		lv = vec;
	}
	
	public Vector getStrafePrevVector() {
		return spv;
	}

	public void setStrafePrevVector(Vector vec) {
		spv = vec;
	}

	public long getLastFoodEatStartTime() {
		return lfest;
	}

	public void setLastFoodEatStartTime(long time) {
		lfest = time;
	}
	
	public long setLastPotionConsumeStartTime() {
		return lpcst;
	}

	public void setLastPotionConsumeStartTime(long time) {
		lpcst = time;
	}
	
	public long getLastBubbleColumnTouchTime() {
		return lbctt;
	}
	
	public void setLastBubbleColumnTouchTime(long time) {
		lbctt = time;
	}
	
	public long getLastActivePotionEffectsTime() {
		return lpet;
	}

	public void setLastActivePotionEffectsTime(long time) {
		lpet = time;
	}
	
	public Collection<PotionEffect> getLastActivePotionEffectTypes() {
		return lapety;
	}

	public void setLastActivePotionEffectTypes(Collection<PotionEffect> types) {
		lapety = types;
	}
	
	public long getLastBedLeaveTime() {
		return lblt;
	}

	public void setLastBedLeaveTime(long time) {
		lblt = time;
	}

	public long getLastPickupItemTime() {
		return lpit;
	}

	public void setLastPickupItemTime(long time) {
		lpit = time;
	}

	public void setLastPickupItem(ItemStack type) {
		lpi = type;
	}

	public long getLastAutoSoupTime() {
		return last;
	}

	public void setLastAutoSoupTime(long time) {
		last = time;
	}

	public boolean getLastAutoSoupLegal() {
		return lastl;
	}

	public void setLastAutoSoupLegal(boolean legal) {
		lastl = legal;
	}

	public long getLastAchievementAwardTime() {
		return laawt;
	}

	public void setLastAchievementAwardTime(long time) {
		laawt = time;
	}

	public Achievement getLastAchievementAwardType() {
		return laawty;
	}

	public void setLastAchievementAwardType(Achievement type) {
		laawty = type;
	}

	public Location getLastGroundLocation() {
		return lgl;
	}

	public long getLastBrokeItemTime() {
		return lbit;
	}

	public void setLastBrokeItemTime(long time) {
		lbit = time;
	}
	
	public void setLastPositionPacketTime(long time) {
		lppt = time;
	}
	
	public long getLastPositionPacketTime() {
		return lppt;
	}
	
	public void setLastKeepAlivePacketTime(long time) {
		lkapt = time;
	}
	
	public long getLastKeepAlivePacketTime() {
		return lkapt;
	}

	public long getLastTacTeleportTime() {
		return lttt;
	}

	public void setLastTacTeleportTime(long time) {
		lttt = time;
	}
	
	public long getLastVehicleTime() {
		return lveit;
	}

	public void setLastVehicleTime(long time) {
		lveit = time;
	}
	
	public long getLastVehicleEnterTime() {
		return lvet;
	}

	public void setLastVehicleEnterTime(long time) {
		lvet = time;
	}
	
	public long getLastVehicleExitTime() {
		return lvext;
	}

	public void setLastVehicleExitTime(long time) {
		lvext = time;
	}

	public long getLastMoveTime() {
		return lmt;
	}

	public void setLastMoveTime(long time) {
		lmt = time;
	}
	
	public long getLastMoveLookTime() {
		return lmlt;
	}

	public void setLastMoveLookTime(long time) {
		lmlt = time;
	}
	
	public long getLastBlinkTime() {
		return lbt;
	}

	public void setLastBlinkTime(long time) {
		lbt = time;
	}

	public long getLDITCD() {
		return lditcd;
	}

	public void setLDITCD(long time) {
		lditcd = time;
	}

	public long getLastAllTeleportTime() {
		return latt;
	}

	public void setLastAllTeleportTime(long time) {
		latt = time;
	}

	public TeleportCause getLastAllTeleportType() {
		return latty;
	}

	public void setLastAllTeleportType(TeleportCause tc) {
		latty = tc;
	}

	public long getSTR() {
		return str;
	}

	public void setSTR(long time) {
		str = time;
	}

	public long getLastCDBlockTime() {
		return lcdbt;
	}

	public void setLastCDBlockTime(long time) {
		lcdbt = time;
	}

	public short getMovePacket() {
		return mp;
	}

	public void setMovePacket(short number) {
		mp = number;
	}

	public void addMovePacket(short number) {
		mp += number;
	}

	public void removeMovePacket(short number) {
		mp -= number;
	}
	
	public short getMovePacketTimer() {
		return mpt;
	}

	public void setMovePacketTimer(short number) {
		mpt = number;
	}

	public void addMovePacketTimer(short number) {
		mpt += number;
	}

	public void removeMovePacketTimer(short number) {
		mpt -= number;
	}
	
	public long getLastThrowTime() {
		return ltht;
	}
	
	public void setLastThrowTime(long time) {
		ltht = time;
	}
	
	public long getLastBowTime() {
		return lbot;
	}
	
	public void setLastBowTime(long time) {
		lbot = time;
	}
	
	public long getLastSwimingTime() {
		return lswt;
	}
	
	public void setLastSwimingTime(long time) {
		lswt = time;
	}
	
	public short getThrowCount() {
		return tc;
	}
	
	public void setThrowCount(int count) {
		tc = (short) count;
	}
	
	public short getBowCount() {
		return bc;
	}
	
	public void setBowCount(int count) {
		bc = (short) count;
	}
	
	public boolean getThrowBlock() {
		return tb;
	}
	
	public void setThrowBlock(boolean block) {
		tb = block;
	}
	
	public boolean getBowBlock() {
		return bob;
	}
	
	public void setBowBlock(boolean block) {
		bob = block;
	}

	public long getFPB() {
		return fpb;
	}

	public void setFPB(long time) {
		fpb = time;
	}

	public long getFPTime() {
		return fpbt;
	}

	public void setFPTime(long time) {
		fpbt = time;
	}

	public short getDICD() {
		return dicd;
	}

	public void addDICD(short number) {
		dicd += number;
	}

	public void removeDICD(short number) {
		dicd -= number;
	}

	public long getTTime() {
		return ttime;
	}

	public void addTTime(short number) {
		ttime += number;
	}

	public void setTTime(long time) {
		ttime = time;
	}

	public long getLastGlideStartTime() {
		return lgst;
	}

	public void setLastGlideStartTime(long time) {
		lgst = time;
	}

	public long getLastGlideEndTime() {
		return lget;
	}

	public void setLastSneakToggleTime(long time) {
		lstt = time;
	}

	public long getLastSneakToggleTime() {
		return lstt;
	}

	public long getLastBlockPlaceTime() {
		return lbpt;
	}

	public void setLastBlockPlaceTime(long time) {
		lbpt = time;
	}

	public long getLastBlockBreakTime() {
		return lbbt;
	}

	public void setLastBlockBreakTime(long time) {
		lbbt = time;
	}
	
	public Material getLastBlockBreakType() {
		return lbbty;
	}

	public void setLastBlockBreakType(Material last) {
		lbbty = last;
	}
	
	public Block getLastStartBlockBreak() {
		return lsbb;
	}

	public void setLastStartBlockBreak(Block b) {
		lsbb = b;
	}
	
	public long getLastStartBlockBreakTime() {
		return lsbbt;
	}
	
	public void setLastStartBlockBreakTime(long time) {
		lsbbt = time;
	}

	public void setLastGlideEndTime(long time) {
		lget = time;
	}

	public long getLastFAEBlockTime() {
		return lfaebt;
	}

	public void setLastFAEBlockTime(long time) {
		lfaebt = time;
	}

	public long getLastSWBlockTime() {
		return lswbt;
	}

	public void setLastSWBlockTime(long time) {
		lswbt = time;
	}

	public long getSCT() {
		return sct;
	}

	public void setSCT(long time) {
		sct = time;
	}

	public long getLastSpeedPotionTime() {
		return lspt;
	}

	public void setLastSpeedPotionTime(long time) {
		lspt = time;
	}

	public void setLastFallTime(long time) {
		lfat = time;
	}

	public long getLastFallTime() {
		return lfat;
	}

	public long getLastFAESCTime() {
		return lfaebt;
	}

	public void setLastFAESCTime(long time) {
		lfaebt = time;
	}

	public short getFAESCVL() {
		return faescvl;
	}

	public void setFAESCVL(short vl) {
		faescvl = vl;
	}

	public void addFAESCVL(short vl) {
		faescvl += vl;
	}
	
	public short getHackerProbabilityCount() {
		return hackerProbCount;
	}

	public double getSAMoveDisstance() {
		return samd;
	}

	public void setSAMoveDisstance(double dist) {
		samd = dist;
	}

	public double getLastSMTime() {
		return lsmt;
	}

	public void setLastSMTime(long time) {
		lsmt = time;
	}

	public boolean getLJP() {
		return ljp;
	}

	public void setLJP(boolean pass) {
		ljp = pass;
	}

	public short getLJC() {
		return ljc;
	}

	public void setLJC(int count) {
		ljc = (short) count;
	}

	public boolean getIFB() {
		return ifb;
	}

	public void setIFB(boolean block) {
		ifb = block;
	}

	public boolean getJK() {
		return jk;
	}

	public void setJK(boolean kick) {
		jk = kick;
	}

	public String getGUILCT() {
		return guilct;
	}

	public void setGUILCT(String type) {
		guilct = type;
	}

	public short getRIPN() {
		return ripn;
	}

	public void setRIPN(short time) {
		ripn = time;
	}

	public short getUEPN() {
		return uepn;
	}

	public void setUEPN(short time) {
		uepn = time;
	}

	public short getCPPN() {
		return cppn;
	}

	public void setCPPN(short time) {
		cppn = time;
	}

	public long getLBMT() {
		return lbmt;
	}

	public void setLBMT(long time) {
		lbmt = time;
	}

	public void setLastFallingLocation(Location fall) {
		lfl = fall;
	}

	public Location getLastFallingLocation() {
		return lfl;
	}
	
	public void setBlinkPrevLocation(Location prev) {
		bpl = prev;
	}

	public Location getBlinkPrevLocation() {
		return bpl;
	}

	public double getUpwardsSpeed() {
		return ups;
	}

	public void addUpwardsSpeed(double speed) {
		ups += speed;
	}

	public void removeUpwardsSpeed(double speed) {
		ups -= speed;
	}

	public short getSneakCount() {
		return sc;
	}

	public void addSneakCount(short count) {
		sc += count;
	}

	public void removeSneakCount(short count) {
		sc -= count;
	}

	public boolean getUseItemSpeed() {
		return uis;
	}

	public void setUseItemSpeed(boolean use) {
		uis = use;
	}

	public boolean getBWCN() {
		return bwcn;
	}

	public void setBWCN(boolean use) {
		bwcn = use;
	}

	public boolean getBWCAdd() {
		return bwcadd;
	}

	public void setBWCAdd(boolean add) {
		bwcadd = add;
	}
	
	public boolean getSCH() {
		return sch;
	}

	public void setSCH(boolean collision) {
		sch = collision;
	}

	public long getLastInvCloseTime() {
		return licp;
	}

	public void setLastInvCloseTime(long time) {
		licp = time;
	}

	public InventoryType getLastInvCloseType() {
		return licty;
	}

	public void setLastInvCloseType(InventoryType inventoryType) {
		licty = inventoryType;
	}

	public long getLastJoinTime() {
		return ljt;
	}

	public void setLastJoinTime(long time) {
		ljt = time;
	}

	public boolean getOpenPlayerInventory() {
		return opi;
	}

	public void setOpenPlayerInventory(boolean open) {
		opi = open;
	}

	public long getOpenPlayerInventoryTime() {
		return opit;
	}

	public void setOpenPlayerInventoryTime(long time) {
		opit = time;
	}

	public boolean getOpenPlayerInventoryFT() {
		return opift;
	}

	public void setOpenPlayerInventoryFT(boolean open) {
		opift = open;
	}

	public MoveDirectionType getLastMoveDirectionType() {
		return lmdty;
	}

	public void setLastMoveDirectionType(MoveDirectionType type) {
		lmdty = type;
	}

	public Material getLastPlateTouchType() {
		return lptty;
	}

	public void setLastPlateTouchType(Material type) {
		lptty = type;
	}

	public void setLastCanceledPlaceBlockTime(long time) {
		lcpbt = time;
	}

	public long getLastCanceledPlaceBlockTime() {
		return lcpbt;
	}

	public long getLastPlateTouchTime() {
		return lptt;
	}
	
	public long getLastRiptileTime() {
		return lrt;
	}

	public void setLastRiptileTime(long time) {
		lrt = time;
	}
	
	public long getLastHorseJumpTime() {
		return lhjt;
	}

	public void setLastHorseJumpTime(long time) {
		lhjt = time;
	}
	
	public long getLastServerSetVelocityTime() {
		return lssvt;
	}

	public void setLastServerSetVelocityTime(long time) {
		lssvt = time;
	}
	
	public Vector getLastServerSetVelocityVector() {
		return lssvv;
	}

	public void setLastServerSetVelocityVector(Vector vec) {
		lssvv = vec;
	}

	public void setLastPlateTouchTime(long time) {
		lptt = time;
	}

	public ItemStack getLastPickupItem() {
		return lpi;
	}

	public void setLastGroundLocation(Location loc) {
		//Utils.debugMessage("SPEED_GROUND: &6" + Utils.get3DXZDisstance(getLastGroundLocation(), loc) + " " + p.isSprinting());
		lgl = loc;
	}

	public Location[] getLastFromToLocations() {
		return lftl;
	}

	public void setLastFromToLocations(Location from, Location to) {
		lftl[0] = from;
		lftl[1] = to;
	}
	
	public void setLastSignPlaceTime(long time) {
		lsplt = time;		
	}
	
	public long getLastSignPlaceTime() {
		return lsplt;
	}

	public void setLegitSpeedVL(short vl, String reason) {
		lsvl = vl;
		lsr = reason;
	}

	public short getLegitSpeedVL() {
		return lsvl;
	}
	
	public String getLegitSpeedReason() {
		return lsr;
	}

	public void setLegitSpeedCWVL(short vl) {
		lscwvl = vl;
	}

	public boolean getNSAnimation() {
		return nsa;
	}

	public void setNSAnimation(boolean enable) {
		nsa = enable;
	}

	public short getLegitSpeedCWVL() {
		return lscwvl;
	}

	public void setLegitInvMoveVL(short vl) {
		limvl = vl;
	}

	public short getLegitInvMoveVL() {
		return limvl;
	}

	public float getServerFallDistance() {
		return sfd;
	}

	public void addServerFallDistance(float dist) {
		sfd += dist;
	}

	public void setServerFallDistance(float dist) {
		sfd = dist;
	}

	public Location getTTL() {
		return ttl;
	}

	public void setTTL(Location loc) {
		ttl = loc;
	}
	
	public synchronized double getTA() {
		double sum = 0;
		
		// Use iterator to avoid concurrent exceptions
		Iterator<Double> it = ta.iterator();
		
		while(it.hasNext()) {
			Double e = it.next();
			
			sum += e;
		}
		
		return sum;
	}
	
	public synchronized double[] getTAArray() {
		return Doubles.toArray(ta);
	}

	public synchronized void addTA(double avg) {
		ta.add(avg);
	}
	
	public synchronized boolean isTaFull() {
		return ta.remainingCapacity() == 0;
	}
	
	public void emptyTA() {
		ta.clear();
	}

	public double getFDD() {
		return fdd;
	}

	public void setFDD(double dist) {
		fdd = dist;
	}
	
	public boolean getNFD() {
		return nfd;
	}

	public void setNFD(boolean fall) {
		nfd = fall;
	}
	
	public short getNFWT() {
		return nfwt;
	}

	public void setNFWT(short times) {
		nfwt = times;
	}
	
	public long getSTLT() {
		return stlt;
	}

	public void setSTLT(long time) {
		stlt = time;
	}
	
	public short getSTP() {
		return stp;
	}

	public void setSTP(short packets) {
		stp = packets;
	}
	
	public double getTD() {
		return td;
	}

	public void setTD(double dist) {
		td = dist;
	}
	
	public double getSTD() {
		return std;
	}

	public void setSTD(double dist) {
		std = dist;
	}

	public long getCNT() {
		return cnt;
	}

	public void setCNT(long time) {
		cnt = time;
	}
	
	public short getCNBB() {
		return cnbb;
	}

	public void setCNBB(short blocks) {
		cnbb = blocks;
	}

	public double getSMoves() {
		return sm;
	}

	public void addSMoves(double move) {
		sm += move;
	}

	public void removeSMoves(double move) {
		sm -= move;
	}
	
	public long getLastmcMMOFakeDamageEntityTime() {
		return lmcmmofdet;
	}
	
	public void setLastmcMMOFakeDamageEntityTime(long time) {
		lmcmmofdet = time;
	}
	
	public long getLastmcMMOAbilityTime() {
		return lmcmmoat;
	}
	
	public void setLastmcMMOAbilityTime(long time) {
		lmcmmoat = time;
	}
	
	public long getLastVeinMineTime() {
		return lvmt;
	}
	
	public void setLastVeinMineTime(long time) {
		lvmt = time;
	}

	public short getJVL() {
		return jvl;
	}

	public void setJVL(short vl) {
		jvl = vl;
	}
	
	public long getSWLT() {
		return swlt;
	}
	
	public void setSWLT(long time) {
		swlt = time;
	}
	
	public short getSWPB() {
		return swpb;
	}
	
	public void setSWPB(short count) {
		swpb = count;
	}

	public boolean getDebugMode() {
		return dm;
	}

	public void setDebugMode(boolean mode) {
		dm = mode;
	}

	public short getDebugModeType() {
		return dmt;
	}

	public void setDebugModeType(short type) {
		dmt = type;
	}

	public HashSet<HackType> getDisabledChecks() {
		return checksDisabled;
	}

	public boolean getBanWave() {
		return bw;
	}

	public void setBanWave(boolean ban) {
		bw = ban;
	}

	public boolean getKnockbackMoveInfo() {
		if (kmi)
			return System.currentTimeMillis() - kmit < kmitt;
		else
			return false;
	}

	public void setKnockbackMoveInfo(boolean info, short timeStamp) {
		kmi = info;
		if (info) {
			kmitt = timeStamp;
			kmit = System.currentTimeMillis();
		}
	}

	public long getBuildBlockageTime() {
		return cbt;
	}

	public void setBuildBlockageTime(long time) {
		cbt = time;
	}
	
	public long getBreakBlockageTime() {
		return cbrt;
	}

	public void setBreakBlockageTime(long time) {
		cbrt = time;
	}

	public long getLastLogMessageTime() {
		return llmt;
	}

	public void setLastLogMessageTime(long time) {
		llmt = time;
	}

	public boolean isGliding() {
		try {
			if (Utils.getServerVersion() >= 2)
				return p.isGliding();
		} catch (NoSuchMethodError e) {
			return ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer) p).getHandle().cB();
		}
		return false;
	}
	
	public boolean isSwimming() {
		try {
			if(cantSwim)
				return false;
			return p.isSwimming();
		} catch(NoSuchMethodError e) {
			// ViaVersion problems 6 -> 1.13 version
			if(Utils.getServerVersion() < 6 && Utils.getClientVersion(this).getVersion() >= 6) {
				return p.isSprinting();
			}
			
			cantSwim = true;
		}
		
		return false;
	}
	
	public boolean isRiptiding() {
		try {
			if(cantRiptide)
				return false;
			return p.isRiptiding();
		} catch(NoSuchMethodError e) { cantRiptide = true; }
		
		return false;
	}

	public boolean isGliched() {
		// TODO: Check is the block is solid a.k.a not bushes signs or other passable
		// blocks
		return !p.getLocation().getBlock().isEmpty() && p.getLocation().getBlock().getType().isSolid();
	}
	
	public boolean isSprinting() {
		return sprinting;
	}

	public void setSprinting(boolean sprint) {
		sprinting = sprint;
	}
	
	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}
	
	public void teleportToGround() {
		teleportToGround(null);
	}

	public void teleportToGround(HackType h) {
		if(Utils.isGhostMode())
			return;
		if(Utils.isDevTest(this))
			return;
		
		if (lgl != null)
			teleport(lgl, h);
	}
	
	public void teleport(Location loc) {
		teleport(loc, null);
	}
	
	public void teleport(Location loc, HackType h) {
		if(Utils.isGhostMode())
			return;
		if(h != null && Utils.isDetectionGhosted(h))
			return;
		if(Utils.isDevTest(this))
			return;
		
		PlayerTACTeleportEvent event = new PlayerTACTeleportEvent(p, p.getPlayer().getLocation(), loc);
		Taka.getThisPlugin().getServer().getPluginManager().callEvent(event);
		Utils.setValues(this, event);
		
		if(event.isCancelled())
			return;
		
		p.teleport(loc);
		Utils.getCheatPlayer(p.getUniqueId()).setLastTacTeleportTime(System.currentTimeMillis());
	}

	public void addViolation(HackType h, ViolationType vType, short vl) {
		if (getViolation(vType).containsKey(HackType.getName(h)))
			getViolation(vType).put(HackType.getName(h), (short) (getViolation(vType).get(HackType.getName(h)) + vl));
		else
			getViolation(vType).put(HackType.getName(h), (short) 0);
	}
	
	public void setViolation(HackType h, ViolationType vType, short vl) {
		getViolation(vType).put(HackType.getName(h), (short) vl);
	}

	public void removeViolation(HackType h, ViolationType vType, short vl) {
		if (getViolation(vType).containsKey(HackType.getName(h))) {
			if ((short) (getViolation(vType).get(HackType.getName(h)) - vl) < 0) {
				getViolation(vType).put(HackType.getName(h), (short) 0);
			} else
				getViolation(vType).put(HackType.getName(h),
						(short) (getViolation(vType).get(HackType.getName(h)) - vl));
		}
	}
	
	/**
	 * Clear all violations of all types
	 */
	public void clearViolation() {
		for (HackType type : HackType.values()) {
			setViolation(type, ViolationType.CANCELMOVE, (short) 0);
			setViolation(type, ViolationType.THRESHOLD, (short) 0);
		}
	}

	public HashMap<String, Short> getViolation(ViolationType vt) {
		if (vt.equals(ViolationType.THRESHOLD))
			return vl1;
		else
			return vl2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheatPlayer other = (CheatPlayer) obj;
		
		if(this.getPlayer().equals(other.getPlayer()))
			return true;
		
		return true;
	}
	
	@Override
	public CheatPlayer clone() {
		try {
			return (CheatPlayer) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
