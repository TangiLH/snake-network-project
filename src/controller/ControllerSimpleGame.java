package controller;

import model.SimpleGame;
import view.ViewCommand;
import view.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController {
    private ViewCommand vc;
    private ViewSimpleGame vsg;
    @SuppressWarnings("deprecation")
    public ControllerSimpleGame(){
        super.game=new SimpleGame(42);
        vc=new ViewCommand(game,this);
        vsg=new ViewSimpleGame(game);
        vc.affiche();
        vsg.affiche();
        super.game.addObserver(vc);
        super.game.addObserver(vsg);
    }

    public boolean togglePlayer(){
        return true;
    }
}
