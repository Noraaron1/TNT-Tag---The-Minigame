package org.pato.tnttag.util;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.MessageManager;

public class CreateArenaData {

	private static HashMap<String, Location> lobbyLocation = new HashMap<String, Location>();
	private static HashMap<String, Location> arenaLocation = new HashMap<String, Location>();
	private static HashMap<String, Location> spectatorsLocation = new HashMap<String, Location>();
	
	private static HashMap<String, Boolean> lobbyLocationBoolean = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> arenaLocationBoolean = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> spectatorsLocationboolean = new HashMap<String, Boolean>();
	
	public static void setArenaLocation(Player player){
		arenaLocation.put(player.getName(), player.getLocation());
		arenaLocationBoolean.put(player.getName(), true);
		return;
	}
	
	public static void setLobbyLocation(Player player){
		lobbyLocation.put(player.getName(), player.getLocation());
		lobbyLocationBoolean.put(player.getName(), true);
		return;
	}
	
	public static void setSpectatorsLocation(Player player){
		spectatorsLocation.put(player.getName(), player.getLocation());
		spectatorsLocationboolean.put(player.getName(), true);
		return;
	}
	
	public static boolean check(Player player){
		boolean b = false;
		if(!lobbyLocationBoolean.get(player.getName())){
			b = true;
			MessageManager.getInstance().sendErrorMessage(player, "Lobby Location is missing.");
		}
		if(!arenaLocationBoolean.get(player.getName())){
			b = true;
			MessageManager.getInstance().sendErrorMessage(player, "Arena Location is missing.");
		}
		if(!spectatorsLocationboolean.get(player.getName())){
			b = true;
			MessageManager.getInstance().sendErrorMessage(player, "Spectators Location is missing.");
		}
		return b;
	}
	
	public static void createArena(String player, String arenaName){
		ArenaManager.getManager().createArena(arenaName, lobbyLocation.get(player), arenaLocation.get(player), spectatorsLocation.get(player));
		spectatorsLocation.remove(player);
		arenaLocation.remove(player);
		lobbyLocation.remove(player);
		spectatorsLocationboolean.remove(player);
		arenaLocationBoolean.remove(player);
		lobbyLocationBoolean.remove(player);
	}
}
