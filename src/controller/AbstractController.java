package controller;

import model.Game;


public abstract class AbstractController {
    public Game game;
    private String mapName;

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

    public void setMap(String map){
        this.mapName=map;
    }
    public String getMap() {
        return this.mapName;
    }
    public abstract boolean togglePlayer();
}
