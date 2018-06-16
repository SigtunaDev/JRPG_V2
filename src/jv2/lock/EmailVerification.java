package jv2.lock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import jv2.lock.SMTP.SMTPException;
import jv2.main.Main;

public class EmailVerification implements CommandExecutor {


	List<String> cooldown = new ArrayList<String>();

	Main plugin = Main.plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;

		final Player player = (Player) sender;

		if (args.length == 0) {

			player.sendMessage("錯誤：未知的指令格式");

			return false;

		}

		if (!MapAndList.queue1.contains(player)) {

			player.sendMessage("您目前不需要驗證信箱。");

			return false;

		}

		if (!args[0].contains("@")) {

			player.sendMessage("信箱格式不正確，請檢查。");

			return false;

		}

		if (cooldown.contains(player.getName())) {

			player.sendMessage("寄信冷卻中，請等待60秒。");

		}

		send_Email(player, args[0]);

		MapAndList.email.put(player, args[0]);

		player.sendMessage("驗證信已經寄出，時效為10分鐘，請前去收信，謝謝。");
		player.sendMessage("若您收到信了，請輸入/ev <驗證碼> 來驗證信箱，謝謝!");

		cooldown.add(player.getName());

		new BukkitRunnable() {

			@Override
			public void run() {

				cooldown.remove(player.getName());

			}

		}.runTaskLater(plugin, 20 * 60);

		new BukkitRunnable() {

			@Override
			public void run() {

				MapAndList.veri.remove(player);

			}

		}.runTaskLater(plugin, 20 * 600);

		return false;
	}

	public void send_Email(Player player, String Email) {

		String random_code = random(9);
		MapAndList.veri.put(player, random_code);

		try {

			SMTP.Email email = SMTP.createEmptyEmail();
			email.add("Content-Type", "text/html");
			email.from("jrpgv2@gmail.com");
			email.to(Email);
			email.subject("JRPGV2 驗證信");
			email.body("您的驗證碼: " + random_code + "\r\n請在十分鐘內使用，謝謝。");
			SMTP.sendEmail("Smtp.gmail.com", "jrpgv2@gmail.com", "han910625", email, false);

		} catch (SMTPException e) {

			e.printStackTrace();

		}

	}

	public String random(int len) {

		String random = "";

		for (int i = 0; i < len; i++) {

			int rand = (int) (Math.random() * 10000 + 1);
			int prand = rand % 62;

			if (prand < 10) {

				random += (char) (prand + 48);

			} else if (prand < 36) {

				random += (char) (prand + 55);

			} else if (prand < 62) {

				random += (char) (prand + 61);

			}

		}

		return random;

	}

}
