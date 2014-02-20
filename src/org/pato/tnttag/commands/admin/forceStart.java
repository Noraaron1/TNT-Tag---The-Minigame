package org.pato.tnttag.commands.admin;

import org.bukkit.command.CommandSender;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.Permissions;

public class forceStart extends AbstractTagAdminCommands {
	
	public forceStart() {
		super("forcestart", "Force start a game", null, new Permissions().forceStart, true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
