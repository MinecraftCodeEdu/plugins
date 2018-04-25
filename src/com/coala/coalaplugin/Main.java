package com.coala.coalaplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.coala.coalaplugin.data.ConfigEvent;
import com.coala.coalaplugin.data.ConfigWorld;
import com.coala.coalaplugin.data.GameData;
import com.coala.coalaplugin.Main_Event;
import com.coala.coalaplugin.commands.cleanCommand;
import com.coala.coalaplugin.commands.eventCommand;
import com.coala.coalaplugin.commands.moveCommand;
import com.coala.coalaplugin.commands.startCommand;

public class Main extends JavaPlugin {
	private static Main instance;
	private static boolean eventToggle = true;
	public GameData gamedata;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info("Coala Plugin Activated.");
		
		getCommand("move").setExecutor(new moveCommand());
		getCommand("clean").setExecutor(new cleanCommand());
		getCommand("event").setExecutor(new eventCommand());
		getCommand("start").setExecutor(new startCommand());
		
		Bukkit.getPluginManager().registerEvents(new Main_Event(), this);
		
		ConfigWorld.setWorld(Bukkit.getServer().getWorlds().get(0));
		for(Player p : Bukkit.getOnlinePlayers()) {					
			p.teleport(ConfigWorld.getWorld().getSpawnLocation());
		}
		//ConfigEvent.readJson(Bukkit.getWorldContainer()+"\\world\\event.json");
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public GameData getGameData()
	{
		return gamedata;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static boolean isEventToggle() {
		return eventToggle;
	}
	
	public static void setEventToggle(boolean bool) {
		eventToggle = bool;
	}
	
}
