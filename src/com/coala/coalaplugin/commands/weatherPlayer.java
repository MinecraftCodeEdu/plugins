package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class weatherPlayer implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Example : /weatherPlayer <clear|rain|thunder> [player]
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /weatherPlayer <clear|rain|thunder> [player]");
			return false;
		}

		if (args.length == 1) {
			Bukkit.dispatchCommand(sender, "weather "+args[0]);
			
			return true;
		} else {	
			Player player = Bukkit.getPlayer(args[1]);
			if (player != null) {
				if(player.isOp()) {
					player.performCommand("weather "+args[0]);
				} else {
					player.setOp(true);
					player.performCommand("weather "+args[0]);
					player.setOp(false);
				}	
				
				return true;
			} else {
				sender.sendMessage("§c" +args[1] + " 플레이어가 존재하지 않습니다.");
				
				return false;
			}
		}
	}
}