package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class modeAllCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /modeall <모드>");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
			Bukkit.dispatchCommand(sender, "gamemode 0 @a");
			return true;
		} else if(args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
			Bukkit.dispatchCommand(sender, "gamemode 1 @a");
			return true;
		} else if(args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
			Bukkit.dispatchCommand(sender, "gamemode 2 @a");
			return true;
		} else if(args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
			Bukkit.dispatchCommand(sender, "gamemode 3 @a");
			return true;
		} else {
			sender.sendMessage("§c사용법: /modeall <survival|creative|adventure|spectator>");
			return false;
		}
		
	}
}
