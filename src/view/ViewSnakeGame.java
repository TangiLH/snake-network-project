package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import model.SnakeGame;
import utils.FeaturesSnake;
import utils.FeaturesItem;

@SuppressWarnings("deprecation")
public class ViewSnakeGame implements Observer {
    private PanelSnakeGame panneau;
    private JFrame jFrame;
    private SnakeGame snakeGame;
    public ViewSnakeGame(PanelSnakeGame panneau,SnakeGame snakeGame){
        this.snakeGame=snakeGame;
        this.panneau=panneau;
        this.jFrame=new JFrame("Jeu du Serpent");
        jFrame.setSize(new Dimension(this.panneau.getSizeX()*50,this.panneau.getSizeY()*50));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);

        jFrame.add(this.panneau);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public ViewSnakeGame(PanelSnakeGame panneau) {
    	this.panneau=panneau;
        this.jFrame=new JFrame("Jeu du Serpent");
        jFrame.setSize(new Dimension(this.panneau.getSizeX()*50,this.panneau.getSizeY()*50));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);

        jFrame.add(this.panneau);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public void affiche(){
        jFrame.setVisible(true);
    }
    
    public void update(Observable o,Object arg){
        System.out.println("update View");
        this.panneau.updateInfoGame(snakeGame.getListSnakes(), snakeGame.getFeaturesItems());
        this.panneau.repaint();
       // panneau.update(ge());
    }
    
    public void updateView(ArrayList<FeaturesSnake>listSnakes,ArrayList<FeaturesItem>listItems) {
    	System.out.println("update View");
        this.panneau.updateInfoGame(listSnakes, listItems);
        this.panneau.repaint();
    }

    public void changePanel(PanelSnakeGame panneau){
        jFrame.remove(this.panneau);
        this.panneau=panneau;
        jFrame.setSize(new Dimension(panneau.getSizeX()*50,panneau.getSizeY()*50));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);

        jFrame.add(this.panneau);
    }


    public void changeGame(SnakeGame snakeGame2) {
        this.snakeGame=snakeGame2;
    }


    public void renew(PanelSnakeGame panneau2, SnakeGame snakeGame2) {
        this.snakeGame=snakeGame2;
        this.panneau=panneau2;
        //this.jFrame=new JFrame("Jeu du Serpent");
        jFrame.setSize(new Dimension(this.panneau.getSizeX()*50,this.panneau.getSizeY()*50));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);
        jFrame.getContentPane().removeAll();
        jFrame.add(this.panneau);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
