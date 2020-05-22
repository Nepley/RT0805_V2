package org.hdanyel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hdanyel.ClientHttp;
import org.json.JSONArray;
import org.json.JSONObject;

public class App {

	public static void main(String[] args) throws InterruptedException {
		String jwt = null;
		// La connexion ne peut s'arrêter que si l'utilisateur est connecté
		while(jwt == null)
			jwt = connexionUtilisateur();

		System.out.println("Utilisateur connecté.");
		Course(jwt);
	}
	
	/**
	 * Fonction gérant la partie connexion
	 * @return Retourne la réponse donnée par le serveur
	 */
	public static JSONObject Connection()
	{
		String login = "";
		JSONObject data = new JSONObject();
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		Console console = System.console();

		System.out.println("Quel est votre identifiant ?");
		//On empêche une entrée vide
		while((login += scanner.nextLine()).equals("")) 
		{
			login = "";
		}
		data.put("login", login);


		System.out.println("Et quel est votre mot de passe ?");
		char[] motdepasse = console.readPassword();
		String mdp = new String(motdepasse);
		
		data.put("mdp", mdp);
		reponse = ClientHttp.sendData(data, "login");
		return reponse;
	}

	/**
	 * Fonction gérant la partie Inscription
	 * @return Retourne la réponse donnée par le serveur
	 */
	public static JSONObject Inscription()
	{
		String login = "";
		String mdp = "";
		String mdp_v = "1";
		JSONObject data = new JSONObject();
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		Console console = System.console();

		System.out.println("Très bien, quel est votre identifiant ?");
		while((login += scanner.nextLine()).equals("")) //On empèche une entrée vide
		{
			login = "";
		}
		data.put("login", login);
		
		while(!mdp.equals(mdp_v))
		{
			mdp = "";
			while(mdp.equals(""))
			{
				System.out.println("Quel est votre mot de passe ?");
				char[] motdepasse = console.readPassword();
				mdp = new String(motdepasse);
			}
			
			mdp_v = "";
			while(mdp_v.equals(""))
			{
				System.out.println("Retapez votre mot de passe :");
				char[] motdepasse_v = console.readPassword();
				mdp_v = new String(motdepasse_v);
			}

			if(!mdp.equals(mdp_v))
			{
				System.out.println("Les mot de passe ne sont pas identiques, veuillez réessayer");
			}
		}
		data.put("mdp", mdp);
		reponse = ClientHttp.sendData(data, "inscription");	
		return reponse;
	}
	/**
	 * Gère toute la première partie du terminal avec la connexion et l'inscription
	 * @return Retourne un jeton qui est égal l'inscription s'est mal déroulée 
	 */
	private static String connexionUtilisateur()
	{
		String jwt = "";
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Veuillez vous inscrire ou vous connecter afin de pouvoir commencer votre activité, que choisissez vous ?");
		System.out.println("1) Se connecter");
        System.out.println("2) S'inscrire");
    
        switch(scanner.nextInt())
        {
        	//A chaque fois, on constitue la requête HTTP
        	case 1:// Cas de la connexion
				reponse = Connection();
				//Si la connexion a échouée, on recommence
				while(reponse.get("status").toString().equals("error"))
        		{
					System.out.println("Mauvais identifiant/mot de passe, veuillez recommencer.");
					reponse = Connection();
				}
        		break;
        	case 2: // Cas de l'inscription
				reponse = Inscription();
				//Si l'inscription a réussi on passe sur la partie connexion
				if(reponse.get("status").toString().equals("OK"))
        		{
					System.out.println("Vous êtes maintenant inscrit ! Veuilez maintenant vous connecter : ");
					reponse = Connection();
					//Si la connexion à échoué, on recommence
					while(reponse.get("status").toString().equals("error"))
					{
						System.out.println("Mauvais idientifiant/mot de passe, veuillez recommencer.");
						reponse = Connection();
					}
				}	
				else
				{
					System.out.println("Cet identifiant est déjà pris.\n");
				}
        		break;
        	default:
        		break;
        }

		// SI la connexion à échoué, le jeton est égal à null
        if(reponse.get("status").toString().equals("OK"))
        {
        	jwt = reponse.getString("jwt");
		}
		else
			jwt = null;
        return jwt;
	}

