package com.coala.coalaplugin;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.coala.coalaplugin.commands.chatPausedCommand;
import com.coala.coalaplugin.commands.cleanCommand;
import com.coala.coalaplugin.commands.comeCommand;
import com.coala.coalaplugin.commands.commandPausedCommand;
import com.coala.coalaplugin.commands.createCommand;
import com.coala.coalaplugin.commands.classDefaultCommand;
import com.coala.coalaplugin.commands.freezeCommand;
import com.coala.coalaplugin.commands.giveAllCommand;
import com.coala.coalaplugin.commands.mobFreezeCommand;
import com.coala.coalaplugin.commands.modeAllCommand;
import com.coala.coalaplugin.commands.moveCommand;
import com.coala.coalaplugin.commands.playerInvincibleCommand;
import com.coala.coalaplugin.commands.preventExplodeCommand;
import com.coala.coalaplugin.commands.preventPKCommand;
import com.coala.coalaplugin.commands.preventWorldEditCommand;
import com.coala.coalaplugin.commands.rabbitCommand;
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
	public boolean isMobFreeze = false; // 몹 얼리기
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info("Coala Plugin Activated.");
		
		getCommand("move").setExecutor(new moveCommand(this));
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
		getCommand("mobfreeze").setExecutor(new mobFreezeCommand(this));
		getCommand("rabbit").setExecutor(new rabbitCommand());
//		getCommand("modeall").setExecutor(new modeAllCommand());
//		getCommand("giveall").setExecutor(new giveAllCommand());
		getCommand("classDefault").setExecutor(new classDefaultCommand(this));
		
		Bukkit.getPluginManager().registerEvents(new Main_Event(this), this);
		
		World world = Bukkit.getServer().getWorld("world");
		Bukkit.setDefaultGameMode(GameMode.SURVIVAL);
		Bukkit.getLogger().info("기본 게임 모드는 서바이벌 모드입니다");
		
		world.setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getLogger().info("게임 난이도를 평화로움으로 설정했습니다");
		world.setTime(1000);
		Bukkit.getLogger().info("시간이 1000(으)로 설정되었습니다");
		world.setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getLogger().info("이제 해의 위치가 변하지 않습니다");
		world.setStorm(false);
		Bukkit.getLogger().info("맑은 날씨로 변합니다");
		world.setGameRuleValue("doWeatherCycle", "false");
		Bukkit.getLogger().info("이제 날씨가 변하지 않습니다");
		this.isPreventPK = true;
		Bukkit.getLogger().info("이제 플레이어와 플레이어 간의 피해가 적용되지 않습니다");
		this.isPreventExplode = true;
		Bukkit.getLogger().info("이제 폭발로 인해 지형이 변경되지 않습니다");
		
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
