package com.coala.coalaplugin;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.coala.coalaplugin.commands.startCommand;

public class Main_Event implements Listener {
	public Main pl;
	Scoreboard scoreboard = startCommand.board;
	Objective objective = startCommand.objective;
	
	public Main_Event(Main instance)
	{
		this.pl = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerRespawnEvent e) // 플레이어 리스폰
	{
		e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(AsyncPlayerChatEvent e) // 채팅 제한
	{
		if(this.pl.isChatPaused && !e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("관리자를 제외한 플레이어는 채팅이 제한되어있습니다.");
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerCommandPreprocessEvent e) // 명렁어 제한
	{
		if(this.pl.isCommandPaused && !e.getPlayer().isOp()) {
			e.getPlayer().sendMessage("관리자를 제외한 플레이어는 명령어 사용이 제한되어있습니다.");
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(EntityDamageEvent e) // 플레이어 무적
	{
		if(this.pl.isPlayerInvincible) {
			if(e.getEntity() instanceof Player) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(EntityDamageByEntityEvent e) // 플레이어 간 데미지 제한 (폭발에 의한 데미지는 막지 못함)
	{
		if(this.pl.isPreventPK) {
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player)
			{
				e.setCancelled(true);
			}
		}
	}	
	@EventHandler(priority = EventPriority.LOW)
	public void Event(EntityExplodeEvent e) // TNT 혹은 크리퍼 폭발에 의한 블록 변화
	{
		if(this.pl.isPreventExplode) {
	        for (Block block : new ArrayList<Block>(e.blockList())) {
	            e.blockList().remove(block);
	        }
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(BlockPlaceEvent e) // 블럭 짓기
	{
		if((this.pl.isPlayerFreeze || this.pl.isPreventWorldEdit) && !e.getPlayer().isOp()) {
			if(this.pl.isPlayerFreeze) {
				e.getPlayer().sendMessage("몸이 얼어있습니다.");
			} else {
				e.getPlayer().sendMessage("관리자를 제외한 플레이어는 월드 변경이 금지되어있습니다.");
			}
			e.setCancelled(true);
		}
			
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(BlockBreakEvent e) // 블럭 부수기
	{
		if((this.pl.isPlayerFreeze || this.pl.isPreventWorldEdit) && !e.getPlayer().isOp()) {
			if(this.pl.isPlayerFreeze) {
				e.getPlayer().sendMessage("몸이 얼어있습니다.");
			} else {
				e.getPlayer().sendMessage("관리자를 제외한 플레이어는 월드 변경이 금지되어있습니다.");
			}
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void Event(PlayerMoveEvent e) { // 플레이어 얼리기
		Location from = e.getFrom(), to = e.getTo();
		if(this.pl.isPlayerFreeze && !e.getPlayer().isOp()) {
			if((from.getX() != to.getX()) || (from.getZ() != to.getZ())) { // OBS: care on what you do with this
			e.getPlayer().teleport(new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), to.getYaw(), to.getPitch()));
			e.setCancelled(true); // I always have an issue with cancelling this, so I just teleport them
			}
		}
	}
}




//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(FoodLevelChangeEvent event) // 배고픔 수치 변경
//	{
//		if((boolean)ConfigWorld.getValue("FoodLevelChangeEvent"))
//		{
//			event.setCancelled(true);
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(BlockExplodeEvent event) // 블럭이 폭발
//	{
//		if((boolean)ConfigWorld.getValue("BlockExplodeEvent"))
//		{
//			event.blockList().clear();
//		}
//	}
/////////////////////////////////////////////////////////////////////////	
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(ItemSpawnEvent event) // 아이템 스폰
//	{
//		if(Main.isEventToggle())
//		{
//			event.setCancelled(true);
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerChangedWorldEvent event) // 플레이어 월드 변경
//	{
//		Player player = event.getPlayer();
//		PlayerData pd = data.getPlayerData(player.getUniqueId());
//		if(data.isGame1() && pd == null && player.getWorld().getName().equals(Main.WORLD_NAME))
//		{
//			player.sendMessage(ChatColor.RED + "미니게임맵에 들어갈 권한이 없습니다.");f
//			player.teleport(event.getFrom().getSpawnLocation());
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerRespawnEvent event) // 플레이어 리스폰
//	{
//		final Player player = event.getPlayer();
//		final PlayerData pd = data.getPlayerData(player.getUniqueId());
//		if(data.isGame2() && pd != null)
//		{
//			event.setRespawnLocation(data.getMinigame().getInstance().teleport(player));
//		}
//	}
/////////////////////////////////////////////////////////////////////////		
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerJoinEvent event) // 플레이어 접속
//	{
//		if((boolean)ConfigWorld.getValue("PlayerJoinEvent")) {
//	    	World world = event.getPlayer().getWorld();
//	    	event.getPlayer().teleport(ConfigWorld.getWorld().getSpawnLocation());
//			world.setStorm(false);
//			world.setTime(6000);
//			world.setDifficulty(Difficulty.NORMAL);
//	    	world.setGameRuleValue("doDaylightCycle", "false");
//	    	world.setGameRuleValue("doWeatherCycle", "false");
//		}
/////////////////////////////////////////////////////////////////////////	
    	
//		Player player = event.getPlayer();
//		if(player.isOp())
//		{
//			LinmaluServer.version(Main.getMain(), player);
//		}
//		if(data.isGame1() && data.getPlayerData(player.getUniqueId()) != null)
//		{
//			data.setScoreboard(player);
//			data.getMinigame().getInstance().teleport(player);
//			if(!data.isResourcePack())
//			{
//				player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
//				player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
//			}
//		}
//		else if(!data.isGame1())
//		{
//			PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
//			if(pd != null)
//			{
//				pd.resetPlayer();
//			}
//		}
//		if(data.isResourcePack())
//		{
//			player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
//			player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerQuitEvent event) // 플레이어 종료
//	{
//		Player player = event.getPlayer();
//		if(data.isGame2() && player.getWorld().getName().equals(Main.WORLD_NAME))
//		{
//			data.diePlayer(player.getUniqueId());
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerDeathEvent event) // 플레이어 사망
//	{
//		Player player = event.getEntity();
//		player.teleport(player.getWorld().getSpawnLocation());
//	}
/////////////////////////////////////////////////////////////////////////	


//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(EntityPickupItemEvent event) // 아이템 습득
//	{
//		if((boolean)ConfigWorld.getValue("EntityPickupItemEvent") && event.getEntity().getWorld().getName().equals("world"))
//		{
////			event.getItem().remove();
////			event.setCancelled(true);
//			Player player = (Player) event.getEntity();
//			World world = player.getWorld();
//			ItemStack item = event.getItem().getItemStack();
//			Material itemType = item.getType();
//			int numOfItem = item.getAmount();
//			int pointOfDiamond = 5;
//			int pointOfGold = 3;
//			int pointOfEmerald = 1;
//			
//	        if (itemType.equals(Material.DIAMOND)){
//	        	player.sendMessage("다이아몬드를 "+ "§b§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfDiamond*numOfItem + "§r점을 얻었습니다.");
//				Score score = objective.getScore(player);
//				score.setScore(score.getScore()+pointOfDiamond*numOfItem);
//	        } else if (itemType.equals(Material.GOLD_INGOT)){
//	        	player.sendMessage("금을 "+ "§e§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfGold*numOfItem + "§r점을 얻었습니다.");
//				Score score = objective.getScore(player);
//				score.setScore(score.getScore()+pointOfGold*numOfItem);
//	        } else if (itemType.equals(Material.EMERALD)){
//	        	player.sendMessage("에메랄드를 "+ "§a§l" + numOfItem + "§r개 주워서 " + "§c§l" + pointOfEmerald*numOfItem + "§r점을 얻었습니다.");
//				Score score = objective.getScore(player);
//				score.setScore(score.getScore()+pointOfEmerald*numOfItem);
//	        }
//	        
//	        int x,y,z;
//	        x = event.getItem().getLocation().getBlockX();
//	        y = event.getItem().getLocation().getBlockY();
//	        z = event.getItem().getLocation().getBlockZ();
//	        
//			if(itemType.equals(Material.DIAMOND) || itemType.equals(Material.GOLD_INGOT) || itemType.equals(Material.EMERALD)) {
//				for(int j = -2; j <= 2 ; j++) {
//					for(int k = -2; k <= 2 ; k++) {
//						world.getBlockAt(x+j, y-1, z+k).setType(Material.AIR);
//					}
//				}
//			}
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerDropItemEvent event) // 아이템 버리기
//	{
//		if((boolean)ConfigWorld.getValue("PlayerDropItemEvent"))
//		{
//			event.setCancelled(true);
//			
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(BlockCanBuildEvent event) // 블럭 지을 수 있는지 확인
//	{
//		if((boolean)ConfigWorld.getValue("BlockCanBuildEvent"))
//		{
//			event.setBuildable(false);
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(BlockPlaceEvent event) // 블럭 놓기
//	{
//		if((boolean)ConfigWorld.getValue("BlockPlaceEvent"))
//		{
//			event.setCancelled(true);
//		}
//	}

/////////////////////////////////////////////////////////////////////////	
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerInteractEvent event) // 플레이어 상호작용
//	{
//		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME) && event.getAction() == Action.PHYSICAL)
//		{
//			if(event.getClickedBlock().getType() == Material.SOIL)
//			{
//				event.setCancelled(true);
//			}
//		}
//	}

/////////////////////////////////////////////////////////////////////////	

/////////////////////////////////////////////////////////////////////////		

//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(EntityDamageByEntityEvent event) // 엔티티가 엔티티에게 데미지 입음
//	{
//		if(data.isGame1() && !data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
//		{
//			event.setCancelled(true);
//		}
//		else if(data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
//		{
//			if(event.getDamager() instanceof Player)
//			{
//				PlayerData pd = data.getPlayerData(event.getDamager().getUniqueId());
//				if(!(pd != null && pd.isLive() && pd.isCooldown()))
//				{
//					event.setCancelled(true);
//				}
//			}
//			if(event.getEntity() instanceof Player)
//			{
//				PlayerData pd = data.getPlayerData(event.getEntity().getUniqueId());
//				if(!(pd != null && pd.isLive() && pd.isCooldown()))
//				{
//					event.setCancelled(true);
//				}
//			}
//		}
//	}
//	// TODO 눈덩이 맞을때 데미지 확인
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(ProjectileHitEvent event) // 발사체 히트
//	{
//		if(data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
//		{
//			Entity entity = event.getHitEntity();
//			if(entity != null && entity.getType() == EntityType.PLAYER)
//			{
//				PlayerData pd = data.getPlayerData(entity.getUniqueId());
//				if(pd != null && pd.isLive())
//				{
//					((Player)entity).damage(1, (Entity)event.getEntity().getShooter());
//				}
//			}
//		}
//	}
//	@EventHandler(priority = EventPriority.LOW)
//	public void Event(PlayerMoveEvent event) // 플레이어 이동
//	{
//		Player player = event.getPlayer();
//		PlayerData pd = data.getPlayerData(player.getUniqueId());
//		if(data.isGame1() && player.getWorld().getName().equals(Main.WORLD_NAME) && pd != null)
//		{
//			if(data.getMinigame() == MiniGame.경마)
//			{
//				return;
//			}
//			int yFrom = event.getFrom().getBlockY();
//			int yTo = event.getTo().getBlockY();
//			if(yFrom != yTo && yTo < 0)
//			{
//				if(data.isGame2() && pd.isLive() && !data.getMapData().isTopScore())
//				{
//					data.diePlayer(player.getUniqueId());
//				}
//				else
//				{
//					data.getMinigame().getInstance().teleport(player);
//				}
//			}
//		}
//	}

/////////////////////////////////////////////////////////////////////////	
//	@EventHandler
//	public void Event(CreatureSpawnEvent event) 
//	{
//		if((boolean)ConfigWorld.getValue("CreatureSpawnEvent"))
//		{
//			if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
//				event.setCancelled(true);
//			}
//		}
//	}

