package model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import utils.AgentAction;
import utils.ColorSnake;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.ItemType;
import utils.Position;



public class InputMap implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private String filename;
	private int size_x;
	private int size_y;
	
	private boolean walls[][];


	



	private ArrayList<FeaturesSnake> start_snakes ;
	private ArrayList<FeaturesItem> start_items ;

	
	private BufferedReader buffer;
	
	ColorSnake[] colorSnake = {ColorSnake.Green,ColorSnake.Red};
	
	public InputMap() {
		
	}
	public InputMap(String filename){
		
		this.filename = filename;
		
		try{
		
		InputStream flux =new FileInputStream(filename); 
		InputStreamReader lecture =new InputStreamReader(flux);
		buffer = new BufferedReader(lecture);
		
		String ligne;

		int nbX=0;
		int nbY=0;

		while ((ligne = buffer.readLine())!=null)
		{
			ligne = ligne.trim();
			if (nbX==0) {nbX = ligne.length();}
			else if (nbX != ligne.length()) throw new Exception("Toutes les lignes doivent avoir la même longueur");
			nbY++;
		}			
		buffer.close(); 
			
		size_x = nbX;
		size_y = nbY;
		
		walls = new boolean [size_x][size_y];
	
		flux = new FileInputStream(filename); 
		lecture = new InputStreamReader(flux);
		buffer = new BufferedReader(lecture);
		int y=0;
	
		
		start_snakes = new ArrayList<FeaturesSnake>();
		start_items = new ArrayList<FeaturesItem>();
				
		int id = 0;
		
		while ((ligne=buffer.readLine())!=null)
		{
			ligne=ligne.trim();

			for(int x=0;x<ligne.length();x++)
			{
				
				if (ligne.charAt(x)=='%') 
					walls[x][y]=true; 
					
				else walls[x][y]=false;
				

		
				if (ligne.charAt(x)=='S' ) {
					
					ArrayList<Position> pos = new ArrayList<Position>();
					pos.add(new Position(x,y));
					
					
					start_snakes.add(new FeaturesSnake(pos, AgentAction.MOVE_DOWN,colorSnake[id%colorSnake.length], false, false));	
					id++;
				}
				
				if (ligne.charAt(x)=='A') {
				
					
					start_items.add(new FeaturesItem(x, y, ItemType.APPLE));
					
					
				}
				
				if (ligne.charAt(x)=='B') {
				
					
					start_items.add(new FeaturesItem(x, y, ItemType.BOX));
					
					
				}

				if (ligne.charAt(x)=='Y') {
				
					
					start_items.add(new FeaturesItem(x, y, ItemType.SICK_BALL));
					
					
				}
				
				if (ligne.charAt(x)=='M') {
				
					
					start_items.add(new FeaturesItem(x, y, ItemType.INVINCIBILITY_BALL));
					
					
				}
				
			}
			y++;
		}	
		
		buffer.close(); 
		

		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}

		
	}
	

	
	public int getSize_x() {return(size_x);}
	
	public void setSize_x(int size_x) {
		this.size_x = size_x;
	}
	public void setSize_y(int size_y) {
		this.size_y = size_y;
	}
	public int getSize_y() {return(size_y);}
	


	
	public String getFilename(){
		return filename;
	}

	public boolean[][] getWalls() {
		return walls;
	}
	
	public void setWalls(boolean[][] walls) {
		this.walls = walls;
	}
	
	
	public ArrayList<FeaturesSnake> getStart_snakes() {
		return start_snakes;
	}

	public ArrayList<FeaturesItem> getStart_items() {
		return start_items;
	}



	public String toJson() {
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(this);
			json.replace("\n","");
			json.replace("\r", "\r");
			
			//sert a renvoyer le json sur une seule ligne
			BufferedReader br= new BufferedReader(new StringReader(json));
			String line=null;
			StringBuilder sb = new StringBuilder();
			while((line=br.readLine()) != null) {
				sb.append(line);
			}
			
			return sb.toString();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";
    }
	
	public static InputMap fromJson(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(InputMap.class);
		ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			return (InputMap)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}



	@Override
	public String toString() {
		return "InputMap [filename=" + filename + ", size_x=" + size_x + ", size_y=" + size_y + ", walls="
				+ Arrays.toString(walls) + ", start_snakes=" + start_snakes + ", start_items=" + start_items
				+ ", buffer=" + buffer + ", colorSnake=" + Arrays.toString(colorSnake) + "]";
	}
	public static String getRandomMap() {
		File dir=new File("./layouts");
		Random ran=new Random();
		try {
			File[]listeFile=dir.listFiles();
			return listeFile[ran.nextInt(listeFile.length)].getAbsolutePath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


}
