package cn.nukkit.network.protocol.types;

import java.util.UUID;

public class CommandOriginData {
	
	public static int ORIGIN_PLAYER = 0;
	public static int ORIGIN_BLOCK = 1;
	public static int ORIGIN_MINECART_BLOCK = 2;
	public static int ORIGIN_DEV_CONSOLE = 3;
	public static int ORIGIN_TEST = 4;
	public static int ORIGIN_AUTOMATION_PLAYER = 5;
	public static int ORIGIN_CLIENT_AUTOMATION = 6;
	public static int ORIGIN_DEDICATED_SERVER = 7;
	public static int ORIGIN_ENTITY = 8;
	public static int ORIGIN_VIRTUAL = 9;
	public static int ORIGIN_GAME_ARGUMENT = 10;
	public static int ORIGIN_ENTITY_PLAYER = 11;
	
	public long type;
	
	public UUID uuid;
	
	public String requestId;
	
	public long varLong1;

}
