package strategies;

import java.util.ArrayList;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

/**
 * interface pour les stratégies de serpents
 */
public interface Strategie {
    /**
     * retourne la prochaine action du serpent
     * @param featuresSnake les propriétés du serpent
     * @param lastInput la dernière touche entrée par le joueur
     * @param listItems la liste des objets en jeu
     * @return
     */
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput,ArrayList<FeaturesItem> listItems);
}
