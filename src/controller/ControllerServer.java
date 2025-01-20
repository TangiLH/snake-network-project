package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import utils.FileAttente;


public class ControllerServer implements Runnable {
	/*programme serveur qui écoute sur le port p (passé en paramètre) et qui renvoie la longueur
	de la chaîne de caractères que lui envoie un client. La chaîne envoyée se termine par un newline.*/
	Socket so;
	Vector<Socket> playerSockets;
	int maxPlayers;
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
					if(serveursDispos.size()>0 ) {
						serveur=(ControllerServer)serveursDispos.pop();
						serveur.addPlayer(so);
						if(serveur.canAccept()) {
							serveursDispos.add(serveur);
						}
						else {
							new Thread(serveur).start();
						}
					}
					else {
						serveursDispos.add(new ControllerServer(so,2));
					}
				}
			} catch (IOException e) { System.out.println("problème\n"+e); }
		} else { System.out.println("syntaxe d’appel java servTexte port\n"); } }
	
	private void addPlayer(Socket so2) {
		this.playerSockets.add(so2);
		
	}
	public ControllerServer(Socket so,int maxPlayers) {
		this.so=so;
		this.playerSockets=new Vector<Socket>();
		this.maxPlayers=maxPlayers;
	}
	@Override
	public void run() {
		try {
		BufferedReader entree;
		PrintWriter sortie;

		String ch; // la chaîne reçue
		
		entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
		sortie = new PrintWriter(so.getOutputStream(), true);
		
		ch = entree.readLine(); // on lit ce qui arrive
		while(ch!=null&&!ch.equals("stop")) {
			System.out.println("touche pressee"+ch);
			
			ch = entree.readLine(); // on lit ce qui arrive
		}
		so.close();
		if(ch!=null) {
			System.out.println("on a envoyé : "+ch.length()+" et on a fermé la connexion");
		}
		else {
			System.out.println("reçu NULL, fermeture de la connexion");
		}
		
			
		}catch (IOException e) { System.out.println("problème\n"+e); }
	} 
	public boolean canAccept() {
		return this.playerSockets.size()<this.maxPlayers;
	}
}
