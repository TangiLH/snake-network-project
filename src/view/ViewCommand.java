package view;
import javax.swing.*;
import java.awt.*;
/**
 * Classe affichant les boutons de commandes
 */
public class ViewCommand {
    private JFrame jFrame;

    public ViewCommand(){
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

        JButton jb1=new JButton(new ImageIcon("res/img/icon_restart.png"));
        JButton jb2=new JButton(new ImageIcon("res/img/icon_play.png"));
        JButton jb3=new JButton(new ImageIcon("res/img/icon_step.png"));
        JButton jb4=new JButton(new ImageIcon("res/img/icon_pause.png"));

        jp2.add(jb1);
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

        JSlider jSlider=new JSlider(0, 10, 5);
        jSlider.setMajorTickSpacing(1);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        jp3.add(jSlider);

        JLabel jlab=new JLabel("defaut",JLabel.CENTER);

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
}
