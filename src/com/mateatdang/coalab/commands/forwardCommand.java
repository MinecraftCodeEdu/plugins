package com.mateatdang.coalab.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class forwardCommand implements CommandExecutor{
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
		Location loc = player.getLocation();
		
		if(args.length == 0) {
			player.sendMessage("이동할 거리를 입력하세요.");
			return false;
		}
		
		int distance = Integer.parseInt(args[0]);
		
		// To convert degrees into radians
		double yaw  = ((loc.getYaw() + 90)  * Math.PI) / 180;

		double x = Math.cos(yaw);
		double z = Math.sin(yaw);

		player.teleport(new Location(world, loc.getX()+x*distance,loc.getY(), loc.getZ()+z*distance, loc.getYaw(), loc.getPitch()));
		
		player.sendMessage("[forward] 앞으로 §e"+ args[0] + "§r만큼 이동했습니다.");
		
		return true;
	}
	
	
}
