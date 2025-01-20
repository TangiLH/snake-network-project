package model;

import java.util.ArrayList;
import java.util.Vector;

import strategies.Strategie;
import strategies.StrategieCarre;
import strategies.StrategieJoueur;
import strategies.StrategieNetwork;
import strategies.StrategieRandom;
import strategies.StrategieSmart;
import strategies.StrategieToutDroit;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.SensHoraire;

/**
 * fabrique pour serpents
 */
public abstract class SnakeFactory {
    /**
     * retourne un serpent aux déplacements aléatoires
     * @param featuresSnake les caractéristiques du serpent
     * @return Snake un serpent
     */
    public static Snake getRandomSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake,StrategieRandom.getStrategieRandom());
    }
    /**
     * retourne un serpent aux déplacements en ligne droite
     * @param featuresSnake les caractéristiques du serpent
     * @param direction la direction de la ligne droite
     * @return Snake un serpent
     */
    public static Snake getStraightLineSnake(FeaturesSnake featuresSnake,AgentAction direction){
        return new Snake(featuresSnake, StrategieToutDroit.getStrategieToutDroit(direction));
    }

    /**
     * retourne un serpent contrôlé au clavier
     * @param featuresSnake les caractéristiques du serpent
     * @return Snake un serpent
     */
    public static Snake getPlayerSnake(FeaturesSnake featuresSnake){
        return new Snake(featuresSnake, StrategieJoueur.getStrategieJoueur());
    }

    /**
     * retourne un serpent aux déplacements en carré
     * @param featuresSnake les caractéristiques du serpent
     * @param direction la direction du premier déplacement
     * @param taille la taille du carré
     * @return Snake un serpent
     */
    public static Snake getSquareSnake(FeaturesSnake featuresSnake, AgentAction direction, int taille){
        return new Snake(featuresSnake, new StrategieCarre(taille,direction,SensHoraire.getSensHoraire()));
    }

    /**
     * retourne un serpent aux déplacements "intelligents"
     * @param featuresSnake les caractéristiques du serpent
     * @param map la carte du jeu
     * @param listSnakes la liste des serpents du jeu
     * @return Snake un serpent
     */
    public static Snake getSmartSnake(FeaturesSnake featuresSnake,InputMap map,ArrayList<Snake> listSnakes){
        return new Snake(featuresSnake, new StrategieSmart(map,listSnakes));
    }
    
    public static Snake getNetworkSnake(FeaturesSnake featuresSnake, Vector<AgentAction> playerInput,int id) {
    	return new Snake(featuresSnake,new StrategieNetwork(playerInput,id));
    }
}
