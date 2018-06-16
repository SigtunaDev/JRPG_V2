package jv2.lock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class MapAndList {
	
	public static List<Player> queue1 = new ArrayList<Player>();
	public static List<Player> queue2 = new ArrayList<Player>();
	public static List<Player> queue3 = new ArrayList<Player>();
	public static List<Player> unknown = new ArrayList<>();
	
	public static Map<Player, String> veri = new HashMap<Player, String>();
	public static Map<Player, String> email = new HashMap<Player, String>();
	public static Map<Player, String> password = new HashMap<Player, String>();
	public static Map<Player, String> birthday = new HashMap<Player, String>();
	
}
