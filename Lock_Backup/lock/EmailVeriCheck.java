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
			
			player.sendMessage("�z�ثe���ݭn���ҫH�c�C");
			
			return false;
			
		}
		
		if(args[0].equals(veri.get(player))) {
			
			player.sendMessage("���Ҧ��\�C");
			
			MapAndList.queue1.remove(player);
			
			step2(player);
			
		}else {
			
			player.sendMessage("���ҽX���~�C");
			
		}
		
		return false;
	}
	
	public void step2(Player player) {
		
		player.sendMessage("(2/3)�г]�w�K�X�A�K�X���ץ����h��7�Ӧr�A�B�K�X����u���Ʀr�A�����t���^��");
		player.sendMessage("���O�榡��/ps <�K�X>");
		
		MapAndList.queue2.add(player);
		
	}

}
