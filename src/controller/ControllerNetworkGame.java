package controller;

import model.InputMap;
import model.SnakeGame;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;

public class ControllerNetworkGame extends AbstractController {

	private InputMap carte;
    private SnakeGame snakeGame;
    private int playernb;
	public ControllerNetworkGame(String map,int playernb) {
		try {
            carte=new InputMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fichier non trouvé");
        }
        this.playernb=playernb;
        super.setMap(map);
        this.snakeGame=new SnakeGame(500,carte,playernb);
        snakeGame.initializeGame();
        super.game=snakeGame;
	}
	@Override
	public boolean togglePlayer() {
		// TODO Auto-generated method stub
		return false;
	}

}
