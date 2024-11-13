package model;

import java.util.ArrayList;
import java.util.Random;

import utils.Position;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.ItemType;

public class SnakeGame extends Game {
    private InputMap inputMap;
    private ArrayList<Snake> listSnakes;
    private ArrayList<FeaturesItem>listItems;
    private SnakeFactory snakeFactory;
    private Random rng;
    private Boolean singlePlayer;
    private int sickDuration=10;
    private int invicibilityDuration=10;

    public SnakeGame(int maxTurn,InputMap inputMap){
        super(maxTurn);
        this.inputMap=inputMap;
        listSnakes=new ArrayList<>();
        listItems=new ArrayList<>();
        snakeFactory=new SnakeFactory();
        rng=new Random();
        singlePlayer=inputMap.getStart_snakes().size()==1;
    }

    @Override
    public void initializeGame() {
        listSnakes.clear();
        listItems.clear();
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            System.out.println("init new snake "+f.getPositions().get(0).getX()+ " "+f.getPositions().get(0).getY());
            listSnakes.add(snakeFactory.getStraightLineSnake(f,AgentAction.MOVE_RIGHT));
        }

        ArrayList<FeaturesItem>start_items=inputMap.getStart_items();
        for(FeaturesItem f : start_items){
            System.out.println("init new item "+f.getX()+ " "+f.getY());
            listItems.add(f.clone());
        }
    }

    public Boolean isLegalMove(Snake snake,AgentAction agentAction){
        if(snake.getFeaturesSnake().getLastAction().isReverse(agentAction)){
            return false;
        }
        return true;
    }

    @Override
    public void takeTurn() {
        System.out.println("\ntour"+super.getTurn());
        AgentAction agentAction;
        int i=0;
        int taille=listSnakes.size();
        Snake s;
        while(i<taille){
            s=listSnakes.get(i);
            agentAction=s.nextMove();
            if(isLegalMove(s, agentAction)){
                System.out.println("legal move");
                s.nextPosition(agentAction,inputMap.getSizeX(),inputMap.getSizeY());
                s.updateCountDowns();
                i++;
            }
            else{
                System.out.println("illegalmove");
            }
        }
        checkCollisionsMurs();
        checkCollisionsSerpents();
        listSnakes.forEach((snake)->checkItems(snake));
        System.out.println("free positions : "+getFreePositions()+System.lineSeparator());
        
    }

    @SuppressWarnings("unchecked")
    private void checkCollisionsMurs() {
        ArrayList<Snake>newListSnake=(ArrayList<Snake>) listSnakes.clone();
        Position tete;
        Snake snakeJ;
        for(int j=0;j<listSnakes.size();j++){
            snakeJ=listSnakes.get(j);
            tete=snakeJ.getFeaturesSnake().getPositions().get(0);
            if(inputMap.get_walls()[tete.getX()][tete.getY()] && !snakeJ.getFeaturesSnake().isInvincible()){
                newListSnake.remove(snakeJ);
                System.out.println("le serpent "+snakeJ.getId()+" a percuté un mur et est mort");
            }
        }
        listSnakes=(ArrayList<Snake>) newListSnake.clone();
    }
    @SuppressWarnings("unchecked")
    public void checkCollisionsSerpents(){
        ArrayList<Snake>newListSnake=(ArrayList<Snake>) listSnakes.clone();
        Position tete;
        Snake snakeI,snakeJ;
        for(int i=0;i<listSnakes.size();i++){
            snakeI=listSnakes.get(i);
            ArrayList<Position>listePos=snakeI.getFeaturesSnake().getPositions();
            for(Position p: listePos){
                for(int j=0;j<listSnakes.size();j++){
                    snakeJ=listSnakes.get(j);
                    tete=snakeJ.getFeaturesSnake().getPositions().get(0);
                    if(tete.samePosition(p)&&i!=j){
                        if(i!=j&&snakeJ.getLength()>snakeI.getLength()&& !snakeI.getFeaturesSnake().isInvincible()){
                            newListSnake.remove(snakeI);
                            System.out.println("le serpent "+snakeI.getId()+" est mort");
                        }
                        else if(!snakeJ.getFeaturesSnake().isInvincible()&&!snakeJ.getFeaturesSnake().isInvincible()){
                            newListSnake.remove(snakeJ);
                            System.out.println("le serpent "+snakeJ.getId()+" est mort");
                        }
                    }
                }
            }
            
        }
        listSnakes=(ArrayList<Snake>) newListSnake.clone();
    }

    /**
     * vérifie les collisions entre le serpent et les objets. Appliques les effets aux serpents. Modifie la position et le type de l'objet consommé
     * @param s le serpent
     */
    public void checkItems(Snake s){
        Position tete=s.getFeaturesSnake().getPositions().get(0);
        boolean collisionSerpent;
        ArrayList<FeaturesItem>itemASuppr=new ArrayList<>();
        for(FeaturesItem item:listItems){
            if(item.getX()==tete.getX()&&item.getY()==tete.getY()&& ! s.getFeaturesSnake().isSick()){
                switch(item.getItemType()){
                    case APPLE:
                        System.out.println("POMME");
                        s.grow();
                        break;
                    case BOX:
                        System.out.println("BOITE");
                        break;
                    case INVINCIBILITY_BALL:
                        System.out.println("INVICIBILITE");
                        s.getFeaturesSnake().setInvincible(true,invicibilityDuration);
                        break;
                    case SICK_BALL:
                        System.out.println("POISON");
                        s.getFeaturesSnake().setSick(true, sickDuration);
                        break;
                    default:
                        break;
                }
                /*boolean[][]murs=inputMap.get_walls();
                int cpt=inputMap.getSizeX()*inputMap.getSizeY();//empeche les boucles infinies
                do {
                    collisionSerpent=false;
                    item.setX(rng.nextInt(inputMap.getSizeX()));
                    item.setY(rng.nextInt(inputMap.getSizeY()));
                    //listSnakes.forEach((serpent) -> collisionSerpent=collisionSerpent&& serpent.checkCollision(item.getX(),item.getY()));
                    for(Snake serpent : listSnakes){
                       collisionSerpent=collisionSerpent || serpent.checkCollision(item.getX(), item.getY());
                    }
                    cpt--;
                } while ((murs[item.getX()][item.getY()]||collisionSerpent)&&cpt>0);
                if(cpt<=0){
                    listItems.remove(item);
                }*/
                ArrayList<Position>positionsLibres=getFreePositions();
                if(positionsLibres.size()==0){
                    itemASuppr.add(item);
                }
                else{
                    item.setPosition(positionsLibres.get(rng.nextInt(positionsLibres.size())));
                }
                item.setItemType(ItemType.values()[(rng.nextInt(ItemType.values().length))]);
            }
        }
        listItems.removeAll(itemASuppr);
    }

    @Override
    public Boolean gameContinue() {
       return listSnakes.size()> (singlePlayer?0:1) && (listItems.size()>0);
    }

    @Override
    public void gameOver() {
       System.out.println("game over");
       if(listSnakes.size()>0){
        System.out.println("le serpent "+listSnakes.get(0).getId()+" a gagne");
       }
       else{
        System.out.println("Personne n'a gagne");
       }
    }

    public ArrayList<FeaturesSnake>getListSnakes(){
        ArrayList<FeaturesSnake> retour=new ArrayList<>();
        for(Snake s:listSnakes){
            retour.add(s.getFeaturesSnake());
        }
        return retour;
    }

    public ArrayList<FeaturesItem>getFeaturesItems(){
        return listItems;
    }

    /**
     * renvoie la liste des positions libres
     * @return une ArrayList de positions libres
     */
    public ArrayList<Position> getFreePositions(){
        boolean[][]murs=inputMap.get_walls();
        ArrayList<Position> listePositions=new ArrayList<>();
        for(int x=0;x<inputMap.getSizeX();x++){
            for(int y=0;y<inputMap.getSizeY();y++){
                if(!murs[x][y]){
                    listePositions.add(new Position(x, y));
                }
                
            }
        }
        

        for(Snake s : listSnakes){
            for(Position p2:s.getFeaturesSnake().getPositions()){
                listePositions.removeIf(p->(p.equals(p2)));
            }
        }

        for(FeaturesItem i : listItems){
            listePositions.removeIf(p->(p.equals(i.getPosition())));
        }
        
        return listePositions;
    }
    
}
