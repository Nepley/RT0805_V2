package org.hdanyel.servlets.rest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import org.json.JSONArray;
import org.json.JSONObject;

import org.hdanyel.commun.GestionUsers;

/**
 * Servlet utilisée dans l'API REST pour que l'utilisateur s'inscrive
 */
@WebServlet(value="/users", name="authServlet")
public class AuthServlet extends HttpServlet {

    /** 
    * L'utilisateur veut s'inscrire
    * On créé un utilisateur.
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        //Puisqu'on lit un JSON, on lit tout le corps de la requête
        String corps = req.getReader().readLine();
        JSONObject requete = new JSONObject(corps);
        int id = GestionUsers.inscriptionUtilisateur(requete.getString("login"), requete.getString("mdp"));
        String status;
        // inscriptionUtilisateur renvoie 0 si ça s'est mal passé.
        if(id > 0)
            status = "OK";
        else
            status = "error";

        JSONObject reponse = new JSONObject();
        reponse.put("status", status);
        resp.getWriter().println(reponse);
    }
}

