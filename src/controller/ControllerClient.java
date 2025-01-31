package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.TypeFactory;

import model.InputMap;
import utils.AgentAction;
import utils.ArrayFromJson;
import utils.FeaturesItem;
import utils.FeaturesSnake;
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
	private static ArrayList<FeaturesSnake> featuresSnakes;
	private static ArrayList<FeaturesItem> featuresItems;
	private static ArrayList<ArrayList<String>> featuresList;
	private static AtomicBoolean keyChanged;
	private PanelSnakeGame panneau;
	public AgentAction getLastKey() {
		return ControllerClient.lastKey;
	}

	public static void setLastKey(AgentAction lastKey) {
		if(lastKey!=ControllerClient.lastKey) {
			ControllerClient.lastKey = lastKey;
			System.out.println(lastKey);
			ControllerClient.keyChanged.set(true);
		}
	}

	public static void main(String[] argu) {
		keyChanged=new AtomicBoolean(true);
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
						Thread.sleep(5000);
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

	public ControllerClient(Socket so, AtomicBoolean continuer,PanelSnakeGame panneau) {
		this.so=so;
		this.continuer=continuer;
		this.panneau=panneau;
		featuresItems=new ArrayList<>();
		featuresSnakes=new ArrayList<>();
	}
	@Override
	public void run() {
		String ch;
		BufferedReader entree;
		PrintWriter sortie;
		String message;
		String prev_message="";
		try {
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			sortie = new PrintWriter(so.getOutputStream(), true);
			while(continuer.get()&& !so.isClosed()) {
				message=entree.readLine();
				if(message!=null) {
					if(!message.equals(prev_message)) {
						getAllFeatures(message);
						prev_message=message;
						panneau.updateInfoGame(featuresSnakes, featuresItems);
						panneau.repaint();
						Thread.sleep(20);
					}

				}
				else if(!so.isClosed()) {
					System.out.println("Socket fermé");
					panneau.setVisible(false);
					continuer.set(false);
				}
				else {
					System.out.println("Erreur : Message null");
				}
			}
			so.close(); // on ferme la connexion
		}catch (IOException | InterruptedException e) { System.out.println("problème\n"+e);
		continuer.set(false);}

	} 

	/**
	 * est appelée quand le client s'est connecté au serveur
	 */
	public static void connected(Socket so) {
		System.out.println("Connected");
		lastKey=AgentAction.MOVE_UP;
		ClientKeyboard kb=new ClientKeyboard();

		InputMap carte=null;
		BufferedReader entree;
		PrintWriter sortie;
		AtomicBoolean continuer;
		continuer=new AtomicBoolean(true);
		String ch=""; // la chaîne envoyée
		int l; // et sa longueur reçue
		try{// on connecte un socket
			sortie = new PrintWriter(so.getOutputStream(), true);
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			System.out.println("waiting for map");
			String in=null;
			while(in==null) {
				in=entree.readLine();
			}

			System.out.println(in);
			try {
				carte=InputMap.fromJson(in);
			}
			catch(IllegalArgumentException e) {
				System.err.println(e);
				return;
			}
			System.out.println("map recieved");
			
			PanelSnakeGame panneau=new PanelSnakeGame(carte.getSize_x(), carte.getSize_y(), carte.getWalls(),carte.getStart_snakes(),carte.getStart_items());
			//vc=new ViewCommand(super.game,this);
			
			if(vue==null) {
				vue=new ViewSnakeGame(panneau);
			}
			else {
				vue.changePanel(panneau);
			}
			panneau.setFocusable(true);
			panneau.addKeyListener(new ClientKeyboard());
			vue.affiche();
			//vc.affiche();

			new Thread(new ControllerClient(so,continuer,panneau)).start();
			while( continuer.get()) {

				if(ControllerClient.keyChanged.get()) {
					ch=lastKey.toJson();
					sortie.println(ch); 
					ControllerClient.keyChanged.set(false);
				}
				// on écrit la chaîne et le newline dans le canal de sortie
				//Thread.sleep(20);

				//System.out.println("Test 2");

			}
			continuer.set(false);
			System.out.println("Fermeture de la connexion");
			panneau.setVisible(false);
			
		} catch(UnknownHostException e) {System.out.println(e);}
		catch (IOException e) {
			System.out.println("Aucun serveur n’est rattaché au port ");
			continuer.set(false);
		}
	}

	public static void getAllFeatures(String json) {
		JavaType javaType = TypeFactory.defaultInstance().constructType(ArrayList.class);

		ObjectReader or= new ObjectMapper().reader().forType(javaType);
		try {
			featuresList=or.readValue(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		featuresSnakes.clear();
		featuresItems.clear();
		for(String s : featuresList.get(0)) {
			featuresSnakes.add(FeaturesSnake.fromJson(s));
		}
		for(String s : featuresList.get(1)) {
			featuresItems.add(FeaturesItem.fromJson(s));
		}

	}

}
