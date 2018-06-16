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

			e.getPlayer().sendMessage("�w��A�s�i��!");
			e.getPlayer().sendMessage("�Х����ڭ̰��H�U�B�J�ӽT�O�A���b���w��!");

			e.getPlayer().sendMessage("(1/3) �п�J�z��email�A�榡��/email <email>");
			e.getPlayer().sendMessage(ChatColor.RED + "�Ф��n�ϥΧK�~�l�c�A����Y�b���Q���ꥻ���A���@�����t�d�C");

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
			
			player.sendMessage("�H�c�G��Ƶn�J���\�C");
			
		}catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}

}