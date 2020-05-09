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

import org.hdanyel.Classes.*;

@WebServlet(value="/users", name="authServlet")
public class AuthServlet extends HttpServlet {

    /*
    * L'utilisateur peut s'inscrire
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String corps = req.getReader().readLine();
        JSONObject requete = new JSONObject(corps);
        int id = GestionUsers.inscriptionUtilisateur(requete.getString("login"), requete.getString("mdp"));
        String status;
        if(id > 0 && id != 0)
            status = "OK";
        else
            status = "error";
        JSONObject reponse = new JSONObject();
        reponse.put("status", status);
        resp.getWriter().println(reponse);
    }
}

