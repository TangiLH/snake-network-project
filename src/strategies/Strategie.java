package strategies;

import utils.AgentAction;

public interface Strategie {
    public AgentAction nextMove(AgentAction lastMove, AgentAction lastInput);
}
