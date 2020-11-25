package tp2;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;



public class ConnexionClient implements Runnable {
	
	private Socket socketCommunication;
	PrintWriter out = null; // le flux de sortie de socket
	BufferedReader in = null;
	
	File fileAccueil = new File("accueil.html");
	File fileContact = new File("contact.html");
	File fileFormulaire = new File("formulaire.html");
	File fileSecret = new File("secret.html");
	
	
	static int httpd = 8085;
	static String cheminSite;
	static String cheminRelatif = "src/siteHTTP";
	static String[] test;
	static String[] enteteTable;

	ConnexionClient(Socket socketCommunication) {
		this.socketCommunication = socketCommunication;
		try {
			out = new PrintWriter(socketCommunication.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void getEntete() {

		String s = null;

		try {
			// lecture de l'entête http
			// http est un protocole structuré en lignes
			while ((s = in.readLine()).compareTo("") != 0) {
				System.out.println("reçu: " + s);
				
				if (s.contains("GET")) {
					cheminSite = s;
					
			        String[] cheminTab = cheminSite.split(" "); 
			        
			        for (int i = 0; i < cheminTab.length ; i++) { 
			        	cheminSite = cheminTab[1];
			        }

			}
				
		}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* le serveur prépare une réponse en format HTTP et L'envoie au client */

	void envoiReponse() throws IOException {

		int len = lireHtml().length();
		
		if (cheminSite.equals("/secret.html") ) {
			out.print("HTTP-1.1 403 FORBIDDEN\r\n");
			out.print("Content-Length: " + len + "\r\n");
			out.print("Content-Type: text/html\r\n\r\n");
			out.print("<h1> 403 Forbidden</h1>");
			out.flush();
		} else {
		
		
		
		out.print("HTTP-1.1 200 OK\r\n");
		out.print("Content-Length: " + len + "\r\n");
		out.print("Content-Type: text/html\r\n\r\n");
		out.print(lireHtml());
		out.flush();
		}
		
	}

	

	/**
	 * cette méthode ferme le flux
	 */
	void fermetureFlux() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Cette méthode permet de lire le contenu du fichier html 
	 * et retourner le contenu */

	public String lireHtml() throws IOException {
		  
		String htmlString = "";
        try {
            File fichier = new File(cheminRelatif+cheminSite);
            BufferedReader lecture = new BufferedReader(new FileReader(fichier.getPath()));
            
            String str;
            while (( str = lecture.readLine()) != null) {
            	htmlString+=str;
            }
            lecture.close();
            
            System.out.println(htmlString);

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return htmlString;
		
		
	}

	@Override
	public void run() {
		 try {

	            out = new PrintWriter(socketCommunication.getOutputStream());
	            in = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
	            this.getEntete();
	            this.envoiReponse();
	            this.fermetureFlux();

	        } catch (Exception e) {
	            // TODO: handle exception
	        }
		
	}
	
	


	
	
}
