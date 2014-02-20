package org.pato.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.Permissions;

public class leave extends AbstractTagCommands{

	public leave() {
		super("leave", "Leaves TNT Tag", null, new Permissions().leave, true, "l");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		ArenaManager.getManager().removePlayer(player);
		
	}

}
