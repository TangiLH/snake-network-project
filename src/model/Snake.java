package model;

import Strategies.Strategie;
import utils.AgentAction;
import utils.FeaturesSnake;

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
}
