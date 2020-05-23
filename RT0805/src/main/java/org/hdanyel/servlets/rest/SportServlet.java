package org.hdanyel.servlets.rest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.RequestDispatcher;

import org.json.JSONArray;
import org.json.JSONObject;

import org.hdanyel.commun.JSONConfig;
import org.hdanyel.commun.GestionUsers;
import org.hdanyel.commun.GestionJWT;

/**
 * Servlet utilisée pour intéragir avec les sports/activités
 */
@WebServlet(value="/sports", name="sportServlet")
public class SportServlet extends HttpServlet {

    /*
    * Création d'une activité
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Nouvelle activité reçue");

        //Puisqu'on lit un JSON, on lit tout le corps de la requête
        String corps = req.getReader().readLine();
        JSONObject requete = new JSONObject(corps);

        //On identifie l'utilisateur à l'aide du JWT
        String jwt = requete.getString("jwt");
        GestionJWT.verifyToken(jwt);
        
        DecodedJWT token = GestionJWT.decodeToken(jwt);
        String id_u = token.getClaim("id").asString();
        String user = token.getClaim("login").asString();

        //On utilise un JSONConfig pour intéragir directement avec le JSON des activités.
        JSONConfig activites = new JSONConfig("donnees/activites.json");

        //On réassigne manuellement chaque champ à la nouvelle activité plutôt que de reprendre le JSON envoyé
        //De cette façon, on est sûr que le JSON entré est conforme et lisible
        JSONObject new_sport = new JSONObject();
        new_sport.put("id", JSONConfig.MaxId(activites.getJSON().getJSONArray("sports")));
        new_sport.put("id_u", id_u);
        new_sport.put("date", requete.getString("date"));
        new_sport.put("debut", requete.getString("debut"));
        new_sport.put("fin", requete.getString("fin"));
        new_sport.put("id_sport", requete.getInt("id_sport"));
        new_sport.put("pts", requete.getJSONArray("pts"));

        activites.ajouterTab("sports", new_sport);
        activites.sauvegarder();

        JSONObject reponse = new JSONObject();
        reponse.put("status", "OK");
        resp.getWriter().println(reponse.toString());
    }


    // Un utilisateur veut récupérer la liste des sports
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Demande de la liste des sports.");

        //On lit le JSON contenant les sports
        JSONObject sports = new JSONConfig("donnees/sports.json").getJSON();
        resp.getWriter().println(sports.toString());
        System.out.println("Liste des sports envoyée");
    }
}

