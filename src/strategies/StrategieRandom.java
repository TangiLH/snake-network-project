package strategies;

import java.util.ArrayList;
import java.util.Random;

import utils.AgentAction;
import utils.FeaturesSnake;
import utils.FeaturesItem;
/**
 * stratégie pour déplacer le serpent au hasard
 */
public class StrategieRandom implements Strategie {
    private static Strategie cache;

    private StrategieRandom(){
        //singleton private constructor
    }

    /**
     * public method to get a StrategieRandom object using the Singleton design patern
     * @return a StrategieRandom object
     */
    public static Strategie getStrategieRandom(){
        if(cache==null){
            cache=new StrategieRandom();
        }
        return cache;
    }
    public AgentAction nextMove(FeaturesSnake featuresSnake,AgentAction lastInput,ArrayList<FeaturesItem> listItems){
        Random random=new Random();
        return AgentAction.values()[random.nextInt(4)];
    }
}
