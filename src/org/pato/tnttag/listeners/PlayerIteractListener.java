package org.pato.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.pato.tnttag.managers.ArenaManager;

public class PlayerIteractListener implements Listener {
	
	 @EventHandler
	 public void Place(PlayerInteractEvent event){
		  Action action = event.getAction();
		  Player player = event.getPlayer();
		  if (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK){
			  if (ArenaManager.getManager().isInGame(player)){
				  event.setCancelled(true);
			  }
		  }
	 }
}
