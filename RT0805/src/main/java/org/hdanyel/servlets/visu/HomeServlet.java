package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.beans.Utilisateur;
import org.hdanyel.commun.GestionUsers;
import org.hdanyel.commun.JSONConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(value="/index", name="homeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        //On met en place la session, si l'utilisateur s'est connecté on le met en attribut.
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }

        //Récupération des activités dans leur globalité
        JSONConfig activites = new JSONConfig("donnees/activites.json");
        JSONArray json_activites = activites.getJSON().getJSONArray("sports");

        int MaxId = 0;

        //Parcours du JSON des activités
        if(user != null)
        {
            if(json_activites.length() != 0)
            {
                for(int i =0; i < json_activites.length(); i++)
                {
                    //On récupère la dernière activité de l'utilisateur, qui sera affichée sur la page Web
                    JSONObject temp = new JSONObject(json_activites.get(i).toString());
                    if(temp.getString("id_u").equals(user.getId()) && Integer.parseInt(temp.getString("id")) > MaxId)
                    {
                        MaxId = Integer.parseInt(temp.getString("id"));
                    }
                }
            }
        }

        JSONObject mess = null;
        //Gestion des erreurs et des succès
        String message = req.getParameter("error");
        if(message != null)
        {
            mess = new JSONObject();
            mess.put("mess", "error");
            switch(message)
            {
                case "-1":
                    mess.put("text" ,"Le premier mot de passe n'est pas égal au second.");
                    break;
                case "-2":
                    mess.put("text" ,"Erreur de connexion, réessayez.");
                    break;
                default:
                    mess.put("text" ,"Erreur : Le pseudo est déjà pris.");
                    break;
            }
            req.setAttribute("message", mess);
        }

        message = req.getParameter("success");
        if(message != null)
        {
            mess = new JSONObject();
            mess.put("mess", "success");
            mess.put("text", "Inscription réussie, essayez de vous connecter !");
            req.setAttribute("message", mess);
        }

        req.setAttribute("maxid", MaxId);

        //On indique à la JSP qu'on est bien passé par la Servlet
        req.setAttribute("servlet", true);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("index.jsp");
        r1.include(req, resp);
    }

}
