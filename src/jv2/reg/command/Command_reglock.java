package jv2.reg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import jv2.gui.GUI_Lock;

public class Command_reglock implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args	) {
		
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		GUI_Lock gui = new GUI_Lock(player);
		
		Inventory inv = gui.getInventory();
		
		player.openInventory(inv);
		
		return false;
		
	}

}
