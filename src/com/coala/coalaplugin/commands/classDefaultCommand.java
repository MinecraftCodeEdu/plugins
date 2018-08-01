package com.coala.coalaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.coala.coalaplugin.Main;

public class classDefaultCommand  implements CommandExecutor{
	public Main pl;
	
	public classDefaultCommand(Main instance)
	{
		this.pl = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage("관리자만 사용 가능합니다.");
			return false;
		}
		
		Bukkit.dispatchCommand(sender, "gamemode survival @a");
		Bukkit.dispatchCommand(sender, "difficulty peaceful");
		Bukkit.dispatchCommand(sender, "time set 1000");
		Bukkit.dispatchCommand(sender, "gamerule doDaylightCycle false");
		sender.sendMessage("이제 해의 위치가 변하지 않습니다");
		Bukkit.dispatchCommand(sender, "gamerule doWeatherCycle false");
		sender.sendMessage("이제 날씨가 변하지 않습니다");
		this.pl.isPreventPK = true;
		sender.sendMessage("이제 플레이어와 플레이어 간의 피해가 적용되지 않습니다");
		this.pl.isPreventExplode = true;
		sender.sendMessage("이제 폭발로 인해 지형이 변경되지 않습니다");
		
		return true;
	}
}
