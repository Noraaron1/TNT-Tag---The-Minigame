package org.pato.tnttag.files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static FileConfiguration config = null;
    private static File configFile = null;
    
    static String[] broadcastTimes = { "1", "2", "3", "4", "5", "10", "15", "20", "30", "60", "120" };
    
    public static void load() {
        config = getConfig();
        
        config.options().header(
        		"############################################################\n" +
        		"# +------------------------------------------------------+ #\n" +
        		"# |                TNT Tag Configuration                 | #\n" +
        		"# +------------------------------------------------------+ #\n" +
        		"############################################################");
        
        config.addDefault("BroadcastTimes", Arrays.asList(broadcastTimes));
        config.addDefault("minplayers", 12);
        config.addDefault("maxplayers", 24);
        config.addDefault("usepermissions", false);
        getConfig().options().copyDefaults(true);
        save();
    }
    
    public static void reload() {
        if (configFile == null) {
            configFile = new File("plugins/TNTTag/config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    public static FileConfiguration getConfig() {
        if (config == null) {
            reload();
        }
        return config;
    }
    
    public static void save() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            config.save(configFile);
        } catch (IOException ex) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save configFile to " + configFile, ex);
        }
    }
    
}