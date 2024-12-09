package strategies;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

public interface Strategie {
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput,FeaturesItem featuresItem);
}
