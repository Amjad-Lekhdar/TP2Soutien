package exemple1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dorsaf serveur qui attend la connexion d'un client sur un port X. Le
 *         serveur reçoit la requête d'un client et l'affiche sur la console
 *         Exécution: lancez le serveur et ensuite, ouvrez le navigateur et
 *         tapez l'adresse http://localhost:port. Observez la console.
 */
public class HTTPServer {
	public static void main(String a[]) throws Exception {

		final int httpd = 8080;
		Socket socketCommunication = null;
		ServerSocket socketServeur = null;

		try {

			// écoute sur le port httpd
			socketServeur = new ServerSocket(httpd);

			// accept est une méthode bloquante
			socketCommunication = socketServeur.accept();
			// un client a fait une connexion

			ConnexionClient connexionClient = new ConnexionClient(socketCommunication);
			connexionClient.getEntete();
			connexionClient.fermetureFlux();

		}

		catch (IOException e) {
			System.out.print("Erreur Client " + e.getMessage());
		} finally {
			try {
				if (socketServeur != null)
					socketServeur.close();
				if (socketCommunication != null)
					socketCommunication.close();

			} catch (IOException e) {
				System.out.println("Erreur !" + e.getMessage());
			}
		}
	}
}