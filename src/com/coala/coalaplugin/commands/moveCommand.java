package com.coala.coalaplugin.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import com.coala.coalaplugin.data.ConfigWorld;

public class moveCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§c플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 1) {
			sender.sendMessage("§c사용법: /move <월드이름>");
			return false;
		} else {
			World world;
			Player player = (Player) sender;
			File file = new File(Bukkit.getWorldContainer()+"\\"+args[0], "region");
			
			if(file.exists()) { // world folder exist
				world = WorldCreator.name(args[0]).createWorld(); // load world

				if(args[0].equals(player.getWorld().getName())) { // 현재 위치한 월드로 이동하려고 하면
					Bukkit.broadcastMessage("§c이미 "+args[0]+" 월드에 위치해 있습니다.");
					return false;
				} else {
					Bukkit.broadcastMessage(args[0]+" 월드로 이동합니다. 잠시 기다려주세요...");
					for(Player p : Bukkit.getOnlinePlayers()) {					
						p.teleport(world.getSpawnLocation());
					}
					Bukkit.broadcastMessage(world.getName()+" 월드로 이동되었습니다.");
					return true;
				}
			} else { // world folder not exist
				player.sendMessage("§c"+args[0]+" 월드가 존재하지 않습니다. 월드 생성 후 이동해주세요.");
				return false;
			}
		}
	}
}