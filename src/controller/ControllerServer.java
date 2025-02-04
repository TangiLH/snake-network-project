package controller;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import model.InputMap;
import utils.AgentAction;
import utils.FileAttente;

/**
 * Classe principale du serveur
 * Ecoute sur le port et distribue les joueurs sur les serveurs de jeu
 */
public class ControllerServer implements Runnable {
	Socket so;
	Vector<Socket> playerSockets;
	int maxPlayers;
	int idPlayer;
	static int idServer=0;
	int id;
	Vector<AgentAction>playerInput;
	
	Vector<ClientListener>vClient;
	Vector<String> jsonFeatures;
	AtomicInteger gameUpdated;
	AtomicBoolean continuer;
	
	ControllerNetworkGame cng;
	public static void main(String[] argu) {
		int p; // le port d’écoute
		ServerSocket ecoute;
		ControllerServer serveur;
		Socket so;
		Vector<Socket> sockets;
		FileAttente serveursDispos=new FileAttente();
		if (argu.length == 1) {
			try {
				p=Integer.parseInt(argu[0]); // on récupère le port
				ecoute = new ServerSocket(p); // on crée le serveur
				sockets=new Vector<Socket>();
				System.out.println("serveur mis en place sur le port : "+ecoute.getLocalPort());
				while (true) {// le serveur va attendre qu’une connexion arrive
					so = ecoute.accept();
					sockets.add(so);
					System.out.println("Requete de connexion acceptee");
					if(serveursDispos.size()>0 ) {
						serveur=(ControllerServer)serveursDispos.pop();
						serveur.addPlayer(so);
						System.out.println("ajout du client au serveur");
						if(serveur.canAccept()) {
							System.out.println("Le serveur retourne en file d'attente");
							serveursDispos.add(serveur);
						}
						else {
							System.out.println("Lancement d'un serveur");
							new Thread(serveur).start();
						}
					}
					else {
						System.out.println("Création d'un nouveau serveur");
						serveur=new ControllerServer(so,1,ControllerServer.idServer++);
						if(serveur.canAccept()) {
							System.out.println("Le serveur retourne en file d'attente");
							serveursDispos.add(serveur);
						}
						else {
							System.out.println("Lancement d'un serveur");
							new Thread(serveur).start();
						}
					}
				}
			} catch (IOException e) { System.out.println("problème\n"+e); }
		} else { System.out.println("syntaxe d’appel java servTexte port\n"); } }
	
	private void addPlayer(Socket so2) {
		this.playerSockets.add(so2);
		ClientHandler ch=new ClientHandler(so2,this.idPlayer++,this.id,this.playerInput,cng.getCarte(),jsonFeatures,vClient,gameUpdated,continuer);
		new Thread(ch).start();
		
	}
	public ControllerServer(Socket so,int maxPlayers,int id) {
		String map=InputMap.getRandomMap();
		InputMap inputMap=new InputMap(map);
		this.maxPlayers = inputMap.getStart_snakes().size();
		
		this.playerInput=new Vector<>(this.maxPlayers);
		for(int i=0;i<this.maxPlayers;i++) {
			playerInput.add(AgentAction.MOVE_DOWN);
		}
		
		this.vClient=new Vector<>();
		this.jsonFeatures = new Vector<>();
		this.gameUpdated=new AtomicInteger(0);
		this.continuer=new AtomicBoolean(true);
		this.cng=new ControllerNetworkGame(inputMap, playerInput,vClient,jsonFeatures,gameUpdated,continuer);
		//this.maxPlayers=cng.getSnakenb();
		this.playerSockets=new Vector<Socket>();
		this.playerSockets.add(so);
		this.id=id;
		this.idPlayer=0;
		System.out.println("PLAYER NB "+this.maxPlayers+System.lineSeparator());
		ClientHandler ch=new ClientHandler(so,this.idPlayer++,this.id,this.playerInput,cng.getCarte(),jsonFeatures,vClient,gameUpdated,continuer);
		new Thread(ch).start();
		
		
		
	}
	@Override
	public void run() {
			
			ClientHandler ch;
			
			for(int i=0;i<maxPlayers;i++) {
				jsonFeatures.add("");
			}
			//ControllerNetworkGame cng=new ControllerNetworkGame(map, maxPlayers, playerInput,vClient,jsonFeatures,gameUpdated,continuer);
			for(Socket so:playerSockets) {
				
			}
			try {
				System.out.println("Lancement de la partie dans 10s");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.cng.play();
			
			
		
	} 
	public boolean canAccept() {
		return this.playerSockets.size()<this.maxPlayers;
	}
}
