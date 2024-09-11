public abstract class Game {

    private int turn;
    private int maxTurn;
    private Boolean isRunning;

    public abstract void initializeGame();

    public abstract void takeTurn();

    public abstract Boolean gameContinue();

    public abstract void gameOver();

    private void init(){
        this.turn=0;
        this.isRunning=true;
        initializeGame();
    }
    
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

    public void pause(){
        isRunning=false;
    }

    public void run(){
        while(isRunning){
            step();
        }
    }
}