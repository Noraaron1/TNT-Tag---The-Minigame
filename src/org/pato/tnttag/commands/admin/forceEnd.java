package org.pato.tnttag.commands.admin;

import org.bukkit.command.CommandSender;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.Permissions;

public class forceEnd extends AbstractTagAdminCommands {

	public forceEnd() {
		super("forceend", "Force end a game", null, new Permissions().forceStart, true);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
