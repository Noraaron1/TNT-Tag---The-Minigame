package org.pato.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.util.AbstractTagSetupCommands;
import org.pato.tnttag.util.CreateArenaData;
import org.pato.tnttag.util.Permissions;

public class setLobby extends AbstractTagSetupCommands {
	
	public setLobby(){
		super("setlobby", "Sets the TNT Tag lobby point.", null, new Permissions().setLobby, true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setLobbyLocation(player);
	}

}
