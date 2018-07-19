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

		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /come <플레이어>");
			return false;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage("§c"+args[0]+" 플레이어가 존재하지 않습니다.");
			return false;
		}
		
		Random random = new Random();
		double randomX = random.nextDouble()*6-3;
		double randomZ = random.nextDouble()*6-3;
		
		// 해당 플레이어의 주변으로 소환
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!p.equals(player))
				p.teleport(player.getLocation().add(randomX,0,randomZ));
		}
		
		Bukkit.broadcastMessage(player.getName()+"의 위치로 소환되었습니다.");
		
		return true;
	}
}
