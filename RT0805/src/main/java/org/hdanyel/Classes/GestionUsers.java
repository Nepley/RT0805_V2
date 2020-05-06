package org.hdanyel.Classes;

import org.json.JSONObject;
import org.json.JSONArray;

import org.hdanyel.Classes.JSONConfig;

public class GestionUsers {

    private final static String file_users = new String("/home/user1/Bureau/projet_java/RT0805/donnees/users.json");  

    public static int inscriptionUtilisateur(String login, String password)
    {
        int retour = 200;
        if(!login.equals("") && !password.equals(""))
        {
            JSONObject new_user = new JSONObject();
            JSONConfig users = new JSONConfig(file_users);
            
            //On vérifie que le login n'est pas déjà pris
            JSONArray verif = users.getJSON().getJSONArray("users");
            if(verif.length() != 0)
            {
                for(int i =0; i < verif.length(); i++)
                {
                    JSONObject temp = new JSONObject(verif.get(i).toString());
                    if(temp.getString("login").equals(login))
                    {
                        retour = 0;
                    }
                }
            }

            //Si le login est déjà présent, on ne l'inscrit pas
            if(retour != 0)
            {
                new_user.put("login", login);
                new_user.put("mdp", password);
                new_user.put("id", JSONConfig.MaxId(users.getJSON().getJSONArray("users")));
                users.ajouterJSON("users", new_user);
                users.sauvegarder();
            }
        }
        else retour = 0;

        return retour;
    }

	/*
	 * Méthode gérant la connexion de l'utilisateur d'un nouveau port
	 * @return le nom de l'utilisateur
	 */
	public static int connexionUtilisateur(String login, String password)
	{
        int retour = 0;
        if(!login.equals("") && !password.equals(""))
        {
            JSONObject users = new JSONConfig(file_users).getJSON();
            JSONArray tableau = users.getJSONArray("users");
            if(tableau.length() != 0)
            {
                for(int i =0; i < tableau.length(); i++)
                {
                    JSONObject temp = new JSONObject(tableau.get(i).toString());
                    if(temp.getString("login").equals(login) && temp.getString("mdp").equals(password))
                    {
                        retour = Integer.parseInt(temp.getString("id"));
                    }
                }
            }

        }

        return retour;
	}

}