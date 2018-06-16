package jv2.lock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import jv2.main.Main;
import net.md_5.bungee.api.ChatColor;

public class LoginSecure implements Listener {

	static Main plugin = Main.plugin;

	

	@EventHandler
	public void SecureLogin(PlayerJoinEvent e) {

		if (check_PlayerInfo_Exist(e.getPlayer()) == false) {

			MapAndList.unknown.add(e.getPlayer());

			e.getPlayer().sendMessage("歡迎，新勇者!");
			e.getPlayer().sendMessage("請先讓我們做以下步驟來確保你的帳號安全!");

			e.getPlayer().sendMessage("(1/3) 請輸入您的email，格式為/email <email>");
			e.getPlayer().sendMessage(ChatColor.RED + "請不要使用免洗郵箱，往後若帳號被封鎖本伺服器一概不負責。");

			MapAndList.queue1.add(e.getPlayer());

		}

	}

	@EventHandler
	public void CantMove(PlayerMoveEvent e) {

		if (MapAndList.unknown.contains(e.getPlayer()))
			e.setCancelled(true);

	}

	@EventHandler
	public void CantTalk(AsyncPlayerChatEvent e) {

		if (MapAndList.unknown.contains(e.getPlayer()))
			e.setCancelled(true);

	}

	public boolean check_PlayerInfo_Exist(Player player) {

		try {

			PreparedStatement ps = plugin.getDB().getSQLConnection()
					.prepareStatement("select * from `jv2_player` where player = ?");

			ps.setString(1, player.getName());

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				return true;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;

		}

		return false;

	}
	
	public static void register_info(Player player,String email,String password,String date) {
		
		try {
			
			PreparedStatement ps = plugin.getDB().getSQLConnection().prepareStatement("insert into `jv2_player`(player,email,password,date) values (?,?,?,?)");
			
			ps.setString(1, player.getName());
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, date);
			
			ps.execute();
			
			player.sendMessage("信箱：資料登入成功。");
			
		}catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}

}