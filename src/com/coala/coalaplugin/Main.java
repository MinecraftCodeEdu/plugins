package com.coala.coalaplugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.coala.coalaplugin.commands.AllowFlight;
import com.coala.coalaplugin.commands.blind;
import com.coala.coalaplugin.commands.chatPausedCommand;
import com.coala.coalaplugin.commands.classDefaultCommand;
import com.coala.coalaplugin.commands.cleanCommand;
import com.coala.coalaplugin.commands.comeCommand;
import com.coala.coalaplugin.commands.commandPausedCommand;
import com.coala.coalaplugin.commands.createCommand;
import com.coala.coalaplugin.commands.freeze;
import com.coala.coalaplugin.commands.mobFreezeCommand;
import com.coala.coalaplugin.commands.moveCommand;
import com.coala.coalaplugin.commands.playerInvincibleCommand;
import com.coala.coalaplugin.commands.preventExplodeCommand;
import com.coala.coalaplugin.commands.preventPKCommand;
import com.coala.coalaplugin.commands.preventWorldEditCommand;
import com.coala.coalaplugin.commands.rabbitCommand;
import com.coala.coalaplugin.commands.startCommand;
import com.coala.coalaplugin.commands.timer;
import com.coala.coalaplugin.commands.unBlind;
import com.coala.coalaplugin.commands.unFreeze;
import com.coala.coalaplugin.commands.weatherPlayer;
import com.coala.coalaplugin.data.GameData;

public class Main extends JavaPlugin {
	private static Main instance;
	private static boolean eventToggle = true;
	public GameData gamedata;
	
	private static final String ALGO = "AES";
	private static final byte[] keyValue =
			new byte[]{'C', 'o', 'a', 'l', 'a', 'E', 'x', 'p', 'i', 'r', 'y', 'D', 'a', 't', 'e', 'e'};
	
	public boolean isChatPaused = false; // 채팅 제한
	public boolean isCommandPaused = false; // 명령어 제한
	public boolean isPlayerInvincible = false; // 플레이어 무적
	public boolean isPreventPK = false; // 플레이어 간 데미지 제한
	public boolean isPreventExplode = false; // 폭발에 의한 데미지 제한
	public boolean isPreventWorldEdit = false; // 월드 변경 제한
	public boolean isPlayerFreeze = false; // 플레이어 얼리기 Deprecated
	public boolean isMobFreeze = false; // 몹 얼리기
	
	@Override
	public void onEnable() {
		instance = this;
		
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
//		getCommand("freeze").setExecutor(new freezeCommand(this));
		getCommand("mobfreeze").setExecutor(new mobFreezeCommand(this));
		getCommand("rabbit").setExecutor(new rabbitCommand());
//		getCommand("modeall").setExecutor(new modeAllCommand());
//		getCommand("giveall").setExecutor(new giveAllCommand());
		getCommand("classDefault").setExecutor(new classDefaultCommand(this));
		
		getCommand("timer").setExecutor(new timer());
		getCommand("unfreeze").setExecutor(new unFreeze());
		getCommand("freeze").setExecutor(new freeze());
		getCommand("weatherPlayer").setExecutor(new weatherPlayer());
		getCommand("blind").setExecutor(new blind());
		getCommand("unblind").setExecutor(new unBlind());
		getCommand("allowflight").setExecutor(new AllowFlight());
		
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
		
    	if (validateExpireDate()) {
    		this.getLogger().info("Coala plugin is activated.");
    	} else {
    		this.getLogger().warning("라이센스가 만료되었습니다. 아래의 이메일로 문의해주시기 바랍니다. webmaster@coalasw.com");
    		Bukkit.getPluginManager().disablePlugin(this);
    	}
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
	
    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    private static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }
    
    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
    
    /**
     * Validate expire date.
     */
    private static boolean validateExpireDate() {
		try {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");	
			
			String encryptedData = readLicenseFile();
			
			if(encryptedData == null) {
				return false;
			} else {
				String decryptedData = decrypt(encryptedData);
				if(decryptedData.substring(0, 5).equalsIgnoreCase("alaoc") &&
						decryptedData.substring(decryptedData.length()-3, decryptedData.length()).equalsIgnoreCase("wws")) {
					int currentDate = Integer.parseInt(df.format(date));
					int expiryDate = Integer.parseInt(decryptedData.substring(5, 13));
					
					if (currentDate <= expiryDate) {
						return true;
					} else {
						return false;
					}
				}
				else {
					return false;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
    }
    
	/**
	 * Read coalacraft license file.
	 */
	private static String readLicenseFile() {
		try {
	        BufferedReader br = new BufferedReader(new FileReader(Paths.get(Bukkit.getWorldContainer().toString(), "license", "LICENSE").toString()));
	        while(true) {
	            String line = br.readLine();
	            if (line==null) break;
	            br.close();
	            return line;       
	        }
	        br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
