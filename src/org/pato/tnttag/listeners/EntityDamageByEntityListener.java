package org.pato.tnttag.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.pato.tnttag.api.PlayerTagEvent;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.FileManager;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.FireworkEffectPlayer;

public class EntityDamageByEntityListener implements Listener {
	
	FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
	
	@EventHandler(priority = EventPriority.NORMAL)
	 public void dmg(EntityDamageByEntityEvent event) throws Exception {
		 Player damager = (Player) event.getDamager();
	     Player victim = (Player) event.getEntity();
		 if (victim instanceof Player && damager instanceof Player){
			 if (ArenaManager.getManager().isInGame(victim)){
				 if (ArenaManager.getManager().isTNT(damager)){ 
					 ArenaManager.getManager().addTNTPlayer(victim);
					 ArenaManager.getManager().removeTNTPlayer(damager);
					 
					 MessageManager.getInstance().sendInGamePlayersMessage(victim.getName() + ChatColor.YELLOW + " is 'it'!", ArenaManager.get(victim));
					 
					 Bukkit.getServer().getPluginManager().callEvent(new PlayerTagEvent(damager, victim));
					 
					 int tags = FileManager.getInstance().getPlayerData().getInt(damager.getName() + ".tags"),
						 taggeds = FileManager.getInstance().getPlayerData().getInt(victim.getName() + ".taggeds");
					 
					FileManager.getInstance().getPlayerData().set(damager.getName() + ".tags", tags + 1);
					FileManager.getInstance().getPlayerData().set(victim.getName() + ".taggeds", taggeds + 1);
					 
					 victim.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
					 damager.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
					 
					 damager.getInventory().setItem(0, new ItemStack(Material.AIR, 1));
					 victim.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
					 
					 FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.CREEPER).build();
					 fplayer.playFirework(victim.getWorld(), victim.getLocation(), effect);
					 
					victim.setHealth((double)20);
				 } else {
					 victim.setHealth((double)20);
				 }
			 }
		 }
	 }
}
