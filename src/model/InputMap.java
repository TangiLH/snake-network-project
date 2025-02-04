package model;


import java.io.BufferedReader;
import java.io.FileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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
		//https://www.tutorialspoint.com/how-to-list-all-files-only-from-a-directory-using-java
		FileFilter fileFilter = new FileFilter(){
	         public boolean accept(File dir) {          
	            if (dir.isFile()) {
	               return true;
	            } else {
	               return false;
	            }
	         }
	      }; 
		
		File dir=new File("./layouts");
		Random ran=new Random();
		try {
			File[]listeFile=dir.listFiles(fileFilter);
			return listeFile[ran.nextInt(listeFile.length)].getAbsolutePath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static Vector<String> getMapList() {
		String[] liste = InputMap.getMapListAsStringArray();
		if(liste==null) {
			System.err.println("erreur, aucune carte");
			return null;
		}
		Vector<String> mapList = new Vector<>();
		for(String map : liste) {
			mapList.add(map);
		}
		return mapList;
	}
	
	 public static String[] getMapListAsStringArray() {
	    	//mettre le bon fichier pour les maps 
	        File folder = new File("layouts");
	        List<String> mapList = new ArrayList<>();

	        if (folder.exists() && folder.isDirectory()) {
	        	//ignore ceux qui sont pas en .lay
	            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".lay")); 
	            
	            if (files != null && files.length > 0) {
	                for (File file : files) {
	                    mapList.add(file.getName()); 
	                }
	            }
	        }

	        // Retourne la liste sous forme de tableau (ou null si vide)
	        return mapList.isEmpty() ? null : mapList.toArray(new String[0]);
	    }
	public static String mapListToJson() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(InputMap.getMapListAsStringArray());
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
	
	public static String[] mapListFromJson(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(String[].class);
		ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			return (String[])or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}


}
