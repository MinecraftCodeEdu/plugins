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
		
		//world.getName().equals("world")

		int height = 100; // y부터 y+height까지 정리
		int width = 200; // -width에서 +width까지 정리
		int x,y,z;
		
		x = player.getLocation().getBlockX();
		y = 4;
		z = player.getLocation().getBlockZ();
		
		for(int j = y-1; j <= y+height ; j++ ) {
			for(int i = x-width; i <= x+width ; i++ ) {
				for(int k = z-width; k <= z+width ; k++ ) {
					if(j != y-1) {
						world.getBlockAt(i,j,k).setType(Material.AIR); // 위쪽 부분은 공기
					} else {
						world.getBlockAt(i,j,k).setType(Material.GRASS); // 바닥 부분인 y-1은 잔디
					}
				}
			}
		}
		
		// 맵에 존재하는 플레이어를 제외한 엔티티 정리
		List<Entity> entList = world.getEntities();
        for(Entity current : entList){
            if (!(current instanceof Player)){
	            current.remove();
	        }
		}
        
		world.setStorm(false); // 맑음
		world.setTime(6000); // 낮
		world.setDifficulty(Difficulty.PEACEFUL); // 난이도 평화로움
        
        player.sendMessage("[clean] 주변을 정리하였습니다.");
			
        return true;
	}
}
