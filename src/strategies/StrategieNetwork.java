package strategies;

import java.util.ArrayList;
import java.util.Vector;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

/**
 * strategie ou le serpent est controllé par un client distant
 */
public class StrategieNetwork implements Strategie {
	private Vector<AgentAction>playerInput;
	private int id;
	public StrategieNetwork(Vector<AgentAction> playerInput,int id) {
		this.playerInput=playerInput;
		this.id=id;
	}

	@Override
	public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput, ArrayList<FeaturesItem> listItems) {
		return playerInput.get(id);
	}

}
