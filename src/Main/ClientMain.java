package Main;
import controller.ControllerClient;

public class ClientMain {
	public static void main(String args[]) {
		String[] params = {"127.0.0.1","49100"};
		ControllerClient.launchClient(params);
	}

}
