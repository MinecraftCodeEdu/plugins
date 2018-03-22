package com.mateatdang.goldmine.commands;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class peaceCommand implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		World world = player.getWorld();
		
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setStorm(false);
		world.setTime(1000);
		world.setWeatherDuration(10000000);
		
		return true;
	}
}
