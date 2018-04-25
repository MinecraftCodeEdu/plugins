package com.coala.coalaplugin.commands;

import java.util.List;

import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class cleanCommand implements CommandExecutor{
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
		
		if(world.getName().equals("world")) {
			int height = 100;
			int width = 200;
			int x,y,z;
			
			x = player.getLocation().getBlockX();
			y = 4;
			z = player.getLocation().getBlockZ();
			
			for(int i = x-width; i <= x+width ; i++ ) {
				for(int j = y-1; j <= y+height ; j++ ) {
					for(int k = z-width; k <= z+width ; k++ ) {
						if(j != y-1) {
							world.getBlockAt(i,j,k).setType(Material.AIR);
						} else {
							world.getBlockAt(i,j,k).setType(Material.GRASS);
						}
					}
				}
			}
			
			List<Entity> entList = world.getEntities();
	        for(Entity current : entList){
	            if (!(current instanceof Player)){
		            current.remove();
		        }
			}
	        
			world.setStorm(false);
			world.setTime(6000);
			world.setDifficulty(Difficulty.PEACEFUL);
	        
	        player.sendMessage("[clean] 주변을 정리하였습니다.");
			
			return true;
		} else {
			sender.sendMessage("[clean] 기본 월드에서만 사용할 수 있습니다.");
			return false;
		}		
	}
}
