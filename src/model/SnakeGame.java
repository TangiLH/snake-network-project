package model;

import java.util.ArrayList;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

public class SnakeGame extends Game {
    private InputMap inputMap;
    private ArrayList<Snake> listSnakes;
    private ArrayList<FeaturesItem>listItems;
    private SnakeFactory snakeFactory;
    public SnakeGame(int maxTurn,InputMap inputMap){
        super(maxTurn);
        this.inputMap=inputMap;
        listSnakes=new ArrayList<>();
        listItems=new ArrayList<>();
        snakeFactory=new SnakeFactory();
    }
    @Override
    public void initializeGame() {
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            listSnakes.add(snakeFactory.getRandomSnake(f));
        }

        ArrayList<FeaturesItem>start_items=inputMap.getStart_items();
        for(FeaturesItem f : start_items){
            listItems.add(f);
        }
    }

    public Boolean isLegalMove(Snake snake,AgentAction agentAction){
        return true;
    }

    @Override
    public void takeTurn() {
        AgentAction agentAction;
        for(Snake s : listSnakes){
            agentAction=s.nextMove();
            if(isLegalMove(s, agentAction)){
                s.nextPosition(agentAction);
            }
        }
    }

    @Override
    public Boolean gameContinue() {
       return true;
    }

    @Override
    public void gameOver() {
       System.out.println("game over");
    }

    public ArrayList<FeaturesSnake>getListSnakes(){
        ArrayList<FeaturesSnake> retour=new ArrayList<>();
        for(Snake s:listSnakes){
            retour.add(s.getFeaturesSnake());
        }
        return retour;
    }

    public ArrayList<FeaturesItem>getFeaturesItems(){
        return listItems;
    }
    
}
