package model;

import strategies.StrategieRandom;
import strategies.StrategieToutDroit;
import utils.AgentAction;
import utils.FeaturesSnake;

public class SnakeFactory {
    public Snake getRandomSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake,StrategieRandom.getStrategieRandom());
    }

    public Snake getStraightLineSnake(FeaturesSnake featuresSnake,AgentAction direction){
        return new Snake(featuresSnake, StrategieToutDroit.getStrategieToutDroit(direction));
    }
}
