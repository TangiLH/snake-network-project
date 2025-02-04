package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import model.InputMap;
import utils.AgentAction;

public class ClientListener implements Runnable {
	Socket so;
	private final int id;
	private int idServer;
	private Vector<AgentAction>playerInput;

	private AtomicBoolean continuer;
	public ClientListener(Socket so, int idPlayer, int idServer, Vector<AgentAction> playerInput,AtomicBoolean continuer) {
		this.so=so;
		this.id=idPlayer;
		this.idServer=idServer;
		this.playerInput=playerInput;
		this.continuer=continuer;
	}
	@Override
	public void run() {
		try {
			BufferedReader entree;

			String ch; // la chaîne reçue
			
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			ch = entree.readLine(); // on lit ce qui arrive
			while(ch!=null&&!ch.equals("stop")&&continuer.get()) {
				//System.out.println("Server : "+idServer+" Client : "+id+" "+ch);
				playerInput.set(id, AgentAction.fromJson(ch));
				
				ch = entree.readLine(); // on lit ce qui arrive
			}
			if(ch!=null) {
				System.out.println("on a envoyé : "+ch.length()+" et on a fermé la connexion");
			}
			else {
				System.out.println("reçu NULL, fermeture de la connexion");
				so.close();
				//continuer.set(false);
			}
			
				
			}catch (IOException e) { System.out.println("problème\n"+e); }


	}
	public int getId() {
		return id;
	}
	
	public boolean isConnected()
	{
		return !this.so.isClosed();
	}
}
