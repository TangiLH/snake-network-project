package controller;

import model.InputMap;
 import model.SnakeGame;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;

public class ControllerSnakeGame extends AbstractController {
    private InputMap carte;
    private PanelSnakeGame panneau;
    private ViewSnakeGame vue;
    @SuppressWarnings("deprecation")
    public ControllerSnakeGame(){
        
        try {
            carte=new InputMap("layouts/arenaNoWall.lay");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
        

        SnakeGame snakeGame=new SnakeGame(500,carte);
        super.game=snakeGame;
        panneau=new PanelSnakeGame(carte.getSizeX(), carte.getSizeY(), carte.get_walls(),carte.getStart_snakes(),carte.getStart_items());
        ViewCommand vc=new ViewCommand(super.game,this);
        vue=new ViewSnakeGame(panneau,snakeGame);
        vue.affiche();
        vc.affiche();
        super.game.addObserver(vue);
        super.game.addObserver(vc);
    }
}
