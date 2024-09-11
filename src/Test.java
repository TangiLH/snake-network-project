/**
 * classe de test
 */
public class Test {
    public static void main(String[] args) {
        ViewSimpleGame vsg = new ViewSimpleGame();
        vsg.affiche();
        ViewCommand vc = new ViewCommand();
        vc.affiche();
        SimpleGame sg= new SimpleGame(42);
        sg.launch();
        
    }
}
