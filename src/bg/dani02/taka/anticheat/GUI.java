package bg.dani02.taka.anticheat;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.XMaterial;

public class GUI {
	// TODO: Made names of the inventories to be changeable
	public static void openMain(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, "TakaAC v." + Taka.getThisPlugin().getDescription().getVersion());
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(XMaterial.WRITABLE_BOOK.parseMaterial());
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&6Reload Config"));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(XMaterial.COMMAND_BLOCK.parseMaterial());
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&6Run Commands"));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(Material.BOOK);
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&6Reports"));
				s2.setItemMeta(i2);

				inv.setItem(0, s);
				inv.setItem(2, s1);
				inv.setItem(4, s2);
				inv.setItem(6, getNoAvailible());
				inv.setItem(8, getNoAvailible());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openCommands(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, "TakaAC - Commands");
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(Material.MAP);
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&5Show Permissions"));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(XMaterial.IRON_BARS.parseMaterial());
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&5Banwave Settings"));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(Material.LEVER);
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&5Toggle Verbose"));
				i2.setLore(Arrays.asList(Utils.prepareColors("&3Right click: &6Settings")));
				s2.setItemMeta(i2);

				ItemStack s3 = new ItemStack(Material.TORCH);
				ItemMeta i3 = s3.getItemMeta();
				i3.setDisplayName(Utils.prepareColors("&5Toggle Check"));
				s3.setItemMeta(i3);

				ItemStack s4 = new ItemStack(XMaterial.BOOK.parseMaterial());
				ItemMeta i4 = s4.getItemMeta();
				i4.setDisplayName(Utils.prepareColors("&5Clear VL's"));
				s4.setItemMeta(i4);
				
				ItemStack s5 = new ItemStack(XMaterial.RED_BED.parseMaterial());
				ItemMeta i5 = s5.getItemMeta();
				i5.setDisplayName(Utils.prepareColors("&5Suspend checks"));
				i5.setLore(Arrays.asList(Utils.prepareColors("&3Description: &5Disable all checks, for temporary time!")));
				s5.setItemMeta(i5);
				
				ItemStack s6 = new ItemStack(XMaterial.FIRE_CHARGE.parseMaterial());
				ItemMeta i6 = s6.getItemMeta();
				i6.setDisplayName(Utils.prepareColors("&5Ghost mode"));
				i6.setLore(Arrays.asList(Utils.prepareColors("&3Description: &5Disable all checks, and leave only verbose messages!"),
						Utils.prepareColors("&6Note: &5To disable all checks, please type /taka ghost")));
				s6.setItemMeta(i6);
				
				ItemStack s7 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i7 = s7.getItemMeta();
				i7.setDisplayName(Utils.prepareColors("&5Disabled worlds"));
				i7.setLore(Arrays.asList(Utils.prepareColors("&3Description: &5Disable TAKA in the selected worlds!")));
				s7.setItemMeta(i7);

				inv.setItem(0, s);
				inv.setItem(1, s1);
				inv.setItem(2, s2);
				inv.setItem(3, s3);
				inv.setItem(4, s4);
				inv.setItem(5, s5);
				inv.setItem(6, s6);
				inv.setItem(7, s7);
				inv.setItem(8, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openPermissions(Player p, final String type) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "TakaAC - Permissions");
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (HackType h : HackType.values()) {
					// Dont iterate checks which are removed due to false positives
					if(HackType.isCheckDisabled(h))
						continue;
					
					if (type.contains("Other")) {
						if (!h.toString().split("_")[0].equals("EXPLOIT") && !h.toString().split("_")[0].equals("CHAT")
								&& !h.toString().split("_")[0].equals("RENDER"))
							continue;
					} else if (type.contains("Combat")) {
						if (!h.toString().split("_")[0].equals("COMBAT"))
							continue;
					} else if (type.contains("World")) {
						if (!h.toString().split("_")[0].equals("WORLD"))
							continue;
					} else if (type.contains("Inventory")) {
						if (!h.toString().split("_")[0].equals("INVENTORY"))
							continue;
					} else if (type.contains("Moving")) {
						if (!h.toString().split("_")[0].equals("MOVING"))
							continue;
					}
					ItemStack s = new ItemStack(Material.PAPER);
					ItemMeta i = s.getItemMeta();
					i.setDisplayName(Utils.prepareColors("&a") + HackType.getName(h));
					i.setLore(Arrays.asList(Utils.getBypassPermission() + "." + HackType.removeCheckType(h)));
					s.setItemMeta(i);

					inv.addItem(s);
				}

