package org.pato.tnttag.managers;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.pato.tnttag.core.TNTTag;
import org.pato.tnttag.util.Arena;

public class CountdownManager {
	
	private static int seconds;
	
	final static List<Integer> timesToBoradcast = FileManager.getInstance().getConfig().getIntegerList("BroadcastTimes");
	
	
	public static void startGame(final int seconds1, final Arena arena) {
		if (!arena.runningCountdown() && arena.getPlayers().size() >= arena.getMinPlayers()){
			
			seconds = seconds1;
	    	  
			arena.setRunningCountdown(true);
			
					arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TNTTag.main, new Runnable(){
				      public void run() {
				    	  if (timesToBoradcast.contains(seconds)){
							 MessageManager.getInstance().sendInGamePlayersMessage(
									 ChatColor.YELLOW + "" + seconds + ((seconds == 1) ? " second" : " seconds") + " until the game starts!", arena);
				    	  }			    	
				    	  
							for (String p : arena.getPlayers()){
								Player player = Bukkit.getPlayer(p);
								player.setLevel(seconds);
								arena.setBoard(player, seconds);
							}

							if (seconds == 0){
								Bukkit.getScheduler().cancelTask(arena.getTaskID());
								
								arena.setInGame(true);
						        
								Location loc = arena.getArenaLocation();
						        
								for (String p : arena.getPlayers()){
									Player player = Bukkit.getPlayer(p);
									player.teleport(loc);
								}
								
						        
								MessageManager.getInstance().sendInGamePlayersMessage(ChatColor.YELLOW + "The TNT has been released!", arena);
								
								pickRandomTNT(arena);
								
								startRound(arena);
								
							}
							
							seconds--;
				      }
					}
				    , 20, 20L));
		}
	}




	protected static void startRound(final Arena arena) {
		
		seconds = ((arena.getPlayers().size() > 6) ? 50 : 30);
		
		 arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TNTTag.main, new Runnable(){
		      public void run() {		    	  
					for (String p : arena.getPlayers()){
						Player player = Bukkit.getPlayer(p);
						player.setLevel(seconds);
						arena.setBoard(player, seconds);
					}
					
					if (seconds == 0){
						Bukkit.getScheduler().cancelTask(arena.getTaskID());
						
						if (arena.getPlayers().size() == 2){	
				        	
							blowUpTNTs(arena);
							
				        	arena.setRunningCountdown(false);
				        	
							for (String s : arena.getPlayers()){
									Player player = Bukkit.getPlayer(s);
	
									int wins = FileManager.getInstance().getPlayerData().getInt(s + ".wins");
									FileManager.getInstance().getPlayerData().set(s  + ".wins", wins + 1);
									
									int money = FileManager.getInstance().getPlayerData().getInt(s + ".money");
									FileManager.getInstance().getPlayerData().set(s + ".money", money + 50);
									
									MessageManager.getInstance().sendWinMessage(player.getName(), arena);
									MessageManager.getInstance().sendNoPrefixMessage(player, ChatColor.GOLD + "You earned a total of 50 Coins!");
									MessageManager.getInstance().sendNoPrefixMessage(player, ChatColor.GOLD + "#" + ChatColor.GRAY + "------------------" + ChatColor.GOLD + "#");
									arena.getAlivePlayers().remove(player.getName());
									
									ArenaManager.getManager().removePlayer(player);
									if (arena.getPlayers().size() == 0){
										
										arena.setInGame(false);;
										
										return;
									}
							}
				        } else if (arena.getPlayers().size() <= 6){
				        	
				        	blowUpTNTs(arena);
				        	
				        	Location loc = arena.getArenaLocation();
					        
							for (String p : arena.getPlayers()){
								Player player = Bukkit.getPlayer(p);
								player.teleport(loc);
							}
					        
							MessageManager.getInstance().sendInGamePlayersMessage(ChatColor.YELLOW + "The TNT has been released!", arena);
							
							pickRandomTNT(arena);

							startDelayedRound(arena);
						} else {
					        
							blowUpTNTs(arena);
							
							MessageManager.getInstance().sendInGamePlayersMessage(ChatColor.YELLOW + "The TNT has been released!", arena);
							
							pickRandomTNT(arena);
							
							startDelayedRound(arena);
						}
						
					}
					
					seconds--;
		      }
			}
		    , 20, 20));
	}      	

	protected static void startDelayedRound(final Arena arena) {
		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TNTTag.main, new Runnable(){
		      public void run() {
		    	  if (arena.runningCountdown() && arena.isInGame()){
		    	  startRound(arena);
		    	  }
		      }
		  }
		  , 100L);			
	}




	public static void blowUpTNTs(final Arena arena){
		
		for (String s : arena.getTNTPlayers()){
			Player TNTplayer = Bukkit.getPlayer(s);
			
			World world = TNTplayer.getWorld();
			
			world.createExplosion(TNTplayer.getLocation(), 0.0F);
			
			MessageManager.getInstance().sendInGamePlayersMessage(TNTplayer.getName() + ChatColor.YELLOW + " blew up!", arena);
			
			TNTplayer.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
			TNTplayer.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
			
			arena.getTNTPlayers().remove(s);
			arena.getPlayers().remove(s);
			arena.removeBoard(TNTplayer);
			if (arena.getTNTPlayers().size() == 0){
				finishBlowingUp(arena);
				return;
			}
		}
	}
	
	private static void finishBlowingUp(final Arena arena) {	
		MessageManager.getInstance().sendInGamePlayersMessage(ChatColor.YELLOW + "Round ended.", arena);
		
		for (String p : arena.getPlayers()){
			Player player = Bukkit.getPlayer(p);
			player.sendMessage(ChatColor.GOLD + "+2 coins!");
			int money = FileManager.getInstance().getPlayerData().getInt(p + ".money");
			FileManager.getInstance().getPlayerData().set(p + ".money", money + 2);
			FileManager.getInstance().saveConfig();
		}
	}

	public static void pickRandomTNT(final Arena arena){		
		int playerThatWillBePicked = ((arena.getPlayers().size() >= 6) ? arena.getPlayers().size()/2 : 1);
		
		while (playerThatWillBePicked != 0){
			pickPlayers(arena);
			playerThatWillBePicked--;
		} 
		
		for (String s : arena.getTNTPlayers()){
			MessageManager.getInstance().sendInGamePlayersMessage(s + ChatColor.YELLOW + " is 'it'", arena);
		}
	} 
	
	private static void pickPlayers(final Arena arena){
		Random random = new Random();
			
			String[] players = new String[arena.getAlivePlayers().size()];
			int i = 0;
			for (String s : arena.getAlivePlayers()){
				players[i] = s;
				i++;
			}
			int randomInt = 0;
			randomInt = random.nextInt(players.length);
			arena.getTNTPlayers().add(players[randomInt]);
			arena.getAlivePlayers().remove(players[randomInt]);
			
			
			if (players[randomInt] != null){
				Player player = Bukkit.getPlayer(players[randomInt]);
				player.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
				player.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
			}
			
			players = null;
			if (arena.getTNTPlayers().contains(null)){
				arena.getTNTPlayers().remove(null);
			}
	}
	
	public static void cancelTask(final Arena arena){
		Bukkit.getScheduler().cancelTask(arena.getTaskID());
		arena.setRunningCountdown(false);
		arena.setInGame(false); 
	}
}
