package jv2.reg.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jv2.classes.Buffer_PlayerInfo;
import jv2.classes.PlayerInfo;
import jv2.classes.Title;
import jv2.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class Command_regname implements CommandExecutor{

	Main plugin = Main.plugin;
	
	public static List<Player> stack = new ArrayList<Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		String name = args[0];
		
		if(name.length() > 10) {
			
			player.sendMessage("�A���W�l�Ӫ��o�A�W�r����C��Q�Ӧr�A���ӦW�l�a!");
			return false;
			
		}
		
		register_buffer(player,name);
		
		send_title(player,String.format(String.format("�A�n! %s",name)),ChatColor.AQUA);
		
		Lock_Register_Notice(player);
		
		return false;
		
	}
	
	public void send_title(Player player,String msg,ChatColor color) {
		
		Title title = new Title();
		
		title.setTitle(msg);
		title.setTitleColor(color);
		
	}
	
	public void register_buffer(Player player,String name) {
		
		PlayerInfo info = Buffer_PlayerInfo.info.get(player);
		
		info.name = name;
		
		//��s���
		Buffer_PlayerInfo.info.put(player, info);
		
		player.sendMessage("\n�ʺٵ��U����!");
		
	}
	
	public void Lock_Register_Notice(Player player) {
		
		String name = Buffer_PlayerInfo.info.get(player).name;
		
		player.sendMessage(String.format("\n�١A%s�A���U�ӡA���F�O�ٱz���b���w���A�е��U�K�X��!",name));
		player.sendMessage("�K�X���²��A�O��9���C��Ҳզ��A�@�ӥi�H���T���C��\n");
		
		TextComponent tc = new TextComponent(ChatColor.GRAY + "�I���ڨӳ]�w�K�X��!");
		
		tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/reglock"));
		
		player.spigot().sendMessage(tc);
		
	}

}
