package org.hdanyel.servlets.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import org.json.JSONArray;
import org.json.JSONObject;

import org.hdanyel.commun.JSONConfig;
import org.hdanyel.commun.GestionUsers;
import org.hdanyel.commun.GestionJWT;

@WebServlet(value="/users/cookie", name="authCookieServlet")
public class AuthCookieServlet extends HttpServlet {

    /*
    * L'utilisateur fournit ses identifiants 
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Demande de connexion reçue.");
        String corps = req.getReader().readLine();
        JSONObject requete = new JSONObject(corps);
        int id = GestionUsers.connexionUtilisateur(requete.getString("login"), requete.getString("mdp"));
        JSONObject reponse = new JSONObject();
        String status;
        if(id > 0)
        {
            status = "OK";
            reponse.put("jwt", GestionJWT.generateToken(id, requete.getString("login")));
        }
        else
            status = "error";
        reponse.put("status", status);
        System.out.println("Réponse envoyée : " +reponse);
        resp.getWriter().println(reponse.toString());
    }

    
}

