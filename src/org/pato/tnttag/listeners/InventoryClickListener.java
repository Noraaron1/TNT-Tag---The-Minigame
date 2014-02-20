package org.pato.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.pato.tnttag.managers.ArenaManager;

public class InventoryClickListener implements Listener {
	
	 @EventHandler
	 public void InventoryMove(InventoryClickEvent e){
		 Player player = (Player) e.getWhoClicked();
		 if (ArenaManager.getManager().isInGame(player)){
			 e.setCancelled(true);
		 }
	 }
	 
}
