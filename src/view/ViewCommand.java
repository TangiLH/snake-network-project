package view;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AbstractController;
import model.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
/**
 * Classe affichant les boutons de commandes
 */
@SuppressWarnings("deprecation")
public class ViewCommand implements Observer{
    private JFrame jFrame;
    private Game game;
    private JLabel jlab;
    private AbstractController controller;
    private HashMap<String,JButton> mapButton;

    public ViewCommand(Game game,AbstractController controller){
        mapButton=new HashMap<String,JButton>();
        this.controller=controller;
        this.game=game;
        jFrame=new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 +350;
        jFrame.setLocation(dx, dy);
        JPanel jp1=new JPanel(new GridLayout(2,1));
        JPanel jp2=new JPanel(new GridLayout(1,2));
        JPanel jp3=new JPanel(new GridLayout(1,2));


        mapButton.put("restart",new JButton(new ImageIcon("images/icon_restart.png")));
        mapButton.put("play", new JButton(new ImageIcon("images/icon_play.png")));
        mapButton.put("step", new JButton(new ImageIcon("images/icon_step.png")));
        mapButton.put("pause",new JButton(new ImageIcon("images/icon_pause.png")));
        mapButton.get("pause").setEnabled(false);

        mapButton.get("restart").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                restart();
            }
        });

        mapButton.get("play").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                play();
            }
        });

        mapButton.get("step").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                step();
            }
        });

        mapButton.get("pause").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                pause();
            }
        });

        jp2.add(mapButton.get("restart"));
        jp2.add(mapButton.get("play"));
        jp2.add(mapButton.get("step"));
        jp2.add(mapButton.get("pause"));

        JSlider jSlider=new JSlider(1, 10, 1);
        jSlider.setMajorTickSpacing(1);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        jp3.add(jSlider);

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                controller.setSpeed(jSlider.getValue());
            }
        });

        jlab=new JLabel("defaut",JLabel.CENTER);

        jp3.add(jlab);

        jp1.add(jp2);
        jp1.add(jp3);
        jFrame.add(jp1);
    }

    /**
     * rend le cadre visible
     */
    public void affiche(){
        jFrame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        jlab.setText(Integer.toString(game.getTurn()));
    }

    public void play(){
        controller.play();
        mapButton.get("play").setEnabled(false);
        mapButton.get("pause").setEnabled(true);
        mapButton.get("step").setEnabled(false);
    }

    public void pause(){
        controller.pause();
        mapButton.get("play").setEnabled(true);
        mapButton.get("pause").setEnabled(false);
        mapButton.get("step").setEnabled(true);
    }

    public void step(){
        controller.step();
    }

    public void restart(){
        controller.restart();
    }
}
