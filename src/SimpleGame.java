public class SimpleGame extends Game{

    @Override
    public void initializeGame() {
        System.out.println("initialisation du Jeu");
    }

    @Override
    public void takeTurn() {
        System.out.println("Tour "+this.getTurn()+" du jeu");
    }

    @Override
    public Boolean gameContinue() {
        System.out.println("La partie continue");
        return true;
    }

    @Override
    public void gameOver() {
        System.out.println("Partie terminée.");
    }
    
}
