package utils;



public enum AgentAction {
	MOVE_UP(0,-1),MOVE_DOWN(0,1),MOVE_LEFT(-1,0),MOVE_RIGHT(1,0);

	public final int x;
	public final int y;

	private AgentAction(int x, int y){
		this.x=x;
		this.y=y;
	}

	/**
	 * determine si l'action effectuée est un demi tour
	 * @param agentAction la future action
	 * @return true si c'est un demi tour, false sinon
	 */
	public boolean isReverse(AgentAction agentAction){
		return agentAction.x+this.x==0 && agentAction.y+this.y==0;
	}
}
