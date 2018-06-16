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
			
			player.sendMessage("�z�{�b�٤��ݭn�]�m�K�X");
			
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
			
			player.sendMessage("�A���K�X�ܤ֭n�]�t�^��r���C");
			return false;
			
		}
		
		if(allword == false) {
	
			player.sendMessage("�A���K�X���঳���F�^��M�Ʀr���r��");
			return false;
			
		}
		
		if(too_short == true) {
			
			player.sendMessage("�A���K�X�ӵu�F�A�ܤ֭n�h��C�Ӧr");
			return false;
			
		}
		
		player.sendMessage("�K�X���U���\�C");
		player.sendMessage("����A�i�H�Ϋ��O���K�X�A�Ա��Ьd�\���O��C");
		player.sendMessage("");
		
		
		MapAndList.password.put(player, password);
		
		MapAndList.queue2.remove(player);
		MapAndList.queue3.add(player);
		
		step3(player);
		
		return false;
	}
	
	public void step3(Player player) {
		
		player.sendMessage("(3/3)�г]�w�ͤ�A�榡��MMDD�AM�����D������A�Ҧp0625��6��25��");
		player.sendMessage("���O�榡: /bs <MMDD>");
		
	}

}
