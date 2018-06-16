package jv2.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import jv2.main.Main;

public class SQLite extends Database {

	String dbname;
	
	Main plugin = Main.plugin;

	public String PlayerDataCreate = String.format("CREATE TABLE IF NOT EXISTS `jv2_player`(%s,%s,%s)",
			"`player` TEXT NOT NULL", 
			"`password` TEXT NOT NULL",
			"`name` TEXT NOT NULL");
	public SQLite(Main instance) {
		
		dbname = plugin.getConfig().getString("SQLite.Filename", "database"); // Set the table name here e.g
																			// player_kills
	}

	public Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), "/" + dbname + ".db");
		if (!dataFolder.exists()) {
			try {
				dataFolder.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				plugin.getLogger().log(Level.SEVERE, "cannot write file: " + dbname + ".db");
			}
		}
		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
		}
		return null;
	}

	public void load() {
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(PlayerDataCreate);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}

	public void runCommand(String command) {

		try {

			PreparedStatement ps = connection.prepareStatement(command);

			ps.execute();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

}
