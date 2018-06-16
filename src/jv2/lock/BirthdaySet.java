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
			
			player.sendMessage("您目前還不需要設定生日");
			
		}

		int[] arr = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		String birthday = args[0];

		try {

			int month = Integer.parseInt(birthday.substring(0, 1));
			int day = Integer.parseInt(birthday.substring(2, 3));

			if (day > 0 && arr[month] < day) {

				player.sendMessage("錯誤，月份所搭配的最大日期錯誤，例如6月只有30天。");

				return false;

			}

			if (month > 12 && month <= 0) {

				player.sendMessage("錯誤，一年只有12個月，並不會有小於等於0或者大於13的數字出現。");

				return false;

			}
			
			player.sendMessage("日期設定成功，謝謝你有耐心的將這些資料設定完。");
			player.sendMessage("未來若忘記密碼請使用這些資訊來找回，系統設計者6ya感謝您。");
			
			MapAndList.birthday.put(player, birthday);
			
			complete(player);

		} catch (NumberFormatException e) {

			player.sendMessage("請輸入數字。");

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
