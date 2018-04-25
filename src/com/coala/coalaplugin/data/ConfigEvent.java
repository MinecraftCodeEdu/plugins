package com.coala.coalaplugin.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigEvent {
	public static JSONParser parser = new JSONParser();
	public static Object obj;
	
	public static void readJson (String path) {
		try {
			obj = parser.parse(new FileReader(path));
		} 		catch (Exception e){
			e.printStackTrace();
		}
	}
}
