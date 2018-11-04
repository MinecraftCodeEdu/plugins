package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.coala.coalaplugin.Main;

// Deprecated Command
public class freezeCommand implements CommandExecutor{
	public Main pl;
	
	public freezeCommand(Main instance)
	{
		this.pl = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /freeze <true|false>");
			return false;
		}
		
		if(args[0].equals("true")) {
			this.pl.isPlayerFreeze = true;
			for(Player p : Bukkit.getOnlinePlayers()) {					
				if(!p.isOp()) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 250));
					p.setWalkSpeed(0);
				}
			}
			Bukkit.broadcastMessage("플레이어들의 몸이 얼어붙습니다.");
			return true;
		} else if (args[0].equals("false")) {
			this.pl.isPlayerFreeze = false;
			for(Player p : Bukkit.getOnlinePlayers()) {					
				if(!p.isOp()) {
					p.removePotionEffect(PotionEffectType.JUMP);
					p.setWalkSpeed(0.2f);
				}
			}
			Bukkit.broadcastMessage("얼음이 녹아 움직일 수 있습니다.");
			return true;
		} else {
			sender.sendMessage("§c사용법: /freeze <true|false>");
			return false;
		}
	}
}
