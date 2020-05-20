package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.Beans.Utilisateur;
import org.hdanyel.commun.GestionUsers;
import org.hdanyel.commun.JSONConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(value="/index", name="homeServlet")
public class HomeServlet extends HttpServlet {

    private ServletContext servletContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }

        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");
        JSONArray json_activites = activites.getJSON().getJSONArray("sports");

        int MaxId = 0;

        if(user != null)
        {
            if(json_activites.length() != 0)
            {
                for(int i =0; i < json_activites.length(); i++)
                {
                    JSONObject temp = new JSONObject(json_activites.get(i).toString());
                    if(temp.getString("id_u").equals(user.getId()) && Integer.parseInt(temp.getString("id")) > MaxId)
                    {
                        MaxId = Integer.parseInt(temp.getString("id"));
                    }
                }
            }
        }

        req.setAttribute("maxid", MaxId);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("index.jsp");
        r1.include(req, resp);
    }

}
