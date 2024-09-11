/**
 * Classe abstraite servant de base pour modeliser un jeu
 */
public abstract class Game {

    private int turn;
    private int maxTurn;

    public int getTurn() {
        return this.turn;
    }

    public int getMaxTurn() {
        return this.maxTurn;
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

    private Boolean isRunning;

    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract Boolean gameContinue();

    public abstract void gameOver();

    /**
     * initialise le jeu en mettant le compteur à zero et isRunning à vrai
     */
    private void init(){
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