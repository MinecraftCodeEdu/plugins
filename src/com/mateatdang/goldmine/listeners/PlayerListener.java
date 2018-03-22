package com.mateatdang.goldmine.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.mateatdang.goldmine.commands.startCommand;

public class PlayerListener implements Listener{
	Scoreboard scoreboard = startCommand.board;
	Objective objective = startCommand.objective;
	
	@EventHandler
	public void onPlayerPickUpItem(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
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
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	    e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0,10,0));
	}
	
//	@EventHandler
//	public void onEntitySpawn(EntitySpawnEvent e) {
//		if(!(e instanceof Item)) {
//			e.setCancelled(true);
//		}
//	}
}
