package org.pato.tnttag.commands.user;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.Permissions;

public class coins extends AbstractTagCommands {
	
	public coins() {
		super("coins", "Check your coins", "", new Permissions().checkCoins, true, "money");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		    Player player = (Player)sender;
		    sendMessage(sender, "Coins: " + ChatColor.GOLD + getPlayerData().getString(player.getName() + "money"));
		    sendMessage(sender, "Use /tag transfer coins <player> <amount> to transfer coins to another player.");
	}

}
