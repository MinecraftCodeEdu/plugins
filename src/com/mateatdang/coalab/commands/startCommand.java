package com.mateatdang.coalab.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
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

import com.mateatdang.coalab.Coalab;


public class startCommand implements CommandExecutor{
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();
	public static Objective objective = board.registerNewObjective("점수", "dummy");
	
	public static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	public static BossBar bossbar = Bukkit.createBossBar("남은 시간", BarColor.YELLOW, BarStyle.SOLID);
	
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
		
		Player player = (Player) sender;
		World world = player.getWorld();
		
		for(Player online : Bukkit.getOnlinePlayers()){
			  online.teleport(player.getLocation());
		}
		
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setStorm(false);
		world.setTime(1000);
		world.setWeatherDuration(10000000);
		
		Bukkit.broadcastMessage("게임 시작!");
		
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
		
		int numOfMaterial = 50;
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

		//world.spawnCreature(player.getLocation(), CreatureType.CHICKEN);

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
		
		scheduler.scheduleSyncRepeatingTask(Coalab.getInstance(), new Runnable() {
			double gameTime = 60;
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
