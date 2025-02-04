package controller;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import model.InputMap;
import model.SnakeGame;
import utils.AgentAction;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;

/**
 * controleur du jeu snake pour le jeu en ligne
 */
@SuppressWarnings("deprecation")
public class ControllerNetworkGame extends AbstractController implements Observer {

	private InputMap carte;
    public InputMap getCarte() {
		return carte;
	}

	private SnakeGame snakeGame;
    private int playernb;
    private Vector<AgentAction>playerInput;
    Vector<ClientListener> vClient;
    private Vector<String> jsonFeatures;
    private AtomicInteger gameUpdated;
    private AtomicBoolean continuer;
    
	public ControllerNetworkGame(String map,Vector<AgentAction>playerInput, Vector<ClientListener> vClient,Vector<String>jsonFeatures,AtomicInteger gameUpdated,AtomicBoolean continuer) {
		try {
            carte=new InputMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
		
		//this=ControllerNetworkGame()
		
	}
	public ControllerNetworkGame(InputMap inputMap, Vector<AgentAction> playerInput, Vector<ClientListener> vClient,
			Vector<String> jsonFeatures, AtomicInteger gameUpdated, AtomicBoolean continuer) {
		this.carte=inputMap;
		this.playernb=carte.getStart_snakes().size();
        this.playerInput=playerInput;
        this.vClient=vClient;
        this.jsonFeatures=jsonFeatures;
        this.gameUpdated=gameUpdated;
        super.setMap(inputMap.getFilename());
        this.snakeGame=new SnakeGame(500,carte,playernb);
        this.snakeGame.setTime(150);
        snakeGame.initializeNetworkGame(this.playerInput,playernb);
        super.game=snakeGame;
        this.continuer=continuer;
	}
	@Override
	public boolean togglePlayer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void play() {
		this.game.addObserver(this);
		this.game.play();
	}
	
	@Override
	public void step(){
        game.step();
        String json=snakeGame.getJsonFeatures();
        System.out.println("Features "+json);
        jsonFeatures.set(0, json);
    }
	@Override
	public void update(Observable o, Object arg) {
		if(game.isRunning()) {
			String json=snakeGame.getJsonFeatures();
	        System.out.println("Features "+json);
	        jsonFeatures.set(0, json);
	        this.gameUpdated.addAndGet(1);
		}
		else {
			System.out.println("Game over, terminating all sockets");
			this.continuer.set(false);
		}
		
	}
	public int getSnakenb() {
		return this.playernb;
	}
	

}
