package controller;

import model.InputMap;
import model.SnakeGame;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;
@SuppressWarnings("deprecation")
public class ControllerSnakeGame extends AbstractController {
    private InputMap carte;
    private PanelSnakeGame panneau;
    private ViewSnakeGame vue;
    private SnakeGame snakeGame;
    private ViewCommand vc;
    private Boolean player;
    
    public ControllerSnakeGame(String map,Boolean player){
        
        try {
            carte=new InputMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
        this.player=player;
        super.setMap(map);
        this.snakeGame=new SnakeGame(500,carte,player);
        snakeGame.initializeGame();
        super.game=snakeGame;
        panneau=new PanelSnakeGame(carte.getSizeX(), carte.getSizeY(), carte.get_walls(),carte.getStart_snakes(),carte.getStart_items());
        
        this.vc=new ViewCommand(super.game,this);
        vue=new ViewSnakeGame(panneau,snakeGame);
        panneau.setFocusable(true);
        panneau.addKeyListener(new Keyboard(snakeGame,vc));
        vue.affiche();
        vc.affiche();
        
        super.game.addObserver(vue);
        super.game.addObserver(vc);
        
    }
    @Override
    public void restart(){
        
        System.out.println("overridden");
        try {
            carte=new InputMap(super.getMap());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
        //this.snakeGame=new SnakeGame(500,carte,player);
        snakeGame.setMap(carte);
        snakeGame.initializeGame();
        super.game=snakeGame;
        panneau=new PanelSnakeGame(carte.getSizeX(), carte.getSizeY(), carte.get_walls(),carte.getStart_snakes(),carte.getStart_items());
        
        //this.vc=new ViewCommand(super.game,this);
        //vue=new ViewSnakeGame(panneau,snakeGame);
        vue.renew(panneau,snakeGame);
        panneau.setFocusable(true);
        panneau.addKeyListener(new Keyboard(snakeGame,vc));
        vue.affiche();
        vc.affiche();
        
        super.game.addObserver(vue);
        super.game.addObserver(vc);
        
    }
}
