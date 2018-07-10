package com.coala.coalaplugin.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class comeCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		}
		
		Player player = (Player) sender;
		Random random = new Random();
		double randomX = random.nextDouble()*6-3;
		double randomZ = random.nextDouble()*6-3;
		
		// 명령어를 사용한 사람의 주변으로 소환
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!p.equals(player))
				p.teleport(player.getLocation().add(randomX,0,randomZ));
		}
		
		Bukkit.broadcastMessage(player.getName()+"님이 소환 명령어를 사용하였습니다.");
		
		return true;
	}
}
