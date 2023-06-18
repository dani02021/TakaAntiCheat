package bg.dani02.taka.anticheat.checks.chat;

import java.util.ArrayList;
import java.util.HashMap;

import bg.dani02.taka.anticheat.CheatPlayer;
import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Taka;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;
import bg.dani02.taka.anticheat.enums.Numbers;

public class ChatCaptcha {
	public static ArrayList<String> registered = new ArrayList<String>();

	private static HashMap<String, String> code = new HashMap<String, String>();

	private static HashMap<String, Short> attempts = new HashMap<String, Short>();

	public static boolean onCheck(final CheatPlayer p, String message) {
		if(!Utils.checkModule(p, HackType.CHAT_CAPTCHA))
			return false;
		
		// Prevent memory leak
		if(registered.size() > 1000)
			for(String name : registered)
				if(Taka.getThisPlugin().getServer().getPlayer(name) == null)
					registered.remove(name);
		
		if (!registered.contains(p.getPlayer().getName())) {
			if (code.containsKey(p.getPlayer().getName())) {
				if (message.equals(code.get(p.getPlayer().getName()))) {
					registered.add(p.getPlayer().getName());
					p.getPlayer().sendMessage(Utils.prepareColors(ConfigsMessages.chat_captcha_success_message));
					code.remove(p.getPlayer().getName());
					attempts.remove(p.getPlayer().getName());
				} else {
					if (!attempts.containsKey(p.getPlayer().getName()))
						attempts.put(p.getPlayer().getName(), (short) 1);
					else
						attempts.put(p.getPlayer().getName(), (short) (attempts.get(p.getPlayer().getName()) + 1));
					if (attempts.get(p.getPlayer().getName()) > ConfigsMessages.chat_captcha_attempts) {
						Taka.getThisPlugin().getServer().getScheduler().runTask(Taka.getThisPlugin(), new Runnable() {
							@Override
							public void run() {
								p.getPlayer().kickPlayer(Utils.prepareColors(
										ConfigsMessages.anticheat_prefix + ConfigsMessages.chat_captcha_kick_message));
							}
						});
						code.remove(p.getPlayer().getName());
						attempts.remove(p.getPlayer().getName());
						return false;
					}

					showMessage(p, Numbers.getRandom(), true);
				}
			} else {
				showMessage(p, Numbers.getRandom(), true);
			}

			return true;
		}

		return false;
	}
	
	public static void showMessage(CheatPlayer p, String[] number, boolean add) {
		p.getPlayer().sendMessage("\n" + Utils.prepareColors(ConfigsMessages.chat_captcha_first_message + "&f\n"
				+ number[0].replaceAll("X", ConfigsMessages.chat_captcha_numbers_color_code + "X&f")) + "\n");
		
		if(add)
			code.put(p.getPlayer().getName(), number[1]);
	}
	
	public static void showMessage(CheatPlayer p, String[] number) { showMessage(p, number, false); }

	public static boolean onCheck1(CheatPlayer p, String message) {
		if (p.haveBypass(HackType.CHAT_CAPTCHA))
			return false;
		if (!registered.contains(p.getPlayer().getName())) {
			if (!ConfigsMessages.chat_captcha_allowed_commands.contains(message.toLowerCase())) {
				return onCheck(p, message);
			}
		}

		return false;
	}

}
