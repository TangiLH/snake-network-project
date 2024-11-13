package strategies;

import utils.AgentAction;
import utils.Sens;

public class StrategieCarre implements Strategie {
    private int cote;
    private Sens sens;
    private int compteur;
    private StrategieCarre(int cote,Sens sens){
        this.cote=cote;
        this.sens=sens;
        this.compteur=0;
    }

    @Override
    public AgentAction nextMove(AgentAction lastMove,AgentAction lastInput) {
        if(compteur==cote){
            return sens.nexAction(lastMove);
        }
        else{
            return lastMove;
        }
    }
    
}
