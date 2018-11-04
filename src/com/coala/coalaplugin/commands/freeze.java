package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class freeze implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용할 수 있는 명령어입니다.");
			return false;
		}
		
		if(args.length == 0 || args[0].equals("@a")) {
			for(Player p : Bukkit.getOnlinePlayers()) {					
				if(!p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 250));
					p.setWalkSpeed(0);
				}
			}
			Bukkit.broadcastMessage("이제부터 모든 플레이어들의 몸이 얼어붙습니다.");
			return true;
		} else {
			Player p = Bukkit.getPlayerExact(args[0]);
			if (p == null) {
				sender.sendMessage("§c"+args[0]+" 플레이어가 존재하지 않습니다.");
			} else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 250));
				p.setWalkSpeed(0);
				Bukkit.broadcastMessage("이제부터 "+args[0]+" 플레이어의 몸이 얼어붙습니다.");
			}
			return true;
		}
	}
}