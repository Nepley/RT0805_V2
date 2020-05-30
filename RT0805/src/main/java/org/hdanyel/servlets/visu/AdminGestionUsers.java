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

/**
 * Servlet utilisée pour que l'admin gère les utilisateurs
 */
@WebServlet(value="/visu/admin/users", name="adminGestionUsers")
public class AdminGestionUsers extends HttpServlet {

    private ServletContext servletContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        //On met en place la session, si l'utilisateur s'est connecté on le met en attribut.
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            //On vérifie si c'est un admin
            if(user.getType().equals("1"))
            {
                req.setAttribute("user", user);
                req.setAttribute("auth", true);
            }
            else
                resp.sendRedirect("/index");
        }
        else
            resp.sendRedirect("/index");


        //On récupère la liste des utilisateurs
        JSONArray liste_users = GestionUsers.listeUsers();
        req.setAttribute("liste_users", liste_users);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/WEB-INF/jsp/admin_users.jsp");
        r1.include(req, resp);
    }

    //On traite les cas où on supprime un utilisateur
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //On met en place la session, si l'utilisateur s'est connecté on le met en attribut.
        Utilisateur user = GestionUsers.SessionUser(req);

        //On récupère l'id de l'utilisateur à supprimer en argument.
        String id = req.getParameter("id");

        if(user != null)
        {
            //On vérifie que l'utilisateur est un admin
            if(user.getType().equals("1"))
            {
                //On supprime ses activités
                JSONConfig activities = new JSONConfig("donnees/activites.json");
                JSONArray supp = activities.getJSON().getJSONArray("sports");
                if(supp.length() != 0)
                {
                    for(int i =0; i < supp.length(); i++)
                    {
                        //On vérifie si l'utilisateur de l'activité est le même que l'id qu'on a récupéré
                        JSONObject temp = new JSONObject(supp.get(i).toString());
                        if(temp.getString("id_u").equals(id))
                        {
                            activities.supprimerDansTab("sports", temp.getString("id"));
                        }
                    }
                    activities.sauvegarder();
                }
                

                JSONConfig users = new JSONConfig("donnees/users.json");
                //On le supprime et on renvoie 200
                users.supprimerDansTab("users", id);
                users.sauvegarder();
                resp.setStatus(200);
            }
            else
                resp.setStatus(401);
        }
        else
            resp.setStatus(401);
    }

    

}

