package controller;

import model.Game;

public abstract class AbstractController {
    public Game game;

    public void restart(){
        game.init();
    }
    public void step(){
        game.step();
    }
    public void play(){
        game.play();
    }

    public void pause(){
        game.pause();
    }
    
    public void setSpeed(double speed){
        game.setTime(500/(long)speed);
    }
}
