package strategies;

import utils.AgentAction;
import utils.FeaturesSnake;
import utils.FeaturesItem;

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
    public AgentAction nextMove(FeaturesSnake featuresSnake,AgentAction lastInput,FeaturesItem featuresItem) {
        return lastInput!=null?lastInput:AgentAction.MOVE_RIGHT;
    }
    
}
