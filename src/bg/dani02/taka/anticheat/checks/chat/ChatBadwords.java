package bg.dani02.taka.anticheat.checks.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.api.PlayerSwearEvent;
import bg.dani02.taka.anticheat.enums.HackType;

public class ChatBadwords {
	public static HashSet<String> badwords = new HashSet<String>();
	
	public static String onCheck(final CheatPlayer p, String message) {
		if(!Utils.checkModule(p, HackType.CHAT_BADWORDS))
			return message;
		// Substring the message by sets of 1 word, 2 words...n sequential words
		String[] words = message.split(" ");
		int n = words.length > 4 ? 4 : words.length; // MAX by 4 words
		
		while(n > 0) {
			loop1:
			for(int i = 0; i < words.length; i++) {
				String msg = "";
				
				for(int o = 0; o < n; o++) {
					// int m = (o + i) >= words.length ? words.length-1 : o + i; // Prevent overflow
					if(o + i >= words.length)
						break loop1;
					
					msg += words[o + i] + " ";
				}
				
				String halfMessage = msg.substring(0, msg.length()-1); // Half ready
				if(checkInBadWorlds(halfMessage)) { // Removes the last character, which is empty space ' ' char
					PlayerSwearEvent event = new PlayerSwearEvent(p.getPlayer(), message, ConfigsMessages.chat_badwords_action.equals("hide"));
					Taka.getThisPlugin().getServer().getPluginManager().callEvent(event);
					
					if(event.isCancelled())
						return message;
					
					if(event.isHidden()) {
						message = message.replace(halfMessage, halfMessage.replaceAll(".", "*"));
						p.getPlayer().sendMessage(Utils.prepareColors(Utils.getTakaPrefix() + ConfigsMessages.chat_badwords_player_message));
						return message;
					} else return null;
				}
			}
			n -= 1;
		}
		
		return message;
	}
	
	private static boolean checkInBadWorlds(String substring) {
		if(badwords.contains(substring))
			return true;
		
		int i = 0;
		
		while(i < substring.length()) {
			if(i != substring.length()-1 && substring.charAt(i) == substring.charAt(i+1)) {
				substring = substring.substring(0, i).concat(substring.substring(i+1));
				i--;
			}
			
			i++;
		}
		
		if(badwords.contains(substring))
			return true;
				
		if(badwords.contains(substring.replaceAll("[\\^$.|\\?\\*\\+()!]", ""))) // Removes special characters
			return true;
		
		return false;
	}

	public static void loadBadwords() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				List<String> files = ConfigsMessages.chat_badwords_wordslist;
				
				File dir = new File("plugins/TakaAntiCheat/badwords");
				
				for(File lang : dir.listFiles()) {
					if(files.contains(lang.getName()) && !lang.getName().equalsIgnoreCase("license")) {
						try {
							BufferedReader br = new BufferedReader(new FileReader(lang));
							
							while(br.ready()) {
								badwords.add(br.readLine());
							}
							
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}.runTaskAsynchronously(Taka.getThisPlugin());
	}
}