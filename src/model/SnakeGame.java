package model;

import java.util.ArrayList;
import java.util.Random;

import utils.Position;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;

public class SnakeGame extends Game {
    private InputMap inputMap;
    private ArrayList<Snake> listSnakes;
    private ArrayList<FeaturesItem>listItems;
    private SnakeFactory snakeFactory;
    private Random rng;
    private Boolean singlePlayer;
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
            System.out.println("init new snake "+f.getPositions().get(0).getX());
            listSnakes.add(snakeFactory.getRandomSnake(f));
        }

        ArrayList<FeaturesItem>start_items=inputMap.getStart_items();
        for(FeaturesItem f : start_items){
            listItems.add(f);
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
                checkItems(s);
                i++;
            }
            else{
                System.out.println("illegalmove");
            }
        }
        checkCollisionsMurs();
        checkCollisionsSerpents();
        
        
    }

    @SuppressWarnings("unchecked")
    private void checkCollisionsMurs() {
        ArrayList<Snake>newListSnake=(ArrayList<Snake>) listSnakes.clone();
        Position tete;
        Snake snakeJ;
        for(int j=0;j<listSnakes.size();j++){
            snakeJ=listSnakes.get(j);
            tete=snakeJ.getFeaturesSnake().getPositions().get(0);
            if(inputMap.get_walls()[tete.getX()][tete.getY()]){
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
                        else if(!snakeJ.getFeaturesSnake().isInvincible()){
                            newListSnake.remove(snakeJ);
                            System.out.println("le serpent "+snakeJ.getId()+" est mort");
                        }
                    }
                }
            }
            
        }
        listSnakes=(ArrayList<Snake>) newListSnake.clone();
    }

    public void checkItems(Snake s){
        Position tete=s.getFeaturesSnake().getPositions().get(0);
        for(FeaturesItem item:listItems){
            if(item.getX()==tete.getX()&&item.getY()==tete.getY()){
                item.setX(rng.nextInt(inputMap.getSizeX()));
                item.setY(rng.nextInt(inputMap.getSizeY()));
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
                        s.getFeaturesSnake().setInvincible(true);
                        break;
                    case SICK_BALL:
                        System.out.println("POISON");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public Boolean gameContinue() {
       return listSnakes.size()> (singlePlayer?0:1);
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
    
}
