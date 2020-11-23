package exemple1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnexionClient {

	Socket socketCommunication;
	BufferedReader in = null; // le flux d'entrée de socket

	/**
	 * Ce constructeur construit un objet qui représente une connexion du client à
	 * partir d'une socket d'échange. Il initialise le flux d'entré du socket.
	 */
	ConnexionClient(Socket socketCommunication) {

		this.socketCommunication = socketCommunication;

		try {
			in = new BufferedReader(new InputStreamReader(this.socketCommunication.getInputStream()));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * cette méthode récupère la requête HTTP se trouvant dans le flux d'entrée de
	 * la socket et l'affiche à la console
	 */

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

	/**
	 * cette méthode ferme le flux
	 */
	void fermetureFlux() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
