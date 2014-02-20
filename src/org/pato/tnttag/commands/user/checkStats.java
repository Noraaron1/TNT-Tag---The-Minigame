package org.pato.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.Permissions;

public class checkStats extends AbstractTagCommands {

	public checkStats() {
		super("checkstats", "Check your stats", null, new Permissions().checkStats, true, "stats");
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
	    int money = getPlayerData().getInt(player.getName() + ".money"),
	    	tags = getPlayerData().getInt(player.getName() + ".tags"),
	    	taggeds = getPlayerData().getInt(player.getName() + ".taggeds"),
	    	wins = getPlayerData().getInt(player.getName() + ".wins");
		
		sendMessage(sender, "Coins: " + money);
	    sendMessage(sender, "Players Tagged: " + tags);
	    sendMessage(sender, "Times Tagged: " + taggeds);
	    sendMessage(sender, "Wins: " + wins);
	}

}
