package org.pato.tnttag.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.pato.tnttag.files.Config;
import org.pato.tnttag.files.GameData;
import org.pato.tnttag.files.PlayerData;
 
public class FileManager {
 
        private FileManager() { }
       
        static FileManager instance = new FileManager();
       
        public static FileManager getInstance() {
                return instance;
        }
       
        public void setup(Plugin p) {
        		if(!p.getDataFolder().exists()) {
                    p.getDataFolder().mkdir();
                }
                
                Config.reload();
                Config.load();
                Config.save();
                Config.reload();	
                
                PlayerData.reload();
                PlayerData.load();
                PlayerData.save();
                PlayerData.reload();
                
                GameData.reload();
                GameData.load();
                GameData.save();
                GameData.reload();
        }
       
        public FileConfiguration getConfig() {
                return Config.getConfig();
        }
        
        public FileConfiguration getPlayerData() {
            return PlayerData.getPlayerData();
        }
        
        public FileConfiguration getGameData() {
            return GameData.getGameData();
        }
        
        public int getMaxPlayers() {
        	return getConfig().getInt("maxplayers");
        }
        
        public int getMinPlayers(){
        	return getConfig().getInt("minplayers");
        }
       
        public void saveConfig() {
        	Config.save();
        	PlayerData.save();
        	GameData.save();
        }
       
        public void reloadConfig() {
        	Config.reload();
        	PlayerData.reload();
        	GameData.reload();
        }
}