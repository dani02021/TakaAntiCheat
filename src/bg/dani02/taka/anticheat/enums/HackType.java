package bg.dani02.taka.anticheat.enums;

public enum HackType {
	DEFAULT, // Used for freezing reason
	MOVING_FLY_STABLE_Y, MOVING_FLY_INVALID_Y, MOVING_FLY_DOUBLE_JUMP_UP, MOVING_FLY_DOUBLE_JUMP_DOWN,
	MOVING_FLY_MODULO, MOVING_FLY_SLOW_Y_NON_INSTANT,MOVING_FLY_SLOW_Y_INSTANT,
	MOVING_FASTLADDER_INSTANT, MOVING_FASTLADDER_NON_INSTANT,
	MOVING_INVALIDFALL_STABLE_DISTANCE, MOVING_INVALIDFALL_SLOWER_DISTANCE,
	MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT, MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT,
	MOVING_INVALIDFALL_FAST_START_DISTANCE,MOVING_INVALIDFALL_SLOW_Y,
	MOVING_SPEED_ONGROUND, MOVING_SPEED_ONGROUND_SLIME, MOVING_SPEED_ONGROUND_ICE, MOVING_SPEED_ONGROUND_SOULSAND,
	MOVING_SPEED_AIR, MOVING_SPEED_COBWEB, MOVING_SPEED_ITEM,
	MOVING_SPEED_LIQUID_WATER, MOVING_SPEED_LIQUID_LAVA,
	MOVING_SPEED_SPRINT_HUNGRY,
	MOVING_SPEED_SOULSAND, MOVING_SPEED_ICE, MOVING_SPEED_SLIME, MOVING_SPEED_VEHICLE,
	MOVING_ANTI_LEVITATION,
	MOVING_SLIME_JUMP,
	MOVING_NO_FALL,
	MOVING_IMPOSSIBLE_JUMP,
	MOVING_STEP,
	MOVING_HIGHJUMP,
	MOVING_SCAFFOLDWALK_BASIC, MOVING_SCAFFOLDWALK_ADVANCED, MOVING_SCAFFOLDWALK_GROUND, MOVING_SCAFFOLDWALK_EXPAND, MOVING_SCAFFOLDWALK_TIMER,
	MOVING_SNEAK,
	MOVING_JESUS,
	MOVING_NO_PITCH,
	MOVING_GROUND_ELYTRA,
	MOVING_TIMER,
	MOVING_LOW_JUMP,
	MOVING_BLINK,
	MOVING_STRAFE,

	CHAT_CAPTCHA, CHAT_BADWORDS,

	INVENTORY_FAST_INVENTORY, INVENTORY_MOVE, INVENTORY_FAST_EAT, INVENTORY_FAST_CLICK, INVENTORY_THROW,

	WORLD_WRONG_BLOCK_DIRECTION, WORLD_LIQUID_INTERACTION, WORLD_CREATIVE_DROP, WORLD_NO_SWING, WORLD_FAST_PLACE,
	WORLD_CREATIVE_NUKER, WORLD_FAST_BREAK, WORLD_NO_BREAK_DELAY, WORLD_INVALID_INTERACTION_BLOCK, WORLD_AUTO_SIGN,

	COMBAT_CRITICALS, COMBAT_REACH, COMBAT_AUTOSOUP, COMBAT_FASTBOW, COMBAT_INVALID_INTERACTION_ENTITY,

	RENDER_HEALTHTAG, RENDER_FREECAM,

	EXPLOIT_PLUGIN_LIST, EXPLOIT_WORLD_DOWNLOADER, EXPLOIT_UUID_SPOOF, EXPLOIT_AUTORESPAWN;

