package org.pato.tnttag.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.Arena;

public class MessageManager {
	
	private MessageManager() { }
	
    static MessageManager instance = new MessageManager();
    
    String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "TNT Tag" + ChatColor.GRAY + "]: ";
    
    public static MessageManager getInstance() {
    	return instance;
    }
    
    public void sendErrorMessage(CommandSender sender, String errormsg){
    	sender.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " " + errormsg);
    }
    
    public void sendInsuficientArgs(CommandSender sender,String command, String args){
    	sendErrorMessage(sender, "Insufficient args.");
    	sender.sendMessage(ChatColor.RED + "Usage: /tag " + command + " " + args);
    }

    public void sendInvalidArgs(CommandSender sender,String command, String args){
    	sendErrorMessage(sender, "Invalid args.");
    	sender.sendMessage(ChatColor.RED + "Usage: /tag " + command + " " + args);
    }
    
	public void sendMessage(CommandSender sender, String s){
		sender.sendMessage(prefix + ChatColor.GRAY + s);
	}
	
	public void sendInGamePlayersMessage(String s, Arena arena){
		arena.sendMessage(prefix + ChatColor.GRAY + s);
	}
	
	public void sendWinMessage(String s, Arena arena){
		for (String p : arena.getPlayers()){
			Player player = Bukkit.getPlayer(p);
			player.sendMessage(ChatColor.GOLD + "#" + ChatColor.GRAY + "------------------" + ChatColor.GOLD + "#");
			player.sendMessage(ChatColor.GOLD + "   " + s + " won at TNT Tag!");
			player.sendMessage(ChatColor.GOLD + "#" + ChatColor.GRAY + "------------------" + ChatColor.GOLD + "#");
		}
	}
	
	public void isConsole(CommandSender sender){
		sender.sendMessage(prefix + ChatColor.GRAY + "This Command Can Only Be Done By In-Game Players");
	}
	
	public void noperm(CommandSender sender){
		sender.sendMessage(prefix +  ChatColor.GRAY + "You Do Not Have Permission To Perform This Command");
	}

	public void sendNoPrefixMessage(CommandSender sender, String s) {
		sender.sendMessage( ChatColor.GRAY + s);	
	}

}
