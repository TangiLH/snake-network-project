package strategies;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;
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
    public AgentAction nextMove(FeaturesSnake featuresSnake,AgentAction lastInput,FeaturesItem featuresItem) {
        AgentAction lastMove=featuresSnake.getLastAction();
        if(this.compteur++==cote){
            this.compteur=0;
            return sens.nexAction(lastMove);
        }
        else{
            return lastMove;
        }
        
    }
    
}
