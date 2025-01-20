package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectReader;
import java.util.Random;

import utils.Position;

import utils.AgentAction;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.ItemType;

/**
 * modélise un jeu de snake
 */
public class SnakeGame extends Game {

    private InputMap inputMap;//carte du jeu
    private ArrayList<Snake> listSnakes;//liste des serpents
    private ArrayList<FeaturesItem>listItems;//liste des objets
    private Random rng; //générateur aléatoire
    private Boolean singleStartSnake; //booleen si le jeu a un seul serpent au départ ou non
    private int sickDuration=10; //durée de l'effet malade
    private int invicibilityDuration=10; //durée de l'effer invicible
    private int playernb; //nombre de joueurs
    private ArrayList<Integer>listeMort; //liste des serpents éliminés dans l'ordre

    public SnakeGame(int maxTurn,InputMap map,int player){
        super(maxTurn,map);
        this.inputMap=super.getMap();
        this.playernb=playernb;
        listSnakes=new ArrayList<>();
        listItems=new ArrayList<>();
        listeMort=new ArrayList<>();
        rng=new Random();
        singleStartSnake=inputMap.getStart_snakes().size()==1;
    }
    /**
     * initialise le jeu
     */
    @Override
    public void initializeGame() {
        this.inputMap=super.getMap();
        super.resetTurn();
        int tempPlayer=this.playernb;
        listSnakes.clear();
        listItems.clear();
        listeMort.clear();
        Snake.resetId();
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            if(tempPlayer>0){
                listSnakes.add(SnakeFactory.getPlayerSnake(f));
                tempPlayer--;
            }
            else{
                Snake snake=SnakeFactory.getSmartSnake(f,inputMap,listSnakes);
                listSnakes.add(snake);
            }
            
        }

