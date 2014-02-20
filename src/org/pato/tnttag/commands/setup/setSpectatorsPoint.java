package org.pato.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.AbstractTagSetupCommands;
import org.pato.tnttag.util.CreateArenaData;
import org.pato.tnttag.util.Permissions;

public class setSpectatorsPoint extends AbstractTagSetupCommands {
	
	public setSpectatorsPoint() {
		super ("setspec", "Set the spectators point", null, new Permissions().setSpec, true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setSpectatorsLocation(player);
	}

}
