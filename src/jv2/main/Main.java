package jv2.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import jv2.sql.Database;
import jv2.sql.SQLite;

public class Main extends JavaPlugin {

	//靜態Main類別變數：plugin
	public static Main plugin;

	//資料庫
	private Database db;
	
	//設定檔：config.yml
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

	//回傳資料庫
	public Database getDB() {

		return this.db;

	}
	
	//註冊檔案
	public void register_file() {
		
		config = new File(this.getDataFolder(), "/config.yml");
		fc = YamlConfiguration.loadConfiguration(config);
		
	}
	
	//初始化資料庫
	public void database_init() {
		
		this.db = new SQLite(this);
		this.db.load();

		this.getLogger().info("初始化資料庫成功!");
		
	}

	//確認config.yml是否存在，如果不存在就生成
	public void check_exist() {

		try {

			if (!config.exists()) {

				fc.set("versions", "0.2");
				
				fc.save(config);
				
				this.getLogger().info("確認檔案是否存在：不存在，已新增。");

			}

		} catch (IOException e) {

			e.printStackTrace();
			
		}

	}

}
