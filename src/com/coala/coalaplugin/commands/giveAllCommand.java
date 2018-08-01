package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class giveAllCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 2) {
			sender.sendMessage("§c사용법: /giveall <아이템> <양>");
			return false;
		}
		
		Bukkit.dispatchCommand(sender, "give @a "+args[0]+" "+args[1]);
		
		return true;
	}
}
