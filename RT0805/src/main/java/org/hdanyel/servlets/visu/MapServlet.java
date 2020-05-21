package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.Beans.Activite;
import org.hdanyel.Beans.Utilisateur;
import org.hdanyel.commun.GestionUsers;
import org.hdanyel.commun.JSONConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;

@WebServlet(urlPatterns="/visu/sports/*", name="mapServlet")
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String pathInfo = req.getPathInfo();

        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }
        else
            resp.sendRedirect("/index");

        String id_activite = pathInfo.substring(1);
        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");


        JSONArray json_activites = activites.getJSON().getJSONArray("sports");
        Activite act = new Activite();
        if(json_activites.length() != 0)
		{
			for(int i =0; i < json_activites.length(); i++)
			{
				JSONObject temp = new JSONObject(json_activites.get(i).toString());
				if(temp.getString("id").equals(id_activite) && temp.getString("id_u").equals(user.getId()))
				{
                    act.setId_u(user.getId());
                    act.setId_s(temp.getInt("id_sport"));
                    act.setId_activite(id_activite);
                    act.setDate_debut(temp.getString("debut"));
                    act.setDate_fin(temp.getString("fin"));
                    act.SetPts(temp.getJSONArray("pts"));
				}
			}
		}

        req.setAttribute("act", act);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/map.jsp");
        r1.include(req, resp);
    }
}
