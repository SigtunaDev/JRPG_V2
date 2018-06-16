package jv2.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import jv2.sql.Database;
import jv2.sql.SQLite;

public class Main extends JavaPlugin {

	//�R�AMain���O�ܼơGplugin
	public static Main plugin;

	//��Ʈw
	private Database db;
	
	//�]�w�ɡGconfig.yml
	private File config;
	private FileConfiguration fc;

	public void onEnable() {

		plugin = this;

		register_file();
		check_exist();
		database_init();
		
		Register_Command.Register();
		Register_Event.Register();
		
	}

	//�^�Ǹ�Ʈw
	public Database getDB() {

		return this.db;

	}
	
	//���U�ɮ�
	public void register_file() {
		
		config = new File(this.getDataFolder(), "/config.yml");
		fc = YamlConfiguration.loadConfiguration(config);
		
	}
	
	//��l�Ƹ�Ʈw
	public void database_init() {
		
		this.db = new SQLite(this);
		this.db.load();

		this.getLogger().info("��l�Ƹ�Ʈw���\!");
		
	}

	//�T�{config.yml�O�_�s�b�A�p�G���s�b�N�ͦ�
	public void check_exist() {

		try {

			if (!config.exists()) {

				fc.set("versions", "0.2");
				
				fc.save(config);
				
				this.getLogger().info("�T�{�ɮ׬O�_�s�b�G���s�b�A�w�s�W�C");

			}

		} catch (IOException e) {

			e.printStackTrace();
			
		}

	}

}
