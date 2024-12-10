package strategies;

import java.util.ArrayList;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

public interface Strategie {
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput,ArrayList<FeaturesItem> listItems);
}
