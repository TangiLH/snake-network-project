package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Game;
import utils.AgentAction;
import view.ViewCommand;

public class ClientKeyboard implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
        case KeyEvent.VK_Z:
            ControllerClient.setLastKey(AgentAction.MOVE_UP);
            break;
        case KeyEvent.VK_Q:
        	ControllerClient.setLastKey(AgentAction.MOVE_LEFT);
            break;
        case KeyEvent.VK_S:
        	ControllerClient.setLastKey(AgentAction.MOVE_DOWN);
            break;
        case KeyEvent.VK_D:
        	ControllerClient.setLastKey(AgentAction.MOVE_RIGHT);
            break;
        case KeyEvent.VK_UP:
            ControllerClient.setLastKey(AgentAction.MOVE_UP);
            break;
        case KeyEvent.VK_LEFT:
        	ControllerClient.setLastKey(AgentAction.MOVE_LEFT);
            break;
        case KeyEvent.VK_DOWN:
        	ControllerClient.setLastKey(AgentAction.MOVE_DOWN);
            break;
        case KeyEvent.VK_RIGHT:
        	ControllerClient.setLastKey(AgentAction.MOVE_RIGHT);
            break;
        default:
            break;
    }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
