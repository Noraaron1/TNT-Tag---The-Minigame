package org.pato.tnttag.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pato.tnttag.managers.ArenaManager;

public class PlayerQuitListener implements Listener {
	
	 @EventHandler
	 public void Quit(PlayerQuitEvent event){
		 if (ArenaManager.getManager().isInGame(event.getPlayer()))
			 ArenaManager.getManager().removePlayer(event.getPlayer());
	 }
}
