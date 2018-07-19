package com.coala.coalaplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.coala.coalaplugin.commands.chatPausedCommand;
import com.coala.coalaplugin.commands.cleanCommand;
import com.coala.coalaplugin.commands.comeCommand;
import com.coala.coalaplugin.commands.commandPausedCommand;
import com.coala.coalaplugin.commands.createCommand;
import com.coala.coalaplugin.commands.freezeCommand;
import com.coala.coalaplugin.commands.moveCommand;
import com.coala.coalaplugin.commands.playerInvincibleCommand;
import com.coala.coalaplugin.commands.preventExplodeCommand;
import com.coala.coalaplugin.commands.preventPKCommand;
import com.coala.coalaplugin.commands.preventWorldEditCommand;
import com.coala.coalaplugin.commands.startCommand;
import com.coala.coalaplugin.data.GameData;

public class Main extends JavaPlugin {
	private static Main instance;
	private static boolean eventToggle = true;
	public GameData gamedata;
	
	public boolean isChatPaused = false; // 채팅 제한
	public boolean isCommandPaused = false; // 명령어 제한
	public boolean isPlayerInvincible = false; // 플레이어 무적
	public boolean isPreventPK = false; // 플레이어 간 데미지 제한
	public boolean isPreventExplode = false; // 폭발에 의한 데미지 제한
	public boolean isPreventWorldEdit = false; // 월드 변경 제한
	public boolean isPlayerFreeze = false; // 플레이어 얼리기
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info("Coala Plugin Activated.");
		
		getCommand("move").setExecutor(new moveCommand());
		getCommand("come").setExecutor(new comeCommand());
		getCommand("clean").setExecutor(new cleanCommand());
//		getCommand("event").setExecutor(new eventCommand());
		getCommand("start").setExecutor(new startCommand());
		getCommand("create").setExecutor(new createCommand());
		getCommand("chatPaused").setExecutor(new chatPausedCommand(this));
		getCommand("commandPaused").setExecutor(new commandPausedCommand(this));
		getCommand("playerInvincible").setExecutor(new playerInvincibleCommand(this));
		getCommand("preventPK").setExecutor(new preventPKCommand(this));
		getCommand("preventExplode").setExecutor(new preventExplodeCommand(this));
		getCommand("preventWorldEdit").setExecutor(new preventWorldEditCommand(this));
		getCommand("freeze").setExecutor(new freezeCommand(this));
		
		Bukkit.getPluginManager().registerEvents(new Main_Event(this), this);
		
//		ConfigWorld.setWorld(Bukkit.getServer().getWorlds().get(0));
//		for(Player p : Bukkit.getOnlinePlayers()) {					
//			p.teleport(ConfigWorld.getWorld().getSpawnLocation());
//		}
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