	/**
	 * Gère la partie du terminal gérant ce que fait l'utilisateur après sa connexion
	 * @param jwt Le jeton de l'utilisateur
	 * @throws InterruptedException
	*/
	private static void Course(String jwt) throws InterruptedException
	{
		Date aujourdhui = new Date();
		int nbpoint = 0;
		SimpleDateFormat formater_heure = new SimpleDateFormat("hh:mm");
		SimpleDateFormat formater_date = new SimpleDateFormat("dd/MM/yyyy");
		JSONObject reponse = new JSONObject();
		JSONArray data_coord = new JSONArray();
		JSONObject data_activite = new JSONObject();
		JSONObject coord = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		JSONObject data = new JSONObject();

		System.out.println("On fait la course");

		//On récupère la liste des sports
		reponse = ClientHttp.sendData(data, "ListeSport");
	
		JSONArray sports = reponse.getJSONArray("sports");
		//L'utilisateur choisi l'activité qu'il veut faire parmi la liste des sports récupèrer sur le serveur
		System.out.println("Que voulez-vous faire ?");

		for(int i = 1; i < sports.length()+1; i++)
		{
			JSONObject sport = sports.getJSONObject(i-1);
			System.out.println(i +") "+ sport.getString("nom"));
		}
		int choix = -1;

		//On vérifie que le choix de l'utilisateur est correct
		while(choix < 0 || choix > sports.length()) {
			choix = scanner.nextInt()-1;
		}

		// Si le choix est correct, on commence la génération des données de l'activité
		if (choix >= 0 && choix <= sports.length())
		{
			data_activite.put("id_sport", choix+1);
			data_activite.put("jwt", jwt);
			data_activite.put("debut", formater_heure.format(aujourdhui));
			data_activite.put("date", formater_date.format(aujourdhui));
		
			// Ici, le terminal commencera toujours au même endroit (à Reims), et se déplacera aléatoirement dans une direction et une vitesse
			Double x = 49.262240;
			Double y = 4.052293;
			// La direction est défini aléatoirement dès le début et une seule fois pour éviter que la génération génère des point sans trop de sens
			double rand_x = Math.random();
			double rand_y = Math.random();
			coord.put("coord_x", x);
			coord.put("coord_y", y);
			coord.put("heure", formater_heure.format(aujourdhui));
			data_coord.put(coord);

			//L'utilisateur choisi la durée de son activité, les points sont générés toute les secondes pour les tests
			System.out.println("Combien de points voulez-vous générer ?");
			while(nbpoint <= 0)
			{
				nbpoint = scanner.nextInt();
			}

			System.out.println("Génération des points...");
			for(int i =1; i<nbpoint+1; i++)
			{
				System.out.println("Point numéro" + i);
				Thread.sleep(1000);
				//x += 0.001;
				//y -= 0.001;
				
				// La distance parcourue est aléatoire à chaque point
				if(rand_x < 0.5)
				{
					x = x + Math.random()*0.001;
				}
				else
				{
					x = x - Math.random()*0.001;
				}
				
				if(rand_y < 0.5)
				{
					y = y + Math.random()*0.001;
				}
				else
				{
					y = y - Math.random()*0.001;
				}

				aujourdhui = new Date();
				JSONObject coord_temp = new JSONObject();
				coord_temp.put("coord_x", x);
				coord_temp.put("coord_y", y);
				coord_temp.put("heure", formater_heure.format(aujourdhui));
				data_coord.put(coord_temp);
			}
			Thread.sleep(5);
			System.out.println(("Activité finie, envoi.."));
			data_activite.put("pts", data_coord);
			aujourdhui = new Date();
			data_activite.put("fin", formater_heure.format(aujourdhui));
			reponse = ClientHttp.sendData(data_activite, "Activite");
			System.out.println(("Envoyé !"));
			scanner.close();
		}
	}
}
