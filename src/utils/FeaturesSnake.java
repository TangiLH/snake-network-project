package utils;

import java.util.ArrayList;


public class FeaturesSnake {



	ArrayList<Position> positions;
	
	private AgentAction lastAction;
	
	ColorSnake colorSnake;
	
	boolean isInvincible;
	boolean isSick;
	int invicibilityCD;
	int sickCD;
	
	public FeaturesSnake(ArrayList<Position> positions, AgentAction lastAction, ColorSnake colorSnake, boolean isInvincible, boolean isSick) {
		
		this.positions = positions;
		this.colorSnake = colorSnake;
		this.lastAction = lastAction;
		
		this.isInvincible = isInvincible;
		
		this.isSick = isSick;

		this.invicibilityCD=0;
		this.sickCD=0;
		
	}

	public FeaturesSnake(FeaturesSnake featuresSnake){
		this.positions=new ArrayList<>();
		for(Position p : featuresSnake.getPositions()){
			this.positions.add(new Position(p));
		}
		this.colorSnake=featuresSnake.getColorSnake();
		this.lastAction=featuresSnake.getLastAction();
		this.isInvincible=featuresSnake.isInvincible;
		this.isSick=featuresSnake.isSick;
		this.invicibilityCD=0;
		this.sickCD=0;
	}
		
	
	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	public void updateCountDowns(){
		this.invicibilityCD-=1;
		this.sickCD-=1;
		this.isInvincible=this.invicibilityCD>0;
		this.isSick=this.sickCD>0;
	}



	public ColorSnake getColorSnake() {
		return colorSnake;
	}


	public void setColorSnake(ColorSnake colorSnake) {
		this.colorSnake = colorSnake;
	}


	public boolean isInvincible() {
		return isInvincible;
	}


	public void setInvincible(boolean isInvincible,int duration) {
		this.isInvincible = isInvincible;
		this.invicibilityCD=duration;
	}


	public boolean isSick() {
		return isSick;
	}


	public void setSick(boolean isSick,int duration) {
		this.isSick = isSick;
		this.sickCD=duration;
	}


	public AgentAction getLastAction() {
		return lastAction;
	}


	public void setLastAction(AgentAction lastAction) {
		this.lastAction = lastAction;
	}

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
}
