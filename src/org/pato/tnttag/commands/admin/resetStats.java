package org.pato.tnttag.commands.admin;

import org.bukkit.command.CommandSender;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.Permissions;

public class resetStats extends AbstractTagAdminCommands {

	public resetStats() {
		super("resetstats", "Reset stats for a player", "<player>", new Permissions().resetStats, true);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
