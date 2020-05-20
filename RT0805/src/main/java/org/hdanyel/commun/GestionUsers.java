package org.hdanyel.commun;

import org.json.JSONObject;
import org.json.JSONArray;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.hdanyel.Beans.Utilisateur;
import org.hdanyel.commun.JSONConfig;

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
                new_user.put("mdp", HashMdp(password));
                new_user.put("type", "2");
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
                    if(temp.getString("login").equals(login) && temp.getString("mdp").equals(HashMdp(password)))
                    {
                        retour = Integer.parseInt(temp.getString("id"));
                    }
                }
            }

        }

        return retour;
	}


    public static JSONArray listeUsers()
    {
        JSONObject users = new JSONConfig(file_users).getJSON();
        JSONArray tableau = users.getJSONArray("users");

        return tableau;
    }

    public static int typeUser(int id)
    {
        int retour = 0;

        JSONObject users = new JSONConfig(file_users).getJSON();
        JSONArray tableau = users.getJSONArray("users");
        if(tableau.length() != 0)
        {
            for(int i =0; i < tableau.length(); i++)
            {
                JSONObject temp = new JSONObject(tableau.get(i).toString());
                if(temp.getString("id").equals(Integer.toString(id)))
                {
                    retour = Integer.parseInt(temp.getString("type"));
                }
            }

        }

        return retour;
    }

    public static String HashMdp(String mdp)
    {
        String mdp_hash = DigestUtils.md5Hex(mdp);
        return mdp_hash;
    }

    public static Utilisateur SessionUser(HttpServletRequest req)
    {
        HttpSession sess = req.getSession(false);
        Utilisateur user = null;
        if(sess != null)
        {
            System.out.println("x");
            user = new Utilisateur();
            user.setId( (String) sess.getAttribute("id"));
            user.setLogin( (String) sess.getAttribute("login"));
            user.setType( (String) sess.getAttribute("type"));
        }

        return user;
    }
}