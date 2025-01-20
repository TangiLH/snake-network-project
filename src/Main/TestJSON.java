package Main;
import model.InputMap;
import model.SnakeGame;

public class TestJSON {
	public static void main(String args[]) {
	private InputMap carte;
	try {
        carte=new InputMap("layouts/alone.lay");
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("fichier non trouvé");
    }
	SnakeGame sg=new SnakeGame()
	}
}
