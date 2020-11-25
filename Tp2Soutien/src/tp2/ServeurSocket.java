package tp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ServeurSocket  {
	
	
	
	public static void main(String a[]) throws Exception {
	
		final int httpd = 8085;
        Socket socketCommunication = null;
        ServerSocket socketServeur = null;
        Thread t;
	
        try {
            //Pr�parer le serveur � �tre lanc�
            socketServeur = new ServerSocket(httpd);
            do {

            //Attendre la requ�te du client et �tablir connexion au serveur
            socketCommunication = socketServeur.accept();
            System.out.println("un client a fait une connexion");

            //Communication entre le serveur et le client
            ConnexionClient connexionClient = new ConnexionClient(socketCommunication);

            t = new Thread(connexionClient);
            t.start();
            } while (true);
        } catch (IOException e) {
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
