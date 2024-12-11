package strategies;

import java.util.ArrayList;

import utils.AgentAction;
import utils.FeaturesSnake;
import utils.FeaturesItem;
/**
 * stratégie pour déplacer le serpent au clavier
 */
public class StrategieJoueur implements Strategie {
    private static StrategieJoueur cache=null;

    private StrategieJoueur(){
        //nothing burger
    }

    public static StrategieJoueur getStrategieJoueur(){
        cache=cache==null?new StrategieJoueur():cache;
        return cache;
    }
    @Override
    public AgentAction nextMove(FeaturesSnake featuresSnake,AgentAction lastInput,ArrayList<FeaturesItem> listItems) {
        return lastInput!=null?lastInput:AgentAction.MOVE_RIGHT;
    }
    
}
