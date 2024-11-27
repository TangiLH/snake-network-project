package model;

import java.util.Observable;

import utils.AgentAction;

/**
 * Classe abstraite servant de base pour modeliser un jeu
 */
@SuppressWarnings("deprecation")
public abstract class Game extends Observable implements Runnable {

    private int turn;
    private int maxTurn;
    private Boolean isRunning;
    private long time;
    private InputMap map;

    private Thread thread;
        private AgentAction lastKey;
    
        public Integer getTurn() {
            return this.turn;
        }
    
        public Integer getMaxTurn() {
            return this.maxTurn;
        }
    
        public Boolean isRunning() {
            return this.isRunning;
        }
    
        public void setTime(long time){
            this.time=time;
        }
    
        public long getTime(){
            return this.time;
        }
    
        /**
         * constructeur de la classe abstraite
         * @param maxTurn le nombre de tours maximal
         * @param time le temps entre chaque tour en millisecondes
         */
        public Game(int maxTurn,long time){
            this.maxTurn=maxTurn;
            this.time=time;
        }
        public Game(int maxTurn){
            this.maxTurn=maxTurn;
            this.time=500;
            this.map=null;
        }
    
        /**
         * constructeur de la classe abstraite avec time par defaut
         * @param maxTurn le nombre de tours maximal
         */
        public Game(int maxTurn,InputMap map){
            this.maxTurn=maxTurn;
            this.time=500;
            this.map=map;
        }
    
        /**
         * initalise la partie
         */
        public abstract void initializeGame();
    
        /**
         * effectue un tour
         */
        public abstract void takeTurn();
    
        /**
         * determine si la partie doit continuer
         * @return true si la partie continue, false sinon
         */
        public abstract Boolean gameContinue();
    
        /**
         * termine la partie
         */
        public abstract void gameOver();
    
        /**
         * initialise le jeu en mettant le compteur à zero et isRunning à vrai
         */
        public void init(){
            System.out.println("init");
            this.turn=0;
            this.isRunning=true;
            this.initializeGame();
            this.setChanged();
            this.notifyObservers();
        }
        
        /**
         * incremente le compteur et effectue un tour
         */
        public void step(){
            System.out.println("step "+turn);
            turn++;
            this.setChanged();
            this.notifyObservers();
            if(gameContinue()){
                takeTurn();
            }
            else{
                isRunning=false;
                gameOver();
            }
        }
    
        /**
         * met le jeu en pause
         */
        public void pause(){
            System.out.println("pause");
            isRunning=false;
            this.setChanged();
        }
    
        /**
         * continue le jeu
         */
        public void play(){
            System.out.println("play");
            launch();
            this.setChanged();
        }
    
        /**
         * lance le jeu en pas à pas
         */
        public void run(){
            System.out.println("run");
            while(isRunning){
                step();
                this.notifyObservers();
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    
        /**
         * lance le jeu en utilisant le thread
         */
        public void launch(){
            System.out.println("launch");
            this.isRunning=true;
            this.thread=new Thread(this);
            thread.start();
        }
    
        public void setLastKey(AgentAction action) {
            this.lastKey=action;
            System.out.println("Player action "+action+System.lineSeparator());
    }
    
    public AgentAction getLastKey(){
        return this.lastKey;
    }

    public void setMap(InputMap carte) {
        this.map=carte;
    }

    public InputMap getMap() {
        return this.map;
    }

    public void resetTurn(){
        this.turn=0;
    }
}