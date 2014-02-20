package org.pato.tnttag.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.pato.tnttag.files.GameData;
import org.pato.tnttag.util.Arena;

/**
 * 
 * @Author Jake
 */
public class ArenaManager {

	private static ArenaManager am = new ArenaManager();

	// Usefull for getting the ArenaManager, like so: ArenaManager.getManager()
	public static ArenaManager getManager() {
		return am;
	}

	// A method for getting one of the Arenas out of the list by name:
	public Arena getArena(String name) {
		for (Arena a : Arena.arenaObjects) { // For all of the arenas in the
												// list of objects
			if (a.getName().equals(name)) { // If the name of an arena object in
											// the list is equal to the one in
											// the parameter...
				return a; // Return that object
			}
		}
		return null; // No objects were found, return null
	}

	// A method for adding players
	public void addPlayers(Player player, String arenaName) {

		if (getArena(arenaName) != null) { // If the arena exsists

			Arena arena = getArena(arenaName); // Create an arena for using in
												// this method

			if (!arena.isFull()) { // If the arena is not full

				if (!arena.isInGame()) {

					// Every check is complete, arena is joinable
					InventoryManager.saveInventory(player); // Clear the players
													// inventory
					// Teleport to the arena's join location
					player.teleport(arena.getLobbyLocation());

					// Add the player to the arena list
					arena.getPlayers().add(player.getName()); // Add the players
																// name to the
																// arena
					int playersLeft = arena.getMinPlayers()
							- arena.getPlayers().size(); // How many players
															// needed to start
					// Send the arena's players a message
					arena.sendMessage(player.getName() + ChatColor.YELLOW + " joined the game (" + 
							ChatColor.LIGHT_PURPLE + arena.getPlayers().size() + ChatColor.YELLOW + "/" + ChatColor.LIGHT_PURPLE + arena.getMaxPlayers() + 
							ChatColor.YELLOW + ")");

					if (playersLeft == 0 && !arena.runningCountdown()) { // IF there are 0 players needed to
											// start the game
						startArena(arenaName); // Start the arena, see the
												// method way below :)
					}

				} else { // Specifiend arena is in game, send the player an
							// error message
					player.sendMessage(ChatColor.RED
							+ "The game in the arena you are looking forhas already started!");

				}
			} else { // Specified arena is full, send the player an error
						// message
				player.sendMessage(ChatColor.RED
						+ "The arena you are looking for is currently full!");
			}

		} else { // The arena doesn't exsist, send the player an error message
			player.sendMessage(ChatColor.RED
					+ "The arena you are looking for could not be found!");
		}

	}
	
	public boolean isInGame(Player player){
        for (Arena a : Arena.arenaObjects) {
            for (String s : a.getPlayers()) {
                Player p = Bukkit.getPlayer(s);
                if (player.getName().equalsIgnoreCase(p.getName())) {
                    return player.getName().equalsIgnoreCase(p.getName());
                }
            }
        }
        return false;
	}
	
	 public static Arena get(Player player) {
	        for (Arena a : Arena.arenaObjects) {
	            for (String s : a.getPlayers()) {
	                if (player.getName().equalsIgnoreCase(s)) {
	                    return a;
	                }
	            }
	        }
	        return null;
	    }
	
	public boolean isTNT(Player player){
        for (Arena a : Arena.arenaObjects) {
            for (String s : a.getTNTPlayers()) {
                Player p = Bukkit.getPlayer(s);
                if (player.getName().equalsIgnoreCase(p.getName())) {
                    return player.getName().equalsIgnoreCase(p.getName());
                }
            }
        }
        return false;
	}

	// A method for removing players
	public void removePlayer(Player player) {

			Arena arena = get(player); // Create an arena for using in
												// this method
				// Every check is complete, arena is leavable
				player.getInventory().clear(); // Clear the players inventory
				InventoryManager.restoreInventory(player);

				// remove the player to the arena list
				arena.getPlayers().remove(player.getName()); 
			if(arena.getPlayers().size() == 1){
				if (arena.isInGame()){
					arena.sendMessage("The last player left!");
					endArena(arena);
				} else {
					CountdownManager.cancelTask(arena);
				}
			}
	}
	
	public void removeTNTPlayer(Player player) {

		Arena arena = get(player); // Create an arena for using in
											// this method
		arena.getTNTPlayers().remove(player.getName());
		arena.getAlivePlayers().add(player.getName());
}
	
	public void addTNTPlayer(Player player) {

		Arena arena = get(player); // Create an arena for using in
											// this method
		arena.getTNTPlayers().add(player.getName());
		arena.getTNTPlayers().remove(player.getName());
}

