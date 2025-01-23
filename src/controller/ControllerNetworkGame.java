package controller;

import java.util.Vector;

import model.InputMap;
import model.SnakeGame;
import utils.AgentAction;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;

/**
 * controleur du jeu snake pour le jeu en ligne
 */
public class ControllerNetworkGame extends AbstractController {

	private InputMap carte;
    public InputMap getCarte() {
		return carte;
	}

	private SnakeGame snakeGame;
    private int playernb;
    private Vector<AgentAction>playerInput;
    Vector<ClientListener> vClient;
    private Vector<String> jsonFeatures;
	public ControllerNetworkGame(String map,int playernb,Vector<AgentAction>playerInput, Vector<ClientListener> vClient,Vector<String>jsonFeatures) {
		try {
            carte=new InputMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
        this.playernb=playernb;
        this.playerInput=playerInput;
        this.vClient=vClient;
        this.jsonFeatures=jsonFeatures;
        super.setMap(map);
        this.snakeGame=new SnakeGame(500,carte,playernb);
        snakeGame.initializeNetworkGame(playerInput);
        super.game=snakeGame;
	}
	@Override
	public boolean togglePlayer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void step(){
        game.step();
        String json=snakeGame.getJsonFeatures();
        System.out.println("Features "+json);
        jsonFeatures.set(0, json);
    }
	

}