	public static String getName(HackType h) {
		switch (h) {
		case MOVING_FLY_STABLE_Y:
			return "Fly(stable y axis)";
		case MOVING_FLY_INVALID_Y:
			return "Fly(invalid y axis(Instant))";
		case MOVING_FLY_DOUBLE_JUMP_UP:
			return "Fly(double jump up)";
		case MOVING_FLY_DOUBLE_JUMP_DOWN:
			return "Fly(double jump down)";
		case MOVING_FLY_MODULO:
			return "Fly(modulo)";
		case MOVING_FLY_SLOW_Y_INSTANT:
			return "Fly(slow y axis(Instant))";
		case MOVING_FLY_SLOW_Y_NON_INSTANT:
			return "Fly(slow y axis(Non Instant))";
		case MOVING_FASTLADDER_INSTANT:
			return "FastLadder(Instant)";
		case MOVING_FASTLADDER_NON_INSTANT:
			return "FastLadder(Non Instant)";
		case MOVING_INVALIDFALL_STABLE_DISTANCE:
			return "InvalidFall(Stable distance)";
		case MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT:
			return "InvalidFall(Faster distance(Instant))";
		case MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT:
			return "InvalidFall(Faster distance(Non Instant))";
		case MOVING_INVALIDFALL_SLOWER_DISTANCE:
			return "InvalidFall(Slower distance)";
		case MOVING_INVALIDFALL_FAST_START_DISTANCE:
			return "InvalidFall(Fast Start Distance)";
		case MOVING_INVALIDFALL_SLOW_Y:
			return "InvalidFall(Slow Y)";
		case MOVING_SPEED_ONGROUND:
			return "Speed(OnGround(Normal))";
		case MOVING_SPEED_ONGROUND_SLIME:
			return "Speed(OnGround(Slime))";
		case MOVING_SPEED_ONGROUND_ICE:
			return "Speed(OnGround(Ice))";
		case MOVING_SPEED_ONGROUND_SOULSAND:
			return "Speed(OnGround(Soulsand))";
		case MOVING_SPEED_AIR:
			return "Speed(Air)";
		case MOVING_SPEED_COBWEB:
			return "Speed(Cobweb)";
		case MOVING_SPEED_ITEM:
			return "Speed(Item)";
		case MOVING_SPEED_LIQUID_WATER:
			return "Speed(Liquid(Water))";
		case MOVING_SPEED_LIQUID_LAVA:
			return "Speed(Liquid(Lava))";
		case MOVING_SPEED_SPRINT_HUNGRY:
			return "Speed(Sprint Hungry)";
		case MOVING_SPEED_ICE:
			return "Speed(Ice)";
		case MOVING_SPEED_SOULSAND:
			return "Speed(Soulsand)";
		case MOVING_SPEED_SLIME:
			return "Speed(Slime)";
		case MOVING_SPEED_VEHICLE:
			return "Speed(Vehicle)";
		case MOVING_SLIME_JUMP:
			return "SlimeJump";
		case MOVING_ANTI_LEVITATION:
			return "AntiLevitation";
		case MOVING_NO_FALL:
			return "NoFall";
		case MOVING_IMPOSSIBLE_JUMP:
			return "ImpossibleJump";
		case MOVING_STEP:
			return "Step";
		case MOVING_HIGHJUMP:
			return "HighJump";
		case MOVING_SCAFFOLDWALK_BASIC:
			return "ScaffoldWalk(Basic)";
		case MOVING_SCAFFOLDWALK_ADVANCED:
			return "ScaffoldWalk(Advanced)";
		case MOVING_SCAFFOLDWALK_GROUND:
			return "ScaffoldWalk(Ground)";
		case MOVING_SCAFFOLDWALK_EXPAND:
			return "ScaffoldWalk(Expand)";
		case MOVING_SCAFFOLDWALK_TIMER:
			return "ScaffoldWalk(Timer)";
		case MOVING_SNEAK:
			return "Sneak";
		case MOVING_JESUS:
			return "Jesus";
		case MOVING_NO_PITCH:
			return "NoPitch";
		case MOVING_GROUND_ELYTRA:
			return "GroundElytra";
		case MOVING_TIMER:
			return "Timer";
		case MOVING_LOW_JUMP:
			return "LowJump";
		case MOVING_BLINK:
			return "Blink";
		case MOVING_STRAFE:
			return "Strafe";
		case INVENTORY_FAST_INVENTORY:
			return "FastInventory";
		case INVENTORY_MOVE:
			return "InvMove";
		case INVENTORY_FAST_EAT:
			return "FastEat";
		case INVENTORY_FAST_CLICK:
			return "FastClick";
		case INVENTORY_THROW:
			return "Throw";
		case WORLD_WRONG_BLOCK_DIRECTION:
			return "WrongBlock(Direction)";
		case WORLD_LIQUID_INTERACTION:
			return "LiquidInteraction";
		case WORLD_CREATIVE_DROP:
			return "CreativeDrop";
		case WORLD_NO_SWING:
			return "NoSwing";
		case WORLD_FAST_PLACE:
			return "FastPlace";
		case WORLD_CREATIVE_NUKER:
			return "CreativeNuker";
		case WORLD_FAST_BREAK:
			return "FastBreak";
		case WORLD_NO_BREAK_DELAY:
			return "NoBreakDelay";
		case WORLD_INVALID_INTERACTION_BLOCK:
			return "InvalidInteraction(Block)";
		case WORLD_AUTO_SIGN:
			return "AutoSign";
		case COMBAT_CRITICALS:
			return "Criticals";
		case COMBAT_REACH:
			return "Reach(Combat)";
		case COMBAT_FASTBOW:
			return "FastBow";
		case COMBAT_INVALID_INTERACTION_ENTITY:
			return "InvalidInteraction(Entity)";
		case COMBAT_AUTOSOUP:
			return "AutoSoup";
		case RENDER_HEALTHTAG:
			return "HealthTag";
		case RENDER_FREECAM:
			return "Freecam";
		case EXPLOIT_PLUGIN_LIST:
			return "PluginList";
		case EXPLOIT_WORLD_DOWNLOADER:
			return "WorldDownloader";
		case EXPLOIT_UUID_SPOOF:
			return "UUIDSpoof";
		case EXPLOIT_AUTORESPAWN:
			return "AutoRespawn";
		case CHAT_CAPTCHA:
			return "Chat(Captcha)";
		case CHAT_BADWORDS:
			return "Chat(Badwords)";

		default:
			return null;
		}
	}

