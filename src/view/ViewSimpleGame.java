package view;
import javax.swing.*;

import model.Game;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/**
 * Classe affichant une vue simple du jeu
 */
@SuppressWarnings("deprecation")
public class ViewSimpleGame implements Observer{
    private Game game;
    private JFrame jFrame;
    private JLabel lab;
    public ViewSimpleGame(Game game){
        this.game=game;
        jFrame=new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);

        lab=new JLabel("defaut", JLabel.CENTER);
        jFrame.add(lab);

    }

    /**
     * rend le cadre visible
     */
    public void affiche(){
        jFrame.setVisible(true);
    }
    public void changeTexte(String str){
        lab.setText(str);
    }

    public void update(Observable o, Object arg){
        lab.setText(Integer.toString(game.getTurn()));
    }

    
}
