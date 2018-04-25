package com.coala.coalaplugin.data;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class GameData {
	private final BossBar bar = Bukkit.createBossBar("남은 시간", BarColor.YELLOW, BarStyle.SOLID);
	private Scoreboard scoreboard;

	public BossBar getBossbar()
	{
		return bar;
	}
	
	public void setScoreboard(Player player)
	{
		player.setScoreboard(scoreboard);
	}
	
	public Score getScore(String name)
	{
		Objective ob = scoreboard.getObjective("미니게임");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("미니게임", "");
		}
		return ob.getScore(name);
	}
}

