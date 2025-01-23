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
		InputMap carte=null;
		try {
	        carte=new InputMap("layouts/alone.lay");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("fichier non trouvé");
	    }
		SnakeGame sg=new SnakeGame(500,carte,2);
		sg.initializeGame();
		System.out.println("Snake : "+sg);
		ArrayList<ArrayList<String>>listFeatures=null;
		String json=sg.getJsonFeatures();
		JavaType javaType = TypeFactory.defaultInstance().constructType(ArrayList.class);
		
		ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			 listFeatures=or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println(listFeatures);
    	FeaturesSnake fs=FeaturesSnake.fromJson(listFeatures.get(0).get(0));
    	FeaturesItem fi=FeaturesItem.fromJson(listFeatures.get(1).get(0));
    	System.out.println("Items "+fi);
    	System.out.println("Snakes "+fs);
    	/*System.out.println(fs);
    	System.out.println(sg.getListSnakes().get(0));

    	System.out.println(fi);

    	System.out.println(sg.getFeaturesItems().get(0));*/
    	
		ArrayList<FeaturesSnake>listSnakes=new ArrayList<>();
		ArrayList<FeaturesItem>listItems=new ArrayList<>();
		
		InputMap map=new InputMap("layouts/alone.lay");
		//System.out.println(map.toString());
		
		map=InputMap.fromJson(map.toJson());
		
		//System.out.println(map.toJson());
		//System.out.println(map.toString());
		
		
	}
}
