package controller;

import model.Game;

/**
 * Modélise un contrôleur de jeu abstrait
 */
public abstract class AbstractController {
    public Game game;
    private String mapName;

    /**
     * redémarre la partie
     */
    public void restart(){
        game.init();
    }

    /**
     * avance la partie d'une étape
     */
    public void step(){
        game.step();
    }

    /**
     * lance la partie
     */
    public void play(){
        game.play();
    }

    /**
     * met la partie en pause
     */
    public void pause(){
        game.pause();
    }
    
    /**
     * défini la vitesse du jeu
     * 
     */
    public void setSpeed(double speed){
        game.setTime(250/(long)speed);
    }

    /**
     * défini la carte du jeu
     * @param map la carte sous forme de String
     */
    public void setMap(String map){
        this.mapName=map;
    }

    /**
     * retourne la carte du jeu
     * @return String carte lacarte du jeu
     */
    public String getMap() {
        return this.mapName;
    }

    /**
     * commute entre le contrôle par joueur ou par le programme
     * @return
     */
    public abstract boolean togglePlayer();
}
