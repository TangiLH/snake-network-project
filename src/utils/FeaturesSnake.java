package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import model.SnakeGame;

/**
 * modélise les propriétes du serpent
 */
public class FeaturesSnake {



	ArrayList<Position> positions;
	
	private AgentAction lastAction;
	
	ColorSnake colorSnake;
	
	boolean invincible;
	boolean sick;
	int invicibilityCD;
	int sickCD;
	
	public FeaturesSnake(ArrayList<Position> positions, AgentAction lastAction, ColorSnake colorSnake, boolean isInvincible, boolean isSick) {
		
		this.positions = positions;
		this.colorSnake = colorSnake;
		this.lastAction = lastAction;
		
		this.invincible = isInvincible;
		
		this.sick = isSick;

		this.invicibilityCD=0;
		
		this.sickCD=0;
		
	}

	/**
	 * constructeur par copie pour featureSnake
	 * @param featuresSnake le featureSnake à copier
	 */
	public FeaturesSnake(FeaturesSnake featuresSnake){
		this.positions=new ArrayList<>();
		for(Position p : featuresSnake.getPositions()){
			this.positions.add(new Position(p));
		}
		this.colorSnake=featuresSnake.getColorSnake();
		this.lastAction=featuresSnake.getLastAction();
		this.invincible=featuresSnake.invincible;
		this.sick=featuresSnake.sick;
		this.invicibilityCD=0;
		this.sickCD=0;
	}
	
	public FeaturesSnake() {
	}
		
	/**
	 * retourne la liste des positions du corps du serpent
	 * @return positions la liste des positions du corps du serpent
	 */
	public ArrayList<Position> getPositions() {
		return positions;
	}
	

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	public void updateCountDowns(){
		this.invicibilityCD-=1;
		this.sickCD-=1;
		this.invincible=this.invicibilityCD>0;
		this.sick=this.sickCD>0;
	}



	public ColorSnake getColorSnake() {
		return colorSnake;
	}


	public void setColorSnake(ColorSnake colorSnake) {
		this.colorSnake = colorSnake;
	}


	public boolean isInvincible() {
		return invincible;
	}


	public void setInvincible(boolean isInvincible,int duration) {
		this.invincible = isInvincible;
		this.invicibilityCD=duration;
	}


	public int getInvicibilityCD() {
		return invicibilityCD;
	}

	public void setInvicibilityCD(int invicibilityCD) {
		this.invicibilityCD = invicibilityCD;
	}

	public int getSickCD() {
		return sickCD;
	}

	public void setSickCD(int sickCD) {
		this.sickCD = sickCD;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void setSick(boolean sick) {
		this.sick = sick;
	}

	public boolean isSick() {
		return sick;
	}


	public void setSick(boolean isSick,int duration) {
		this.sick = isSick;
		this.sickCD=duration;
	}


	public AgentAction getLastAction() {
		return lastAction;
	}


	public void setLastAction(AgentAction lastAction) {
		this.lastAction = lastAction;
	}

	@JsonIgnore
	public int getLength(){
		return positions.size();
	}

    public boolean checkCollision(int x, int y) {
        boolean retour = false;
		for (Position p : positions){
			retour=retour || (p.getX()==x && p.getY() == y);
		}
		return retour;
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
	
	public static FeaturesSnake fromJson(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(FeaturesSnake.class);
		ObjectReader or= new ObjectMapper().reader().forType(javaType);
    	try {
			return (FeaturesSnake)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}

	@Override
	public String toString() {
		return "FeaturesSnake [positions=" + positions + ", lastAction=" + lastAction + ", colorSnake=" + colorSnake
				+ ", invincible=" + invincible + ", sick=" + sick + ", invicibilityCD=" + invicibilityCD + ", sickCD="
				+ sickCD + "]";
	}
	
	
}
