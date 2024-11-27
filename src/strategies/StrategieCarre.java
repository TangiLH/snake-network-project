package strategies;

import utils.AgentAction;
import utils.Sens;

public class StrategieCarre implements Strategie {
    private int cote;
    private AgentAction direction;
    private int compteur;
    private Sens sens;
    public StrategieCarre(int cote,AgentAction direction,Sens sens){
        this.cote=cote;
        this.direction=direction;
        this.compteur=0;
        this.sens=sens;
    }

    @Override
    public AgentAction nextMove(AgentAction lastMove,AgentAction lastInput) {
        if(this.compteur++==cote){
            this.compteur=0;
            return sens.nexAction(lastMove);
        }
        else{
            return lastMove;
        }
        
    }
    
}
