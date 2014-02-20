package org.pato.tnttag.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.pato.tnttag.managers.MessageManager;

/**
 * 
 * @Author Jake
 */
public class Arena {

	// A list of all the Arena Objects
	public static ArrayList<Arena> arenaObjects = new ArrayList<Arena>();

	// Some fields we want each Arena object to store:
	private Location lobbyLocation, arenaLocation, spectatorLocation; // Some general
																// arena
																// locations

	private String name; // Arena name
	private ArrayList<String> players = new ArrayList<String>(); // And
	private ArrayList<String> TNTPlayers = new ArrayList<String>();																// arraylist
	private ArrayList<String> AlivePlayers = new ArrayList<String>();																// of															// players
																	// name

	private int maxPlayers, minPlayers, taskID;

	private boolean inGame = false,
			runningCountdown = false; // Boolean to determine if an Arena is
									// ingame or not, automaticly make it false

	// Now for a Constructor:
	public Arena(String arenaName, Location joinLocation,
			Location startLocation, Location endLocation, int maxPlayers, int minPlayers) { // So
																			// basicly:
																			// Arena
																			// myArena
																			// =
																			// new
																			// Arena("My Arena",
																			// joinLocation,
																			// startLocation,
																			// endLocation,
																			// 17)
	// Lets initalize it all:
		this.name = arenaName;
		this.lobbyLocation = joinLocation;
		this.arenaLocation = startLocation;
		this.spectatorLocation = endLocation;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;

		// Now lets add this object to the list of objects:
		arenaObjects.add(this);

	}

	// Now for some Getters and Setters, so with our arena object, we can use
	// special methods:
	public Location getLobbyLocation() {
		return this.lobbyLocation;
	}

	public void setLobbyLocation(Location joinLocation) {
		this.lobbyLocation = joinLocation;
	}

	public Location getArenaLocation() {
		return this.arenaLocation;
	}

	public void setArenaLocation(Location startLocation) {
		this.arenaLocation = startLocation;
	}

	public Location getSpectatorLocation() {
		return this.spectatorLocation;
	}

	public void setSpectatorLocation(Location endLocation) {
		this.spectatorLocation = endLocation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}
	public ArrayList<String> getPlayers() {
		return this.players;
	}
	public ArrayList<String> getTNTPlayers() {
		return this.TNTPlayers;
	}
	public ArrayList<String> getAlivePlayers() {
		return this.AlivePlayers;
	}

	// And finally, some booleans:
	public boolean isFull() { // Returns weather the arena is full or not
		if (players.size() >= maxPlayers) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isInGame() {
		return inGame;
	}
	
	public boolean runningCountdown() {
		return runningCountdown;
	}
	
	public void setRunningCountdown(Boolean b) {
		runningCountdown = b;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	// To send each player in the arena a message
	public void sendMessage(String message) {
		for (String s : players) {
			MessageManager.getInstance().sendMessage(Bukkit.getPlayer(s), message);
		}
	}
	
	  public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	ScoreboardManager manager = Bukkit.getScoreboardManager();
	  Scoreboard board = manager.getNewScoreboard();

	  Objective objective = board.registerNewObjective("lives", "dummy");

	  public void setBoard(Player player, int time){
	    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    objective.setDisplayName("TNT Tag");

	    Score money = objective.getScore(Bukkit.getOfflinePlayer("Players:"));
	    money.setScore(players.size());

	    Score Tags = objective.getScore(Bukkit.getOfflinePlayer("Time:"));
	    Tags.setScore(time);
	    player.setScoreboard(board);
	  }

	  public void removeBoard(Player player) {
	    player.setScoreboard(manager.getNewScoreboard());
	  }

	  public void updateBoard(Player player, int time) {
	    setBoard(player, time);
	  }

}