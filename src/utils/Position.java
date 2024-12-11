package utils;

/**
 * modélise une position dans un plan
 */
public class Position {

	private int x;
	private int y;

	
	public Position(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	
	/**
	 * constructeur par copie
	 * @param p la position à copier
	 */
	public Position(Position p) {
        this.x=p.getX();
		this.y=p.getY();
    }


	/**
	 * retourne x
	 * @return x
	 */
    public int getX() {
		return x;
	}

	/**
	 * modifie x
	 * @param x la nouvelle valeur de x
	 */
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * retourne une nouvelle position calculée à partir d'un déplacement (HAUT BAS GAUCHE DROITE)
	 * @param agentAction la direction de déplacement
	 * @return une nouvelle position
	 */
	public Position ajouterAction(AgentAction agentAction){
		return new Position(x+agentAction.x, y+agentAction.y);
	}

	/**
	 * détermine si la position passée est égale
	 * @param position la position 
	 * @return vrai si les positions sont égales
	 */
	public boolean samePosition(Position position){
		return x==position.getX()&&y==position.getY();
	}

	/**
	 * calcule la position en fonction des limites à ne pas dépasser. si le max est dépassé alors la position devient min et inversement
	 * @param min_x la valeur minimale de x
	 * @param max_x la valeur maximale de x
	 * @param min_y la valeur minimale de y
	 * @param max_y la valeur maximale de y
	 */
	public void limites(int min_x, int max_x, int min_y, int max_y) {
		x=x<min_x?max_x-1:x;
        x=x>=max_x?min_x:x;
        y=y<min_y?max_y-1:y;
       	y=y>=max_y?min_y:y;
	}

	/**
	 * implémenation de la méthode equals pour Position
	 */
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

	/**
	 * calcule la distance entre deux points. S'il n'y a pas de murs, calcule en prenant en compte le retour par l'autre coté de la carte.
	 * @param positionB le deuxième points
	 * @param sizeX la taille de la carte en abcisse
	 * @param sizeY la taille de la carte en ordonnée
	 * @param murs vrai s'il y a des murs faux sinon. 
	 * @return
	 */
	public Double distance(Position positionB,int sizeX,int sizeY,boolean murs){
		double deltaX=this.x-positionB.x;
		double deltaY=this.y-positionB.y;
		Double retour;
		if(!murs){
			double deltaXalt=Math.abs(sizeX-Math.max(this.x,positionB.x)+Math.abs(Math.min(this.x,positionB.x)));
			double deltaYalt=Math.abs(sizeY-Math.max(this.y,positionB.y)+Math.abs(Math.min(this.y,positionB.y)));
			Double minX=Math.min(deltaX, deltaXalt);
			Double minY=Math.min(deltaY, deltaYalt);
			retour=Math.sqrt(minX*minX+minY*minY);
		}
		else{
			retour =Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		}
		return retour;
	}
}
