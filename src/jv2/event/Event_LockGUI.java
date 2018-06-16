package jv2.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jv2.classes.Buffer_PlayerInfo;
import jv2.classes.PlayerInfo;
import jv2.main.Main;
import net.md_5.bungee.api.ChatColor;

public class Event_LockGUI implements Listener {

	Main plugin = Main.plugin;

	public static Map<Player,Boolean> logged = new HashMap<Player,Boolean>();
	
	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
	
		Map<Integer,String> woolname = new HashMap<Integer,String>();
		
		woolname.put(0, ChatColor.WHITE + "白");
		woolname.put(1, ChatColor.GOLD + "橙");
		woolname.put(4, ChatColor.YELLOW + "黃");
		
		Player player = (Player) e.getWhoClicked();
		
		if (!e.getInventory().getName().contains(" 的密碼鎖"))
			return;

		e.setCancelled(true);

		System.out.println(e.getCurrentItem().getItemMeta().getDisplayName());
		
		if (e.getCurrentItem().equals(new ItemStack(Material.THIN_GLASS)))
			return;

		if (e.getCurrentItem().equals(Wool(0,woolname.get(0))) || e.getCurrentItem().equals(Wool(0,null))) {

			e.setCurrentItem(Wool(4,woolname.get(4)));

		} else if (e.getCurrentItem().equals(Wool(4,woolname.get(4)))) {

			e.setCurrentItem(Wool(1,woolname.get(1)));

		} else if (e.getCurrentItem().equals(Wool(1,woolname.get(1)))) {

			e.setCurrentItem(Wool(0,woolname.get(0)));

		}

		if (e.getCurrentItem().getType().equals(Material.DIAMOND)) {

			verification(player,e.getInventory());

		}

	}
	
	@EventHandler
	public void closeKick(InventoryCloseEvent e) {
		
		if(!e.getInventory().getName().contains("的密碼鎖")) return;
		
		if(logged.get((Player)e.getPlayer()) == false) {
			
			((Player)e.getPlayer()).kickPlayer("請登入或註冊密碼後再進入遊戲。");
			
		}
		
	}

	public ItemStack Wool(int n,String name) {

		ItemStack item = new ItemStack(Material.WOOL, 1, (byte) n);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		
		item.setItemMeta(meta);

		return item;

	}

	public void verification(Player player,Inventory inv) {

		if(registered(player) == true) {
			
			String password = getPassword(player);
			
			String current = getCurrentPassword(player,inv);
			
			if(current.equals(password)) {
				
				player.sendMessage("驗證成功。");
				
				logged.put(player, true);
				
				player.closeInventory();
				
			}else {
				
				player.kickPlayer("密碼錯誤，請重新登入。");
				
			}
			
		}else {
			
			String current = getCurrentPassword(player,inv);
			
			buffer_register(player,current);
			
		}
		
		Event_PlayerLogin.logining.remove(player);
		
	}

	public boolean registered(Player player) {

		try {

			Connection connection = plugin.getDB().getSQLConnection();

			PreparedStatement state = connection.prepareStatement("select * from `jv2_player` where player = ?");
			
			state.setString(1, player.getName());

			ResultSet result = state.executeQuery();
			
			if(result.next() == true) return true;
			
		} catch (SQLException e) {

			e.printStackTrace();

		}
		
		return false;

	}
	
	public void buffer_register(Player player,String password) {
		
		PlayerInfo info = Buffer_PlayerInfo.info.get(player);
		
		info.password = password;
		
		Buffer_PlayerInfo.info.put(player, info);
		
		Buffer_PlayerInfo.register_database(player);
		
	}
	
	public String getPassword(Player player) {
		
		try {

			Connection connection = plugin.getDB().getSQLConnection();

			PreparedStatement state = connection.prepareStatement("select password from `jv2_player` where player = ?");
			
			state.setString(1, player.getName());

			ResultSet result = state.executeQuery();
			
			String password = null;
			
			while(result.next()) {
				
				password = result.getString(1);
				
			}
			
			return password;
			
		} catch (SQLException e) {

			e.printStackTrace();

		}
		
		return null;
		
	}
	
	public String getCurrentPassword(Player player,Inventory inv) {
		
		String str = "";
		
		int[] password = {12,13,14,21,22,23,30,31,32};
		
		for(int i = 0; i < password.length; i++) {
			
			ItemStack item = inv.getItem(password[i]);
			
			ItemMeta meta = item.getItemMeta();
			
			String name = meta.getDisplayName();
			
			if(name.contains("白") || name == null) {
				
				str += "0";
				
			}else if(name.contains("橙")){
				
				str += "1";
				
			}else if(name.contains("黃")) {
				
				str += "2";
				
			}
			
		}
		
		return str;
		
	}

}