package com.mateatdang.goldmine.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		//if(!player.isOp()) {
			e.setCancelled(true);
			//e.getPlayer().sendMessage("§c블록을 부술 수 없습니다.");
		//}
	}
}
