package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.InputMap;
import utils.AgentAction;
import view.PanelSnakeGame;
import view.ViewCommand;
import view.ViewSnakeGame;

/**
 * classe principale du client
 * se connecte au port et envoie les instructions clavier
 */
public class ControllerClient implements Runnable {
	Socket so;
	AtomicBoolean continuer;
	private static ViewSnakeGame vue;
	private static ViewCommand vc;
	static AgentAction lastKey;
	public AgentAction getLastKey() {
		return ControllerClient.lastKey;
	}

	public static void setLastKey(AgentAction lastKey) {
		ControllerClient.lastKey = lastKey;
		System.out.println(lastKey);
	}

	public static void main(String[] argu) {
		String s;
		int p;
		Socket so;
		if (argu.length == 2) { // on récupère les paramètres
			s=argu[0];
			p=Integer.parseInt(argu[1]);
			while(true) {
				try{// on connecte un socket
					so = new Socket(s, p);
					if(so.isConnected()) {
						connected(so);
					}
				} catch(UnknownHostException e) {System.out.println(e);}
				catch (IOException e) {
					System.out.println("Aucun serveur n’est rattaché au port, nouvelle tentative dans 5s");
					try {
						Thread.sleep(5);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("syntaxe d’appel java cliTexte serveur port \n");
		} 
	}

	public ControllerClient(Socket so, AtomicBoolean continuer) {
		this.so=so;
		this.continuer=continuer;
	}
	@Override
	public void run() {
		BufferedReader entree;
		try {
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			while(continuer.get()&& !so.isClosed()) {
				String message=entree.readLine();
				if(message!=null) {
					System.out.println("message : "+message);

				}
				else if(!so.isClosed()) {
					System.out.println("Socket fermé");
					continuer.set(false);
				}
				else {
					System.out.println("Erreur : Message null");
				}
			}
			so.close(); // on ferme la connexion
		}catch (IOException e) { System.out.println("problème\n"+e);
		continuer.set(false);}

	} 

	/**
	 * est appelée quand le client s'est connecté au serveur
	 */
	public static void connected(Socket so) {
		lastKey=AgentAction.MOVE_UP;
		ClientKeyboard kb=new ClientKeyboard();
		
		
		BufferedReader entree;
		PrintWriter sortie;
		AtomicBoolean continuer;
		continuer=new AtomicBoolean(true);
		String ch=""; // la chaîne envoyée
		int l; // et sa longueur reçue
			try{// on connecte un socket
				sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
				
				InputMap carte=InputMap.fromJson(entree.readLine());
				
				PanelSnakeGame panneau=new PanelSnakeGame(carte.getSize_x(), carte.getSize_y(), carte.getWalls(),carte.getStart_snakes(),carte.getStart_items());
				 //vc=new ViewCommand(super.game,this);
			     vue=new ViewSnakeGame(panneau);
			     panneau.setFocusable(true);
			     panneau.addKeyListener(new ClientKeyboard());
			     vue.affiche();
			     vc.affiche();
				
				new Thread(new ControllerClient(so,continuer)).start();
				while( continuer.get()) {
					ch=lastKey.toJson();
					sortie.println(ch); // on écrit la chaîne et le newline dans le canal de sortie


				}
				continuer.set(false);
				sortie.println(ch);
				System.out.println("Fermeture de la connexion");
			} catch(UnknownHostException e) {System.out.println(e);}
			catch (IOException e) {
				System.out.println("Aucun serveur n’est rattaché au port ");
				continuer.set(false);
			}
	}

}
