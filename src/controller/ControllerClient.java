package controller;

import java.awt.GraphicsEnvironment;
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

import utils.AgentAction;

public class ControllerClient implements Runnable {
	Socket so;
	AtomicBoolean continuer;
	static AgentAction lastKey;
	public AgentAction getLastKey() {
		return ControllerClient.lastKey;
	}

	public static void setLastKey(AgentAction lastKey) {
		ControllerClient.lastKey = lastKey;
		System.out.println(lastKey);
	}

	public static void main(String[] argu) {
		lastKey=AgentAction.MOVE_UP;
		Socket so;
		ClientKeyboard kb=new ClientKeyboard();
		JPanel panneau=new JPanel();
		panneau.addKeyListener(kb);
		panneau.repaint();
		panneau.setFocusable(true);
		JFrame jf=new JFrame();
		jf.add(panneau);
		jf.setVisible(true);
		DataInputStream entree;
		PrintWriter sortie;
		BufferedReader clavier;
		AtomicBoolean continuer;
		String s; // le serveur
		
		int p; // le port de connexion
		String ch=""; // la chaîne envoyée
		int l; // et sa longueur reçue
		if (argu.length == 2) { // on récupère les paramètres
			s=argu[0];
			p=Integer.parseInt(argu[1]);
			try{// on connecte un socket
				so = new Socket(s, p);
				sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new DataInputStream(so.getInputStream());
				clavier=new BufferedReader(new InputStreamReader(System.in));
				continuer=new AtomicBoolean(true);
				new Thread(new ControllerClient(so,continuer)).start();
				while(!ch.equals("stop") && continuer.get()) {
					ch=lastKey.toJson();
					sortie.println(ch); // on écrit la chaîne et le newline dans le canal de sortie
					
					
				}
				continuer.set(false);
				sortie.println(ch);
				System.out.println("Fermeture de la connexion");
			} catch(UnknownHostException e) {System.out.println(e);}
			catch (IOException e) {System.out.println("Aucun serveur n’est rattaché au port ");}
		} else {System.out.println("syntaxe d’appel java cliTexte serveur port \n");
		} }
	
	public ControllerClient(Socket so, AtomicBoolean continuer) {
		this.so=so;
		this.continuer=continuer;
	}
	@Override
	public void run() {
		BufferedReader entree;
		try {
			entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
			while(continuer.get()) {
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
		}catch (IOException e) { System.out.println("problème\n"+e); }

		
		
	} 

}
