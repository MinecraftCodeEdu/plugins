package com.coala.coalaplugin.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class rabbitCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /rabbit <플레이어>");
			return false;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage("§c"+args[0]+" 플레이어가 존재하지 않습니다.");
			return false;
		}
		
		int width = 50;
		int num = 100;
		double randomX;
		double randomZ;
		Random random = new Random();
		for(int i = 0; i < num; i++) {
			randomX = random.nextDouble()*width-width/2;
			randomZ = random.nextDouble()*width-width/2;

			player.getWorld().spawnEntity(player.getLocation().add(randomX,0,randomZ), EntityType.RABBIT);
		}
		
		
		Bukkit.broadcastMessage("토끼를 뿌렸습니다.");
		
		return true;
	}
}