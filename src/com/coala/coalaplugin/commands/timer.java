package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import com.coala.coalaplugin.Main;

public class timer implements CommandExecutor{
	public static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	public static BossBar bossbar = Bukkit.createBossBar("남은 시간", BarColor.BLUE, BarStyle.SEGMENTED_10);
	double total = 10;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
			// Use Operator Only
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /timer <초>");
			return false;
			// Need second argument
		} else if(!isInteger(args[0])) {
			sender.sendMessage("§c사용법: /timer <초>");
			return false;
			// Check argument is valid integer
		}
		
		total = Integer.parseInt(args[0]);
		
		// Remove previous bossbar and scheduler
		bossbar.removeAll();
		Bukkit.getScheduler().cancelAllTasks();
		
		// Timer scheduler
		scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			int remain = (int)total;
			// Sets the time to 60 seconds
			@Override
			public void run() {
				if (remain >= 1) {
					// loops through all online players and puts them individualized.
					bossbar.setTitle("남은 시간 : " + (int)remain + "초");
					bossbar.setProgress(remain/total);

					// bossbar
					for (Player p : Bukkit.getOnlinePlayers()) {
						 bossbar.addPlayer(p);
					}
					
					if(remain == 10) {
						Bukkit.broadcastMessage("시간이 10초 남았습니다.");
					}
					
					remain--;
				} else {
					bossbar.removeAll();
					Bukkit.getScheduler().cancelAllTasks();

					for(Player p : Bukkit.getOnlinePlayers()) {					
						if(!p.isOp()) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 250));
							p.setWalkSpeed(0);
						}
					}
					Bukkit.broadcastMessage("플레이어들의 몸이 얼어붙습니다.");
				}
			}
		}, 0L, 20L);
		//runs a repeating task every 20 ticks
		// 20 ticks = 1 second
		
		return false;
	}
	
	public static boolean isInteger(String str) {	
	    if (str == null) {
	        return false;
	    }
	    if (str.isEmpty()) {
	        return false;
	    }
	    
		int length = str.length();
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}

}
