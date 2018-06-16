package jv2.lock;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmailVeriCheck implements CommandExecutor{

	Map<Player, String> veri = MapAndList.veri;
 	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		if(!MapAndList.queue1.contains(player)) {
			
			player.sendMessage("您目前不需要驗證信箱。");
			
			return false;
			
		}
		
		if(args[0].equals(veri.get(player))) {
			
			player.sendMessage("驗證成功。");
			
			MapAndList.queue1.remove(player);
			
			step2(player);
			
		}else {
			
			player.sendMessage("驗證碼錯誤。");
			
		}
		
		return false;
	}
	
	public void step2(Player player) {
		
		player.sendMessage("(2/3)請設定密碼，密碼長度必須多於7個字，且密碼不能只有數字，必須含有英文");
		player.sendMessage("指令格式為/ps <密碼>");
		
		MapAndList.queue2.add(player);
		
	}

}
