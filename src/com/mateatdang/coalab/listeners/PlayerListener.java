package com.mateatdang.coalab.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.mateatdang.coalab.commands.startCommand;

public class PlayerListener implements Listener{
	Scoreboard scoreboard = startCommand.board;
	Objective objective = startCommand.objective;
	
	@EventHandler
	public void onPlayerPickUpItem(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		ItemStack item = e.getItem().getItemStack();
		Material itemType = item.getType();
		int numOfItem = item.getAmount();
		int pointOfDiamond = 5;
		int pointOfGold = 3;
		int pointOfEmerald = 1;
		
        if (itemType.equals(Material.DIAMOND)){
        	player.sendMessage("다이아몬드를 "+ "§b§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfDiamond*numOfItem + "§r점을 얻었습니다.");
			Score score = objective.getScore(player);
			score.setScore(score.getScore()+pointOfDiamond*numOfItem);
        } else if (itemType.equals(Material.GOLD_INGOT)){
        	player.sendMessage("금을 "+ "§e§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfGold*numOfItem + "§r점을 얻었습니다.");
			Score score = objective.getScore(player);
			score.setScore(score.getScore()+pointOfGold*numOfItem);
        } else if (itemType.equals(Material.EMERALD)){
        	player.sendMessage("에메랄드를 "+ "§a§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfEmerald*numOfItem + "§r점을 얻었습니다.");
			Score score = objective.getScore(player);
			score.setScore(score.getScore()+pointOfEmerald*numOfItem);
        }
        
        int x,y,z;
        x = e.getItem().getLocation().getBlockX();
        y = e.getItem().getLocation().getBlockY();
        z = e.getItem().getLocation().getBlockZ();
        
		if(itemType.equals(Material.DIAMOND) || itemType.equals(Material.GOLD_INGOT) || itemType.equals(Material.EMERALD)) {
			for(int j = -2; j <= 2 ; j++) {
				for(int k = -2; k <= 2 ; k++) {
					world.getBlockAt(x+j, y-1, z+k).setType(Material.AIR);
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if(!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	    e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0,10,0));
	    
    	World world = e.getPlayer().getWorld();
    	
    	world.setStorm(false);
    	world.setTime(6000);
    	world.setGameRuleValue("doDaylightCycle", "false");
    	world.setGameRuleValue("doWeatherCycle", "false");
	}
	
//	@EventHandler
//	public void onEntitySpawn(EntitySpawnEvent e) {
//		if(!(e.getEntity() instanceof Item)) {
//			e.setCancelled(true);
//		}
//	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}
	}
}
