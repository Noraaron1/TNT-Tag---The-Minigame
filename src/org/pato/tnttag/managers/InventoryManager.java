package org.pato.tnttag.managers;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class InventoryManager {
	
	private static HashMap<String, ItemStack[]> armourContents = new HashMap<String, ItemStack[]>();
	private static HashMap<String, ItemStack[]> inventoryContents = new HashMap<String, ItemStack[]>();
	private static HashMap<String, Location> locations = new HashMap<String, Location>();
	private static HashMap<String, GameMode> gameModes = new HashMap<String, GameMode>();
	private static HashMap<String, Float> xp = new HashMap<String, Float>();
	private static HashMap<String, Integer> xpLevel = new HashMap<String, Integer>();
	private static HashMap<String, Integer> foodLevel = new HashMap<String, Integer>();
	private static HashMap<String, Double> Health = new HashMap<String, Double>();
	private static HashMap<String, Boolean> flight = new HashMap<String, Boolean>();
	private static HashMap<String, Collection<PotionEffect>> potions = new HashMap<String, Collection<PotionEffect>>();
	
	public static void saveInventory(Player player){
		armourContents.put(player.getName(), player.getInventory().getArmorContents());
		inventoryContents.put(player.getName(), player.getInventory().getContents());
		locations.put(player.getName(), player.getLocation());
		xp.put(player.getName(), player.getExp());
		xpLevel.put(player.getName(), player.getLevel());
		gameModes.put(player.getName(), player.getGameMode());
		potions.put(player.getName(), player.getActivePotionEffects());
		foodLevel.put(player.getName(), player.getFoodLevel());
		Health.put(player.getName(), (double)player.getHealth());
		flight.put(player.getName(), player.getAllowFlight());
		
		player.setAllowFlight(false);
		player.setHealth((double)20);
		player.setFoodLevel(20);
		for (PotionEffect effect : player.getActivePotionEffects()){
	        player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.setLevel(0);
		player.setExp((float)0);
		player.setGameMode(GameMode.SURVIVAL);
	}
	
	public static void restoreInventory(Player player){
		player.getInventory().clear();
		player.teleport(locations.get(player.getName()));
		
		player.getInventory().setContents(inventoryContents.get(player.getName()));
		player.getInventory().setArmorContents(armourContents.get(player.getName()));
		player.setExp(xp.get(player.getName()));
		player.setLevel(xpLevel.get(player.getName()));
		player.setGameMode(gameModes.get(player.getName()));
		player.addPotionEffects(potions.get(player.getName()));
		player.setFoodLevel(foodLevel.get(player.getName()));
		player.setHealth((double)Health.get(player.getName()));
		player.setAllowFlight(flight.get(player.getName()));
		
		flight.remove(player.getName());
		Health.remove(player.getName());
		foodLevel.remove(player.getName());		
		potions.remove(player.getName());
		xpLevel.remove(player.getName());
		xp.remove(player.getName());
		locations.remove(player.getName());
		armourContents.remove(player.getName());
		inventoryContents.remove(player.getName());
		gameModes.remove(player.getName());
	}
}