        ArrayList<FeaturesItem>start_items=inputMap.getStart_items();
        for(FeaturesItem f : start_items){
            System.out.println("init new item "+f.getX()+ " "+f.getY());
            listItems.add(f.clone());
        }
        this.singleStartSnake=inputMap.getStart_snakes().size()==1;
    }
    
    public void initializeNetworkGame() {
    	this.inputMap=super.getMap();
        super.resetTurn();
        int tempPlayer=this.playernb;
        listSnakes.clear();
        listItems.clear();
        listeMort.clear();
        Snake.resetId();
        ArrayList<FeaturesSnake>start_snakes=inputMap.getStart_snakes();
        for(FeaturesSnake f : start_snakes){
            if(tempPlayer>0){
                listSnakes.add(SnakeFactory.getNetworkSnake(f));
                tempPlayer--;
            }
            else{
                Snake snake=SnakeFactory.getSmartSnake(f,inputMap,listSnakes);
                listSnakes.add(snake);
            }
            
        }

        ArrayList<FeaturesItem>start_items=inputMap.getStart_items();
        for(FeaturesItem f : start_items){
            System.out.println("init new item "+f.getX()+ " "+f.getY());
            listItems.add(f.clone());
        }
        this.singleStartSnake=inputMap.getStart_snakes().size()==1;
    }

    /**
     * vérifie si l'action est légale pour le serpent (pas le droit de faire demi tour)
     * @param snake
     * @param agentAction
     * @return
     */
    public Boolean isLegalMove(Snake snake,AgentAction agentAction){
        if(snake.getFeaturesSnake().getPositions().size()>1 && snake.getFeaturesSnake().getLastAction().isReverse(agentAction)){
            return false;
        }
        return true;
    }

    /**
     * effectue un tour de jeu
     */
    @Override
    public void takeTurn() {
        System.out.println("\ntour"+super.getTurn());
        AgentAction agentAction;
        int i=0;
        int taille=listSnakes.size();
        Snake s;
        while(i<taille){
            s=listSnakes.get(i);
            agentAction=s.nextMove(super.getLastKey(),listItems.isEmpty()?null:listItems);
            if(isLegalMove(s, agentAction)){
                System.out.println("legal move");
                s.nextPosition(agentAction,inputMap.getSizeX(),inputMap.getSizeY());
                s.updateCountDowns();
                i++;
            }
            else{
                System.out.println("illegalmove");
                s.nextPosition(s.getFeaturesSnake().getLastAction(),inputMap.getSizeX(),inputMap.getSizeY());
                s.updateCountDowns();
                i++;
            }
        }
        checkCollisionsMurs();
        checkCollisionsSerpents();
        listSnakes.forEach((snake)->checkItems(snake));
       // System.out.println("free positions : "+getFreePositions()+System.lineSeparator());
        
    }

    /**
     * vérifie pour chaque serpent s'il est entré en collision avec un mur et l'élimine le cas échéant
     */
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
                if (! listeMort.contains(snakeJ.getId()))listeMort.add(snakeJ.getId());
            }
        }
        listSnakes=(ArrayList<Snake>) newListSnake.clone();
    }

    /**
     * vérifie pour chaque serpent s'il est mort à cause d'un autre serpent, et l'élimine le cas échéant
     */
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
                    if(tete.samePosition(p)&&p!=tete){
                        if((i==j||snakeJ.getLength()>snakeI.getLength())&& !snakeI.getFeaturesSnake().isInvincible()){
                            newListSnake.remove(snakeI);
                            System.out.println("le serpent "+snakeI.getId()+" est mort");
                            if (! listeMort.contains(snakeI.getId()))listeMort.add(snakeI.getId());
                        }
                        else if(!snakeJ.getFeaturesSnake().isInvincible()&&!snakeJ.getFeaturesSnake().isInvincible()){
                            newListSnake.remove(snakeJ);
                            System.out.println("le serpent "+snakeJ.getId()+" est mort");
                            if (! listeMort.contains(snakeJ.getId()))listeMort.add(snakeJ.getId());
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
        ArrayList<FeaturesItem>itemASuppr=new ArrayList<>();
        for(FeaturesItem item:listItems){
            if(item.getX()==tete.getX()&&item.getY()==tete.getY()&& ! s.getFeaturesSnake().isSick()){
                switch(item.getItemType()){
                    case BOX:
                        System.out.println("BOITE");
                        item.setItemType(ItemType.getRandomEffect(rng.nextInt(ItemType.values().length)));
                    case APPLE:
                        System.out.println("POMME");
                        s.grow();
                        break;
                    case INVINCIBILITY_BALL:
                        System.out.println("INVICIBILITE");
                        s.getFeaturesSnake().setInvincible(true,invicibilityDuration);
                        break;
                    case SICK_BALL:
                        System.out.println("POISON");
                        if(!s.getFeaturesSnake().isInvincible()){
                            s.getFeaturesSnake().setSick(true, sickDuration);
                        }
                        break;
                    default:
                        break;
                }
                
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

    /**
     * détermine si la partie doit continuer
     * @return Boolean vrai si la partie continue faux sinon
     */
    @Override
    public Boolean gameContinue() {
       return listSnakes.size()> (singleStartSnake?0:1) && (listItems.size()>-1);
    }

    /**
     * est appelé quand la partie se termine
     */
    @Override
    public void gameOver() {
       System.out.println("game over");
       System.out.println("Les serpents sont mort dans cet ordre :"+listeMort.toString());
       if(listSnakes.size()>0){
        System.out.println("le serpent "+listSnakes.get(0).getId()+" a gagne");
       }
       else{
        System.out.println("Personne n'a gagne");
       }
    }

    /**
     * retourne la liste des serpents
     * @return listSnakes la liste des serpents
     */
    public ArrayList<FeaturesSnake>getListSnakes(){
        ArrayList<FeaturesSnake> retour=new ArrayList<>();
        for(Snake s:listSnakes){
            retour.add(s.getFeaturesSnake());
        }
        return retour;
    }

    /**
     * retourne la liste des objets
     * @return listItems la liste des objets
     */
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

    
    
    public String toJson() {

    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(this);
			return json;
		} 
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";
    }
    
    public String getJsonFeatures() {
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	ArrayList<ArrayList<String>>featuresList=new ArrayList<>();
    	ArrayList<String>jsonItems=new ArrayList<>();
    	ArrayList<String>jsonSnakes=new ArrayList<>();
		try {
			for(Snake s:listSnakes) {
				jsonSnakes.add(s.getFeaturesSnake().toJson());
			}
			for(FeaturesItem i:listItems) {
				jsonItems.add(i.toJson());
			}
			featuresList.add(jsonSnakes);
			featuresList.add(jsonItems);
			String json = ow.writeValueAsString(featuresList);
			return json;
		} 
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";
    }
    
    public static SnakeGame fromJson(String json) {
    	ObjectReader or= new ObjectMapper().reader();
    	try {
			return (SnakeGame)or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public String toString() {
    	StringBuilder sb= new StringBuilder();
    	sb.append(this.inputMap);
    	sb.append(this.listItems);
    	sb.append(this.listSnakes);
    	return sb.toString();
    }
	public void setPlayer(int playernb2) {
		this.playernb=playernb2;
		
	}
    
}
