package strategies;

import java.util.ArrayList;

import model.InputMap;
import model.Snake;
import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.Position;

public class StrategieSmart implements Strategie{

    private InputMap map;
    private ArrayList<Snake> listSnakes;
    private Strategie random;

    @Override
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput,FeaturesItem featuresItem) {
        for(AgentAction nextAction:getBestMoves(featuresSnake, featuresItem)){
            if(!featuresSnake.getLastAction().isReverse(nextAction)&&checkCollisionsMurs(featuresSnake, nextAction)&&(featuresSnake.isInvincible()||!checkCollisionsSnakes(featuresSnake, nextAction))){
                return nextAction;
            }
        }
        
        System.out.println("random");
        return random.nextMove(featuresSnake, featuresSnake.getLastAction(),featuresItem);

    }

    public StrategieSmart(InputMap map, ArrayList<Snake> listSnakes){
        this.map=map;
        this.listSnakes=listSnakes;
        this.random=StrategieRandom.getStrategieRandom();
    }
    

    private boolean checkCollisionsMurs(FeaturesSnake featuresSnake,AgentAction direction){
        System.out.println("checking for walls");
        Position nextTete=featuresSnake.getPositions().get(0).ajouterAction(direction);
        if(map.get_walls()[nextTete.getX()][nextTete.getY()] && !featuresSnake.isInvincible()){
            System.out.println("walls detected");
            return false;
        }
        return true;
    }

    private Boolean checkCollisionsSnakes(FeaturesSnake featuresSnake,AgentAction direction){
        Position nextTete=featuresSnake.getPositions().get(0).ajouterAction(direction);
        for(Snake snake:listSnakes){
            FeaturesSnake featuresSnake2=snake.getFeaturesSnake();
            for(Position p:featuresSnake2.getPositions()){
                if(p.samePosition(nextTete)){
                    return true;
                }
            }
        }
        return false;
        
    }

    public ArrayList<AgentAction> getBestMoves(FeaturesSnake featuresSnake,FeaturesItem featuresItem){
        ArrayList<AgentAction> retour = new ArrayList<>();
        Position positionSnake=featuresSnake.getPositions().get(0);
        
        for(AgentAction action:AgentAction.values()){
            retour.add(action);
        }
        if(featuresItem!=null){
            Position positionItem=featuresItem.getPosition();
            retour.sort((AgentAction a, AgentAction b)->{return positionItem.distance(positionSnake.ajouterAction(a)).compareTo(positionItem.distance(positionSnake.ajouterAction(b)));});
        }
        
        return retour;
    }
}
