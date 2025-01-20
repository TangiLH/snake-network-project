package strategies;

import java.util.ArrayList;

import model.InputMap;
import model.Snake;
import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.Position;

/**
 * stratégie pour déplacer le serpent de façon "intelligente"
 */
public class StrategieSmart implements Strategie{

    private InputMap map;
    private ArrayList<Snake> listSnakes;
    private Strategie random;
    private boolean walls;

    @Override
    /**
     * calcule la liste des meilleurs coups à faire parmi les quatre possibilités
     * parcourt cette liste dans l'ordre jusqu'à trouver un coup qui ne cause pas la mort du serpent
     * renvoie un coup aléatoire sinon
     */
    public AgentAction nextMove(FeaturesSnake featuresSnake, AgentAction lastInput,ArrayList<FeaturesItem> listItems) {
        for(AgentAction nextAction:getBestMoves(featuresSnake, listItems,map)){
            if((featuresSnake.getPositions().size()==1||!featuresSnake.getLastAction().isReverse(nextAction))&&checkCollisionsMurs(featuresSnake, nextAction)&&((featuresSnake.isInvincible()&&featuresSnake.getInvicibilityCD()>1)||checkCollisionsSnakes(featuresSnake, nextAction))){
                return nextAction;
            }
        }
        
        System.out.println("random");
        return random.nextMove(featuresSnake, featuresSnake.getLastAction(),null);
        
    }

    public StrategieSmart(InputMap map, ArrayList<Snake> listSnakes){
        System.out.println("liste : "+Integer.toHexString(listSnakes.hashCode()));
        this.map=map;
        this.listSnakes=listSnakes;
        this.random=StrategieRandom.getStrategieRandom();//instance de random pour des déplacements aléatoires

        walls=false;
        boolean[][]wallslist=map.get_walls();
        for(int i=0;i<map.getSizeX();i++){
            for(int j=0;i<map.getSizeY();i++){
                walls=walls||wallslist[i][j];
            }
        }
        
    }
    
    /**
     * vérifie si le prochain déplacement prévu dirigie vers un mur
     * @param featuresSnake les propriétés du serpent
     * @param direction la direction de déplacement souhaitée
     * @return true si le serpent ne rentrera pas dans un mur
     */
    private boolean checkCollisionsMurs(FeaturesSnake featuresSnake,AgentAction direction){
        System.out.println("checking for walls");
        Position nextTete=featuresSnake.getPositions().get(0).ajouterAction(direction,0,map.getSizeX(),0,map.getSizeY());
        if(nextTete.getX()==map.getSizeX()) nextTete.setX(0);
        if(nextTete.getX()<0) nextTete.setX(map.getSizeX()-1);
        if(nextTete.getY()==map.getSizeY()) nextTete.setY(0);
        if(nextTete.getY()<0) nextTete.setY(map.getSizeY()-1);
        if(map.get_walls()[nextTete.getX()][nextTete.getY()] /*&& !(featuresSnake.isInvincible()&&featuresSnake.getInviciblilityCD()>1)*/){
            System.out.println("walls detected");
            return false;
        }
        return true;
    }

    /**
     * vérifie si le prochain déplacement mène à un serpent
     * @param featuresSnake les propriétés du serpent
     * @param direction la direction voulue
     * @return true si le serpent ne percutera pas un autre serpent/lui-même
     */
    private Boolean checkCollisionsSnakes(FeaturesSnake featuresSnake,AgentAction direction){
        Position nextTete=featuresSnake.getPositions().get(0).ajouterAction(direction,0,map.getSizeX(),0,map.getSizeY());
        if(nextTete.getX()==map.getSizeX()) nextTete.setX(0);
        if(nextTete.getX()<0) nextTete.setX(map.getSizeX()-1);
        if(nextTete.getY()==map.getSizeY()) nextTete.setY(0);
        if(nextTete.getY()<0) nextTete.setY(map.getSizeY()-1);
        for(Snake snake:listSnakes){
            if(snake.checkCollision(nextTete.getX(), nextTete.getY())){
                return false;
            }
        }
        return true;
        
    }

    /**
     * calcule la liste triée des meileurs coups possibles pour atteindre l'objet le plus proche
     * @param featuresSnake les propriétés du serpent
     * @param listItems la liste des objets en jeu
     * @param map la carte du jeu
     * @return ArrayList<AgentAction> la liste triée des meilleurs coups pour le prochain tour afin d'atteindre l'objet
     */
    public ArrayList<AgentAction> getBestMoves(FeaturesSnake featuresSnake,ArrayList<FeaturesItem>listItems,InputMap map){
        ArrayList<AgentAction> retour = new ArrayList<>();
        for(AgentAction action:AgentAction.values()){
            retour.add(action);
        }
        if(listItems==null||listItems.size()==0){
            return retour;
        }
        
        Position positionSnake=featuresSnake.getPositions().get(0);
        
        double distance;
        double minDistance=(double)(map.getSizeX()*map.getSizeY());
        FeaturesItem featuresItem=null;
        for(FeaturesItem item:listItems){
            distance=positionSnake.distance(item.getPosition(), map.getSizeX(), map.getSizeY(), walls);
            if(minDistance>distance){
                minDistance=distance;
                featuresItem=item;
            }

        }

        
        if(featuresItem!=null){
            Position positionItem=featuresItem.getPosition();
            retour.sort((AgentAction a, AgentAction b)->{return positionItem.distance(positionSnake.ajouterAction(a,0,map.getSizeX(),0,map.getSizeY()),map.getSizeX(),map.getSizeY(),walls).compareTo(positionItem.distance(positionSnake.ajouterAction(b,0,map.getSizeX(),0,map.getSizeY()),map.getSizeX(),map.getSizeY(),walls));});
            System.out.println(retour);
        }
        
        return retour;
    }
}
