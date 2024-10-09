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
        listSnakes.clear();
        listItems.clear();
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            System.out.println("init new snake "+f.getPositions().get(0).getX());
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
        System.out.println("\ntour"+super.getTurn());
        AgentAction agentAction;
        for(Snake s : listSnakes){
            agentAction=s.nextMove();
            if(isLegalMove(s, agentAction)){
                System.out.println("legal move");
                s.nextPosition(agentAction,inputMap.getSizeX(),inputMap.getSizeY());
            }
            else{
                System.out.println("illegalmove");
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
