package jv2.sql;

import java.util.logging.Level;

import jv2.main.Main;

public class Error {
	
	public static void execute(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "SQL�y�k���~: ", ex);
    }
	
    public static void close(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "�L�k����SQL�s��: ", ex);
    }
    
}
