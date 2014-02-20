package org.pato.tnttag.commands.admin;

import org.bukkit.command.CommandSender;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.Permissions;

public class remove extends AbstractTagAdminCommands {

	int amount;
	
	public remove() {
		super("remove", "remove coins, wins, tags, or taggeds to a player.", "<coins|wins|tags|taggeds> <player>", new Permissions().remove, true);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length != 0 && args.length != 1){
			if (getPlayerData().getString(args[0]) != null){
				String player = args[0];
				try { amount = Integer.parseInt(args[1]); } 
				catch (NumberFormatException e) {
					MessageManager.getInstance().sendErrorMessage(sender, "Invalid number.");
					return;
				}
				switch (args[1]){
				case "coins":
					int coins = getPlayerData().getInt(player + ".money");
					if (!(amount > coins)){
						getPlayerData().set(player + ".money", coins - amount);
						MessageManager.getInstance().sendMessage(sender, player + " now has " +  getPlayerData().getInt(player + ".money") + " coins.");
					} else { MessageManager.getInstance().sendErrorMessage(sender, "Amount (" + amount + ") is bigger than " + player + "'s coins (" + coins + ")"); }
					break;
				case "wins":
					int wins = getPlayerData().getInt(player + ".wins");
					if (!(amount > wins)){
						getPlayerData().set(player + ".wins", wins - amount);
						MessageManager.getInstance().sendMessage(sender, player + " now has " +  getPlayerData().getInt(player + ".wins") + " wins.");
					} else { MessageManager.getInstance().sendErrorMessage(sender, "Amount (" + amount + ") is bigger than " + player + "'s wins (" + wins + ")"); }
					break;	
				case "tags":
					int tags = getPlayerData().getInt(player + ".tags");
					if (!(amount > tags)){
						getPlayerData().set(player + ".tags", tags - amount);
						MessageManager.getInstance().sendMessage(sender, player + " now has " +  getPlayerData().getInt(player + ".tags") + " tags.");
					} else { MessageManager.getInstance().sendErrorMessage(sender, "Amount (" + amount + ") is bigger than " + player + "'s tags (" + tags + ")"); }
					break;	
				case "taggeds":
					int taggeds = getPlayerData().getInt(player + ".taggeds");
					if (!(amount > taggeds)){
						getPlayerData().set(player + ".taggeds", taggeds - amount);
						MessageManager.getInstance().sendMessage(sender, player + " now has " +  getPlayerData().getInt(player + ".taggeds") + " taggeds.");
					} else { MessageManager.getInstance().sendErrorMessage(sender, "Amount (" + amount + ") is bigger than " + player + "'s coins (" + taggeds + ")"); }
					break;	
				default:
					MessageManager.getInstance().sendInvalidArgs(sender, "add", "<coins|wins|tags|taggeds> <player>");
					break;
				}
			} else { MessageManager.getInstance().sendErrorMessage(sender, "Player " + args[0] + "could not be find."); }
		} else { MessageManager.getInstance().sendInsuficientArgs(sender, "add", "<coins|wins|tags|taggeds> <player>"); }
	}
}
