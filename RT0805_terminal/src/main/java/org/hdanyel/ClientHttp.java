package org.hdanyel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetPermission;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

/**
 * Classe utilisée pour communiquer en HTTP
 * Ici utilisée pour communiquer avec l'API REST
 */
public class ClientHttp 
{
	/*
	 * Intermediaire permettant d'envoyer des données HTTP à l'API REST
	 * @param query la requête reçue par le Back Office
	 * @param but Objectif de la requête, permet de pointer la bonne URL
	 * @return JSONObject renvoit la reponse traitée correctement
	 */
	public static JSONObject sendData(JSONObject query, String but)
	{
        // Mise en forme de l'URL
        URL url = null;
        String Method = new String();
        try { 
            if(but.equals("login"))
            {
                url = new URL("http://localhost:8080/users/cookie");
                Method = "POST";
            }
            else if(but.equals("inscription"))
            {
                url = new URL("http://localhost:8080/users");
                Method = "POST";
            }
            else if(but.equals("Activite"))
            {
                url = new URL("http://localhost:8080/sports"); 
                Method = "POST";
            }
            else if(but.equals("ListeSport"))
            {
                url = new URL("http://localhost:8080/sports"); 
                Method = "GET";
            }
        } catch(MalformedURLException e) { 
            System.err.println("URL incorrect : " + e);
            System.exit(-1);
        }

        // Etablissement de la connexion
        HttpURLConnection connexion = null; 
        try { 
            connexion = (HttpURLConnection) url.openConnection(); 
            connexion.setDoOutput(true);
            connexion.setRequestMethod(Method);
            connexion.setRequestProperty("Content-Type", "application/json; utf-8");
            connexion.setRequestProperty("Accept", "application/json");
        } catch(IOException e) { 
            System.err.println("Connexion impossible : " + e);
            System.exit(-1);
        } 
        
 
        // La méthode est automatiquement fixée à POST si on utilise le writer
        // Donc on s'assure que pour la méthode GET, on n'utilise pas le writer
        if(!Method.equals("GET")){
            try {
                OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
                writer.write(query.toString());
                writer.flush();
                writer.close();
            } catch(IOException e) {
                System.err.println("Erreur lors de l'envoi de la requete : " + e);
                System.exit(-1);            
            }        
        }
 
        // Réception des données depuis le serveur
        String donnees = ""; 
        try { 
            BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream())); 
            String tmp; 
            while((tmp = reader.readLine()) != null) 
                donnees += tmp;
            
            reader.close(); 
        } catch(Exception e) { 
            System.err.println("Erreur lors de la lecture de la réponse : " + e);
            System.exit(-1);
        }

        JSONObject retour = new JSONObject(donnees);
        return retour;
    }
}
