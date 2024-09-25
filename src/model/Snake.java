package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import Strategies.Strategie;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;

public class Snake {
    private FeaturesSnake featuresSnake;
    

    private Strategie strategie;

    public Snake(FeaturesSnake featuresSnake, Strategie strategie){
        this.featuresSnake=featuresSnake;
        this.strategie=strategie;
    }

    public AgentAction nextMove(){
        return strategie.nextMove();
    }
    public FeaturesSnake getFeaturesSnake() {
        return featuresSnake;
    }
    public void nextPosition(AgentAction agentAction){
        featuresSnake.setLastAction(agentAction);
        ArrayList<Position>positions=featuresSnake.getPositions();
        for(int i=0;i<positions.size()-1;i++){
            positions.set(i, positions.get(i+1));
        }
        positions.set(positions.size()-1,new Position(agentAction.x, agentAction.y));
    }
}
