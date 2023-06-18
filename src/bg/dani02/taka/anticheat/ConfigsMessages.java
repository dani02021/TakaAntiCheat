package bg.dani02.taka.anticheat;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigsMessages {
	public static YamlConfiguration messages;
	public static YamlConfiguration disabled_worlds;
	
	public static double config_version;
	public static double messages_version;
	
	public static String anticheat_prefix;
	public static String anticheat_admin_permission;
	public static String anticheat_bypass_permission;
	public static String anticheat_admin_message_form;
	public static String anticheat_admin_staff_mode_message;
	public static String anticheat_admin_debug_mode_message;
	public static String anticheat_admin_debug_mode_type_message;
	public static String anticheat_no_permission_message;
	public static String anticheat_report_complete_message;
	public static String anticheat_report_staff_message;
	public static String anticheat_debug_message;
	public static String anticheat_vl_cleaner_message;
	public static String anticheat_treport_gui_title;
	public static String anticheat_world_enable_message;
	public static String anticheat_world_disable_message;
	public static String anticheat_teleport_to_hacker_message;
	
	public static double anticheat_break_point_tps;
	public static int anticheat_vl_cleaner_time;
	public static boolean anticheat_debug_default;
	public static int anticheat_debug_mode_default;
	public static List<String> anticheat_disable_in_world;
	public static boolean anticheat_ghost;
	public static String anticheat_special_command;
	
	public static int anticheat_verbose_mode_3_timing;

	public static boolean log_file_enable;
	public static int log_file_wtdt;
	public static String log_file_message;
	public static double log_file_warning;
	
	public static boolean log_console_enable;
	public static boolean log_console_use_colors;

	public static boolean ban_wave_enable;
	public static int ban_wave_time;
	public static boolean ban_wave_ibol;
	public static double ban_wave_hack_prob;
	public static String ban_wave_command;

	public static boolean fly_invalid_y_instant_enable;
	public static boolean fly_invalid_y_instant_ghosted;
	public static Map<String, Object> fly_invalid_y_instant_threshold;

	public static boolean fly_stable_y_enable;
	public static boolean fly_stable_y_ghosted;
	public static String fly_stable_y_cancelMove;
	public static Map<String, Object> fly_stable_y_threshold;

	public static boolean fly_double_jump_up_enable;
	public static boolean fly_double_jump_up_ghosted;
	public static Map<String, Object> fly_double_jump_up_threshold;

	public static boolean fly_double_jump_down_enable;
	public static boolean fly_double_jump_down_ghosted;
	public static Map<String, Object> fly_double_jump_down_threshold;

	public static boolean fastladder_non_instant_enable;
	public static boolean fastladder_non_instant_ghosted;
	public static String fastladder_non_instant_cancelMove;
	public static Map<String, Object> fastladder_non_instant_threshold;

	public static boolean fastladder_instant_enable;
	public static boolean fastladder_instant_ghosted;
	public static Map<String, Object> fastladder_instant_threshold;

	public static boolean fastladder_impossible_jump_enable;
	public static boolean fastladder_impossible_jump_ghosted;
	public static double fastladder_impossible_jump_speed;
	public static Map<String, Object> fastladder_impossible_jump_threshold;

	public static boolean invalidfall_stabledistance_enable;
	public static boolean invalidfall_stabledistance_ghosted;
	public static String invalidfall_stabledistance_cancelMove;
	public static Map<String, Object> invalidfall_stabledistance_threshold;

	public static boolean invalidfall_slowerdistance_enable;
	public static boolean invalidfall_slowerdistance_ghosted;
	public static String invalidfall_slowerdistance_cancelMove;
	public static Map<String, Object> invalidfall_slowerdistance_threshold;

	public static boolean invalidfall_fasterdistance_non_instant_enable;
	public static boolean invalidfall_fasterdistance_non_instant_ghosted;
	public static String invalidfall_fasterdistance_non_instant_cancelMove;
	public static Map<String, Object> invalidfall_fasterdistance_non_instant_threshold;

	public static boolean invalidfall_fasterdistance_instant_enable;
	public static boolean invalidfall_fasterdistance_instant_ghosted;
	public static Map<String, Object> invalidfall_fasterdistance_instant_threshold;

	public static boolean invalidfall_faststartdistance_enable;
	public static boolean invalidfall_faststartdistance_ghosted;
	public static Map<String, Object> invalidfall_faststartdistance_threshold;

	public static boolean invalidfall_slow_y_enable;
	public static boolean invalidfall_slow_y_ghosted;
	public static Map<String, Object> invalidfall_slow_y_threshold;

	public static boolean fly_modulo_enable;
	public static boolean fly_modulo_ghosted;
	public static Map<String, Object> fly_modulo_threshold;

	public static boolean fly_slow_y_non_instant_enable;
	public static boolean fly_slow_y_non_instant_ghosted;
	public static String fly_slow_y_non_instant_cancelMove;
	public static Map<String, Object> fly_slow_y_non_instant_threshold;

	public static boolean fly_slow_y_instant_enable;
	public static boolean fly_slow_y_instant_ghosted;
	public static Map<String, Object> fly_slow_y_instant_threshold;

	public static boolean step_basic_jump_pads_wooden;
	public static boolean step_basic_jump_pads_stone;
	public static boolean step_basic_jump_pads_iron;
	public static boolean step_basic_jump_pads_gold;

	public static boolean step_ultra_enable;
	public static boolean step_ultra_ghosted;
	public static Map<String, Object> step_ultra_threshold;

	public static boolean highjump_enable;
	public static boolean highjump_ghosted;
	public static Map<String, Object> highjump_threshold;

	public static boolean speed_onground_enable;
	public static boolean speed_onground_ghosted;
	public static String speed_onground_cancelMove;
	public static Map<String, Object> speed_onground_threshold;

	public static boolean speed_onground_slime_enable;
	public static boolean speed_onground_slime_ghosted;
	public static String speed_onground_slime_cancelMove;
	public static Map<String, Object> speed_onground_slime_threshold;

	public static boolean speed_onground_ice_enable;
	public static boolean speed_onground_ice_ghosted;
	public static String speed_onground_ice_cancelMove;
	public static Map<String, Object> speed_onground_ice_threshold;

	public static boolean speed_onground_soulsand_enable;
	public static boolean speed_onground_soulsand_ghosted;
	public static String speed_onground_soulsand_cancelMove;
	public static Map<String, Object> speed_onground_soulsand_threshold;

	public static boolean speed_air_enable;
	public static boolean speed_air_ghosted;
	public static String speed_air_cancelMove;
	public static Map<String, Object> speed_air_threshold;

	public static boolean speed_cobweb_enable;
	public static boolean speed_cobweb_ghosted;
	public static String speed_cobweb_cancelMove;
	public static Map<String, Object> speed_cobweb_threshold;

	public static boolean speed_item_enable;
	public static boolean speed_item_ghosted;
	public static String speed_item_cancelMove;
	public static Map<String, Object> speed_item_threshold;

	public static boolean speed_liquid_water_enable;
	public static boolean speed_liquid_water_ghosted;
	public static String speed_liquid_water_cancelMove;
	public static Map<String, Object> speed_liquid_water_threshold;

	public static boolean speed_liquid_lava_enable;
	public static boolean speed_liquid_lava_ghosted;
	public static String speed_liquid_lava_cancelMove;
	public static Map<String, Object> speed_liquid_lava_threshold;

	public static boolean speed_sprint_hungry_enable;
	public static boolean speed_sprint_hungry_ghosted;
	public static String speed_sprint_hungry_cancelMove;
	public static Map<String, Object> speed_sprint_hungry_threshold;
	
	public static boolean speed_soulsand_enable;
	public static boolean speed_soulsand_ghosted;
	public static String speed_soulsand_cancelMove;
	public static Map<String, Object> speed_soulsand_threshold;
	
	public static boolean speed_ice_enable;
	public static boolean speed_ice_ghosted;
	public static String speed_ice_cancelMove;
	public static Map<String, Object> speed_ice_threshold;
	
	public static boolean speed_slime_enable;
	public static boolean speed_slime_ghosted;
	public static String speed_slime_cancelMove;
	public static Map<String, Object> speed_slime_threshold;
	
	public static boolean speed_vehicle_enable;
	public static boolean speed_vehicle_ghosted;
	public static String speed_vehicle_cancelMove;
	public static Map<String, Object> speed_vehicle_threshold;

	public static boolean slime_jump_enable;
	public static boolean slime_jump_ghosted;
	public static Map<String, Object> slime_jump_threshold;

	public static boolean anti_levitation_enable;
	public static boolean anti_levitation_ghosted;
	public static String anti_levitation_cancelMove;
	public static Map<String, Object> anti_levitation_threshold;

	public static boolean nofall_enable;
	public static boolean nofall_ghosted;
	public static Map<String, Object> nofall_threshold;

	public static boolean impossible_jump_enable;
	public static boolean impossible_jump_ghosted;
	public static String impossible_jump_cancelMove;
	public static Map<String, Object> impossible_jump_threshold;

	public static boolean fastinventory_enable;
	public static boolean fastinventory_ghosted;
	public static Map<String, Object> fastinventory_threshold;

	public static boolean inv_move_enable;
	public static boolean inv_move_ghosted;
	public static String inv_move_cancelMove;
	public static Map<String, Object> inv_move_threshold;
	
	public static boolean strafe_enable;
	public static boolean strafe_ghosted;
	public static String strafe_cancelMove;
	public static Map<String, Object> strafe_threshold;

	public static boolean fast_eat_enable;
	public static boolean fast_eat_ghosted;
	public static Map<String, Object> fast_eat_threshold;

	public static boolean fast_click_enable;
	public static boolean fast_click_ghosted;
	public static String fast_click_cancelMove;
	public static Map<String, Object> fast_click_threshold;
	
	public static boolean throw_enable;
	public static boolean throw_ghosted;
	public static Map<String, Object> throw_threshold;

	public static boolean wrongblock_direction_enable;
	public static boolean wrongblock_direction_ghosted;
	public static Map<String, Object> wrongblock_direction_threshold;

	public static boolean liquid_interaction_enable;
	public static boolean liquid_interaction_ghosted;
	public static Map<String, Object> liquid_interaction_threshold;

	public static boolean creative_drop_enable;
	public static boolean creative_drop_ghosted;
	public static Map<String, Object> creative_drop_threshold;

	public static boolean no_swing_enable;
	public static boolean no_swing_ghosted;
	public static String no_swing_cancelMove;
	public static Map<String, Object> no_swing_threshold;

	public static boolean scaffoldwalk_basic_enable;
	public static boolean scaffoldwalk_basic_ghosted;
	public static String scaffoldwalk_basic_cancelMove;
	public static Map<String, Object> scaffoldwalk_basic_threshold;

	public static boolean scaffoldwalk_advanced_enable;
	public static boolean scaffoldwalk_advanced_ghosted;
	public static String scaffoldwalk_advanced_cancelMove;
	public static Map<String, Object> scaffoldwalk_advanced_threshold;

	public static boolean scaffoldwalk_ground_enable;
	public static boolean scaffoldwalk_ground_ghosted;
	public static String scaffoldwalk_ground_cancelMove;
	public static Map<String, Object> scaffoldwalk_ground_threshold;

	public static boolean scaffoldwalk_expand_enable;
	public static boolean scaffoldwalk_expand_ghosted;
	public static String scaffoldwalk_expand_cancelMove;
	public static Map<String, Object> scaffoldwalk_expand_threshold;
	
	public static boolean scaffoldwalk_timer_enable;
	public static boolean scaffoldwalk_timer_ghosted;
	public static String scaffoldwalk_timer_cancelMove;
	public static Map<String, Object> scaffoldwalk_timer_threshold;

	public static boolean fast_place_enable;
	public static boolean fast_place_ghosted;
	public static String fast_place_cancelMove;
	public static Map<String, Object> fast_place_threshold;

	public static boolean nuker_enable;
	public static boolean nuker_ghosted;
	public static String nuker_cancelMove;
	public static Map<String, Object> nuker_threshold;
	
	public static boolean fast_break_enable;
	public static boolean fast_break_ghosted;
	public static Map<String, Object> fast_break_threshold;
	
	public static boolean no_break_delay_enable;
	public static boolean no_break_delay_ghosted;
	public static Map<String, Object> no_break_delay_threshold;
	
	public static boolean invaliditeraction_block_enable;
	public static boolean invaliditeraction_block_ghosted;
	public static Map<String, Object> invaliditeraction_block_threshold;
	
	public static boolean autosign_enable;
	public static String autosign_cancelMove;
	public static boolean autosign_ghosted;
	public static Map<String, Object> autosign_threshold;

	public static boolean sneak_enable;
	public static boolean sneak_ghosted;
	public static Map<String, Object> sneak_threshold;

	public static boolean jesus_enable;
	public static boolean jesus_ghosted;
	public static Map<String, Object> jesus_threshold;

	public static boolean no_pitch_enable;
	public static boolean no_pitch_ghosted;
	public static Map<String, Object> no_pitch_threshold;

	public static boolean ground_elytra_enable;
	public static boolean ground_elytra_ghosted;
	public static String ground_elytra_cancelMove;
	public static Map<String, Object> ground_elytra_threshold;

	public static boolean timer_enable;
	public static boolean timer_ghosted;
	public static String timer_cancelMove;
	public static Map<String, Object> timer_threshold;

	public static boolean low_jump_enable;
	public static boolean low_jump_ghosted;
	public static String low_jump_cancelMove;
	public static Map<String, Object> low_jump_threshold;
	
	public static boolean blink_enable;
	public static boolean blink_ghosted;
	public static String blink_cancelMove;
	public static Map<String, Object> blink_threshold;

	public static boolean healthtag_enable;
	public static boolean healthtag_player_only;

	public static boolean criticals_enable;
	public static boolean criticals_ghosted;
	public static String criticals_cancelMove;
	public static Map<String, Object> criticals_threshold;

	public static boolean reach_combat_enable;
	public static boolean reach_combat_ghosted;
	public static String reach_combat_cancelMove;
	public static Map<String, Object> reach_combat_threshold;

	public static boolean autosoup_enable;
	public static boolean autosoup_ghosted;
	public static Map<String, Object> autosoup_threshold;

	public static boolean fastbow_enable;
	public static boolean fastbow_ghosted;
	public static String fastbow_cancelMove;
	public static Map<String, Object> fastbow_threshold;
	
	public static boolean invaliditeraction_entity_enable;
	public static boolean invaliditeraction_entity_ghosted;
	public static Map<String, Object> invaliditeraction_entity_threshold;

	public static boolean plugin_list_enable;

	public static boolean uuid_spoof_enable;

	public static boolean autorespawn_enable;
	public static String autorespawn_kickMsg;

	public static boolean world_downloader_enable;
	public static String world_downloader_cmd;

	public static boolean jigsaw_servercrash_enable;
	public static String jigsaw_servercrash_cmd;
	
	public static boolean freecam_enable;
	public static boolean freecam_better_check;

	public static boolean chat_captcha_enable;
	public static int chat_captcha_attempts;
	public static String chat_captcha_first_message;
	public static String chat_captcha_success_message;
	public static String chat_captcha_kick_message;
	public static String chat_captcha_numbers_color_code;
	public static List<String> chat_captcha_allowed_commands;
	
	public static boolean chat_badwords_enable;
	public static String chat_badwords_action;
	public static String chat_badwords_player_message;
	public static List<String> chat_badwords_wordslist;

	public static void load() {
		config_version = Taka.getThisPlugin().getConfig().getDouble("config-version");
		
		anticheat_break_point_tps = Taka.getThisPlugin().getConfig().getDouble("anticheat-break-point-tps");
		anticheat_vl_cleaner_time = Taka.getThisPlugin().getConfig().getInt("anticheat-vl-cleaner-time");
		anticheat_debug_default = Taka.getThisPlugin().getConfig().getBoolean("anticheat-verbose-default");
		anticheat_debug_mode_default = Taka.getThisPlugin().getConfig().getInt("anticheat-verbose-mode-default");
		anticheat_ghost = Taka.getThisPlugin().getConfig().getBoolean("anticheat-ghost");
		anticheat_special_command = Taka.getThisPlugin().getConfig().getString("special-command");
		
		anticheat_verbose_mode_3_timing = Taka.getThisPlugin().getConfig().getInt("anticheat-verbose-mode-3-timing");

		log_file_enable = Taka.getThisPlugin().getConfig().getBoolean("Log.File.enable");
		log_file_wtdt = Taka.getThisPlugin().getConfig().getInt("Log.File.wtdt");
		log_file_message = Taka.getThisPlugin().getConfig().getString("Log.File.message");
		log_file_warning = Taka.getThisPlugin().getConfig().getDouble("Log.File.warning");
		
		log_console_enable = Taka.getThisPlugin().getConfig().getBoolean("Log.Console.enable");
		log_console_use_colors = Taka.getThisPlugin().getConfig().getBoolean("Log.Console.use-colors");

		ban_wave_enable = Taka.getThisPlugin().getConfig().getBoolean("BanWave.enable");
		ban_wave_time = Taka.getThisPlugin().getConfig().getInt("BanWave.time");
		ban_wave_ibol = Taka.getThisPlugin().getConfig().getBoolean("BanWave.ibol");
		ban_wave_hack_prob = Taka.getThisPlugin().getConfig().getDouble("BanWave.hacker_probability");
		ban_wave_command = Taka.getThisPlugin().getConfig().getString("BanWave.command");

		fly_invalid_y_instant_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.InvalidY.enable");
		fly_invalid_y_instant_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.InvalidY.ghosted");
		fly_invalid_y_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Fly.InvalidY.threshold").getValues(true);

		fly_stable_y_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.StableY.enable");
		fly_stable_y_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.StableY.ghosted");
		fly_stable_y_cancelMove = Taka.getThisPlugin().getConfig().getString("Fly.StableY.cancelMove");
		fly_stable_y_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Fly.StableY.threshold")
				.getValues(true);

		fly_double_jump_up_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.DoubleJump.Up.enable");
		fly_double_jump_up_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.DoubleJump.Up.ghosted");
		fly_double_jump_up_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Fly.DoubleJump.Up.threshold").getValues(true);

		fly_double_jump_down_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.DoubleJump.Down.enable");
		fly_double_jump_down_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.DoubleJump.Down.ghosted");
		fly_double_jump_down_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Fly.DoubleJump.Down.threshold").getValues(true);

		fly_modulo_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.Modulo.enable");
		fly_modulo_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.Modulo.ghosted");
		fly_modulo_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Fly.Modulo.threshold")
				.getValues(true);

		fly_slow_y_non_instant_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.SlowY.NonInstant.enable");
		fly_slow_y_non_instant_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.SlowY.NonInstant.ghosted");
		fly_slow_y_non_instant_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("Fly.SlowY.NonInstant.cancelMove");
		fly_slow_y_non_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Fly.SlowY.NonInstant.threshold").getValues(true);

		fly_slow_y_instant_enable = Taka.getThisPlugin().getConfig().getBoolean("Fly.SlowY.Instant.enable");
		fly_slow_y_instant_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Fly.SlowY.Instant.ghosted");
		fly_slow_y_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Fly.SlowY.Instant.threshold").getValues(true);

		step_basic_jump_pads_wooden = Taka.getThisPlugin().getConfig().getBoolean("Step.jump-pads.wooden-plate");
		step_basic_jump_pads_stone = Taka.getThisPlugin().getConfig().getBoolean("Step.jump-pads.stone-plate");
		step_basic_jump_pads_iron = Taka.getThisPlugin().getConfig().getBoolean("Step.jump-pads.iron-plate");
		step_basic_jump_pads_gold = Taka.getThisPlugin().getConfig().getBoolean("Step.jump-pads.gold-plate");

		step_ultra_enable = Taka.getThisPlugin().getConfig().getBoolean("Step.enable");
		step_ultra_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Step.ghosted");
		step_ultra_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Step.threshold")
				.getValues(true);

		highjump_enable = Taka.getThisPlugin().getConfig().getBoolean("HighJump.enable");
		highjump_ghosted = Taka.getThisPlugin().getConfig().getBoolean("HighJump.ghosted");
		highjump_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("HighJump.threshold")
				.getValues(true);

		fastladder_non_instant_enable = Taka.getThisPlugin().getConfig().getBoolean("FastLadder.NonInstant.enable");
		fastladder_non_instant_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastLadder.NonInstant.ghosted");
		fastladder_non_instant_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("FastLadder.NonInstant.cancelMove");
		fastladder_non_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("FastLadder.NonInstant.threshold").getValues(true);

		fastladder_instant_enable = Taka.getThisPlugin().getConfig().getBoolean("FastLadder.Instant.enable");
		fastladder_instant_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastLadder.Instant.ghosted");
		fastladder_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("FastLadder.Instant.threshold").getValues(true);

		invalidfall_stabledistance_enable = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.StableDistance.enable");
		invalidfall_stabledistance_ghosted = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.StableDistance.ghosted");
		invalidfall_stabledistance_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("InvalidFall.StableDistance.cancelMove");
		invalidfall_stabledistance_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.StableDistance.threshold").getValues(true);

		invalidfall_slowerdistance_enable = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.SlowerDistance.enable");
		invalidfall_slowerdistance_ghosted = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.SlowerDistance.ghosted");
		invalidfall_slowerdistance_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("InvalidFall.SlowerDistance.cancelMove");
		invalidfall_slowerdistance_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.SlowerDistance.threshold").getValues(true);

		invalidfall_fasterdistance_non_instant_enable = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FasterDistance.NonInstant.enable");
		invalidfall_fasterdistance_non_instant_ghosted = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FasterDistance.NonInstant.ghosted");
		invalidfall_fasterdistance_non_instant_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("InvalidFall.FasterDistance.NonInstant.cancelMove");
		invalidfall_fasterdistance_non_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.FasterDistance.NonInstant.threshold").getValues(true);

		invalidfall_fasterdistance_instant_enable = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FasterDistance.Instant.enable");
		invalidfall_fasterdistance_instant_ghosted = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FasterDistance.Instant.ghosted");
		invalidfall_fasterdistance_instant_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.FasterDistance.Instant.threshold").getValues(true);

		invalidfall_faststartdistance_enable = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FastStartDistance.enable");
		invalidfall_faststartdistance_ghosted = Taka.getThisPlugin().getConfig()
				.getBoolean("InvalidFall.FastStartDistance.ghosted");
		invalidfall_faststartdistance_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.FastStartDistance.threshold").getValues(true);

		invalidfall_slow_y_enable = Taka.getThisPlugin().getConfig().getBoolean("InvalidFall.SlowY.enable");
		invalidfall_slow_y_ghosted = Taka.getThisPlugin().getConfig().getBoolean("InvalidFall.SlowY.ghosted");
		invalidfall_slow_y_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("InvalidFall.SlowY.threshold").getValues(true);

		speed_onground_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Normal.enable");
		speed_onground_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Normal.ghosted");
		speed_onground_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.OnGround.Normal.cancelMove");
		speed_onground_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.OnGround.Normal.threshold").getValues(true);

		speed_onground_slime_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Slime.enable");
		speed_onground_slime_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Slime.ghosted");
		speed_onground_slime_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.OnGround.Slime.cancelMove");
		speed_onground_slime_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.OnGround.Slime.threshold").getValues(true);

		speed_onground_ice_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Ice.enable");
		speed_onground_ice_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.Ice.ghosted");
		speed_onground_ice_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.OnGround.Ice.cancelMove");
		speed_onground_ice_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.OnGround.Ice.threshold").getValues(true);

		speed_onground_soulsand_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.SoulSand.enable");
		speed_onground_soulsand_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.OnGround.SoulSand.ghosted");
		speed_onground_soulsand_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("Speed.OnGround.SoulSand.cancelMove");
		speed_onground_soulsand_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.OnGround.SoulSand.threshold").getValues(true);

		speed_air_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Air.enable");
		speed_air_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Air.ghosted");
		speed_air_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Air.cancelMove");
		speed_air_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Speed.Air.threshold")
				.getValues(true);

		speed_cobweb_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.CobWeb.enable");
		speed_cobweb_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.CobWeb.ghosted");
		speed_cobweb_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.CobWeb.cancelMove");
		speed_cobweb_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Speed.CobWeb.threshold")
				.getValues(true);

		speed_item_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Item.enable");
		speed_item_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Item.ghosted");
		speed_item_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Item.cancelMove");
		speed_item_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Speed.Item.threshold")
				.getValues(true);

		speed_liquid_water_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Liquid.Water.enable");
		speed_liquid_water_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Liquid.Water.ghosted");
		speed_liquid_water_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Liquid.Water.cancelMove");
		speed_liquid_water_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Liquid.Water.threshold").getValues(true);

		speed_liquid_lava_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Liquid.Lava.enable");
		speed_liquid_lava_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Liquid.Lava.ghosted");
		speed_liquid_lava_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Liquid.Lava.cancelMove");
		speed_liquid_lava_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Liquid.Lava.threshold").getValues(true);
		
		speed_soulsand_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Soulsand.enable");
		speed_soulsand_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Soulsand.ghosted");
		speed_soulsand_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Soulsand.cancelMove");
		speed_soulsand_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Soulsand.threshold").getValues(true);
		
		speed_ice_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Ice.enable");
		speed_ice_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Ice.ghosted");
		speed_ice_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Ice.cancelMove");
		speed_ice_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Ice.threshold").getValues(true);
		
		speed_slime_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Slime.enable");
		speed_slime_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Slime.ghosted");
		speed_slime_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Slime.cancelMove");
		speed_slime_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Slime.threshold").getValues(true);
		
		speed_vehicle_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.Vehicle.enable");
		speed_vehicle_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.Vehicle.ghosted");
		speed_vehicle_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.Vehicle.cancelMove");
		speed_vehicle_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.Vehicle.threshold").getValues(true);
		
		speed_sprint_hungry_enable = Taka.getThisPlugin().getConfig().getBoolean("Speed.SprintHungry.enable");
		speed_sprint_hungry_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Speed.SprintHungry.ghosted");
		speed_sprint_hungry_cancelMove = Taka.getThisPlugin().getConfig().getString("Speed.SprintHungry.cancelMove");
		speed_sprint_hungry_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("Speed.SprintHungry.threshold").getValues(true);

		anti_levitation_enable = Taka.getThisPlugin().getConfig().getBoolean("AntiLevitation.enable");
		anti_levitation_ghosted = Taka.getThisPlugin().getConfig().getBoolean("AntiLevitation.ghosted");
		anti_levitation_cancelMove = Taka.getThisPlugin().getConfig().getString("AntiLevitation.cancelMove");
		anti_levitation_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("AntiLevitation.threshold")
				.getValues(true);

		nofall_enable = Taka.getThisPlugin().getConfig().getBoolean("NoFall.enable");
		nofall_ghosted = Taka.getThisPlugin().getConfig().getBoolean("NoFall.ghosted");
		nofall_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("NoFall.threshold").getValues(true);

		fastinventory_enable = Taka.getThisPlugin().getConfig().getBoolean("FastInventory.enable");
		fastinventory_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastInventory.ghosted");
		fastinventory_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastInventory.threshold")
				.getValues(true);

		inv_move_enable = Taka.getThisPlugin().getConfig().getBoolean("InvMove.enable");
		inv_move_ghosted = Taka.getThisPlugin().getConfig().getBoolean("InvMove.ghosted");
		inv_move_cancelMove = Taka.getThisPlugin().getConfig().getString("InvMove.cancelMove");
		inv_move_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("InvMove.threshold")
				.getValues(true);
		
		strafe_enable = Taka.getThisPlugin().getConfig().getBoolean("Strafe.enable");
		strafe_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Strafe.ghosted");
		strafe_cancelMove = Taka.getThisPlugin().getConfig().getString("Strafe.cancelMove");
		strafe_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Strafe.threshold")
				.getValues(true);

		fast_eat_enable = Taka.getThisPlugin().getConfig().getBoolean("FastEat.enable");
		fast_eat_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastEat.ghosted");
		fast_eat_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastEat.threshold")
				.getValues(true);

		fast_click_enable = Taka.getThisPlugin().getConfig().getBoolean("FastClick.enable");
		fast_click_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastClick.ghosted");
		fast_click_cancelMove = Taka.getThisPlugin().getConfig().getString("FastClick.cancelMove");
		fast_click_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastClick.threshold")
				.getValues(true);
		
		throw_enable = Taka.getThisPlugin().getConfig().getBoolean("Throw.enable");
		throw_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Throw.ghosted");
		throw_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Throw.threshold")
				.getValues(true);

		wrongblock_direction_enable = Taka.getThisPlugin().getConfig().getBoolean("WrongBlock.Direction.enable");
		wrongblock_direction_ghosted = Taka.getThisPlugin().getConfig().getBoolean("WrongBlock.Direction.ghosted");
		wrongblock_direction_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("WrongBlock.Direction.threshold").getValues(true);

		liquid_interaction_enable = Taka.getThisPlugin().getConfig().getBoolean("LiquidInteraction.enable");
		liquid_interaction_ghosted = Taka.getThisPlugin().getConfig().getBoolean("LiquidInteraction.ghosted");
		liquid_interaction_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("LiquidInteraction.threshold").getValues(true);

		creative_drop_enable = Taka.getThisPlugin().getConfig().getBoolean("CreativeDrop.enable");
		creative_drop_ghosted = Taka.getThisPlugin().getConfig().getBoolean("CreativeDrop.ghosted");
		creative_drop_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("CreativeDrop.threshold")
				.getValues(true);

		no_swing_enable = Taka.getThisPlugin().getConfig().getBoolean("NoSwing.enable");
		no_swing_ghosted = Taka.getThisPlugin().getConfig().getBoolean("NoSwing.ghosted");
		no_swing_cancelMove = Taka.getThisPlugin().getConfig().getString("NoSwing.cancelMove");
		no_swing_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("NoSwing.threshold")
				.getValues(true);

		impossible_jump_enable = Taka.getThisPlugin().getConfig().getBoolean("ImpossibleJump.enable");
		impossible_jump_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ImpossibleJump.ghosted");
		impossible_jump_cancelMove = Taka.getThisPlugin().getConfig().getString("ImpossibleJump.cancelMove");
		impossible_jump_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("ImpossibleJump.threshold")
				.getValues(true);

		sneak_enable = Taka.getThisPlugin().getConfig().getBoolean("Sneak.enable");
		sneak_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Sneak.ghosted");
		sneak_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Sneak.threshold").getValues(true);

		jesus_enable = Taka.getThisPlugin().getConfig().getBoolean("Jesus.enable");
		jesus_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Jesus.ghosted");
		jesus_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Jesus.threshold").getValues(true);

		no_pitch_enable = Taka.getThisPlugin().getConfig().getBoolean("NoPitch.enable");
		no_pitch_ghosted = Taka.getThisPlugin().getConfig().getBoolean("NoPitch.ghosted");
		no_pitch_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("NoPitch.threshold")
				.getValues(true);

		ground_elytra_enable = Taka.getThisPlugin().getConfig().getBoolean("GroundElytra.enable");
		ground_elytra_ghosted = Taka.getThisPlugin().getConfig().getBoolean("GroundElytra.ghosted");
		ground_elytra_cancelMove = Taka.getThisPlugin().getConfig().getString("GroundElytra.cancelMove");
		ground_elytra_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("GroundElytra.threshold")
				.getValues(true);

		timer_enable = Taka.getThisPlugin().getConfig().getBoolean("Timer.enable");
		timer_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Timer.ghosted");
		timer_cancelMove = Taka.getThisPlugin().getConfig().getString("Timer.cancelMove");
		timer_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Timer.threshold").getValues(true);

		low_jump_enable = Taka.getThisPlugin().getConfig().getBoolean("LowJump.enable");
		low_jump_ghosted = Taka.getThisPlugin().getConfig().getBoolean("LowJump.ghosted");
		low_jump_cancelMove = Taka.getThisPlugin().getConfig().getString("LowJump.cancelMove");
		low_jump_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("LowJump.threshold")
				.getValues(true);
		
		blink_enable = Taka.getThisPlugin().getConfig().getBoolean("Blink.enable");
		blink_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Blink.ghosted");
		blink_cancelMove = Taka.getThisPlugin().getConfig().getString("Blink.cancelMove");
		blink_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Blink.threshold")
				.getValues(true);

		healthtag_enable = Taka.getThisPlugin().getConfig().getBoolean("HealthTag.enable");
		healthtag_player_only = Taka.getThisPlugin().getConfig().getBoolean("HealthTag.player-only");

		plugin_list_enable = Taka.getThisPlugin().getConfig().getBoolean("PluginList.enable");

		uuid_spoof_enable = Taka.getThisPlugin().getConfig().getBoolean("UUIDSpoof.enable");

		autorespawn_enable = Taka.getThisPlugin().getConfig().getBoolean("AutoRespawn.enable");

		world_downloader_enable = Taka.getThisPlugin().getConfig().getBoolean("WorldDownloader.enable");
		world_downloader_cmd = Taka.getThisPlugin().getConfig().getString("WorldDownloader.cmd");

		jigsaw_servercrash_enable = Taka.getThisPlugin().getConfig().getBoolean("JigsawServerCrash.enable");
		jigsaw_servercrash_cmd = Taka.getThisPlugin().getConfig().getString("JigsawServerCrash.cmd");
		
		freecam_enable = Taka.getThisPlugin().getConfig().getBoolean("Freecam.enable");
		freecam_better_check = Taka.getThisPlugin().getConfig().getBoolean("Freecam.better-check");

		criticals_enable = Taka.getThisPlugin().getConfig().getBoolean("Criticals.enable");
		criticals_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Criticals.ghosted");
		criticals_cancelMove = Taka.getThisPlugin().getConfig().getString("Criticals.cancelMove");
		criticals_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Criticals.threshold")
				.getValues(true);

		reach_combat_enable = Taka.getThisPlugin().getConfig().getBoolean("Reach.Combat.enable");
		reach_combat_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Reach.Combat.ghosted");
		reach_combat_cancelMove = Taka.getThisPlugin().getConfig().getString("Reach.Combat.cancelMove");
		reach_combat_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Reach.Combat.threshold")
				.getValues(true);

		autosoup_enable = Taka.getThisPlugin().getConfig().getBoolean("AutoSoup.enable");
		autosoup_ghosted = Taka.getThisPlugin().getConfig().getBoolean("AutoSoup.ghosted");
		autosoup_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("AutoSoup.threshold")
				.getValues(true);

		fastbow_enable = Taka.getThisPlugin().getConfig().getBoolean("FastBow.enable");
		fastbow_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastBow.ghosted");
		fastbow_cancelMove = Taka.getThisPlugin().getConfig().getString("FastBow.cancelMove");
		fastbow_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastBow.threshold")
				.getValues(true);
		
		invaliditeraction_entity_enable = Taka.getThisPlugin().getConfig().getBoolean("InvalidIteraction.Entity.enable");
		invaliditeraction_entity_ghosted = Taka.getThisPlugin().getConfig().getBoolean("InvalidIteraction.Entity.ghosted");
		invaliditeraction_entity_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("InvalidIteraction.Entity.threshold")
				.getValues(true);

		scaffoldwalk_basic_enable = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Basic.enable");
		scaffoldwalk_basic_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Basic.ghosted");
		scaffoldwalk_basic_cancelMove = Taka.getThisPlugin().getConfig().getString("ScaffoldWalk.Basic.cancelMove");
		scaffoldwalk_basic_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("ScaffoldWalk.Basic.threshold").getValues(true);

		scaffoldwalk_advanced_enable = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Advanced.enable");
		scaffoldwalk_advanced_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Advanced.ghosted");
		scaffoldwalk_advanced_cancelMove = Taka.getThisPlugin().getConfig()
				.getString("ScaffoldWalk.Advanced.cancelMove");
		scaffoldwalk_advanced_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("ScaffoldWalk.Advanced.threshold").getValues(true);

		scaffoldwalk_ground_enable = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Ground.enable");
		scaffoldwalk_ground_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Ground.ghosted");
		scaffoldwalk_ground_cancelMove = Taka.getThisPlugin().getConfig().getString("ScaffoldWalk.Ground.cancelMove");
		scaffoldwalk_ground_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("ScaffoldWalk.Ground.threshold").getValues(true);

		scaffoldwalk_expand_enable = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Expand.enable");
		scaffoldwalk_expand_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Expand.ghosted");
		scaffoldwalk_expand_cancelMove = Taka.getThisPlugin().getConfig().getString("ScaffoldWalk.Expand.cancelMove");
		scaffoldwalk_expand_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("ScaffoldWalk.Expand.threshold").getValues(true);
		
		scaffoldwalk_timer_enable = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Timer.enable");
		scaffoldwalk_timer_ghosted = Taka.getThisPlugin().getConfig().getBoolean("ScaffoldWalk.Timer.ghosted");
		scaffoldwalk_timer_cancelMove = Taka.getThisPlugin().getConfig().getString("ScaffoldWalk.Timer.cancelMove");
		scaffoldwalk_timer_threshold = Taka.getThisPlugin().getConfig()
				.getConfigurationSection("ScaffoldWalk.Timer.threshold").getValues(true);

		fast_place_enable = Taka.getThisPlugin().getConfig().getBoolean("FastPlace.enable");
		fast_place_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastPlace.ghosted");
		fast_place_cancelMove = Taka.getThisPlugin().getConfig().getString("FastPlace.cancelMove");
		fast_place_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastPlace.threshold")
				.getValues(true);

		nuker_enable = Taka.getThisPlugin().getConfig().getBoolean("Nuker.enable");
		nuker_ghosted = Taka.getThisPlugin().getConfig().getBoolean("Nuker.ghosted");
		nuker_cancelMove = Taka.getThisPlugin().getConfig().getString("Nuker.cancelMove");
		nuker_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("Nuker.threshold").getValues(true);
		
		fast_break_enable = Taka.getThisPlugin().getConfig().getBoolean("FastBreak.enable");
		fast_break_ghosted = Taka.getThisPlugin().getConfig().getBoolean("FastBreak.ghosted");
		fast_break_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("FastBreak.threshold").getValues(true);
		
		no_break_delay_enable = Taka.getThisPlugin().getConfig().getBoolean("NoBreakDelay.enable");
		no_break_delay_ghosted = Taka.getThisPlugin().getConfig().getBoolean("NoBreakDelay.ghosted");
		no_break_delay_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("NoBreakDelay.threshold").getValues(true);
		
		invaliditeraction_block_enable = Taka.getThisPlugin().getConfig().getBoolean("InvalidIteraction.Block.enable");
		invaliditeraction_block_ghosted = Taka.getThisPlugin().getConfig().getBoolean("InvalidIteraction.Block.ghosted");
		invaliditeraction_block_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("InvalidIteraction.Block.threshold")
				.getValues(true);
		
		autosign_enable = Taka.getThisPlugin().getConfig().getBoolean("AutoSign.enable");
		autosign_ghosted = Taka.getThisPlugin().getConfig().getBoolean("AutoSign.ghosted");
		autosign_cancelMove = Taka.getThisPlugin().getConfig().getString("AutoSign.cancelMove");
		autosign_threshold = Taka.getThisPlugin().getConfig().getConfigurationSection("AutoSign.threshold")
				.getValues(true);

		chat_captcha_enable = Taka.getThisPlugin().getConfig().getBoolean("Chat.Captcha.enable");
		chat_captcha_attempts = Taka.getThisPlugin().getConfig().getInt("Chat.Captcha.attempts");
		chat_captcha_allowed_commands = Taka.getThisPlugin().getConfig().getStringList("Chat.Captcha.allowed-commands");
		
		chat_badwords_enable = Taka.getThisPlugin().getConfig().getBoolean("Chat.BadWords.enable");
		chat_badwords_action = Taka.getThisPlugin().getConfig().getString("Chat.BadWords.action");
		chat_badwords_wordslist = Taka.getThisPlugin().getConfig().getStringList("Chat.BadWords.wordslist");
	}
	
	public static void loadMessages() {
		messages_version = messages.getDouble("messages-version");
		anticheat_prefix = messages.getString("anticheat-prefix") + " ";
		anticheat_admin_permission = messages.getString("anticheat-admin-permission");
		anticheat_bypass_permission = messages.getString("anticheat-bypass-permission");
		anticheat_admin_message_form = messages.getString("anticheat-admin-message-form");
		anticheat_admin_staff_mode_message = messages
				.getString("anticheat-admin-staff-mode-message");
		anticheat_admin_debug_mode_message = messages
				.getString("anticheat-admin-verbose-mode-message");
		anticheat_admin_debug_mode_type_message = messages
				.getString("anticheat-admin-verbose-mode-type-message");
		anticheat_no_permission_message = messages.getString("anticheat-no-permission-message");
		anticheat_report_complete_message = messages
				.getString("anticheat-report-complete-message");
		anticheat_report_staff_message = messages.getString("anticheat-report-staff-message");
		anticheat_debug_message = messages.getString("anticheat-verbose-message");
		anticheat_vl_cleaner_message = messages.getString("anticheat-vl-cleaner-message");
		anticheat_treport_gui_title = messages.getString("anticheat-treport-gui-title");
		
		anticheat_world_enable_message = messages.getString("anticheat-world-enable-message");
		anticheat_world_disable_message = messages.getString("anticheat-world-disable-message");
		
		anticheat_teleport_to_hacker_message = messages.getString("anticheat-teleport-to-hacker-message");
		
		anticheat_treport_gui_title = messages.getString("anticheat-treport-gui-title");
		
		autorespawn_kickMsg = messages.getString("autorespawn-kickMsg");
		
		chat_captcha_first_message = messages.getString("chat-captcha-first-message");
		chat_captcha_success_message = messages.getString("chat-captcha-success-message");
		chat_captcha_kick_message = messages.getString("chat-captcha-kick-message");
		chat_captcha_numbers_color_code = messages.getString("chat-captcha-numbers-color-code");
		
		chat_badwords_player_message = messages.getString("chat-badwords-player-message");
	}
	
	public static void loadDisabledWorlds() {
		anticheat_disable_in_world = disabled_worlds.getStringList("anticheat-disable-in-world");
	}
}
