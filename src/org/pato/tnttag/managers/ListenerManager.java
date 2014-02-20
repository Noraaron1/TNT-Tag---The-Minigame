package org.pato.tnttag.managers;

import org.bukkit.plugin.PluginManager;
import org.pato.tnttag.core.TNTTag;
import org.pato.tnttag.listeners.BlockBreakListener;
import org.pato.tnttag.listeners.DropItemListener;
import org.pato.tnttag.listeners.EntityDamageByEntityListener;
import org.pato.tnttag.listeners.EntityDamageListener;
import org.pato.tnttag.listeners.FoodLevelChangeListener;
import org.pato.tnttag.listeners.InventoryClickListener;
import org.pato.tnttag.listeners.PlayerCommandPreprocessListener;
import org.pato.tnttag.listeners.PlayerIteractListener;
import org.pato.tnttag.listeners.PlayerQuitListener;

public class ListenerManager {

	public static void registerEvents(TNTTag plugin) {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(), plugin);
		pm.registerEvents(new DropItemListener(), plugin);
		pm.registerEvents(new EntityDamageByEntityListener(), plugin);
		pm.registerEvents(new EntityDamageListener(), plugin);
		pm.registerEvents(new FoodLevelChangeListener(), plugin);
		pm.registerEvents(new InventoryClickListener(), plugin);
		pm.registerEvents(new PlayerCommandPreprocessListener(), plugin);
		pm.registerEvents(new PlayerIteractListener(), plugin);
		pm.registerEvents(new PlayerQuitListener(), plugin);
	}

}
