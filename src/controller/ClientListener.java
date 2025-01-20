package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import model.InputMap;
import utils.AgentAction;

public class ClientListener implements Runnable {
	Socket so;
	private final int id;
	private int idServer;
	private Vector<AgentAction>playerInput;
	private InputMap carte;
	public ClientListener(Socket so, int idPlayer, int idServer, Vector<AgentAction> playerInput,InputMap map) {
		this.so=so;
		this.id=idPlayer;
		this.idServer=idServer;
		this.playerInput=playerInput;
		this.carte=map;
	}
	@Override
	public void run() {
		try {
			BufferedReader entree;
			PrintWriter sortie;

			String ch; // la chaîne reçue
			
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			sortie = new PrintWriter(so.getOutputStream(), true);
			sortie.println(carte.toJson());
			ch = entree.readLine(); // on lit ce qui arrive
			while(ch!=null&&!ch.equals("stop")) {
				System.out.println("Server : "+idServer+" Client : "+id+" "+ch);
				playerInput.set(id, AgentAction.fromJson(ch));
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
	public int getId() {
		return id;
	}

}
