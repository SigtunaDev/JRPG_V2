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

			player.sendMessage("���~�G���������O�榡");

			return false;

		}

		if (!MapAndList.queue1.contains(player)) {

			player.sendMessage("�z�ثe���ݭn���ҫH�c�C");

			return false;

		}

		if (!args[0].contains("@")) {

			player.sendMessage("�H�c�榡�����T�A���ˬd�C");

			return false;

		}

		if (cooldown.contains(player.getName())) {

			player.sendMessage("�H�H�N�o���A�е���60��C");

		}

		send_Email(player, args[0]);

		MapAndList.email.put(player, args[0]);

		player.sendMessage("���ҫH�w�g�H�X�A�ɮĬ�10�����A�Ыe�h���H�A���¡C");
		player.sendMessage("�Y�z����H�F�A�п�J/ev <���ҽX> �����ҫH�c�A����!");

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
			email.subject("JRPGV2 ���ҫH");
			email.body("�z�����ҽX: " + random_code + "\r\n�Цb�Q�������ϥΡA���¡C");
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
