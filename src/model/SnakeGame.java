package model;

import java.util.ArrayList;

import utils.FeaturesSnake;

public class SnakeGame extends Game {
    private InputMap inputMap;
    private ArrayList<Snake> listSnakes;
    private SnakeFactory snakeFactory;
    public SnakeGame(int maxTurn,InputMap inputMap){
        super(maxTurn);
        this.inputMap=inputMap;
        listSnakes=new ArrayList<>();
        snakeFactory=new SnakeFactory();
    }
    @Override
    public void initializeGame() {
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            listSnakes.add(snakeFactory.getRandomSnake(f));
        }
    }

    @Override
    public void takeTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }

    @Override
    public Boolean gameContinue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameContinue'");
    }

    @Override
    public void gameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameOver'");
    }
    
}
