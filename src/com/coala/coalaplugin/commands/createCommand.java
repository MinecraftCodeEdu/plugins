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

public class createCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§c플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 2) {
			sender.sendMessage("§c사용법: /create <월드이름> <normal|flat|largeBiomes|amplified>");
			return false;
		} else {
			World world;
			Player player = (Player) sender;
			File file = new File(Bukkit.getWorldContainer(), args[0]);
			
			if(file.exists()) {
				player.sendMessage("§c"+args[0]+" 월드가 이미 존재합니다.");
				return false;
			} else {
				if(args[1].equalsIgnoreCase("normal") || args[1].equalsIgnoreCase("amplified") || args[1].equalsIgnoreCase("largebiomes") || args[1].equalsIgnoreCase("flat")) {
					switch(args[1].toLowerCase()) {
					case "normal":
						player.sendMessage("기본 타입의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.NORMAL).environment(World.Environment.NORMAL).createWorld();
						break;
					case "amplified":
						player.sendMessage("지형이 높은 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.AMPLIFIED).environment(World.Environment.NORMAL).createWorld();
						break;
					case "largebiomes":
						player.sendMessage("넓은 생물군계의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.LARGE_BIOMES).environment(World.Environment.NORMAL).createWorld();
						break;
					case "flat":
						player.sendMessage("평지 타입의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						world = WorldCreator.name(args[0]).type(WorldType.FLAT).environment(World.Environment.NORMAL).createWorld();
						world.setSpawnLocation(0, 4, 0);
						break;
					default:
						player.sendMessage("§c월드 생성 중 오류가 발생했습니다.");
						return false;
					}
				} else {
					player.sendMessage("§c사용법: /create <월드이름> <normal|flat|largeBiomes|amplified>");
					player.sendMessage("§c<normal|flat|largeBiomes|amplified> 4개의 타입 중에서 선택하세요.");
					return false;
				}
				player.sendMessage(args[0]+" 월드가 생성되었습니다.");
				return true;
			}
		}
	}
}