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
			
			player.sendMessage("你的名子太長囉，名字不能低於十個字，換個名子吧!");
			return false;
			
		}
		
		register_buffer(player,name);
		
		send_title(player,String.format(String.format("你好! %s",name)),ChatColor.AQUA);
		
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
		
		//更新資料
		Buffer_PlayerInfo.info.put(player, info);
		
		player.sendMessage("\n暱稱註冊完成!");
		
	}
	
	public void Lock_Register_Notice(Player player) {
		
		String name = Buffer_PlayerInfo.info.get(player).name;
		
		player.sendMessage(String.format("\n嗨，%s，接下來，為了保障您的帳號安全，請註冊密碼鎖!",name));
		player.sendMessage("密碼鎖很簡單，是由9格顏色所組成，一個可以換三種顏色\n");
		
		TextComponent tc = new TextComponent(ChatColor.GRAY + "點擊我來設定密碼鎖!");
		
		tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/reglock"));
		
		player.spigot().sendMessage(tc);
		
	}

}