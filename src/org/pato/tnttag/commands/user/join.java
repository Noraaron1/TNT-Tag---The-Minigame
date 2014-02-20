package org.pato.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.Permissions;

public class join extends AbstractTagCommands {

	public join() {
		super("join", "Joins TNT Tag.", "<ArenaName>", new Permissions().join, true, "j");
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if (args.length != 0){
			MessageManager.getInstance().sendInsuficientArgs(sender, "join", "<ArenaName>");
		}
		
		String arenaName = args[0];
		
		ArenaManager.getManager().addPlayers(player, arenaName);
	}
}
	
	