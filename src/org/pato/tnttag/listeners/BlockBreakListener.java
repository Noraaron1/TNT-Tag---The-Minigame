package org.pato.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.pato.tnttag.managers.ArenaManager;

public class BlockBreakListener implements Listener {
	
	 @EventHandler
	 public void Break(BlockBreakEvent event){
		  Player player = event.getPlayer();
			  if (ArenaManager.getManager().isInGame(player)){
				  event.setCancelled(true);
		  }
	 }
}
