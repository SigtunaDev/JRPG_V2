package jv2.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import jv2.main.Main;

public abstract class Database {
	
	Main plugin = Main.plugin;
	
	Connection connection;
	
	public String table = "`jv2_player`";

	public int tokens = 0;
	
	public abstract Connection getSQLConnection();
	
	public abstract void load();
	
	public abstract void runCommand(String command);
	
	public void initialize(){
		
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
	}
	
	public void close(PreparedStatement ps,ResultSet rs) {
		
		try {
		
		ps.close();
		rs.close();
		
		} catch (SQLException ex) {
			
			Error.close(plugin,ex);
			
		}
		
	}
	
}
