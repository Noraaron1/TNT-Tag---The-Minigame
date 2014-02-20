package org.pato.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.AbstractTagSetupCommands;
import org.pato.tnttag.util.CreateArenaData;
import org.pato.tnttag.util.Permissions;

public class setArenaPoint extends AbstractTagSetupCommands {

	public setArenaPoint() {
		super("setarena", "Sets the Arena point.", null, new Permissions().setArena, true);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setArenaLocation(player);
	}
}
