package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import model.Game;
import utils.AgentAction;
import view.ViewCommand;

public class Keyboard implements KeyListener{

    private ViewCommand vc;
    private Game game;
        public Keyboard(Game game,ViewCommand vc){
            this.game=game;
            this.vc=vc;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:
                game.setLastKey(AgentAction.MOVE_UP);
                break;
            case KeyEvent.VK_Q:
                game.setLastKey(AgentAction.MOVE_LEFT);
                break;
            case KeyEvent.VK_S:
                game.setLastKey(AgentAction.MOVE_DOWN);
                break;
            case KeyEvent.VK_D:
                game.setLastKey(AgentAction.MOVE_RIGHT);
                break;
            case KeyEvent.VK_P:
                if(game.isRunning()==null || game.isRunning()){
                    vc.pause();
                }
                else{
                    vc.play();
                }
                break;
            case KeyEvent.VK_R:
                vc.restart();
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing burger
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing burger
    }
    
}
