package utils;



public enum AgentAction {
	MOVE_UP(0,-1),MOVE_DOWN(0,1),MOVE_LEFT(-1,0),MOVE_RIGHT(1,0);

	public final int x;
	public final int y;

	private AgentAction(int x, int y){
		this.x=x;
		this.y=y;
	}
}
