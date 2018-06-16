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

		final Player player = e.getPlayer();

		Event_LockGUI.logged.put(player, false);

		if (registered(player) == false) {

			anno.add(player);

			// 註冊預模資料
			Buffer_PlayerInfo.info.put(player, new PlayerInfo());
			

			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 80));

			player.sendMessage("歡迎呀新勇者，不要害怕黑暗，我是開發者6ya的秘書小希");
			player.sendMessage("進入遊戲前，先告訴我你叫什麼名子吧!\n");

			TextComponent tc = new TextComponent(ChatColor.GRAY + "點擊我來輸入名稱!");
			tc.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/regname <請輸入名稱>"));

			player.spigot().sendMessage(tc);

		} else if (registered(player) == true) {

			final GUI_Lock gui = new GUI_Lock(player);

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

			// 運行指令：查詢欄位是否有玩家資料
			PreparedStatement state = connection.prepareStatement("select * from `jv2_player` where player = ?");

			// 將第一個問號(參數)套入玩家名稱
			state.setString(1, player.getName());

			// 運行指令，蒐集結果
			ResultSet result = state.executeQuery();

			// 如果結果有資料，定義boolean值find為true
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