package exemple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnexionClient {
	Socket socketCommunication;
	PrintWriter out = null; // le flux de sortie de socket
	BufferedReader in = null;

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

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* le serveur prépare une réponse en format HTTP et L'envoie au client */

	void envoiReponse() {

		// préparation du corps de la réponse
		String corps = "";
		corps += "<html>";
		corps += "<body>";
		corps += "<p>";
		corps += "Hello tout le monde";
		corps += "</p>";
		corps += "</body>";
		corps += "</html>";

		// longueur du corps de la réponse
		int len = corps.length();

		// envoie de la line de début, entêtes, la ligne vide et le corps
		out.print("HTTP-1.0 200 OK\r\n");
		out.print("Content-Length: " + len + "\r\n");
		out.print("Content-Type: text/html\r\n\r\n");
		out.print(corps);

		out.flush();

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
	
	
}
