package org.pato.tnttag.managers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pato.tnttag.commands.admin.add;
import org.pato.tnttag.commands.admin.forceEnd;
import org.pato.tnttag.commands.admin.forceStart;
import org.pato.tnttag.commands.admin.reload;
import org.pato.tnttag.commands.admin.remove;
import org.pato.tnttag.commands.admin.resetStats;
import org.pato.tnttag.commands.setup.createArena;
import org.pato.tnttag.commands.setup.setArenaPoint;
import org.pato.tnttag.commands.setup.setLobby;
import org.pato.tnttag.commands.setup.setSpectatorsPoint;
import org.pato.tnttag.commands.user.coins;
import org.pato.tnttag.commands.user.checkStats;
import org.pato.tnttag.commands.user.join;
import org.pato.tnttag.commands.user.leave;
import org.pato.tnttag.commands.user.transfer;
import org.pato.tnttag.util.AbstractTagAdminCommands;
import org.pato.tnttag.util.AbstractTagCommands;
import org.pato.tnttag.util.AbstractTagSetupCommands;
import org.pato.tnttag.util.Permissions;

public class CommandManager implements CommandExecutor {

	private ArrayList<AbstractTagCommands> cmds = new ArrayList<AbstractTagCommands>();
	private ArrayList<AbstractTagAdminCommands> adminCmds = new ArrayList<AbstractTagAdminCommands>();
	private ArrayList<AbstractTagSetupCommands> setupCmds = new ArrayList<AbstractTagSetupCommands>();
	
	public CommandManager() {
		cmds.add(new join());
		cmds.add(new leave());
		cmds.add(new coins());
		cmds.add(new checkStats());
		cmds.add(new transfer());
		
		adminCmds.add(new add());
		adminCmds.add(new remove());
		adminCmds.add(new resetStats());
		adminCmds.add(new forceEnd());
		adminCmds.add(new forceStart());
		adminCmds.add(new reload());
		
		setupCmds.add(new setLobby());
		setupCmds.add(new setSpectatorsPoint());
		setupCmds.add(new setArenaPoint());
		setupCmds.add(new createArena());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player){
			if (cmd.getName().equalsIgnoreCase("tnttag") || cmd.getName().equalsIgnoreCase("tag")) {
				if (args.length == 0){
					showHelp(sender);
					return true;
				}
				
				ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
				a.remove(0);
				
				if (args[0].equalsIgnoreCase("admin")){
					if (args.length != 1){
						for (AbstractTagAdminCommands c : adminCmds) {
							if (c.getName().equalsIgnoreCase(args[1])) {
								if (((c.usePermissions()) ? sender.hasPermission(c.getPermission()) || sender.hasPermission(new Permissions().all) : c.usePermissions() == false )){
									a.remove(0);
									if (args.length != 1){
										try { c.onCommand(sender, a.toArray(new String[a.size()])); }
										catch (Exception e) { sender.sendMessage(ChatColor.RED + "An error has occurred."); e.printStackTrace(); }
										return true;
									} else { MessageManager.getInstance().sendInsuficientArgs(sender, c.getName(), c.getArgs()); }
								} else { MessageManager.getInstance().sendErrorMessage(sender, "No Permission"); return true; }
							} 
						} 
							MessageManager.getInstance().sendErrorMessage(sender, "Invalid Command!");
							return true;
					} else { showAdminHelp(sender); }
					
				} else if (args[0].equalsIgnoreCase("setup")){
					if (args.length != 1){
						for (AbstractTagSetupCommands c : setupCmds) {
							if (c.getName().equalsIgnoreCase(args[1])) {
								if (((c.usePermissions()) ? sender.hasPermission(c.getPermission()) || sender.hasPermission(new Permissions().all) : c.usePermissions() == false )){
									a.remove(0);
									if (args.length != 1){
										try { c.onCommand(sender, a.toArray(new String[a.size()])); }
										catch (Exception e) { sender.sendMessage(ChatColor.RED + "An error has occurred."); e.printStackTrace(); }
										return true;
									} else { MessageManager.getInstance().sendInsuficientArgs(sender, c.getName(), c.getArgs()); }
								} else { MessageManager.getInstance().sendErrorMessage(sender, "No Permission"); return true; }
							}
						}
						MessageManager.getInstance().sendErrorMessage(sender, "Invalid Command!");
						return true;
					} else { showCreateHelp(sender); }
				} else {
					for (AbstractTagCommands c : cmds) {
						if (c.getName().equalsIgnoreCase(args[0]) || c.getAlias().equalsIgnoreCase(args[0])) {
							if (((c.usePermissions()) ? sender.hasPermission(c.getPermission()) || sender.hasPermission(new Permissions().all) : c.usePermissions() == false )){
								if (args.length != 0){
									try { c.onCommand(sender, a.toArray(new String[a.size()])); }
									catch (Exception e) { sender.sendMessage(ChatColor.RED + "An error has occurred."); e.printStackTrace(); }
									return true;
								} else { MessageManager.getInstance().sendInsuficientArgs(sender, c.getName(), c.getArgs()); }
							} else { MessageManager.getInstance().sendErrorMessage(sender, "No Permission"); return true; }
						}
					}
					MessageManager.getInstance().sendErrorMessage(sender, "Invalid Command!");
					return true;
				}
			} else { MessageManager.getInstance().isConsole(sender); }
		}
		return true;
	}
	
	public void showHelp(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "=================== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag Help " + ChatColor.AQUA + "===================");
		
		for (AbstractTagCommands c : cmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag " + c.getName() + ((c.getArgs() == null) ? " " : " " + c.getArgs() + " ")  + ChatColor.DARK_AQUA + c.getDescription());
		}
		
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}
	
	public void showAdminHelp(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "================ " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag  Admin Help " + ChatColor.AQUA + "================");
		
		for (AbstractTagAdminCommands c : adminCmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag admin " + c.getName() + ((c.getArgs() == null) ? " " : " " + c.getArgs() + " ")  + ChatColor.DARK_AQUA + c.getDescription());
		}
		
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}
	
	public void showCreateHelp(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "=============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag Setup Help " + ChatColor.AQUA + "==================");
		
		for (AbstractTagSetupCommands c : setupCmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag setup " + c.getName() + ((c.getArgs() == null) ? " " : " " + c.getArgs() + " ")  + ChatColor.DARK_AQUA + c.getDescription());
		}
		
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}
}