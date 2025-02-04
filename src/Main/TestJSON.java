package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.TypeFactory;

import model.InputMap;
import model.SnakeGame;
import utils.FeaturesItem;
import utils.FeaturesSnake;

public class TestJSON {
	public static void main(String args[]) {
		
		System.out.println(InputMap.mapListToJson());
		String json=InputMap.mapListToJson();
		String[] liste =InputMap.mapListFromJson(json);
		for (String map : liste) {
			System.out.println(map);
		}
		
		
		//System.out.println(map.toJson());
		//System.out.println(map.toString());
		
		
	}
}