	// A method for starting an Arena:
	public void startArena(String arenaName) {

		if (getArena(arenaName) != null) { // If the arena exsists

			Arena arena = getArena(arenaName); // Create an arena for using in
												// this method
			CountdownManager.startGame(50, arena);
		}

	}

	// A method for ending an Arena:
	public void endArena(Arena arena) {

			// Set ingame
			arena.setInGame(false);

			CountdownManager.cancelTask(arena);
			
			for (String s : arena.getPlayers()) {// Loop through every player in
													// the arena

				// Teleport them:

				Player player = Bukkit.getPlayer(s); // Create a player by the
														// name

				InventoryManager.restoreInventory(player); // Clear the players inventory

				// Remove them all from the list
				arena.getPlayers().remove(player.getName());

			}

	}

	// And our final method, loading each arena
	// This will be resonsible for creating each arena from the config, and
	// creating an object to represent it
	// Call this method in your main class, onEnable

	public void loadArenas() {

		// I just create a quick Config Variable, obviously don't do this.
		// Use your own config file
		FileConfiguration fc = GameData.getGameData(); // If you just use this code, it will
										// erorr, its null. Read the notes
										// above, USE YOUR OWN CONFIGURATION
										// FILE

		// Youll get an error here, FOR THE LOVE OF GAWD READ THE NOTES ABOVE!!!
		for (String keys : fc.getConfigurationSection("arenas").getKeys(false)) { // For
																					// each
																					// arena
																					// name
																					// in
																					// the
																					// arena
																					// file

			// Now lets get all of the values, and make an Arena object for
			// each:
			// Just to help me remember: Arena myArena = new Arena("My Arena",
			// joinLocation, startLocation, endLocation, 17)

			World world = Bukkit.getWorld("arenas." + keys + ".world");

			// Arena's name is keys

			double joinX = fc.getDouble("arenas." + keys + "joinX");
			double joinY = fc.getDouble("arenas." + keys + "joinY");
			double joinZ = fc.getDouble("arenas." + keys + "joinZ");
			Location joinLocation = new Location(world, joinX, joinY, joinZ);

			double startX = fc.getDouble("arenas." + keys + "startX");
			double startY = fc.getDouble("arenas." + keys + "startY");
			double startZ = fc.getDouble("arenas." + keys + "startZ");

			Location startLocation = new Location(world, startX, startY, startZ);

			double endX = fc.getDouble("arenas." + keys + "endX");
			double endY = fc.getDouble("arenas." + keys + "endX");
			double endZ = fc.getDouble("arenas." + keys + "endX");

			Location endLocation = new Location(world, endX, endY, endZ);

			int maxPlayers = FileManager.getInstance().getMaxPlayers();
			int minPlayers = FileManager.getInstance().getMinPlayers();

			// Now lets create an object to represent it:
			
			new Arena(keys, joinLocation, startLocation,
					endLocation, maxPlayers, minPlayers);

		}

	}

	// Our final method, create arena!
	public void createArena(String arenaName, Location joinLocation,
			Location startLocation, Location endLocation) {

		// Now, lets create an arena object to represent it:
		
		int maxPlayers = FileManager.getInstance().getMaxPlayers();
		int minPlayers = FileManager.getInstance().getMinPlayers();
		
		new Arena(arenaName, joinLocation, startLocation,
				endLocation, maxPlayers, minPlayers);

		// Now here is where you would save it all to a file, again, im going to
		// create a null FileConfiguration, USE YOUR OWN!!!
		FileConfiguration fc = GameData.getGameData(); // USE YOUR OWN PUNK

		fc.set("arenas." + arenaName, null); // Set its name
		// Now sets the other values

		String path = "arenas." + arenaName + "."; // Shortcut
		// Sets the paths
		fc.set(path + "joinX", joinLocation.getX());
		fc.set(path + "joinY", joinLocation.getY());
		fc.set(path + "joinZ", joinLocation.getZ());

		fc.set(path + "startX", startLocation.getX());
		fc.set(path + "startY", startLocation.getY());
		fc.set(path + "startZ", startLocation.getZ());

		fc.set(path + "endX", endLocation.getX());
		fc.set(path + "endY", endLocation.getY());
		fc.set(path + "endZ", endLocation.getZ());

		fc.set(path + "maxPlayers", maxPlayers);
		fc.set(path + "minPlayers", minPlayers);

		// Now save it up down here
		FileManager.getInstance().saveConfig();
	}
}
