package strategies;

import utils.AgentAction;

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
    public AgentAction nextMove(AgentAction lastMove,AgentAction lastInput) {
        return lastInput!=null?lastInput:AgentAction.MOVE_RIGHT;
    }
    
}
