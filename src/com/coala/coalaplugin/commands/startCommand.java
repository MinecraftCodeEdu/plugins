package com.coala.coalaplugin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.coala.coalaplugin.Main;

public class startCommand implements CommandExecutor{
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();
	public static Objective objective = board.registerNewObjective("점수", "dummy");
	public static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	public static BossBar bossbar = Bukkit.createBossBar("남은 시간", BarColor.YELLOW, BarStyle.SOLID);
	
	public static Player tagger;
	
	HashMap<String, Integer> playerscores = new HashMap<String, Integer>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("플레이어만 사용 가능합니다.");
			return false;
		} else if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		}
		if(args.length < 1) {
			sender.sendMessage("§c사용법: /start <게임이름>");
			return false;
		}
		
		Player player = (Player) sender;
		World world = player.getWorld();
		
		if((args[0].equalsIgnoreCase("tresure") || args[0].equalsIgnoreCase("보물찾기"))) {
			if(!world.getName().equals("world")) {
				sender.sendMessage("§c기본월드에서만 사용할 수 있습니다.");
				return false;
			}
			
			for(Player online : Bukkit.getOnlinePlayers()){
				  online.teleport(player.getLocation());
			}
			
			Bukkit.broadcastMessage("보물 찾기 게임 시작!");
			
			Random random = new Random();

			int x,y,z;

			//remove previous game
			bossbar.removeAll();
			scheduler.cancelAllTasks();	
			List<Entity> entList = world.getEntities();
	        for(Entity current : entList){//loop through the list
	            if (current instanceof Item){//make sure we aren't deleting mobs/players
		            current.remove();//remove it
		            
		            x = current.getLocation().getBlockX();
		            y = current.getLocation().getBlockY();
		            z = current.getLocation().getBlockZ();
		            
		    		for(int j = -2; j <= 2 ; j++) {
		    			for(int k = -2; k <= 2 ; k++) {
							world.getBlockAt(x+j, y-1, z+k).setType(Material.AIR);
						}
					}
		        }
			}
			
			Material[] material = {Material.DIAMOND, Material.GOLD_INGOT, Material.EMERALD}; 
			
			int numOfMaterial = 10;
			int width = 400;
			int height = 1;
			
			for(Material mat : material) {
				for(int i = 0; i < numOfMaterial; i++) {
					x = player.getLocation().getBlockX() + random.nextInt(width) - width/2;
					y = player.getLocation().getBlockY() + height;
					z = player.getLocation().getBlockZ() + random.nextInt(width) - width/2;
		    		for(int j = -1; j <= 1 ; j++) {
		    			for(int k = -1; k <= 1 ; k++) {
							world.getBlockAt(x+j, player.getLocation().getBlockY(), z+k).setType(Material.GOLD_BLOCK);
						}
					}
					Location loc = new Location(player.getWorld(),x,y,z);
					world.dropItemNaturally(loc, new ItemStack(mat, 1));
				}
			}
			
			//scoreboard initialize
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);

			for(Player online : Bukkit.getOnlinePlayers()){
				  Score score = objective.getScore(online);
				  score.setScore(0); //Example
			}
				 
			for(Player online : Bukkit.getOnlinePlayers()){
				online.setScoreboard(board);
			}
			
			//bossbar
			for (Player p : Bukkit.getOnlinePlayers()) {
				 bossbar.addPlayer(p);
			}
			
			scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				double gameTime = 30;
				double time = gameTime;
				// Sets the time to 60 seconds
				@Override
				public void run() {
					if (time >= 1) {
						//loops through all online players and puts them individualized.
						bossbar.setTitle("남은 시간 : " + (int)time + "초");
						bossbar.setProgress(time/gameTime);

						time--;
					} else {
						bossbar.removeAll();
						Bukkit.getScheduler().cancelAllTasks();
				        Bukkit.broadcastMessage("§e"+ bestPlayer() +"§r님이 게임에서 승리하였습니다.");
						
						board.clearSlot(DisplaySlot.SIDEBAR);
						
				        List<Entity> entList = world.getEntities();//get all entities in the world
				        
				        for(Entity current : entList){//loop through the list
				            if (current instanceof Item){//make sure we aren't deleting mobs/players
					            current.remove();//remove it
					            
					            int x,y,z;
					            x = current.getLocation().getBlockX();
					            y = current.getLocation().getBlockY();
					            z = current.getLocation().getBlockZ();
					            //
					    		for(int j = -2; j <= 2 ; j++) {
					    			for(int k = -2; k <= 2 ; k++) {
										world.getBlockAt(x+j, y-1, z+k).setType(Material.AIR);
									}
								}
					    		
					        }
						}
				        
					}
				}
			}, 0L, 20L);
			//runs a repeating task every 20 ticks
			// 20 ticks = 1 second
			
			return true;
		} else if((args[0].equalsIgnoreCase("compass") || args[0].equalsIgnoreCase("동서남북"))){
			if(!world.getName().equals("world")) {
				sender.sendMessage("§c기본월드에서만 사용할 수 있습니다.");
				return false;
			}
			
			Location playerloc = player.getLocation();
			int x = playerloc.getBlockX();
			int y = playerloc.getBlockY();
			int z = playerloc.getBlockZ();
			
			world.getBlockAt(x, y-1, z).setType(Material.GOLD_BLOCK); // Standing Block
			
			int diameter = 30;
			int height = 50;
			
			for(int i = 0 ; i < height ; i++) {
				world.getBlockAt(x+diameter, y+i, z).setTypeIdAndData(35, (byte) 14, true);
				world.getBlockAt(x-diameter, y+i, z).setTypeIdAndData(35, (byte) 1, true);
				world.getBlockAt(x, y+i, z+diameter).setTypeIdAndData(35, (byte) 4, true);
				world.getBlockAt(x, y+i, z-diameter).setTypeIdAndData(35, (byte) 5, true);
			}
			
			player.sendMessage("§4동§c서§e남§a북 §r기둥을 세웠습니다.");
			
			return true;
		} else if((args[0].equalsIgnoreCase("hideandseek") || args[0].equalsIgnoreCase("숨바꼭질") || args[0].equalsIgnoreCase("술래잡기"))){
			if(!world.getName().equals("1-1")) {
				sender.sendMessage("§c1-1월드에서만 사용할 수 있습니다.");
				return false;
			}
			
			if(args.length != 2) {
				sender.sendMessage("§c사용법: /start hideandseek <술래>");
				return false;
			}
			
			tagger = Bukkit.getPlayer(args[1]);
			
			if(tagger == null) {
				Bukkit.broadcastMessage("§c"+args[1]+"가 존재하지 않습니다.");
				return false;
			}
			
			if((tagger = Bukkit.getPlayer(args[1])) == null) {
//				Bukkit.broadcastMessage("§c"+args[1]+"가 존재하지 않습니다.");
//				return false;
			}
			
			
	
			//tagger = Bukkit.getPlayer(args[1]);
			Bukkit.broadcastMessage("술래는 §e"+tagger.getName()+"§r입니다.");
			Bukkit.broadcastMessage("§e술래"+"§r는 10초동안 움직일 수 없고, §e나머지§r는 숨도록 합니다.");
			
			for(Player online : Bukkit.getOnlinePlayers()){
				  //online.teleport(player.getLocation());
				online.teleport(world.getSpawnLocation());
			}
			
			tagger.teleport(new Location(world, 0, 100, 0));
			tagger.setWalkSpeed((float) 0.4);
			
			Bukkit.broadcastMessage("숨바꼭질 게임 시작!");
			
			//remove previous game
			bossbar.removeAll();
			scheduler.cancelAllTasks();	
			
			//scoreboard initialize
			//objective.setDisplaySlot(DisplaySlot.SIDEBAR);

			for(Player online : Bukkit.getOnlinePlayers()){
				  Score score = objective.getScore(online);
				  score.setScore(0); //Example
			}
				 
			for(Player online : Bukkit.getOnlinePlayers()){
				online.setScoreboard(board);
			}
			
			//bossbar
			for (Player p : Bukkit.getOnlinePlayers()) {
				 bossbar.addPlayer(p);
			}
			
			scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				double gameTime = 180;
				double startTime = 10;
				double time = gameTime;
				// Sets the time to 60 seconds
				@Override
				public void run() {
					if (time >= 1) {
						//loops through all online players and puts them individualized.
						bossbar.setTitle("남은 시간 : " + (int)time + "초");
						bossbar.setProgress(time/gameTime);
						
						if(time == gameTime-startTime) {
							Bukkit.broadcastMessage("§e술래"+"§r는 §c감옥§r을 만들고 사람들을 잡아 §c감옥§r 안에 소환하세요.");
							tagger.teleport(world.getSpawnLocation());
						}

						time--;
					} else { // time end
						bossbar.removeAll();
						Bukkit.getScheduler().cancelAllTasks();
						Bukkit.broadcastMessage("숨바꼭질 게임 끝!");
						tagger.setWalkSpeed((float) 0.2);
				        //Bukkit.broadcastMessage("§e"+ bestPlayer() +"§r님이 게임에서 승리하였습니다.");
						
						board.clearSlot(DisplaySlot.SIDEBAR);			        
					}
				}
			}, 0L, 20L);
			//runs a repeating task every 20 ticks
			// 20 ticks = 1 second
			
			return true;
		}

		return true;
	}
	
    public String bestPlayer() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            playerscores.put(all.getName(), objective.getScore(all).getScore());
        }
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(Integer all : playerscores.values()) {
            values.add(all);
        }
        Collections.sort(values);
        Collections.reverse(values);
        return getKey(values.get(0), playerscores);
    }
    
    public String getKey(Integer value, HashMap<String, Integer> hs) {
        String key = "";
        for(String keys : hs.keySet()) {
            if(hs.get(keys) == value) {
                key = keys;
            }
        }
        return key;
    }
    
   
    
}
