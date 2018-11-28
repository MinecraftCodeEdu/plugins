package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AllowFlight implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용할 수 있는 명령어입니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /allowflight <true|false>");
			return false;
			// Need second argument
		}
		
		if(args[0].toLowerCase().equals("true") || args[0].equals("1")) {
			for(Player p : Bukkit.getOnlinePlayers()) {		
				if(!p.isOp()) {
					p.setAllowFlight(true);
				}
			}
			Bukkit.broadcastMessage("이제부터 모든 플레이어들의 비행이 허용됩니다.");
			return true;
		} else if (args[0].toLowerCase().equals("false") || args[0].equals("0")){
			for(Player p : Bukkit.getOnlinePlayers()) {					
				if(!p.isOp()) {
					p.setAllowFlight(false);
				}
			}
			Bukkit.broadcastMessage("이제부터 모든 플레이어들의 비행이 제한됩니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /allowflight <true|false>");
			return false;
			// Need second argument
		}
	}
}