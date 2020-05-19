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
		String jwt = connexionUtilisateur();
		System.out.println("Utilisateur connecté.");
		int Au = Course(jwt);
	}
	
	public static JSONObject Connection()
	{
		String login = "";
		JSONObject data = new JSONObject();
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		Console console = System.console();

		System.out.println("Quel est votre identifiant ?");
		while((login += scanner.nextLine()).equals("")) //On empèche une entrée vide
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
			System.out.println("Quel est votre mot de passe ?");
			char[] motdepasse = console.readPassword();
			mdp = new String(motdepasse);

			System.out.println("Retapez votre mot de passe :");
			char[] motdepasse_v = console.readPassword();
			mdp_v = new String(motdepasse_v);

			if(!mdp.equals(mdp_v))
			{
				System.out.println("Les mot de passe ne sont pas identiques, veuillez réessayer");
			}
		}
		data.put("mdp", mdp);
		reponse = ClientHttp.sendData(data, "inscription");	
		System.out.println("Vous êtes maintenat inscris ! Veuilez maintenant vous connecter : ");
		return reponse;
	}
	/*
	 * Gestion de la connexion d'un utilisateur
	 */
	private static String connexionUtilisateur()
	{
		String jwt = "";
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vous devez maintenant vous inscrire ou vous connecter afin de pouvoir commencer votre activité, que choisissez vous ?");
		System.out.println("1) Se connecter");
        System.out.println("2) S'inscrire");
    
        switch(scanner.nextInt())
        {
        	//A chaque fois, on constitue la requ�te HTTP
        	case 1:// Cas de la connexion
				reponse = Connection();
				while(reponse.get("status").toString().equals("error"))
        		{
					System.out.println("Mauvais idientifiant/mot de passe, veuillez recommencer.");
					reponse = Connection();
				}
        		break;
        	case 2: // Cas de l'inscription
				reponse = Inscription();
				if(reponse.get("status").toString().equals("OK"))
        		{
					reponse = Connection();
					while(reponse.get("status").toString().equals("error"))
					{
						System.out.println("Mauvais idientifiant/mot de passe, veuillez recommencer.");
						reponse = Connection();
					}
				}	
				else
				{
					System.out.println("Une erreur s'est produite lors de votre inscription.");
				}
        		break;
        	default:
        		break;
        }

        if(reponse.get("status").toString().equals("OK"))
        {
        	jwt = reponse.getString("jwt");
        }
        return jwt;
	}

	private static int Course(String jwt) throws InterruptedException
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

		//Récuperation des sports
		reponse = ClientHttp.sendData(data, "ListeSport");
	
		JSONArray sports = reponse.getJSONArray("sports");
		System.out.println("Que voulez-vous faire ?");
		for(int i = 1; i < sports.length()+1; i++)
		{
			JSONObject sport = sports.getJSONObject(i-1);
			System.out.println(i +") "+ sport.getString("nom"));
		}
		int choix = -1;
		while(choix < 0 || choix > sports.length()) {
			choix = scanner.nextInt()-1;
		}

		if (choix >= 0 && choix <= sports.length())
		{
			data_activite.put("id_sport", choix+1);
			data_activite.put("jwt", jwt);
			data_activite.put("debut", formater_heure.format(aujourdhui));
			data_activite.put("date", formater_date.format(aujourdhui));
		
			Double x = 49.262240;
			Double y = 4.052293;
			double rand_x = Math.random();
			double rand_y = Math.random();
			coord.put("coord_x", x);
			coord.put("coord_y", y);
			coord.put("heure", formater_heure.format(aujourdhui));
			data_coord.put(coord);

			System.out.println("Combien de point voulez-vous générer ?");
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
		return 1;
	}
}
