package org.pato.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.pato.tnttag.managers.ArenaManager;

public class FoodLevelChangeListener implements Listener {
	
	 @EventHandler
	 public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (ArenaManager.getManager().isInGame((Player) event.getEntity())){
			event.setCancelled(true);
		}
	 }
}
