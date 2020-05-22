package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.commun.GestionUsers;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;

/**
 * Servlet utilisée pour les demandes de connexion, d'inscription et de déconnexion
 */
@WebServlet(value="/visu/auth", name="visuAuthServlet")
public class VisuAuthServlet extends HttpServlet {

    //Demande de connexion ou d'inscription
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        System.out.println("Demande reçue sur /visu/auth");

        //Un paramètre purpose est dédié pour savoir si l'utilisateur se connecte ou s'inscris
        String purpose = (String) req.getParameter("purpose");
        String login = (String) req.getParameter("login");
        String mdp = (String) req.getParameter("mdp");
        int id = 0;

        //Connexion
        if(purpose.equals("login"))
        {
            System.out.println("C'est une demande de login");
            id = GestionUsers.connexionUtilisateur(login, mdp);
            if(id > 0)
            {
                //On créé la session si la connexion est réussie
                System.out.println("Connexion réussie.");
                HttpSession sess = req.getSession(true);
                sess.setAttribute("login", login);
                sess.setAttribute("id", Integer.toString(id));
                sess.setAttribute("type", Integer.toString(GestionUsers.typeUser(id)));
                resp.sendRedirect("/index");  
            }
            else     
            {
                //On redirige si la connexion échoue
                System.out.println("Connexion échouée.");
                resp.sendRedirect("/index?error=-2");  
            }   
        }
        else if(purpose.equals("inscription")) //inscription
        {
            System.out.println("C'est une demande d'inscription");
            String mdp2 = (String) req.getParameter("mdp2");
            if(mdp.equals(mdp2))
                id = GestionUsers.inscriptionUtilisateur(login, mdp);                
            else
                id = -1;

            if(id <= 0)
                resp.sendRedirect("/index?error=" + Integer.toString(id)); 
            else
                resp.sendRedirect("/index?success");    
        }
        else
            resp.sendRedirect("/index?error=-3"); 
    }

    //Pour les demandes de déconnexion, on invalide et on redirige
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        sess.invalidate();
        resp.sendRedirect("/");
    }
}
