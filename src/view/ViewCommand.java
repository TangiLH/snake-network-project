package view;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AbstractController;
import model.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public ViewCommand(Game game,AbstractController controller){
        this.controller=controller;
        this.game=game;
        jFrame=new JFrame();
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 +350;
        jFrame.setLocation(dx, dy);
        JPanel jp1=new JPanel(new GridLayout(2,1));
        JPanel jp2=new JPanel(new GridLayout(1,2));
        JPanel jp3=new JPanel(new GridLayout(1,2));


        JButton restartButton=new JButton(new ImageIcon("images/icon_restart.png"));
        JButton playButton=new JButton(new ImageIcon("images/icon_play.png"));
        JButton stepButton=new JButton(new ImageIcon("images/icon_step.png"));
        JButton pauseButton=new JButton(new ImageIcon("images/icon_pause.png"));
        pauseButton.setEnabled(false);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                controller.restart();
            }
        });

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                controller.play();
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stepButton.setEnabled(false);
            }
        });

        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                controller.step();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement){
                controller.pause();
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stepButton.setEnabled(true);
            }
        });

        jp2.add(restartButton);
        jp2.add(playButton);
        jp2.add(stepButton);
        jp2.add(pauseButton);

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
}
