package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener implements Runnable {
	Socket so;
	public ClientListener(Socket so) {
		this.so=so;
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

}
