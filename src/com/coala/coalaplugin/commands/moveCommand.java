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
	protected static final int MAP_DEFAULT_HEIGHT = 0;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§c플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("§c관리자만 사용 가능합니다.");
			return false;
		} else if(args.length != 1) {
			sender.sendMessage("§c사용법: /move <이동할월드이름>");
			return false;
		} else {
			World world;
			Player player = (Player) sender;
			File file = new File(Bukkit.getWorldContainer()+"\\"+args[0]+"\\level.dat");
			
			if(file.exists()) { // world folder exist
				world = WorldCreator.name(args[0]).createWorld(); // load world
				
				ConfigWorld.setWorld(world); // readJson

				if(args[0].equals(player.getWorld().getName())) {
					for(Player p : Bukkit.getOnlinePlayers()) {					
						p.teleport(world.getSpawnLocation());
					}
					Bukkit.broadcastMessage("관리자에 의해 세계의 생성 위치로 이동하였습니다.");
					return true;
				} else {
					world.setTime(6000L);
					world.setDifficulty(Difficulty.PEACEFUL);
					
					world.setAutoSave(false);
					world.setGameRuleValue("keepInventory", "true");
					world.setGameRuleValue("doDaylightCycle", "false");
					world.setGameRuleValue("doWeatherCycle", "false");
					world.setGameRuleValue("doMobSpawning", "false");
					world.setGameRuleValue("doFireTick", "false");
					world.setGameRuleValue("doTileDrops", "false");
					world.setGameRuleValue("doMobLoot", "false");
					world.setGameRuleValue("mobGriefing", "false");
					world.setGameRuleValue("naturalRegeneration", "false");
					world.getLivingEntities().forEach(LivingEntity::remove);
					
					for(Player p : Bukkit.getOnlinePlayers()) {					
						p.teleport(world.getSpawnLocation());
					}
					Bukkit.broadcastMessage("관리자에 의해 "+world.getName()+" 세계로 이동되었습니다.");
					return true;
				}
			} else { // world folder not exist
				sender.sendMessage("맵이 존재하지 않습니다.");
				
				return false;
				
//				sender.sendMessage("맵이 존재하지 않습니다. 새로운 맵을 생성합니다.");
//				world = WorldCreator.name(args[0]).type(WorldType.FLAT).environment(World.Environment.NORMAL).generator(new ChunkGenerator()
//				{
//					@Override
//					public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
//					{
//						ChunkData cd = Bukkit.createChunkData(world);
//						
//						return cd;
//					}
//					@Override
//					public Location getFixedSpawnLocation(World world, Random random)
//					{
//						return new Location(world, 0, 1, 0);
//					}
//				}).createWorld();
//				
//				int minX = -25;
//				int maxX = 25;
//				int minZ = -25;
//				int maxZ = 25;
//				int minY = 0;
//				int maxY = 10;
//				
//				for(int i = minX ; i <= maxX; i++) {
//					for(int j = minZ ; j <= maxZ; j++) {
//						if(minX < i && i < maxX && minZ < j && j < maxZ) {
//							if(i == 0 || j == 0) {
//								world.getBlockAt(i, minY, j).setType(Material.STONE);
//							} else if(i > 0 && j > 0) {
//								world.getBlockAt(i, minY, j).setType(Material.BRICK);
//							} else if(i > 0 && j < 0) {
//								world.getBlockAt(i, minY, j).setType(Material.ICE);
//							} else if(i < 0 && j > 0) {
//								world.getBlockAt(i, minY, j).setType(Material.SNOW_BLOCK);
//							} else if(i < 0 && j < 0) {
//								world.getBlockAt(i, minY, j).setType(Material.GRASS);
//							}
//						} else {
//							for(int k = minY; k <= maxY; k++) {
//								world.getBlockAt(i, k, j).setType(Material.BARRIER);
//							}
//						}
//					}
//				}

			}
		}
		
		


		
		
		
		
		
		
		
//		World lobby = Bukkit.getServer().getWorld("world");
//		Location lobbyspawn = lobby.getSpawnLocation();
		
//		if(args[0].equals("list")) {
//			List<String> list = new ArrayList<String>();
//			for(World w : Bukkit.getWorlds()) {
//				list.add(w.getName());
//			}
//			player.sendMessage(""+list);
//			return true;
//		}
		
//		if(args[0].equals("unload")) {
//			if(!(player.getWorld().getName().equals("world") || player.getWorld().getName().equals("world_nether") || player.getWorld().getName().equals("world_the_end"))) {
//				for(Player p : Bukkit.getOnlinePlayers()) {					
//					p.teleport(lobbyspawn);
//				}
//			}
//			for(World w : Bukkit.getWorlds()) {
//				if(!(w.equals("world") || w.equals("world_nether") || w.equals("world_the_end"))) {
//					Bukkit.unloadWorld(w.getName(), true);
//				}
//			}
//			Bukkit.broadcastMessage("추가된 맵들을 삭제하였습니다.");
//			return true;
//		}

	}
}
