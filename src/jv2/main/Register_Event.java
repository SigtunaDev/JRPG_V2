package jv2.main;

import jv2.event.Event_LockGUI;
import jv2.event.Event_PlayerLogin;

public class Register_Event {
	
	static Main plugin = Main.plugin;
	
	public static void Register() {
		
		plugin.getServer().getPluginManager().registerEvents(new Event_PlayerLogin() , plugin);
		plugin.getServer().getPluginManager().registerEvents(new Event_LockGUI(), plugin);
		
	}

}
