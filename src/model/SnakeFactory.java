package model;

import Strategies.StrategieRandom;
import utils.FeaturesSnake;

public class SnakeFactory {
    public Snake getRandomSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake,StrategieRandom.getStrategieRandom());
    }
}
