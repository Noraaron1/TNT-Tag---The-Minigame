package org.pato.tnttag.commands.user;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.Permissions;

public class transfer extends AbstractTagCommands {

	int amount;
	
	public transfer(){
		super("transfer", "Transfer coins to another player.", "coins <player> <amount>", new Permissions().transferCoins, true, "pay");
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 2){
			String playerName = args[1];
			if (getPlayerData().getString(playerName) != null){
				Player player = (Player) sender;
				try { amount = Integer.parseInt(args[2]); } 
				catch (NumberFormatException e) {
					MessageManager.getInstance().sendErrorMessage(sender, "Invalid number!");
					return;
				}
				
				if (getPlayerData().getInt(player.getName() + ".money") >= amount){
					int giverMoney = getPlayerData().getInt(player.getName() + ".money");
					int recieverMoney = getPlayerData().getInt(playerName + ".money");
					
					getPlayerData().set(playerName, recieverMoney + amount);
					getPlayerData().set(playerName, giverMoney - amount);
					sendMessage(sender, ChatColor.GREEN + "Successfully transfered " + amount + " to " + playerName + ".");
				}
			} else { MessageManager.getInstance().sendErrorMessage(sender, playerName + " does not exist!"); }
		} else { MessageManager.getInstance().sendInsuficientArgs(sender, "transfer", "coins <player> <amount>"); }
	}

}
