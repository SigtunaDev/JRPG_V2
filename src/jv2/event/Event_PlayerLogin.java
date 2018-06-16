package jv2.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import jv2.classes.Buffer_PlayerInfo;
import jv2.classes.PlayerInfo;
import jv2.gui.GUI_Lock;
import jv2.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class Event_PlayerLogin implements Listener {

	Main plugin = Main.plugin;

	public static List<Player> anno = new ArrayList<Player>();
	public static List<Player> change_name = new ArrayList<Player>();
	public static List<Player> logining = new ArrayList<Player>();

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerLogin(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		Event_LockGUI.logged.put(player, false);

		if (registered(player) == false) {

			anno.add(player);

			// ���U�w�Ҹ��
			Buffer_PlayerInfo.info.put(player, new PlayerInfo());
			

			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 80));

			player.sendMessage("�w��r�s�i�̡A���n�`�ȶ·t�A�ڬO�}�o��6ya�����Ѥp��");
			player.sendMessage("�i�J�C���e�A���i�D�ڧA�s����W�l�a!\n");

			TextComponent tc = new TextComponent(ChatColor.GRAY + "�I���ڨӿ�J�W��!");
			tc.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/regname <�п�J�W��>"));

			player.spigot().sendMessage(tc);

		} else if (registered(player) == true) {

			GUI_Lock gui = new GUI_Lock(player);

			new BukkitRunnable() {

				public void run() {

					player.openInventory(gui.getInventory());

				}

			}.runTaskLater(plugin, 20);

			logining.add(player);

		}

	}

	@EventHandler
	public void MoveDenied(PlayerMoveEvent e) {

		if (anno.contains(e.getPlayer()))
			e.setCancelled(true);

	}

	@EventHandler
	public void TalkDenied(AsyncPlayerChatEvent e) {

		if (anno.contains(e.getPlayer()))
			e.setCancelled(true);

	}

	@EventHandler
	public void DamageDenied(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player) {

			Player player = (Player) e.getEntity();

			if (anno.contains(player))
				e.setCancelled(true);

			if (logining.contains(player))
				e.setCancelled(true);

		}

	}

	public boolean registered(Player player) {

		Connection connection = plugin.getDB().getSQLConnection();

		boolean find = false;

		try {

			// �B����O�G�d�����O�_�����a���
			PreparedStatement state = connection.prepareStatement("select * from `jv2_player` where player = ?");

			// �N�Ĥ@�Ӱݸ�(�Ѽ�)�M�J���a�W��
			state.setString(1, player.getName());

			// �B����O�A�`�����G
			ResultSet result = state.executeQuery();

			// �p�G���G����ơA�w�qboolean��find��true
			if (result.next() == true)
				find = true;

			result.close();

			state.close();

			return find;

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return find;

	}

}
