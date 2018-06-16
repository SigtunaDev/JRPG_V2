package jv2.lock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PasswordSet implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		if(!MapAndList.queue2.contains(player)) {
			
			player.sendMessage("您現在還不需要設置密碼");
			
		}
		
		String password = args[0];
		
		boolean allnum = true;
		
		for(int i = 0; i < password.length(); i++) {
			
			Pattern pattern = Pattern.compile("[0-9]");
			
			Matcher match = pattern.matcher(String.valueOf(password.charAt(i)));
			
			if(match.matches() == false) {
				
				allnum = false;
			
				break;
				
			}
			
		}
		
		boolean allword = true;
		
		for(int i = 0; i < password.length(); i++) {
			
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
			
			Matcher match = pattern.matcher(String.valueOf(password.charAt(i)));
			
			if(match.matches() == false) {
				
				allword = false;
			
				break;
				
			}
			
		}
		
		boolean too_short = (password.length() <= 7);
		
		if(allnum == true) {
			
			player.sendMessage("你的密碼至少要包含英文字母。");
			return false;
			
		}
		
		if(allword == false) {
	
			player.sendMessage("你的密碼不能有除了英文和數字的字元");
			return false;
			
		}
		
		if(too_short == true) {
			
			player.sendMessage("你的密碼太短了，至少要多於七個字");
			return false;
			
		}
		
		player.sendMessage("密碼註冊成功。");
		player.sendMessage("往後你可以用指令更改密碼，詳情請查閱指令表。");
		player.sendMessage("");
		
		
		MapAndList.password.put(player, password);
		
		MapAndList.queue2.remove(player);
		MapAndList.queue3.add(player);
		
		step3(player);
		
		return false;
	}
	
	public void step3(Player player) {
		
		player.sendMessage("(3/3)請設定生日，格式為MMDD，M為月份D為日期，例如0625為6月25號");
		player.sendMessage("指令格式: /bs <MMDD>");
		
	}

}
