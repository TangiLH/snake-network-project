package model;

import java.util.ArrayList;

import strategies.Strategie;
import strategies.StrategieCarre;
import strategies.StrategieJoueur;
import strategies.StrategieRandom;
import strategies.StrategieSmart;
import strategies.StrategieToutDroit;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.SensHoraire;

public class SnakeFactory {
    public Snake getRandomSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake,StrategieRandom.getStrategieRandom());
    }

    public Snake getStraightLineSnake(FeaturesSnake featuresSnake,AgentAction direction){
        return new Snake(featuresSnake, StrategieToutDroit.getStrategieToutDroit(direction));
    }

    public Snake getPlayerSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake, StrategieJoueur.getStrategieJoueur());
    }

    public Snake getSquareSnake(FeaturesSnake featuresSnake, AgentAction direction, int taille){
        return new Snake(featuresSnake, new StrategieCarre(taille,direction,new SensHoraire()));
    }

    public Snake getSmartSnake(FeaturesSnake featuresSnake,InputMap map,ArrayList<Snake> listSnakes){
        return new Snake(featuresSnake, new StrategieSmart(map,listSnakes));
    }
}
