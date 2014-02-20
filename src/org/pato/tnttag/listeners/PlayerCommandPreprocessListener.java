package org.pato.tnttag.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.FileManager;
import org.pato.tnttag.managers.MessageManager;

public class PlayerCommandPreprocessListener implements Listener {
	
	 @EventHandler
		private void blockcommand(PlayerCommandPreprocessEvent event) {
		      List<String> cmds = FileManager.getInstance().getConfig().getStringList("AllowedCommands");
		      cmds.add("tag");
		      cmds.add("tnttag");
		      String cmdPerformed = event.getMessage().toLowerCase();
		      if (ArenaManager.getManager().isInGame(event.getPlayer())){
			      for (String command : cmds) {
			        if (cmdPerformed.startsWith("/" + command)) { return; } 
		      }
		          event.setCancelled(true);
		          MessageManager.getInstance().sendErrorMessage(event.getPlayer(), "You cannot perform " + cmdPerformed +  " while playing TNT Tag");
		  }
		}
}
