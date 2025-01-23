package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class FeaturesItem {

	private ItemType itemType;
	

	private Position position;


	public FeaturesItem(int x, int y, ItemType itemType) {
		this.itemType = itemType;
		this.position=new Position(x,y);
	
	}
	
	public FeaturesItem() {
		
	}
	
	public FeaturesItem clone(){
		return new FeaturesItem(this.getX(), this.getY(), this.itemType);
	}
	
	public int getX() {
		return this.position.getX();
	}


	public void setX(int x) {
		this.position.setX(x);
	}


	public int getY() {
		return this.position.getY();
	}


	public void setY(int y) {
		this.position.setY(y);
	}


	public ItemType getItemType() {
		return itemType;
	}


	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public void setPosition(Position position){
		this.position=position;
	}
	public Position getPosition(){
		return this.position;
	}
	
	public String toJson() {

    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(this);
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
	
	public static FeaturesItem fromJson(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(FeaturesItem.class);
	ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			return (FeaturesItem)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}

	@Override
	public String toString() {
		return "FeaturesItem [itemType=" + itemType + ", position=" + position + "]";
	}


	
	
}
