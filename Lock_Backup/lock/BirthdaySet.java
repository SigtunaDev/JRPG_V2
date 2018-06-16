package jv2.lock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BirthdaySet implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {

			return false;

		}
		
		Player player = (Player) sender;

		if (!MapAndList.queue3.contains(player)) {
			
			player.sendMessage("�z�ثe�٤��ݭn�]�w�ͤ�");
			
		}

		int[] arr = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		String birthday = args[0];

		try {

			int month = Integer.parseInt(birthday.substring(0, 1));
			int day = Integer.parseInt(birthday.substring(2, 3));

			if (day > 0 && arr[month] < day) {

				player.sendMessage("���~�A����ҷf�t���̤j������~�A�Ҧp6��u��30�ѡC");

				return false;

			}

			if (month > 12 && month <= 0) {

				player.sendMessage("���~�A�@�~�u��12�Ӥ�A�ä��|���p�󵥩�0�Ϊ̤j��13���Ʀr�X�{�C");

				return false;

			}
			
			player.sendMessage("����]�w���\�A���§A���@�ߪ��N�o�Ǹ�Ƴ]�w���C");
			player.sendMessage("���ӭY�ѰO�K�X�Шϥγo�Ǹ�T�ӧ�^�A�t�γ]�p��6ya�P�±z�C");
			
			MapAndList.birthday.put(player, birthday);
			
			complete(player);

		} catch (NumberFormatException e) {

			player.sendMessage("�п�J�Ʀr�C");

		}

		return false;
	}
	
	public void complete(Player player) {
		
		String email = MapAndList.email.get(player);
		String password = MapAndList.password.get(player);
		String date = MapAndList.birthday.get(player);
		
		LoginSecure.register_info(player, email, password, date);
		
		MapAndList.queue3.remove(player);
		MapAndList.unknown.remove(player);
		
	}

}
