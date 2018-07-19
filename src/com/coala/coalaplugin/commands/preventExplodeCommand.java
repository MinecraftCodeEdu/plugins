package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.coala.coalaplugin.Main;

public class preventExplodeCommand implements CommandExecutor{
	public Main pl;
	
	public preventExplodeCommand(Main instance)
	{
		this.pl = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /preventExplode <true|false>");
			return false;
		}
		
		if(args[0].equals("true")) {
			this.pl.isPreventExplode = true;
			Bukkit.broadcastMessage("이제 폭발로 인해 지형이 변경되지 않습니다.");
			return true;
		} else if (args[0].equals("false")) {
			this.pl.isPreventExplode = false;
			Bukkit.broadcastMessage("이제 폭발로 인한 지형 변경이 적용됩니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /preventExplode <true|false>");
			return false;
		}
	}

}