	public static HackType getByName(String h) {
		switch (h) {
		case "Fly(stable y axis)":
			return MOVING_FLY_STABLE_Y;
		case "Fly(invalid y axis(Instant))":
			return MOVING_FLY_INVALID_Y;
		case "Fly(double jump up)":
			return MOVING_FLY_DOUBLE_JUMP_UP;
		case "Fly(double jump down)":
			return MOVING_FLY_DOUBLE_JUMP_DOWN;
		case "Fly(modulo)":
			return MOVING_FLY_MODULO;
		case "Fly(slow y axis(Instant))":
			return MOVING_FLY_SLOW_Y_INSTANT;
		case "Fly(slow y axis(Non Instant))":
			return MOVING_FLY_SLOW_Y_NON_INSTANT;
		case "FastLadder(Instant)":
			return MOVING_FASTLADDER_INSTANT;
		case "FastLadder(Non Instant)":
			return MOVING_FASTLADDER_NON_INSTANT;
		case "InvalidFall(Stable distance)":
			return MOVING_INVALIDFALL_STABLE_DISTANCE;
		case "InvalidFall(Faster distance(Instant))":
			return MOVING_INVALIDFALL_FASTER_DISTANCE_INSTANT;
		case "InvalidFall(Faster distance(Non Instant))":
			return MOVING_INVALIDFALL_FASTER_DISTANCE_NON_INSTANT;
		case "InvalidFall(Slower distance)":
			return MOVING_INVALIDFALL_SLOWER_DISTANCE;
		case "InvalidFall(Fast Start Distance)":
			return MOVING_INVALIDFALL_FAST_START_DISTANCE;
		case "InvalidFall(Slow Y)":
			return MOVING_INVALIDFALL_SLOW_Y;
		case "Speed(OnGround(Normal))":
			return MOVING_SPEED_ONGROUND;
		case "Speed(OnGround(Slime))":
			return MOVING_SPEED_ONGROUND_SLIME;
		case "Speed(OnGround(Ice))":
			return MOVING_SPEED_ONGROUND_ICE;
		case "Speed(OnGround(Soulsand))":
			return MOVING_SPEED_ONGROUND_SOULSAND;
		case "Speed(Air)":
			return MOVING_SPEED_AIR;
		case "Speed(Cobweb)":
			return MOVING_SPEED_COBWEB;
		case "Speed(Item)":
			return MOVING_SPEED_ITEM;
		case "Speed(Liquid(Water))":
			return MOVING_SPEED_LIQUID_WATER;
		case "Speed(Liquid(Lava))":
			return MOVING_SPEED_LIQUID_LAVA;
		case "Speed(Sprint Hungry)":
			return MOVING_SPEED_SPRINT_HUNGRY;
		case "Speed(Ice)":
			return MOVING_SPEED_ICE;
		case "Speed(Soulsand)":
			return MOVING_SPEED_SOULSAND;
		case "Speed(Slime)":
			return MOVING_SPEED_SLIME;
		case "Speed(Vehicle)":
			return MOVING_SPEED_VEHICLE;
		case "SlimeJump":
			return MOVING_SLIME_JUMP;
		case "AntiLevitation":
			return MOVING_ANTI_LEVITATION;
		case "NoFall":
			return MOVING_NO_FALL;
		case "ImpossibleJump":
			return MOVING_IMPOSSIBLE_JUMP;
		case "Step":
			return MOVING_STEP;
		case "HighJump":
			return MOVING_HIGHJUMP;
		case "ScaffoldWalk(Basic)":
			return MOVING_SCAFFOLDWALK_BASIC;
		case "ScaffoldWalk(Advanced)":
			return MOVING_SCAFFOLDWALK_ADVANCED;
		case "ScaffoldWalk(Ground)":
			return MOVING_SCAFFOLDWALK_GROUND;
		case "ScaffoldWalk(Expand)":
			return MOVING_SCAFFOLDWALK_EXPAND;
		case "ScaffoldWalk(Timer)":
			return MOVING_SCAFFOLDWALK_TIMER;
		case "Sneak":
			return MOVING_SNEAK;
		case "Jesus":
			return MOVING_JESUS;
		case "NoPitch":
			return MOVING_NO_PITCH;
		case "GroundElytra":
			return MOVING_GROUND_ELYTRA;
		case "Timer":
			return MOVING_TIMER;
		case "LowJump":
			return MOVING_LOW_JUMP;
		case "Blink":
			return MOVING_BLINK;
		case "Strafe":
			return MOVING_STRAFE;
		case "FastInventory":
			return INVENTORY_FAST_INVENTORY;
		case "InvMove":
			return INVENTORY_MOVE;
		case "FastEat":
			return INVENTORY_FAST_EAT;
		case "FastClick":
			return INVENTORY_FAST_CLICK;
		case "Throw":
			return INVENTORY_THROW;
		case "WrongBlock(Direction)":
			return WORLD_WRONG_BLOCK_DIRECTION;
		case "LiquidInteraction":
			return WORLD_LIQUID_INTERACTION;
		case "CreativeDrop":
			return WORLD_CREATIVE_DROP;
		case "NoSwing":
			return WORLD_NO_SWING;
		case "FastPlace":
			return WORLD_FAST_PLACE;
		case "CreativeNuker":
			return WORLD_CREATIVE_NUKER;
		case "FastBreak":
			return WORLD_FAST_BREAK;
		case "NoBreakDelay":
			return WORLD_NO_BREAK_DELAY;
		case "InvalidInteraction(Block)":
			return WORLD_INVALID_INTERACTION_BLOCK;
		case "AutoSign":
			return WORLD_AUTO_SIGN;
		case "Criticals":
			return COMBAT_CRITICALS;
		case "Reach(Combat)":
			return COMBAT_REACH;
		case "FastBow":
			return COMBAT_FASTBOW;
		case "InvalidInteraction(Entity)":
			return COMBAT_INVALID_INTERACTION_ENTITY;
		case "AutoSoup":
			return COMBAT_AUTOSOUP;
		case "HealthTag":
			return RENDER_HEALTHTAG;
		case "Freecam":
			return RENDER_FREECAM;
		case "PluginList":
			return EXPLOIT_PLUGIN_LIST;
		case "WorldDownloader":
			return EXPLOIT_WORLD_DOWNLOADER;
		case "UUIDSpoof":
			return EXPLOIT_UUID_SPOOF;
		case "AutoRespawn":
			return EXPLOIT_AUTORESPAWN;
		case "Chat(Captcha)":
			return CHAT_CAPTCHA;
		case "Chat(Badwords)":
			return CHAT_BADWORDS;

		default:
			return null;
		}
	}

	public static boolean isSpecialCheck(HackType type) {
		if (!type.name().startsWith("CHAT"))
			return true;

		return false;
	}

	public static String removeCheckType(HackType h) {
		return h.name().replaceAll("CHAT_|INVENTORY_|RENDER_|EXPLOIT_|MOVING_|WORLD_|COMBAT_", "");
	}
	
	public static boolean isCheckDisabled(HackType h)
	{
		return false;
	}
	
	// Some checks are better off not showing notifications, because of spam, or because they are not needed
	public static boolean isCheckGhosted(HackType h)
	{
		return h == WORLD_FAST_PLACE || h == MOVING_BLINK;
	}

}
