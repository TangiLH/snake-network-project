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

public class ClientTalker implements Runnable{
	Socket so;
	private final int id;
	private int idServer;
	private InputMap carte;
	private Vector<String> jsonFeatures;
	private AtomicBoolean continuer;
	private AtomicInteger gameUpdated;
	private int gameUpdateMemory;

	public ClientTalker(Socket so, int idPlayer, int idServer, InputMap map, Vector<String> jsonFeatures,AtomicBoolean continuer,AtomicInteger gameUpdated) {
		this.so=so;
		this.id=idPlayer;
		this.idServer=idServer;
		this.carte=map;
		this.jsonFeatures=jsonFeatures;
		this.continuer=continuer;
		this.gameUpdated=gameUpdated; //AtomicInteger incrémenté quand le jeu est mis à jour
		this.gameUpdateMemory=gameUpdated.get();
	}
	@Override
	public void run() {
		try {
			PrintWriter sortie;
			sortie = new PrintWriter(so.getOutputStream(), true);
			String c = carte.toJson();
			System.out.print("PRINT-------------\n"+c);
			sortie.println(c);
			System.out.println("map sent");
			
			while(continuer.get()) {
				//System.out.println("Server : "+idServer+" Client : "+id+" ");
				//System.out.println("Sending to client "+jsonFeatures.get(0));
				if(gameUpdated.get()>this.gameUpdateMemory) {
					this.gameUpdateMemory=gameUpdated.get();
					sortie.println(jsonFeatures.get(0));
				}
				
				//Thread.sleep(20);
			}
			System.out.println("Client Talker "+this.id+" terminated");


		}catch (IOException e) { System.out.println("problème\n"+e); }


	}
	public int getId() {
		return id;
	}


}
