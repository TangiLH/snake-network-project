package utils;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import model.SnakeGame;

public final class ArrayFromJson {
	public static ArrayList fromJson(String json) {
		ObjectReader or= new ObjectMapper().reader();
    	try {
			return (ArrayList)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}
}
