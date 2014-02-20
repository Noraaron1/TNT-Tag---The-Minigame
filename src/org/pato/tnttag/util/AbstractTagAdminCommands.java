package org.pato.tnttag.util;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.pato.tnttag.managers.MessageManager;
import org.pato.tnttag.managers.FileManager;

public abstract class AbstractTagAdminCommands {
	
	private String name, desc, args;
	private Permission perm;
	private boolean useperms;
	
	public AbstractTagAdminCommands(String name, String desc, String args, Permission perm, boolean useperms) {
		this.name = name;
		this.desc = desc;
		this.args = args;
		this.perm = perm;
		this.useperms = useperms;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String getArgs() {
		return args;
	}

	public Permission getPermission() {
		return perm;
	}
	
	public void sendMessage(CommandSender sender, String s){
		MessageManager.getInstance().sendMessage(sender, s);
	}
	
	public FileConfiguration getPlayerData(){
		return FileManager.getInstance().getPlayerData();
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);

	public boolean usePermissions() {
		return useperms;
	}

}
