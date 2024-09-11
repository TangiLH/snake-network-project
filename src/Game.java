/**
 * Classe abstraite servant de base pour modeliser un jeu
 */
public abstract class Game {

    private int turn;
    private int maxTurn;
    private Boolean isRunning;

    public int getTurn() {
        return this.turn;
    }

    public int getMaxTurn() {
        return this.maxTurn;
    }

    public Boolean isRunning() {
        return this.isRunning;
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
        this.turn=0;
        this.isRunning=true;
        initializeGame();
    }
    
    /**
     * incremente le compteur et effectue un tour
     */
    public void step(){
        turn++;
        if(gameContinue()&&turn<=maxTurn){
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
        isRunning=false;
    }

    /**
     * lance le jeu en pas à pas
     */
    public void run(){
        while(isRunning){
            step();
        }
    }
}