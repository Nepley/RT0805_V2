package org.hdanyel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		String mdp = "";
		JSONObject data = new JSONObject();
		JSONObject reponse = new JSONObject();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Quel est votre identifiant ?");
		while((login += scanner.nextLine()).equals("")) //On empèche une entrée vide
		{
			login = "";
		}
		data.put("login", login);

		System.out.println("Et quel est votre mot de passe ?");
		while((mdp += scanner.nextLine()).equals("")) //On empèche une entrée vide
		{
			mdp = "";
		}
		
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

		System.out.println("Très bien, quel est votre identifiant ?");
		while((login += scanner.nextLine()).equals("")) //On empèche une entrée vide
		{
			login = "";
		}
		data.put("login", login);
		
		while(!mdp.equals(mdp_v))
		{
			System.out.println("Quel est votre mot de passe ?");
			mdp = "";
			while((mdp += scanner.nextLine()).equals("")) //On empèche une entrée vide
			{
				mdp = "";
			}
			System.out.println("Retapez votre mot de passe :");
			mdp_v = "";
			while((mdp_v += scanner.nextLine()).equals("")) //On empèche une entrée vide
			{
				mdp_v = "";
			}

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
        		break;
        	case 2: // Cas de l'inscription
				reponse = Inscription();
				if(reponse.get("status").toString().equals("OK"))
        		{
					reponse = Connection();
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
		SimpleDateFormat formater = new SimpleDateFormat("hh:mm");
		JSONObject reponse = new JSONObject();
		JSONArray data_coord = new JSONArray();
		JSONObject data_activite = new JSONObject();
		JSONObject coord = new JSONObject();
		Scanner scanner = new Scanner(System.in);
		JSONObject data = new JSONObject();

		System.out.println("On fait la course");

		//Récuperation des sports
		reponse = ClientHttp.sendData(data, "ListeSport");
	
		System.out.println("On a la liste des sports");

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
			data_activite.put("id_sport", choix);
			data_activite.put("jwt", jwt);
			data_activite.put("debut", formater.format(aujourdhui));
		
			Double x = 49.262240;
			Double y = 4.052293;
			coord.put("coord_x", x);
			coord.put("coord_y", y);
			coord.put("heure", formater.format(aujourdhui));
			data_coord.put(coord);

			System.out.println("Génération des points...");
			for(int i =1; i<11; i++)
			{
				System.out.println("Point numéro" + i);
				Thread.sleep(1000);
				x += 0.001;
				y -= 0.002;
				aujourdhui = new Date();
				JSONObject coord_temp = new JSONObject();
				coord_temp.put("coord_x", x);
				coord_temp.put("coord_y", y);
				coord_temp.put("heure", formater.format(aujourdhui));
				data_coord.put(coord_temp);
			}
			Thread.sleep(5);
			System.out.println(("Activité finie, envoi.."));
			data_activite.put("pts", data_coord);
			aujourdhui = new Date();
			data_activite.put("fin", formater.format(aujourdhui));
			reponse = ClientHttp.sendData(data_activite, "Activite");
			System.out.println(("Envoyé !"));
			scanner.close();
		}
		return 1;
	}
}
