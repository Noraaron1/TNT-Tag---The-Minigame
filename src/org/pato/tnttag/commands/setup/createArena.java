package org.pato.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.AbstractTagSetupCommands;
import org.pato.tnttag.util.CreateArenaData;
import org.pato.tnttag.util.Permissions;

public class createArena extends AbstractTagSetupCommands {

	public createArena(){
		super("createarena", "Creates an arena", "<ArenaName>", new Permissions().all, true);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length != 0){
			MessageManager.getInstance().sendInsuficientArgs(sender, "createarena", "<ArenaName>");
			return;
		}
		
		
		String arenaName = args[0];
		
		if (!CreateArenaData.check(player)){
			CreateArenaData.createArena(player.getName(), arenaName);
		}
	}

}