				inv.setItem(53, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openToggleCheck(Player p, String name) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, name);
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(Material.BOOK);
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&5Moving checks"));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(Material.BOOK);
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&5Inventory checks"));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(Material.BOOK);
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&5World checks"));
				s2.setItemMeta(i2);

				ItemStack s3 = new ItemStack(Material.BOOK);
				ItemMeta i3 = s3.getItemMeta();
				i3.setDisplayName(Utils.prepareColors("&5Combat checks"));
				s3.setItemMeta(i3);

				ItemStack s4 = new ItemStack(Material.BOOK);
				ItemMeta i4 = s4.getItemMeta();
				i4.setDisplayName(Utils.prepareColors("&5Other checks"));
				s4.setItemMeta(i4);

				inv.setItem(0, s);
				inv.setItem(2, s1);
				inv.setItem(4, s2);
				inv.setItem(6, s3);
				inv.setItem(8, s4);
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openToggleCheck1(Player p, final String type) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "TakaAC - Checks");
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (HackType h : HackType.values()) {
					if (type.contains("Other")) {
						if (!h.toString().split("_")[0].equals("EXPLOIT") && !h.toString().split("_")[0].equals("CHAT")
								&& !h.toString().split("_")[0].equals("RENDER"))
							continue;
					} else if (type.contains("Combat")) {
						if (!h.toString().split("_")[0].equals("COMBAT"))
							continue;
					} else if (type.contains("World")) {
						if (!h.toString().split("_")[0].equals("WORLD"))
							continue;
					} else if (type.contains("Inventory")) {
						if (!h.toString().split("_")[0].equals("INVENTORY"))
							continue;
					} else if (type.contains("Moving")) {
						if (!h.toString().split("_")[0].equals("MOVING"))
							continue;
					}
					ItemStack s = new ItemStack(Material.PAPER);
					ItemMeta i = s.getItemMeta();
					i.setDisplayName(HackType.getName(h));
					if (Utils.isDetectionEnabled(h))
						i.setLore(Arrays.asList(Utils.prepareColors("Enabled: &a" + Utils.isDetectionEnabled(h))));
					else
						i.setLore(Arrays.asList(Utils.prepareColors("Enabled: &c" + Utils.isDetectionEnabled(h))));
					s.setItemMeta(i);

					inv.addItem(s);
				}

				inv.setItem(53, getBack());
			}
		}).start();

		p.openInventory(inv);
	}
	
	private static void openToggleCheck2(Player p, final String type) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "TakaAC - Ghosted Checks");
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (HackType h : HackType.values()) {
					if (type.contains("Other")) {
						if (!h.toString().split("_")[0].equals("EXPLOIT") && !h.toString().split("_")[0].equals("CHAT")
								&& !h.toString().split("_")[0].equals("RENDER"))
							continue;
					} else if (type.contains("Combat")) {
						if (!h.toString().split("_")[0].equals("COMBAT"))
							continue;
					} else if (type.contains("World")) {
						if (!h.toString().split("_")[0].equals("WORLD"))
							continue;
					} else if (type.contains("Inventory")) {
						if (!h.toString().split("_")[0].equals("INVENTORY"))
							continue;
					} else if (type.contains("Moving")) {
						if (!h.toString().split("_")[0].equals("MOVING"))
							continue;
					}
					ItemStack s = new ItemStack(Material.PAPER);
					ItemMeta i = s.getItemMeta();
					i.setDisplayName(HackType.getName(h));
					if (Utils.isDetectionEnabled(h))
						i.setLore(Arrays.asList(Utils.prepareColors("Ghosted: &a" + Utils.isDetectionGhosted(h))));
					else
						i.setLore(Arrays.asList(Utils.prepareColors("Ghosted: &c" + Utils.isDetectionGhosted(h))));
					s.setItemMeta(i);

					inv.addItem(s);
				}

				inv.setItem(53, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openBanWaveSettings(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, "TakaAC - BanWave");
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(Material.LEVER);
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&5Toggle BanWave"));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(XMaterial.LEAD.parseMaterial());
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&5Add Player"));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(XMaterial.LEAD.parseMaterial());
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&5Remove Player"));
				s2.setItemMeta(i2);

				ItemStack s3 = new ItemStack(Material.PAPER);
				ItemMeta i3 = s3.getItemMeta();
				i3.setDisplayName(Utils.prepareColors("&5Banwave List"));
				s3.setItemMeta(i3);

				inv.setItem(0, s);
				inv.setItem(2, s1);
				inv.setItem(4, s2);
				inv.setItem(6, s3);
				inv.setItem(8, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openDebugModeTypeSettings(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, "TakaAC - Verbose Mode");
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(Material.BOOK);
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&6Mode 1"));
				i.setLore(Arrays.asList(Utils.prepareColors("&5See all notifications"), Utils.prepareColors("&6WARNING: This is debug mode."), Utils.prepareColors("&6Please don't report false positives in debug mode!")));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(Material.BOOK);
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&6Mode 2"));
				i1.setLore(Arrays.asList(Utils.prepareColors("&5See only notifications with actions only (Recommended)")));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(Material.BOOK);
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&6Mode 3"));
				i2.setLore(Arrays.asList(Utils.prepareColors("&5See only notifications with actions only, at a certain time")));
				s2.setItemMeta(i2);
				
				ItemStack s3 = new ItemStack(Material.BOOK);
				ItemMeta i3 = s3.getItemMeta();
				i3.setDisplayName(Utils.prepareColors("&6Mode 4"));
				i3.setLore(Arrays.asList(Utils.prepareColors("&5Developer Test mode"), Utils.prepareColors("&6WARNING: This is supposed to be used only in developer version, not for consumer build!")));
				s3.setItemMeta(i3);

				inv.setItem(0, s);
				inv.setItem(1, s1);
				inv.setItem(2, s2);
				inv.setItem(3, Utils.isDevTest(Utils.getCheatPlayer(p.getUniqueId())) ? s3 : getNoAvailible()); // Show test mode option only if DEV_TEST is enabled
				inv.setItem(4, getNoAvailible());
				inv.setItem(5, getNoAvailible());
				inv.setItem(6, getNoAvailible());
				inv.setItem(7, getNoAvailible());
				inv.setItem(8, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openReportList(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "TakaAC - Report List");
		new Thread(new Runnable() {

			@Override
			@SuppressWarnings("deprecation")
			public void run() {
				// p.getPlayer().sendMessage("REPS: " + Utils.getReports().size());
				for (bg.dani02.taka.anticheat.Report rep : Utils.getReports()) {
					// p.getPlayer().sendMessage("REPS: " + rep.toString());
					ItemStack s = XMaterial.PLAYER_HEAD.parseItem();
					SkullMeta i = (SkullMeta) s.getItemMeta();
					i.setDisplayName(Utils.prepareColors("&6" + rep.getReported().getName()));
					i.setLore(Arrays.asList(Utils.prepareColors("&5Report by: &e" + rep.getPlayer().getName()),
							Utils.prepareColors("&5Message: " + rep.getReport())));
					i.setOwner(rep.getReported().getName());
					s.setItemMeta(i);
					inv.addItem(s);
				}

				inv.setItem(53, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static void openReportListCommands(Player p, String playerName) {
		Inventory inv = Bukkit.createInventory((InventoryHolder) null, 27, "TakaAC - Report Commands");
		new Thread(new Runnable() {

			@Override
			@SuppressWarnings("deprecation")
			public void run() {
				final ItemStack a = new ItemStack(Material.APPLE);
				final ItemMeta aa = a.getItemMeta();
				aa.setDisplayName(Utils.prepareColors("&8Teleport"));
				a.setItemMeta(aa);
				inv.setItem(10, a);

				final ItemStack a2 = new ItemStack(Material.PAPER);
				final ItemMeta aa2 = a2.getItemMeta();
				aa2.setDisplayName(Utils.prepareColors("&8Remove report"));
				a2.setItemMeta(aa2);
				inv.setItem(16, a2);

				ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
				final SkullMeta headm = (SkullMeta) head.getItemMeta();
				headm.setOwner(playerName);
				headm.setDisplayName(Utils.prepareColors("&a" + playerName));
				head.setItemMeta(headm);
				inv.setItem(13, head);

				inv.setItem(26, getBack());
			}

		}).start();

		p.openInventory(inv);
	}
	
	private static void openSuspend(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 9, "TakaAC - Suspend");
		new Thread(new Runnable() {

			@Override
			public void run() {
				ItemStack s = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i = s.getItemMeta();
				i.setDisplayName(Utils.prepareColors("&55 Minutes"));
				s.setItemMeta(i);

				ItemStack s1 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i1 = s1.getItemMeta();
				i1.setDisplayName(Utils.prepareColors("&510 Minutes"));
				s1.setItemMeta(i1);

				ItemStack s2 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i2 = s2.getItemMeta();
				i2.setDisplayName(Utils.prepareColors("&515 Minutes"));
				s2.setItemMeta(i2);

				ItemStack s3 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i3 = s3.getItemMeta();
				i3.setDisplayName(Utils.prepareColors("&530 Minutes"));
				s3.setItemMeta(i3);

				ItemStack s4 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i4 = s4.getItemMeta();
				i4.setDisplayName(Utils.prepareColors("&51 Hour"));
				s4.setItemMeta(i4);
				
				ItemStack s5 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i5 = s5.getItemMeta();
				i5.setDisplayName(Utils.prepareColors("&52 Hours"));
				s5.setItemMeta(i5);
				
				ItemStack s6 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i6 = s6.getItemMeta();
				i6.setDisplayName(Utils.prepareColors("&53 Hours"));
				s6.setItemMeta(i6);
				
				ItemStack s7 = new ItemStack(XMaterial.OAK_SIGN.parseMaterial());
				ItemMeta i7 = s7.getItemMeta();
				i7.setDisplayName(Utils.prepareColors("&5Info"));
				i7.setLore(Arrays.asList(Utils.prepareColors("&3Is Taka suspended: &5" + Utils.isSuspended()),Utils.prepareColors("&3Time Left: &5" + (Utils.isSuspended() ? Utils.formatSuspendTime(""+((System.currentTimeMillis() - Utils.suspendMillis) / 1000 / 60)) : "NaN")), Utils.prepareColors("&3Usage: &5(m - Mins, h - Hours, d - Days, w - Weeks)"), Utils.prepareColors("&3Example: &5/taka suspend 15d")));
				s7.setItemMeta(i7);
				
				ItemStack s8 = new ItemStack(XMaterial.CLOCK.parseMaterial());
				ItemMeta i8 = s8.getItemMeta();
				i8.setDisplayName(Utils.prepareColors("&5Custom time"));
				i8.setLore(Arrays.asList(Utils.prepareColors("&3Description: &5 Use: &c/taka suspend <time>"), Utils.prepareColors("&5(m - Mins, h - Hours, d - Days, w - Weeks)"), Utils.prepareColors("&5In order to enable all checks back again type: &6/taka suspend 0")));
				s8.setItemMeta(i8);

				inv.setItem(0, s);
				inv.setItem(1, s1);
				inv.setItem(2, s2);
				inv.setItem(3, s3);
				inv.setItem(4, s4);
				inv.setItem(5, s5);
				inv.setItem(6, s6);
				inv.setItem(7, s7);
				inv.setItem(8, s8);
			}
		}).start();

		p.openInventory(inv);
	}
	
	private static void openDisabledWorlds(Player p) {
		final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "TakaAC - Disabled Worlds");
		new Thread(new Runnable() {

			@Override
			public void run() {
				for(World w : Taka.getThisPlugin().getServer().getWorlds()) {
					if(Utils.isWorldDisabled(w.getName())) {
						ItemStack s = new ItemStack(XMaterial.ENCHANTED_BOOK.parseMaterial());
						ItemMeta i = s.getItemMeta();
						i.setDisplayName(Utils.prepareColors("&5" + w.getName()));
						i.setLore(Arrays.asList(Utils.prepareColors("&4Taka is &cDISABLED!")));
						s.setItemMeta(i);
						
						inv.addItem(s);
					} else {
						ItemStack s = new ItemStack(XMaterial.BOOK.parseMaterial());
						ItemMeta i = s.getItemMeta();
						i.setDisplayName(Utils.prepareColors("&5" + w.getName()));
						i.setLore(Arrays.asList(Utils.prepareColors("&4Taka is &aENABLED!")));
						s.setItemMeta(i);
						
						inv.addItem(s);
					}
				}
				
				inv.setItem(53, getBack());
			}
		}).start();

		p.openInventory(inv);
	}

	private static ItemStack getNoAvailible() {
		ItemStack s2 = new ItemStack(Material.BARRIER);
		ItemMeta i2 = s2.getItemMeta();
		i2.setDisplayName(Utils.prepareColors("&4Not available"));
		s2.setItemMeta(i2);

		return s2;
	}

	private static ItemStack getBack() {
		ItemStack s2 = new ItemStack(Material.REDSTONE);
		ItemMeta i2 = s2.getItemMeta();
		i2.setDisplayName(Utils.prepareColors("&cBack"));
		s2.setItemMeta(i2);

		return s2;
	}

	// Event
	public static void onEvent(InventoryOpenEvent e) {
		// Chest with name TakaAC bypass code
		if (e.getView().getTitle().contains("TakaAC")) {
			if (e.getView().getTitle().equals("TakaAC - Check"))
				if (!e.getPlayer().hasPermission(Utils.getCommandCheckPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			if (e.getView().getTitle().equals("TakaAC - Checks")) {
				if (!e.getPlayer().hasPermission(Utils.getCommandBanWavePermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			}
			if (e.getView().getTitle().equals("TakaAC - BanWave"))
				if (!e.getPlayer().hasPermission(Utils.getCommandBanWavePermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			if (e.getView().getTitle().equals("TakaAC - Verbose mode"))
				if (!e.getPlayer().hasPermission(Utils.getCommandDebugPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			if (e.getView().getTitle().equals("TakaAC - Report List"))
				if (!e.getPlayer().hasPermission(Utils.getCommandReportListPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			if (e.getView().getTitle().equals("TakaAC - Commands"))
				if (!e.getPlayer().hasPermission(Utils.getCommandCommandsPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			if (e.getView().getTitle().equals("TakaAC - Suspend"))
				if (!e.getPlayer().hasPermission(Utils.getCommandSuspendPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
			
			if(!e.getView().getTitle().equals(ConfigsMessages.anticheat_treport_gui_title))
				if (!e.getPlayer().hasPermission(Utils.getAdminPermission())) {
					e.setCancelled(true);
					e.getPlayer().closeInventory();
					e.getPlayer().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
				}
		}
	}

	public static void onEvent(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		// Chest with name TakaAC bypass code
		if (e.getView().getTitle().contains("TakaAC")) {
			if (e.getView().getTitle().equals("TakaAC - Check"))
				if (!e.getWhoClicked().hasPermission(Utils.getCommandCheckPermission())) {
					e.getWhoClicked().sendMessage(Utils.getTakaPrefix() + Utils.getNoPermissionMessage());
					e.setCancelled(true);
					return;
				}
			if (e.getView().getTitle().equals("TakaAC - Checks"))
				if (!e.getWhoClicked().hasPermission(Utils.getCommandCheckPermission())) {
					e.getWhoClicked().sendMessage(Utils.getTakaPrefix() + Utils.getNoPermissionMessage());
					e.setCancelled(true);
					return;
				}
			if (e.getView().getTitle().equals("TakaAC - BanWave"))
				if (!e.getWhoClicked().hasPermission(Utils.getCommandBanWavePermission())) {
					e.getWhoClicked().sendMessage(Utils.getTakaPrefix() + Utils.getNoPermissionMessage());
					e.setCancelled(true);
					return;
				}
			if (e.getView().getTitle().equals("TakaAC - Verbose mode"))
				if (!e.getWhoClicked().hasPermission(Utils.getCommandDebugPermission())) {
					e.getWhoClicked().sendMessage(Utils.getTakaPrefix() + Utils.getNoPermissionMessage());
					e.setCancelled(true);
					return;
				}
			if (e.getView().getTitle().equals("TakaAC - Suspend"))
				if (!e.getWhoClicked().hasPermission(Utils.getCommandSuspendPermission())) {
					e.getWhoClicked().sendMessage(Utils.getTakaPrefix() + Utils.getNoPermissionMessage());
					e.setCancelled(true);
					return;
				}
		}
		if (e.getView().getTitle().equals("TakaAC v." + Taka.getThisPlugin().getDescription().getVersion())) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getSlot() == 0) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandReloadPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				e.getWhoClicked().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&6Reloading config..."));
				Utils.reloadConfig();
				e.getWhoClicked().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&aConfig reloaded"));
			} else if (e.getSlot() == 2) {
				openCommands((Player) e.getWhoClicked());
			} else if (e.getSlot() == 4) {
				// TODO: Permissions
				openReportList((Player) e.getWhoClicked());
			}
		} else if (e.getView().getTitle().equals("TakaAC - Commands")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getSlot() == 0) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandPemrsPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openToggleCheck((Player) e.getWhoClicked(), "TakaAC - Permission");
			} else if (e.getSlot() == 1) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandBanWavePermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openBanWaveSettings((Player) e.getWhoClicked());
			} else if (e.getSlot() == 2) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandDebugPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}

				if (e.getClick().isLeftClick()) {
					e.getWhoClicked().closeInventory();
					Utils.getCheatPlayer(e.getWhoClicked().getUniqueId())
							.setDebugMode(!Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getDebugMode());
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getAdminTurnDebugModeMessage(
									Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getDebugMode())));
				} else if (e.getClick().isRightClick()) {
					openDebugModeTypeSettings((Player) e.getWhoClicked());
				}
			} else if (e.getSlot() == 3) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandCheckPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openToggleCheck((Player) e.getWhoClicked(), "TakaAC - Check");
			} else if (e.getSlot() == 4) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandClearVLPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				Utils.clearVL(true);
			} else if (e.getSlot() == 5) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandSuspendPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openSuspend((Player) e.getWhoClicked());
			} else if (e.getSlot() == 6) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandGhostPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openToggleCheck((Player) e.getWhoClicked(), "TakaAC - Ghosted Check");
			} else if (e.getSlot() == 7) {
				if (!e.getWhoClicked().hasPermission(Utils.getCommandDisabledWorldsPermission())) {
					e.getWhoClicked()
							.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
				openDisabledWorlds((Player) e.getWhoClicked());
			} else if (e.getSlot() == 8) {
				openMain((Player) e.getWhoClicked());
			}
		} else if (e.getView().getTitle().equals("TakaAC - Permission")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals(Material.AIR))
				return;
			openPermissions((Player) e.getWhoClicked(),
					e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
			Utils.getCheatPlayer(e.getWhoClicked().getUniqueId())
					.setGUILCT(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
		} else if (e.getView().getTitle().equals("TakaAC - Permissions")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getSlot() == 53)
				openCommands((Player) e.getWhoClicked());
		} else if (e.getView().getTitle().equals("TakaAC - Check")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			openToggleCheck1((Player) e.getWhoClicked(),
					e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
			Utils.getCheatPlayer(e.getWhoClicked().getUniqueId())
					.setGUILCT(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
		} else if (e.getView().getTitle().equals("TakaAC - Ghosted Check")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			openToggleCheck2((Player) e.getWhoClicked(),
					e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
			Utils.getCheatPlayer(e.getWhoClicked().getUniqueId())
					.setGUILCT(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
		} else if (e.getView().getTitle().equals("TakaAC - Checks")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getGUILCT() == null)
				return;
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getSlot() == 53) {
				openToggleCheck((Player) e.getWhoClicked(), "TakaAC - Check");
				return;
			}
			Utils.setDetectionEnabled(HackType.getByName(e.getCurrentItem().getItemMeta().getDisplayName()),
					!Utils.isDetectionEnabled(HackType.getByName(e.getCurrentItem().getItemMeta().getDisplayName())));
			openToggleCheck1((Player) e.getWhoClicked(),
					Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getGUILCT());
		} else if (e.getView().getTitle().equals("TakaAC - Ghosted Checks")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getGUILCT() == null)
				return;
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getSlot() == 53) {
				openToggleCheck((Player) e.getWhoClicked(), "TakaAC - Ghosted Check");
				return;
			}
			Utils.setDetectionGhosted(HackType.getByName(e.getCurrentItem().getItemMeta().getDisplayName()),
					!Utils.isDetectionGhosted(HackType.getByName(e.getCurrentItem().getItemMeta().getDisplayName())));
			openToggleCheck2((Player) e.getWhoClicked(),
					Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getGUILCT());
		} else if (e.getView().getTitle().equals("TakaAC - BanWave")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);
			if (e.getSlot() == 0) {
				e.getWhoClicked().closeInventory();
				
				((Player) e.getWhoClicked()).performCommand("taka banwave toggle");
			} else if (e.getSlot() == 2) {
				e.getWhoClicked().closeInventory();
				e.getWhoClicked()
						.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&5Please type the player's name:"));
				Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).setBWCN(true);
				Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).setBWCAdd(true);
			} else if (e.getSlot() == 4) {
				e.getWhoClicked().closeInventory();
				e.getWhoClicked()
						.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + "&5Please type the player's name:"));
				Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).setBWCN(true);
				Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).setBWCAdd(false);
			} else if (e.getSlot() == 6) {
				e.getWhoClicked().closeInventory();

				((Player) e.getWhoClicked()).performCommand("taka banwave list");
			} else if (e.getSlot() == 8)
				openCommands((Player) e.getWhoClicked());
		} else if (e.getView().getTitle().equals("TakaAC - Verbose Mode")) {
			e.setCancelled(true);
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if(e.getCurrentItem().getType() == getNoAvailible().getType())
				return;
			if(e.getCurrentItem().getType() == getBack().getType()) {
				openMain((Player) e.getWhoClicked());
				return;
			}
			
			e.getWhoClicked().closeInventory();

			if (!Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getDebugMode())
				Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).setDebugMode(true);
			
			short debugMode = (short) Character.getNumericValue(e.getCurrentItem().getItemMeta()
					.getDisplayName().charAt(e.getCurrentItem().getItemMeta().getDisplayName().length() - 1));
			
			if(debugMode == 4) {
				if(!e.getWhoClicked().getName().equals("PoshPoser") &&
						!e.getWhoClicked().getName().equals("_dakata")) {
					e.getWhoClicked()
					.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getNoPermissionMessage()));
					return;
				}
			}

			Utils.getCheatPlayer(e.getWhoClicked().getUniqueId())
					.setDebugModeType(debugMode);// TODO: ER

			e.getWhoClicked().sendMessage(Utils.prepareColors(
					Utils.getTakaPrefix() + ConfigsMessages.anticheat_admin_debug_mode_type_message.replace("%m%",
							Short.toString(Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getDebugModeType()))));

			e.getWhoClicked()
					.sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + Utils.getAdminTurnDebugModeMessage(
							Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getDebugMode())));
		} else if (e.getView().getTitle().equals("TakaAC - Report List")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			e.setCancelled(true);
			if (e.getSlot() == 53)
				openMain((Player) e.getWhoClicked());
			else openReportListCommands((Player) e.getWhoClicked(),
					Utils.getReport(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
							.getReported().getName());
		} else if (e.getView().getTitle().equals("TakaAC - Report Commands")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			e.setCancelled(true);

			if (e.getSlot() == 10) {
				((Player) e.getWhoClicked()).performCommand(
						"tp " + ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName()));
			} else if (e.getSlot() == 16) {
				e.getWhoClicked().closeInventory();
				Utils.removeReport(Utils
						.getReport(ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName())));
			} else if (e.getSlot() == 26) {
				openReportList((Player) e.getWhoClicked());
			}
		} else if (e.getView().getTitle().equals("TakaAC - Suspend")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			if(e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			
			e.setCancelled(true);
			
			if (e.getSlot() == 0) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 5");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 1) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 10");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 2) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 15");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 3) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 30");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 4) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 1h");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 5) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 2h");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 6) {
				((Player) e.getWhoClicked()).performCommand("taka suspend 3h");
				e.getWhoClicked().getOpenInventory().close();
				return;
			} else if (e.getSlot() == 7) {
				return;
			} else if (e.getSlot() == 8) {
				((Player) e.getWhoClicked()).performCommand("taka suspend");
				e.getWhoClicked().getOpenInventory().close();
				return;
			}
		}else if (e.getView().getTitle().equals("TakaAC - Disabled Worlds")) {
			if (e.getInventory() == null)
				return;
			if (!e.getInventory().getType().equals(InventoryType.CHEST))
				return;
			if(e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR))
				return;
			
			e.setCancelled(true);
			
			if(e.getSlot() == 53) {
				e.getWhoClicked().getOpenInventory().close();
				
				openCommands((Player) e.getWhoClicked());
				return;
			}
			
			String world = Utils.removeColors(e.getCurrentItem().getItemMeta().getDisplayName());
			
			if(Utils.isWorldDisabled(world)) {
				((Player) e.getWhoClicked()).performCommand("taka world disable " + world);
			} else {
				((Player) e.getWhoClicked()).performCommand("taka world enable " + world);
			}
			
			openDisabledWorlds((Player) e.getWhoClicked());
		}
	}

	public static class Report {
		public static void mainReport(Player p) {
			openToggleCheck1(p);
		}

		private static void openToggleCheck1(Player p) {
			final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 27, ConfigsMessages.anticheat_treport_gui_title);
			new Thread(new Runnable() {

				@Override
				public void run() {
					String[][] hacks = {
							{ "Fly", "Speed", "Jesus", "NoFall", "Step", "Timer", "FastLadder", "Glide", "Dolphin" },
							{ "KillAura", "MultiAura", "Criticals", "AimBot", "BowAimbot", "AntiKnockback", "Regen",
									"AutoSoup", "FastBow" },
							{ "Tower", "Scaffold", "FastPlace", "FastBreak", "FastBow", "Phase", "NoSwing", "VClip",
									"Other" } };
					for (short s = 0; s < hacks[0].length; s++) {
						ItemStack sa = new ItemStack(Material.DIAMOND_BOOTS);
						ItemMeta i = sa.getItemMeta();
						i.setDisplayName(Utils.prepareColors("&2" + hacks[0][s]));
						sa.setItemMeta(i);

						inv.setItem(s, sa);
					}

					for (short s = 0; s < hacks[1].length; s++) {
						ItemStack sa = new ItemStack(XMaterial.WOODEN_SWORD.parseMaterial());
						ItemMeta i = sa.getItemMeta();
						i.setDisplayName(Utils.prepareColors("&2" + hacks[1][s]));
						sa.setItemMeta(i);

						inv.setItem(s + 9, sa);
					}

					for (short s = 0; s < hacks[2].length; s++) {
						ItemStack sa = new ItemStack(XMaterial.BRICK.parseMaterial());
						ItemMeta i = sa.getItemMeta();
						i.setDisplayName(Utils.prepareColors("&2" + hacks[2][s]));
						sa.setItemMeta(i);

						inv.setItem(s + 9 * 2, sa);
					}
				}
			}).start();
			
			p.openInventory(inv);
		}
		
		public static void reportEvents(InventoryClickEvent e) {
			if (!(e.getWhoClicked() instanceof Player))
				return;
			if (e.getView().getTitle().equals(ConfigsMessages.anticheat_treport_gui_title)) {
				if (e.getInventory() == null)
					return;
				if (!e.getInventory().getType().equals(InventoryType.CHEST))
					return;
				e.setCancelled(true);
				
				e.getWhoClicked().closeInventory();
				
				Utils.addReport(new bg.dani02.taka.anticheat.Report((Player) e.getWhoClicked(),
						Utils.getCheatPlayer(e.getWhoClicked().getUniqueId()).getReport(),
						Utils.removeColors(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())), false);
				
				e.getWhoClicked().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + ConfigsMessages.anticheat_report_complete_message));
			}
		}
	}
}