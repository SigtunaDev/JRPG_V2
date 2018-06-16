package jv2.main;

import jv2.reg.command.Command_reglock;
import jv2.reg.command.Command_regname;

public class Register_Command {

	static Main plugin = Main.plugin;
	
	public static void Register() {

		// EMAIL SECURE CODE
		/*
		plugin.getServer().getPluginManager().registerEvents(new LoginSecure(), this);
		plugin.getCommand("email").setExecutor(new EmailVerification());
		plugin.getCommand("ev").setExecutor(new EmailVeriCheck());
		plugin.getCommand("ps").setExecutor(new PasswordSet());
		plugin.getCommand("bs").setExecutor(new BirthdaySet());
		*/
		
		plugin.getCommand("regname").setExecutor(new Command_regname());
		plugin.getCommand("reglock").setExecutor(new Command_reglock());

	}

}
