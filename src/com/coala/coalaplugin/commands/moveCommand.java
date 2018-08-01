package com.coala.coalaplugin.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.coala.coalaplugin.Main;

public class moveCommand implements CommandExecutor{
	public Main pl;
	
	public moveCommand(Main instance)
	{
		this.pl = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /move <월드이름>");
			return false;
		} else {
			World world;
			//Player player = (Player) sender;
			File file = new File(Bukkit.getWorldContainer()+"\\"+args[0], "region");
			
			if(file.exists()) { // world folder exist
				world = WorldCreator.name(args[0]).createWorld(); // load world

				world.setDifficulty(Difficulty.PEACEFUL);
				Bukkit.getLogger().info("게임 난이도를 평화로움으로 설정했습니다");
				world.setTime(1000);
				Bukkit.getLogger().info("시간이 1000(으)로 설정되었습니다");
				world.setGameRuleValue("doDaylightCycle", "false");
				Bukkit.getLogger().info("이제 해의 위치가 변하지 않습니다");
				world.setStorm(false);
				Bukkit.getLogger().info("맑은 날씨로 변합니다");
				world.setGameRuleValue("doWeatherCycle", "false");
				Bukkit.getLogger().info("이제 날씨가 변하지 않습니다");
				this.pl.isPreventPK = true;
				Bukkit.getLogger().info("이제 플레이어와 플레이어 간의 피해가 적용되지 않습니다");
				this.pl.isPreventExplode = true;
				Bukkit.getLogger().info("이제 폭발로 인해 지형이 변경되지 않습니다");
				
				Bukkit.broadcastMessage(args[0]+" 월드로 이동합니다. 잠시 기다려주세요...");
				for(Player p : Bukkit.getOnlinePlayers()) {					
					p.teleport(world.getSpawnLocation());
				}
				Bukkit.broadcastMessage(world.getName()+" 월드로 이동되었습니다.");
				return true;
				
//				if(args[0].equals(player.getWorld().getName())) { // 현재 위치한 월드로 이동하려고 하면
//					Bukkit.broadcastMessage("§c이미 "+args[0]+" 월드에 위치해 있습니다.");
//					return false;
//				} else {
//					Bukkit.broadcastMessage(args[0]+" 월드로 이동합니다. 잠시 기다려주세요...");
//					for(Player p : Bukkit.getOnlinePlayers()) {					
//						p.teleport(world.getSpawnLocation());
//					}
//					Bukkit.broadcastMessage(world.getName()+" 월드로 이동되었습니다.");
//					return true;
//				}
			} else { // world folder not exist
				Bukkit.broadcastMessage("§c"+args[0]+" 월드가 존재하지 않습니다. 월드 생성 후 이동해주세요.");
				return false;
			}
		}
	}
}