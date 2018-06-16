package jv2.sql;

import java.util.logging.Level;

import jv2.main.Main;

public class Error {
	
	public static void execute(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "SQL語法錯誤: ", ex);
    }
	
    public static void close(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "無法關閉SQL連接: ", ex);
    }
    
}
