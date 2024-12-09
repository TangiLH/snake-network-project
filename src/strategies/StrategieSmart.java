package strategies;

import java.util.ArrayList;

import model.InputMap;
import model.Snake;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;

public class StrategieSmart implements Strategie{

    private InputMap map;
    private ArrayList<Snake> listSnakes;
    private Strategie random;

    @Override
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput) {
        AgentAction nextAction=AgentAction.MOVE_DOWN;
        if(!lastInput.isReverse(nextAction)&&!checkCollisionsMurs(featuresSnake, nextAction)&&(!featuresSnake.isInvincible()&&checkCollisionsSnakes(featuresSnake, nextAction))){
            return nextAction;
        }
        nextAction=AgentAction.MOVE_LEFT;
        if(!lastInput.isReverse(nextAction)&&!checkCollisionsMurs(featuresSnake, nextAction)&&(!featuresSnake.isInvincible()&&checkCollisionsSnakes(featuresSnake, nextAction))){
            return nextAction;
        }
         nextAction=AgentAction.MOVE_RIGHT;
        if(!lastInput.isReverse(nextAction)&&!checkCollisionsMurs(featuresSnake, nextAction)&&(!featuresSnake.isInvincible()&&checkCollisionsSnakes(featuresSnake, nextAction))){
            return nextAction;
        }
         nextAction=AgentAction.MOVE_UP;
        if(!lastInput.isReverse(nextAction)&&!checkCollisionsMurs(featuresSnake, nextAction)&&(!featuresSnake.isInvincible()&&checkCollisionsSnakes(featuresSnake, nextAction))){
            return nextAction;
        }
        return random.nextMove(featuresSnake, lastInput);

    }

    public StrategieSmart(InputMap map, ArrayList<Snake> listSnakes){
        this.map=map;
        this.listSnakes=listSnakes;
        this.random=StrategieRandom.getStrategieRandom();
    }
    

    private boolean checkCollisionsMurs(FeaturesSnake featuresSnake,AgentAction direction){

        Position nextTete=featuresSnake.getPositions().get(0).ajouterAction(direction);
        if(map.get_walls()[nextTete.getX()][nextTete.getY()] && featuresSnake.isInvincible()){
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
}
