package com.mateatdang.goldmine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mateatdang.goldmine.commands.peaceCommand;
import com.mateatdang.goldmine.commands.startCommand;
import com.mateatdang.goldmine.listeners.BlockListener;
import com.mateatdang.goldmine.listeners.PlayerListener;


public class GoldMine extends JavaPlugin{
	private static GoldMine instance;
	
	public static GoldMine getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		System.out.println("GoldMine Plugin 활성화.");
		
		getCommand("start").setExecutor(new startCommand());
		getCommand("peace").setExecutor(new peaceCommand());
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
	}
	
	public void onDisable() {

	}
}
