package strategies;

import utils.AgentAction;
import utils.FeaturesSnake;

public class StrategieToutDroit implements Strategie {
    private AgentAction direction;
    private static StrategieToutDroit cache=null;

    public StrategieToutDroit(AgentAction direction){
        this.direction=direction;
    }

    @Override
    public AgentAction nextMove(FeaturesSnake featuresSnake,AgentAction lastInput) {
        return this.direction;
    }

    public static Strategie getStrategieToutDroit(AgentAction direction) {
        cache=cache==null?new StrategieToutDroit(direction):cache;
        return cache;
    }
    
}
