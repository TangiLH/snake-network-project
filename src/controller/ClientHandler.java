package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import model.InputMap;
import utils.AgentAction;

public class ClientHandler implements Runnable {
	Socket so;
	private final int id;
	private int idServer;
	private Vector<AgentAction>playerInput;
	private InputMap carte;
	private Vector<String> jsonFeatures;
	private AtomicBoolean continuer;
	private Vector<ClientListener> vClient;
	private AtomicInteger gameUpdated;
	public ClientHandler(Socket so, int idPlayer, int idServer, Vector<AgentAction> playerInput,InputMap map, Vector<String> jsonFeatures, Vector<ClientListener> vClient,AtomicInteger gameUpdated, AtomicBoolean continuer) {
		this.so=so;
		this.id=idPlayer;
		this.idServer=idServer;
		this.playerInput=playerInput;
		this.carte=map;
		this.jsonFeatures=jsonFeatures;
		this.continuer=continuer;
		this.continuer.set(true);
		this.vClient=vClient;
		this.gameUpdated=gameUpdated;
	}
	@Override
	public void run() {
		ClientListener cl=new ClientListener( so,  id,  idServer, playerInput, continuer);
		vClient.add(cl);
		new Thread(cl).start(); 
		new Thread(new ClientTalker(so, id, idServer, carte, jsonFeatures, continuer,gameUpdated)).start();
		while(continuer.get()) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			this.so.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * envoie la liste des cartes et attends la réponde du client
	 * @param so le socket client;
	 */
	public static void listenForMap(Socket so) {
		PrintWriter sortie;
		BufferedReader entree;
		try {
			sortie = new PrintWriter(so.getOutputStream(), true);
			String out=InputMap.mapListToJson();
			System.out.println("Sending "+out);
			sortie.println(out);
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			String ch = entree.readLine();
			System.out.println("player has selectred "+ch);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
