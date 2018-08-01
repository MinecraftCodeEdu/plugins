package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.coala.coalaplugin.Main;

public class mobFreezeCommand implements CommandExecutor{
	public Main pl;
	
	public mobFreezeCommand(Main instance)
	{
		this.pl = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /mobFreeze <플레이어>");
			return false;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage("§c"+args[0]+" 플레이어가 존재하지 않습니다.");
			return false;
		}
		
		if(!this.pl.isMobFreeze) {
			this.pl.isMobFreeze = true;

			for(LivingEntity e : player.getWorld().getLivingEntities()) {
				if(!(e instanceof Player)) {
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 30));
					e.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, -10000000, 128));
				}
			}
			Bukkit.broadcastMessage("몹들의 몸이 얼어붙습니다.");
			return true;
		} else if (this.pl.isMobFreeze) {
			this.pl.isMobFreeze = false;
			
			for(LivingEntity e : player.getWorld().getLivingEntities()) {
				if(!(e instanceof Player)) {
					e.removePotionEffect(PotionEffectType.SLOW);
					e.removePotionEffect(PotionEffectType.JUMP);
				}
			}
			
			Bukkit.broadcastMessage("얼음이 녹아 몹들이 움직일 수 있습니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /mobFreeze <플레이어>");
			return false;
		}
	}
}