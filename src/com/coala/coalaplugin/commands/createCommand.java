package com.coala.coalaplugin.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class createCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length < 2) {
			sender.sendMessage("§c사용법: /create <월드이름> <normal|flat|largeBiomes|amplified>");
			return false;
		} else {
			World world;
			//Player player = (Player) sender;
			File file = new File(Bukkit.getWorldContainer(), args[0]);
			
			if(file.exists()) {
				Bukkit.broadcastMessage("§c"+args[0]+" 월드가 이미 존재합니다.");
				return false;
			} else {
				if(args[1].equalsIgnoreCase("normal") || args[1].equalsIgnoreCase("amplified") || args[1].equalsIgnoreCase("largebiomes") || args[1].equalsIgnoreCase("flat")) {
					switch(args[1].toLowerCase()) {
					case "normal":
						sender.sendMessage("기본 타입의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.NORMAL).environment(World.Environment.NORMAL).createWorld();
						break;
					case "amplified":
						sender.sendMessage("지형이 높은 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.AMPLIFIED).environment(World.Environment.NORMAL).createWorld();
						break;
					case "largebiomes":
						sender.sendMessage("넓은 생물군계의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						WorldCreator.name(args[0]).type(WorldType.LARGE_BIOMES).environment(World.Environment.NORMAL).createWorld();
						break;
					case "flat":
						sender.sendMessage("평지 타입의 "+args[0]+" 월드를 생성합니다. 잠시 기다려주세요...");
						world = WorldCreator.name(args[0]).type(WorldType.FLAT).environment(World.Environment.NORMAL).createWorld();
						world.setSpawnLocation(0, 4, 0);
						break;
					default:
						Bukkit.broadcastMessage("§c월드 생성 중 오류가 발생했습니다.");
						return false;
					}
				} else {
					sender.sendMessage("§c사용법: /create <월드이름> <normal|flat|largeBiomes|amplified>");
					sender.sendMessage("§c<normal|flat|largeBiomes|amplified> 4개의 타입 중에서 선택하세요.");
					return false;
				}
				Bukkit.broadcastMessage(args[0]+" 월드가 생성되었습니다.");
				return true;
			}
		}
	}
}