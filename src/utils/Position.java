package utils;

public class Position {

	private int x;
	private int y;

	
	public Position(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	
	
	public Position(Position p) {
        this.x=p.getX();
		this.y=p.getY();
    }



    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Position ajouterAction(AgentAction agentAction){
		return new Position(x+agentAction.x, y+agentAction.y);
	}

	public boolean samePosition(Position position){
		return x==position.getX()&&y==position.getY();
	}

	public void limites(int min_x, int max_x, int min_y, int max_y) {
		x=x<0?max_x-1:x;
        x=x>=max_x?0:x;
        y=y<0?max_y-1:y;
       	y=y>=max_y?0:y;
	}

	@Override
	public boolean equals(Object o){
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
		Position p2=(Position)o;
		return this.x==p2.getX() && this.y==p2.getY();
	}

	@Override
	public String toString(){
		return "["+this.x+","+this.y+"]";
	}

	public Double distance(Position positionB){
		double deltaX=this.x-positionB.x;
		double deltaY=this.y-positionB.y;
		return Math.sqrt(deltaX*deltaX+deltaY*deltaY);
	}
}
