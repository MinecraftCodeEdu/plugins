package com.mateatdang.goldmine.commands;

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

import com.mateatdang.goldmine.GoldMine;

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
		
//		for(Entity e : world.getEntities()) {
//		     if(e instanceof Spider || e instanceof Skeleton || e instanceof Creeper || e instanceof Slime || e instanceof Zombie)
//		          e.remove();
//		}
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setStorm(false);
		world.setTime(1000);
		world.setWeatherDuration(10000000);
		
		Bukkit.broadcastMessage("게임 시작!");
		
		Random random = new Random();
		int numOfMaterial = 1000;
		double x,y,z;
		//ItemStack drop = new ItemStack(Material.DIAMOND, 1);
		
		Material[] material = {Material.DIAMOND, Material.GOLD_INGOT, Material.EMERALD}; 
		
		int width = 500;
		int height = 20;
		
		for(Material mat : material) {
			for(int i = 0; i < numOfMaterial; i++) {
				x = player.getLocation().getX() + random.nextInt(width) - width/2;
				y = player.getLocation().getY() + height;
				z = player.getLocation().getZ() + random.nextInt(width) - width/2;
				
				Location loc = new Location(player.getWorld(),x,y,z);
				world.dropItem(loc, new ItemStack(mat, 1));
			}
		}

		//world.spawnCreature(player.getLocation(), CreatureType.CHICKEN);
		
		//
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		for(Player online : Bukkit.getOnlinePlayers()){
			  Score score = objective.getScore(online);
			  score.setScore(0); //Example
		}
			 
		for(Player online : Bukkit.getOnlinePlayers()){
			online.setScoreboard(board);
		}
		
		bossbar.removeAll();
		scheduler.cancelAllTasks();		
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			 bossbar.addPlayer(p);
		}
		
		scheduler.scheduleSyncRepeatingTask(GoldMine.getInstance(), new Runnable() {
			double gameTime = 300;
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
