import controller.ControllerSimpleGame;
import controller.ControllerSnakeGame;
import model.SimpleGame;
import view.ViewCommand;
import view.ViewSimpleGame;

/**
 * classe de test
 */
public class Test {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        /*SimpleGame sg= new SimpleGame(42);
        ViewSimpleGame vsg = new ViewSimpleGame(sg);
        ViewCommand vc = new ViewCommand(sg);
        sg.addObserver(vc);
        sg.addObserver(vsg);
        vsg.affiche();
        
        vc.affiche();
       
        sg.launch();*/

        //ControllerSimpleGame csg=new ControllerSimpleGame();
        ControllerSnakeGame csg=new ControllerSnakeGame("layouts/testItems3.lay");
    }
}
