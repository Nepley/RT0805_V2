package org.hdanyel.commun;

import org.json.JSONObject;
import org.json.JSONArray;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.hdanyel.beans.Utilisateur;
import org.hdanyel.commun.JSONConfig;

/**
 * Classe utilisée pour gérer les utilisateurs dans leur globalité
 */
public class GestionUsers {

    private final static String file_users = new String("donnees/users.json");  

    /**
     * Méthode utilisée pour inscrire un utilisateur
     * @param login le login rentré
     * @param password le mot de passe rentré
     * @return 200 si l'inscription s'est bien déroulée, 0 sinon
     */
    public static int inscriptionUtilisateur(String login, String password)
    {
        int retour = 200;
        if(!login.equals("") && !password.equals(""))
        {
            //Récupération du fichier JSON des utilisateurs
            //JSONConfig sert à intéragir directement avec un fichier.
            JSONObject new_user = new JSONObject();
            JSONConfig users = new JSONConfig(file_users);
            
            //On vérifie que le login n'est pas déjà pris
            JSONArray verif = users.getJSON().getJSONArray("users");
            if(verif.length() != 0)
            {
                for(int i =0; i < verif.length(); i++)
                {
                    //Cas où le login est déjà pris
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
                //Sinon, on met à jour le fichier avec le nouvel utilisateur.
                new_user.put("login", login);
                new_user.put("mdp", HashMdp(password)); //Le mdp est hashé 
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
	 * Méthode gérant la connexion d'un utilisateur
     * @param login le login rentré
     * @param password le mot de passe rentré
     * @return l'id de l'utilisateur si la connexion s'est bien déroulée, 0 sinon
	 */
	public static int connexionUtilisateur(String login, String password)
	{
        int retour = 0;
        if(!login.equals("") && !password.equals(""))
        {
            //Récupération du fichier JSON des utilisateurs
            //JSONConfig sert à intéragir directement avec un fichier.
            JSONObject users = new JSONConfig(file_users).getJSON();
            JSONArray tableau = users.getJSONArray("users");
            if(tableau.length() != 0)
            {
                for(int i =0; i < tableau.length(); i++)
                {
                    //Si le login et le mdp rentrés sont présents, on retourne l'id de l'utilisateur
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

    /**
     * Méthode utilisée pour lister les utilisateurs
     * @return la liste des utilisateurs
     */
    public static JSONArray listeUsers()
    {
        //Récupération du  JSON des utilisateurs
        JSONObject users = new JSONConfig(file_users).getJSON();
        JSONArray tableau = users.getJSONArray("users");

        return tableau;
    }

    /**
     * Récupération du type de l'utilisateur
     * @param id l'id de l'utilisateur
     * @return son type
     */
    public static int typeUser(int id)
    {
        int retour = 0;

        //Récupération du  JSON des utilisateurs
        JSONObject users = new JSONConfig(file_users).getJSON();
        JSONArray tableau = users.getJSONArray("users");
        if(tableau.length() != 0)
        {
            for(int i =0; i < tableau.length(); i++)
            {
                //Si l'id fourni a une correspondance, on renvoie le type de l'utilisateur
                JSONObject temp = new JSONObject(tableau.get(i).toString());
                if(temp.getString("id").equals(Integer.toString(id)))
                {
                    retour = Integer.parseInt(temp.getString("type"));
                }
            }

        }

        return retour;
    }

    /**
     * Méthode utilisée pour Hasher un mot de passe
     * @param mdp le mot de passe en clair
     * @return le mdp hashé
     */
    public static String HashMdp(String mdp)
    {
        String mdp_hash = DigestUtils.md5Hex(mdp);
        return mdp_hash;
    }

    /**
     * Etablissement de la session de l'utilisateur
     * @param req la requête HTTP dans laquelle est contenue la session
     * @return un objet Utilisateur, null si l'utilisateur n'est pas connecté
     */
    public static Utilisateur SessionUser(HttpServletRequest req)
    {
        HttpSession sess = req.getSession();
        Utilisateur user = null;

        //L'utilisateur s'est connecté
        if(sess.getAttribute("id") != null)
        {
            user = new Utilisateur();
            user.setId( (String) sess.getAttribute("id"));
            user.setLogin( (String) sess.getAttribute("login"));
            user.setType( (String) sess.getAttribute("type"));
        }

        return user;
    }
}