package com.coala.coalaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.coala.coalaplugin.Main;

public class eventCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		}
		if(args.length != 1) {
			sender.sendMessage("§c사용법: /event <on|off>");
			return false;
		}
		
		if(args[0].equals("on")) {
			Main.setEventToggle(true);
			sender.sendMessage("[event] 이벤트 처리가 활성화되었습니다.");
			return true;
		} else if (args[0].equals("off")) {
			Main.setEventToggle(false);
			sender.sendMessage("[event] 이벤트 처리가 비활성화되었습니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /event <on|off>");
			return false;
		}
	}
}
