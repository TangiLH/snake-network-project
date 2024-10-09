package model;

import java.util.ArrayList;

import strategies.Strategie;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;

public class Snake {
    private FeaturesSnake featuresSnake;
    

    private Strategie strategie;

    public Snake(FeaturesSnake featuresSnake, Strategie strategie){
        this.featuresSnake=new FeaturesSnake(featuresSnake);
        this.strategie=strategie;
    }

    public AgentAction nextMove(){
        return strategie.nextMove();
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
        featuresSnake.setLastAction(agentAction);
        ArrayList<Position>positions=featuresSnake.getPositions();
        for(int i=positions.size()-1;i>0;i--){
            positions.set(i, positions.get(i-1));
        }
        Position tete=positions.get(0);
        int newX = tete.getX()+agentAction.x ;
        int newY= tete .getY()+agentAction.y;
        newX=newX<0?max_x-1:newX;
        newX=newX>=max_x?0:newX;
        newY=newY<0?max_y-1:newY;
        newY=newY>=max_y?0:newY;
        positions.set(0,new Position(newX,newY));
        //System.out.println("positions"+positions.get(0).getX()+positions.get(0).getY());
    }
}
