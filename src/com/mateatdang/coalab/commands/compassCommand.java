package com.mateatdang.coalab.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class compassCommand implements CommandExecutor{
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
		
		player.sendMessage("[compass] §4동§c서§e남§a북 §r기둥을 세웠습니다.");
		
		return true;
	}
}
