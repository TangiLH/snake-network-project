package model;

import java.util.ArrayList;

import strategies.Strategie;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;

public class Snake {
    private FeaturesSnake featuresSnake;
    
    private static int compteur=0;
    private Strategie strategie;
    private int id;

    public int getId() {
        return this.id;
    }

    public Snake(FeaturesSnake featuresSnake, Strategie strategie){
        this.featuresSnake=new FeaturesSnake(featuresSnake);
        this.strategie=strategie;
        this.id=compteur++;
    }

    public AgentAction nextMove(){
        return strategie.nextMove(featuresSnake.getLastAction());
    }
    public FeaturesSnake getFeaturesSnake() {
        return featuresSnake;
    }

    public void grow(){
        ArrayList<Position>positions=featuresSnake.getPositions();
        Position newPosition;
        if(positions.size()==1){
            Position tete=positions.get(0);
            AgentAction lastAction=featuresSnake.getLastAction();
            newPosition=new Position(tete.getX()-lastAction.x,tete.getY()-lastAction.y);
        }
        else{
            Position queue=positions.get(positions.size()-1);
            Position avantDernier=positions.get(positions.size()-2);
            newPosition=new Position(2*queue.getX()+avantDernier.getX(), 2*queue.getY()+avantDernier.getY());
        }
        positions.add(newPosition);
        
    }

    public void nextPosition(AgentAction agentAction,int max_x,int max_y){
        System.out.println("direction : "+agentAction);
        featuresSnake.setLastAction(agentAction);
        ArrayList<Position>positions=featuresSnake.getPositions();
        for(int i=positions.size()-1;i>0;i--){
            positions.set(i, positions.get(i-1));
        }
        Position tete=positions.get(0);
        Position newPosition = tete.ajouterAction(agentAction);
        newPosition.limites(0,max_x,0,max_y);
        positions.set(0,newPosition);
        //System.out.println("positions"+positions.get(0).getX()+positions.get(0).getY());
    }

    public int getLength(){
        return featuresSnake.getLength();
    }

    public void updateCountDowns(){
        this.featuresSnake.updateCountDowns();
    }

    public boolean checkCollision(int x, int y) {
        return this.featuresSnake.checkCollision(x,y);
    }
}
