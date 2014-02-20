package org.pato.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.pato.tnttag.managers.ArenaManager;

public class EntityDamageListener implements Listener {
	 
	 @EventHandler
	 public void cancelAllDamage(EntityDamageEvent event){
		 if (event.getEntity() instanceof Player){
			 Player player = (Player) event.getEntity();
			 if (event.getCause() != DamageCause.ENTITY_ATTACK && ArenaManager.getManager().isInGame(player)){
				 event.setCancelled(true);
			 }
		 } else { return; } 
	 }
}
