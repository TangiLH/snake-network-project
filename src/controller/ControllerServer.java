package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

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
		
	}
	public ControllerServer(Socket so,int maxPlayers,int id) {
		this.playerSockets=new Vector<Socket>();
		this.playerSockets.add(so);
		this.maxPlayers=maxPlayers;
		this.id=id;
		this.playerInput=new Vector<>(maxPlayers);
		for(int i=0;i<maxPlayers;i++) {
			playerInput.add(AgentAction.MOVE_DOWN);
		}
		this.idPlayer=0;
	}
	@Override
	public void run() {
			String map="layouts/alone.lay";
			ClientHandler ch;
			Vector<ClientListener>vClient=new Vector<>();
			Vector<String> jsonFeatures = new Vector<>();
			for(int i=0;i<maxPlayers;i++) {
				jsonFeatures.add("");
			}
			ControllerNetworkGame cng=new ControllerNetworkGame(map, maxPlayers, playerInput,vClient,jsonFeatures);
			for(Socket so:playerSockets) {
				ch=new ClientHandler(so,this.idPlayer++,this.id,this.playerInput,cng.getCarte(),jsonFeatures,vClient);
				new Thread(ch).start();
			}
			try {
				System.out.println("Lancement de la partie dans 10s");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cng.play();
			
			
		
	} 
	public boolean canAccept() {
		return this.playerSockets.size()<this.maxPlayers;
	}
}
