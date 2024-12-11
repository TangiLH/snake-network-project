package model;

import java.util.ArrayList;

import strategies.Strategie;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;
import utils.FeaturesItem;

/**
 * modélise un serpebt
 */
public class Snake {
    private FeaturesSnake featuresSnake;
    
    private static int compteur=0;//compteur statique pour l'id du serpent
    private Strategie strategie;
    private int id;

    /**
     * remet à 0 le compteur statique pour l'id des serpents
     */
    public static void resetId(){
        Snake.compteur=0;
    }
    public int getId() {
        return this.id;
    }

    public Snake(FeaturesSnake featuresSnake, Strategie strategie){
        this.featuresSnake=new FeaturesSnake(featuresSnake);
        this.strategie=strategie;
        this.id=compteur++;

        FeaturesSnake f=featuresSnake;
        System.out.println("init new snake "+f.getPositions().get(0).getX()+ " "+f.getPositions().get(0).getY()+" strategie : "+strategie.toString());
    }

    /**
     * retourne la prochaine action du serpent
     * @param lastInput la dernière touche entrée par le joueur
     * @param listItem la liste des items
     * @return agentAction la prochaine action du serpent
     */
    public AgentAction nextMove(AgentAction lastInput,ArrayList<FeaturesItem>listItem){
        return strategie.nextMove(this.featuresSnake,lastInput,listItem);
    }
    public FeaturesSnake getFeaturesSnake() {
        return featuresSnake;
    }

    /**
     * fait grandir le serpent d'un élément
     */
    public void grow(){
        ArrayList<Position>positions=featuresSnake.getPositions();
        Position newPosition;
        if(positions.size()==1){
            Position tete=positions.get(0);
            AgentAction lastAction=featuresSnake.getLastAction();
            newPosition=new Position(tete.getX()-lastAction.x,tete.getY()-lastAction.y);
        }
        else{
            Position queue=positions.get(positions.size()-1);
            Position avantDernier=positions.get(positions.size()-2);
            newPosition=new Position(2*queue.getX()+avantDernier.getX(), 2*queue.getY()+avantDernier.getY());
        }
        positions.add(newPosition);
        
    }

    /**
     * fait avancer le serpent et déplace son corps
     * @param agentAction l'action de déplacement
     * @param max_x l'abcisse maximale
     * @param max_y l'ordonnée maximale
     */
    public void nextPosition(AgentAction agentAction,int max_x,int max_y){
        System.out.println("direction : "+agentAction);
        featuresSnake.setLastAction(agentAction);
        ArrayList<Position>positions=featuresSnake.getPositions();
        for(int i=positions.size()-1;i>0;i--){
            positions.set(i, positions.get(i-1));
        }
        Position tete=positions.get(0);
        Position newPosition = tete.ajouterAction(agentAction,0,max_x,0,max_y);
        positions.set(0,newPosition);
        //System.out.println("positions"+positions.get(0).getX()+positions.get(0).getY());
    }

    public int getLength(){
        return featuresSnake.getLength();
    }

    /**
     * met à jour les compteurs du serpebt
     */
    public void updateCountDowns(){
        this.featuresSnake.updateCountDowns();
    }

    /**
     * vérifie les collisions du serpent
     * @param x
     * @param y
     * @return
     */
    public boolean checkCollision(int x, int y) {
        return this.featuresSnake.checkCollision(x,y);
    }
}
