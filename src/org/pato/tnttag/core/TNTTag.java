package org.pato.tnttag.core;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.CommandManager;
import org.pato.tnttag.managers.FileManager;
import org.pato.tnttag.managers.ListenerManager;
import org.pato.tnttag.util.Arena;
import org.pato.tnttag.util.Permissions;

public class TNTTag extends JavaPlugin {
	
	 FileManager settings = FileManager.getInstance();
     protected Logger log;
     public static TNTTag main;
     
     public void onEnable() {
    	 main = this;
    	 this.log = this.getLogger();
         settings.setup(this);
         Permissions.loadPermissions(this);
         ListenerManager.registerEvents(this);
         getCommand("tnttag").setExecutor(new CommandManager());
         getCommand("tag").setExecutor(new CommandManager());
         this.log.info("Has Been Enabled!");
     }
     
     public void onDisable(){
    	 main = null;
    	 this.log = this.getLogger();
    	 FileManager.getInstance().saveConfig();
    	 for (Arena arena : Arena.arenaObjects){
    		 arena.sendMessage("There was a reload");
    		 ArenaManager.getManager().endArena(arena);
    	 }
    	 Permissions.unloadPermissions(this);
    	 this.log.info("Has Been Disabled!");
     }

}
