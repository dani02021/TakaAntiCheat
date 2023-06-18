package bg.dani02.taka.anticheat.managers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.api.PlayerBlockInteractEvent;
import bg.dani02.taka.anticheat.api.PlayerBlockInteractEvent.IteractionType;
import bg.dani02.taka.anticheat.checks.exploit.AutoRespawn;
import bg.dani02.taka.anticheat.checks.exploit.PluginList;
import bg.dani02.taka.anticheat.checks.exploit.WorldDownloader;
import bg.dani02.taka.anticheat.checks.movement.ScaffoldWalk;
import bg.dani02.taka.anticheat.checks.movement.Timer;
import bg.dani02.taka.anticheat.checks.render.HealthTag;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.ViolationType;
import io.netty.buffer.ByteBuf;

public class ProtocolLibManager {
	public static void enb() {
		try {
			if (Utils.isDetectionEnabled(HackType.MOVING_SCAFFOLDWALK_BASIC)
					|| Utils.isDetectionEnabled(HackType.MOVING_SCAFFOLDWALK_ADVANCED)
					|| Utils.isDetectionEnabled(HackType.MOVING_SCAFFOLDWALK_EXPAND)
					|| Utils.isDetectionEnabled(HackType.MOVING_SCAFFOLDWALK_GROUND)) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.NORMAL,
								com.comphenix.protocol.PacketType.Play.Client.BLOCK_PLACE) {
							@Override
							public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
								try {
									if(event.getPlayer() == null || !event.getPlayer().isOnline() || event.isCancelled())
										return;
									
									short counter = 0;

									for (Float f : event.getPacket().getFloat().getValues()) {
										if (f.floatValue() == 0.0F)
											counter++;
									}
									
									BlockPosition bp = event.getPacket().getBlockPositionModifier().getValues().get(0);
									
									if (counter >= 3
											&& bp.getX() == -1
											&& bp.getY() == -1
											&& bp.getZ() == -1) {
										Utils.getCheatPlayer(event.getPlayer().getUniqueId()).removeViolation(
												HackType.MOVING_SCAFFOLDWALK_BASIC, ViolationType.CANCELMOVE,
												(short) 1);
										return;
									}
									
									if(!event.getPlayer().getWorld().isChunkLoaded(bp.getX() >> 4, bp.getZ() >> 4))
										return;
									
									new BukkitRunnable() {
										
										@Override
										public void run() {
											ScaffoldWalk.onCheck(Utils.getCheatPlayer(event.getPlayer().getUniqueId()),
													event.getPacket().getBlockPositionModifier().getValues().get(0)
															.toLocation(event.getPlayer().getWorld()).getBlock(),
													event.getPacket().getFloat().getValues().get(0),
													event.getPacket().getFloat().getValues().get(1),
													event.getPacket().getFloat().getValues().get(2),
													event.getPacket().getIntegers().getValues().get(0));
										}
									}.runTask(Taka.getThisPlugin());
								} catch (IndexOutOfBoundsException | IllegalStateException ex) {
									/* When use items */ }
							}
						});

			}
			if (Utils.isDetectionEnabled(HackType.EXPLOIT_PLUGIN_LIST)) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.NORMAL,
								com.comphenix.protocol.PacketType.Play.Client.TAB_COMPLETE) {
							@Override
							public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
								if(event.getPlayer() == null)
									return;
								
								if (!Utils.isDetectionEnabled(HackType.EXPLOIT_PLUGIN_LIST))
									return;
								
								if(PluginList.onCheck(event))
									event.setCancelled(true);
							}
						});
			}

			if (Utils.isDetectionEnabled(HackType.RENDER_HEALTHTAG)) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.NORMAL,
								com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA) {
							@Override
							public void onPacketSending(com.comphenix.protocol.events.PacketEvent event) {
								if(event.getPlayer() == null)
									return;
								
								HealthTag.onCheck(event);
							}
						});
			}

			if (Utils.isDetectionEnabled(HackType.EXPLOIT_AUTORESPAWN)) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.NORMAL,
								com.comphenix.protocol.PacketType.Play.Client.CLIENT_COMMAND) {
							@Override
							public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
								if(event.getPlayer() == null)
									return;
								
								if (!Utils.isDetectionEnabled(HackType.EXPLOIT_AUTORESPAWN))
									return;
								
								if(event.getPacketType().name().contains("CLIENT_COMMAND")) // Fixes ProtocolLib bug on 1.8 server
									AutoRespawn.onCheck(event);
							}
						});
			}
			
			Utils.getProtocolManager()
			.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
					com.comphenix.protocol.events.ListenerPriority.NORMAL,
					com.comphenix.protocol.PacketType.Play.Server.ENTITY_VELOCITY) {
				@Override
				public void onPacketSending(com.comphenix.protocol.events.PacketEvent event) {
					if(!(event.getPacket().getIntegers().getValues().get(0) == event.getPlayer().getEntityId()))
						return;
					
					// The packet return int values which are believed to be / 8000 power
					// example: x: 1550 / 8000 = 0.193 x move
					float x = (float) event.getPacket().getIntegers().getValues().get(1) / 8000;
					float y = (float) event.getPacket().getIntegers().getValues().get(2) / 8000;
					float z = (float) event.getPacket().getIntegers().getValues().get(3) / 8000;
					
					Vector velPower = new Vector(x, y, z);
					
					Utils.getCheatPlayer(event.getPlayer().getUniqueId()).setLastServerSetVelocityTime(System.currentTimeMillis());
					Utils.getCheatPlayer(event.getPlayer().getUniqueId()).setLastServerSetVelocityVector(velPower);
				}
			});

			if (ConfigsMessages.jigsaw_servercrash_enable && Utils.getServerVersion() < 6) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.HIGHEST,
								com.comphenix.protocol.PacketType.Play.Client.CUSTOM_PAYLOAD) {
							@Override
							public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
								try {
									if (Utils.getCheatPlayer(event.getPlayer().getUniqueId()).getJK()) {
										event.setCancelled(true);
										return;
									}
									if (event.getPacket().getStrings().getValues().get(0).equals("MC|BEdit")
											|| event.getPacket().getStrings().getValues().get(0).equals("MC|BSign"))
										if (((ByteBuf) event.getPacket().getModifier().getValues().get(1))
												.capacity() > 30000) {
											Utils.getCheatPlayer(event.getPlayer().getUniqueId()).setJK(true);
											Taka.getThisPlugin().getServer().getScheduler().runTask(
													Taka.getThisPlugin(),
													() -> Taka.getThisPlugin().getServer().dispatchCommand(
															Taka.getThisPlugin().getServer().getConsoleSender(),
															ConfigsMessages.jigsaw_servercrash_cmd.replaceAll("%player%|%p%",
																	event.getPlayer().getName())));
										}
								} catch (NullPointerException ex) { }
							}
						});
			}

			if (ConfigsMessages.world_downloader_enable && Utils.getServerVersion() < 6) {
				Utils.getProtocolManager()
						.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
								com.comphenix.protocol.events.ListenerPriority.NORMAL,
								com.comphenix.protocol.PacketType.Play.Client.CUSTOM_PAYLOAD) {
							@Override
							public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
								if(event.getPlayer() == null)
									return;
								
								if (!Utils.isDetectionEnabled(HackType.EXPLOIT_WORLD_DOWNLOADER))
									return;
								WorldDownloader.onCheck(event.getPlayer(), event.getPacket().getStrings().getValues().get(0));
							}
						});
			}
			
			Utils.getProtocolManager()
			.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
					com.comphenix.protocol.events.ListenerPriority.NORMAL,
					com.comphenix.protocol.PacketType.Play.Client.KEEP_ALIVE, com.comphenix.protocol.PacketType.Play.Client.POSITION, com.comphenix.protocol.PacketType.Play.Client.POSITION_LOOK) {
				@Override
				public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
					if(event.getPlayer() == null || Utils.getCheatPlayer(event.getPlayer().getUniqueId()) == null)
						return;
					
					if(Utils.isDetectionEnabled(HackType.MOVING_TIMER) && !event.getPacket().getType().equals(PacketType.Play.Client.KEEP_ALIVE))
						Timer.check(Utils.getCheatPlayer(event.getPlayer().getUniqueId()), new Location(event.getPlayer().getWorld(), event.getPacket().getDoubles().read(0), event.getPacket().getDoubles().read(1), event.getPacket().getDoubles().read(2)));
					
					if(event.getPacket().getType().equals(com.comphenix.protocol.PacketType.Play.Client.POSITION) ||
							event.getPacket().getType().equals(com.comphenix.protocol.PacketType.Play.Client.POSITION_LOOK))
						Utils.getCheatPlayer(event.getPlayer().getUniqueId()).setLastPositionPacketTime(System.currentTimeMillis());
					else Utils.getCheatPlayer(event.getPlayer().getUniqueId()).setLastKeepAlivePacketTime(System.currentTimeMillis());
				}
			});
			
			Utils.getProtocolManager()
			.addPacketListener(new com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
					com.comphenix.protocol.events.ListenerPriority.NORMAL,
					com.comphenix.protocol.PacketType.Play.Client.BLOCK_DIG) {
				@Override
				public void onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
					if(event.getPlayer() == null)
						return;
					
					CheatPlayer p = Utils.getCheatPlayer(event.getPlayer().getUniqueId());
					
					if(!Utils.checkPlayer(p))
						return;
					
					PlayerDigType pdt = event.getPacket().getPlayerDigTypes().getValues().get(0);
					
					if(pdt.equals(PlayerDigType.RELEASE_USE_ITEM))
						Utils.releaseItem(p);
										
					if(!pdt.equals(PlayerDigType.START_DESTROY_BLOCK) &&
							!pdt.equals(PlayerDigType.STOP_DESTROY_BLOCK) &&
							!pdt.equals(PlayerDigType.ABORT_DESTROY_BLOCK))
						return;
										
					BlockFace face = BlockFace.valueOf(event.getPacket().getSpecificModifier(Enum.class).readSafely(0).name());
					BlockPosition pos = event.getPacket().getBlockPositionModifier().readSafely(0);
					Block block = new Location(event.getPlayer().getWorld(), pos.getX(), pos.getY(), pos.getZ()).getBlock();
					IteractionType it;
					
					if(pdt.equals(PlayerDigType.START_DESTROY_BLOCK))
						it = IteractionType.START_DESTROY_BLOCK;
					else if(pdt.equals(PlayerDigType.STOP_DESTROY_BLOCK))
						it = IteractionType.STOP_DESTROY_BLOCK;
					else it = IteractionType.ABORT_DESTROY_BLOCK;
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							PlayerBlockInteractEvent call = new PlayerBlockInteractEvent(event.getPlayer(), block, face, it);
							
							Taka.getThisPlugin().getServer().getPluginManager().callEvent(call);
						}
					}.runTask(Taka.getThisPlugin());
				}
			});
			// enabled?
			/*
			 * Utils.getProtocolManager().addPacketListener(new
			 * com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
			 * com.comphenix.protocol.events.ListenerPriority.NORMAL,
			 * com.comphenix.protocol.PacketType.Play.Client.POSITION) {
			 * 
			 * @Override public void
			 * onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
			 * Timer.add(Utils.getCheatPlayer(event.getPlayer().getUniqueId()), (short) 1);
			 * } }); Utils.getProtocolManager().addPacketListener(new
			 * com.comphenix.protocol.events.PacketAdapter(Taka.getThisPlugin(),
			 * com.comphenix.protocol.events.ListenerPriority.NORMAL,
			 * com.comphenix.protocol.PacketType.Play.Client.POSITION_LOOK) {
			 * 
			 * @Override public void
			 * onPacketReceiving(com.comphenix.protocol.events.PacketEvent event) {
			 * Timer.add(Utils.getCheatPlayer(event.getPlayer().getUniqueId()), (short) 1);
			 * } });
			 */

		} catch (IllegalStateException ex) {
		}
		// TODO: Check on packet fly, fire ticks
	}
}
