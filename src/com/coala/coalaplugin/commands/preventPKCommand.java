package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.coala.coalaplugin.Main;

public class preventPKCommand implements CommandExecutor{
	public Main pl;
	
	public preventPKCommand(Main instance)
	{
		this.pl = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /preventPK <true|false>");
			return false;
		}
		
		if(args[0].equals("true")) {
			this.pl.isPreventPK = true;
			Bukkit.broadcastMessage("이제 플레이어와 플레이어 간의 피해가 적용되지 않습니다.");
			return true;
		} else if (args[0].equals("false")) {
			this.pl.isPreventPK = false;
			Bukkit.broadcastMessage("이제 플레이어와 플레이어 간의 피해가 적용됩니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /playerInvincible <true|false>");
			return false;
		}
	}

}
