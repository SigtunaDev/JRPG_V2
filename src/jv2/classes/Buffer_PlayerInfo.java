package jv2.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import jv2.event.Event_PlayerLogin;
import jv2.main.Main;

public class Buffer_PlayerInfo {

	static Main plugin = Main.plugin;

	public static Map<Player, PlayerInfo> info = new HashMap<Player, PlayerInfo>();

	public static void register_database(Player player) {

		PlayerInfo inf = info.get(player);
		
		try {

			Connection connection = plugin.getDB().getSQLConnection();

			PreparedStatement state = connection.prepareStatement("insert into `jv2_player`(player,name,password) values (?,?,?)");
			
			state.setString(1, player.getName());
			state.setString(2, inf.name);
			state.setString(3, inf.password);
			
			state.execute();
			
			Event_PlayerLogin.anno.remove(player);
			
			player.removePotionEffect(PotionEffectType.BLINDNESS);

		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		player.kickPlayer("檔案註冊成功，請重新登入 :D");

	}

}