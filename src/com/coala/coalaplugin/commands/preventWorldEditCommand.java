package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.coala.coalaplugin.Main;

public class preventWorldEditCommand implements CommandExecutor{
	public Main pl;
	
	public preventWorldEditCommand(Main instance)
	{
		this.pl = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /preventWorldEdit <true|false>");
			return false;
		}
		
		if(args[0].equals("true")) {
			this.pl.isPreventWorldEdit = true;
			Bukkit.broadcastMessage("플레이어의 월드 변경을 금지합니다.");
			return true;
		} else if (args[0].equals("false")) {
			this.pl.isPreventWorldEdit = false;
			Bukkit.broadcastMessage("플레이어의 월드 변경을 허용합니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /preventWorldEdit <true|false>");
			return false;
		}
	}

}
