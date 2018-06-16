package jv2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jv2.main.Main;
import net.md_5.bungee.api.ChatColor;

public class GUI_Lock {

	Main plugin = Main.plugin;

	Inventory inv = null;

	Player player = null;

	public GUI_Lock(Player player) {

		this.player = player;

		inv = Bukkit.createInventory(null, 54, player.getName() + " ���K�X��");
		
	}

	public void preSet() {

		for (int i = 0; i <= 8; i++) {

			inv.setItem(i, new ItemStack(Material.THIN_GLASS));

		}

		for (int i = 36; i <= 53; i++) {

			inv.setItem(i, new ItemStack(Material.THIN_GLASS));

		}
		
		int[] array = {9,10,11,15,16,17,18,19,20,24,25,26,27,28,29,33,34,35};
		
		for(int i = 0; i < array.length; i++) {
			
			inv.setItem(array[i], new ItemStack(Material.THIN_GLASS));
			
		}
		
		int[] password = {12,13,14,21,22,23,30,31,32};
		
		ItemStack item = new ItemStack(Material.WOOL);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.WHITE + "��");
		
		item.setItemMeta(meta);
		
		for(int i = 0; i < password.length; i++) {
			
			inv.setItem(password[i], item);
			
		}
		
		inv.setItem(49, verification());

	}
	
	public Inventory getInventory() {
		
		preSet();
		
		return inv;
		
	}
	
	public ItemStack verification() {
		
		ItemStack item = new ItemStack(Material.DIAMOND);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.AQUA + "����!");
			
		meta.setLore(Arrays.asList(ChatColor.RED + "�Ъ`�N�A�̦h�u��n�J���ѤT���A�Y�W�L�T���N�|�ᵲ�b���C",ChatColor.RED + "�Y�A�|�����U�K�X�A���U�����~�Y�i���U�C"));
		
		item.setItemMeta(meta);
		
		return item;
		
	}

}
