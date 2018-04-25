package com.coala.coalaplugin.data;

import java.io.FileReader;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConfigWorld {
	private static World world = null;
	private static JSONParser parser = new JSONParser();
	private static JSONObject jsonObject;
	
	public static World getWorld() {
		return world;
	}
	
	public static void setWorld(World w) {
		world = w;
		
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(Bukkit.getWorldContainer()+"\\"+w.getName()+"\\event.json"));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static JSONObject getJSONObject() {
		return jsonObject;
	}
	
	public static Object getValue(String key) {
		return ConfigWorld.getJSONObject().get(key);
	}
}
