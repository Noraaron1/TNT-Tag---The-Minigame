package org.pato.tnttag.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.pato.tnttag.managers.ArenaManager;
import org.pato.tnttag.managers.FileManager;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.Arena;
import org.pato.tnttag.util.Permissions;

public class reload extends AbstractTagAdminCommands {
	
	public reload(){
		super("reload", "Reloads the config.", null, new Permissions().reload, true);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		FileManager.getInstance().reloadConfig();
   	 	for (Arena arena : Arena.arenaObjects){
			 arena.sendMessage("There was a reload");
			 ArenaManager.getManager().endArena(arena);
   	 	}
		MessageManager.getInstance().sendMessage(sender, ChatColor.GREEN + "Reloaded plugin");
	}
}
