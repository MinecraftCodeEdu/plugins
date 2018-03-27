package com.mateatdang.coalab;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mateatdang.coalab.commands.cleanCommand;
import com.mateatdang.coalab.commands.compassCommand;
import com.mateatdang.coalab.commands.forwardCommand;
import com.mateatdang.coalab.commands.peaceCommand;
import com.mateatdang.coalab.commands.startCommand;
import com.mateatdang.coalab.listeners.BlockListener;
import com.mateatdang.coalab.listeners.EnvironmentListener;
import com.mateatdang.coalab.listeners.PlayerListener;

public class Coalab extends JavaPlugin{
	private static Coalab instance;
	
	public static Coalab getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		System.out.println("코알라 플러그인 활성화. 2018-03-26 업데이트.");
		
		getCommand("start").setExecutor(new startCommand());
		getCommand("clean").setExecutor(new cleanCommand());
		getCommand("peace").setExecutor(new peaceCommand());
		getCommand("compass").setExecutor(new compassCommand());
		getCommand("forward").setExecutor(new forwardCommand());

		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnvironmentListener(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
