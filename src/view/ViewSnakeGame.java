package view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

@SuppressWarnings("deprecation")
public class ViewSnakeGame implements Observer {
    private PanelSnakeGame panneau;
    private JFrame jFrame;
    public ViewSnakeGame(PanelSnakeGame panneau){
        this.panneau=panneau;
        this.jFrame=new JFrame("Jeu du Serpent");
        jFrame.setSize(new Dimension(panneau.getSizeX(),panneau.getSizeY()));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);
    }
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void affiche(){
        jFrame.setVisible(true);
    }
    
}
