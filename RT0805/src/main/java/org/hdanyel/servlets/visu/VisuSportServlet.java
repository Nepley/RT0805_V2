package org.hdanyel.servlets.visu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.Beans.Activite;
import org.hdanyel.Beans.Utilisateur;
import org.hdanyel.commun.JSONConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;

@WebServlet(urlPatterns = "/visu/sports", name = "VisuSportServlet")
public class VisuSportServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String pathInfo = req.getPathInfo();

        HttpSession sess = req.getSession();
        Utilisateur user = new Utilisateur();
        user.setId( (String) sess.getAttribute("id"));

        req.setAttribute("user", user);

        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");
        JSONConfig sports = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/sports.json");

        JSONArray json_activites = activites.getJSON().getJSONArray("sports");
        JSONArray json_sports = sports.getJSON().getJSONArray("sports");
        JSONArray liste_activite = new JSONArray();
        if(json_activites.length() != 0)
		{
			for(int i =0; i < json_activites.length(); i++)
			{
				JSONObject temp = new JSONObject(json_activites.get(i).toString());
				if(temp.getString("id_u").equals(user.getId()))
				{
                    String nom_sport = "";
                    for(int j = 0; j < json_sports.length(); j++)
                    {
                        JSONObject temp_2 = new JSONObject(json_sports.get(j).toString());
                        if(temp_2.getInt("id") == temp.getInt("id_sport"))
                        {
                            nom_sport = temp_2.getString("nom");
                        }
                    }

                    JSONObject act = new JSONObject();
                    act.put("Sport", nom_sport);
                    act.put("Activite", temp.getInt("id"));
                    act.put("Heure", temp.getString("debut"));
                    act.put("Date", temp.getString("date"));
                    liste_activite.put(act);
				}
			}
		}

        req.setAttribute("liste_activite", liste_activite);
        System.out.println(liste_activite);

        RequestDispatcher r1 = req.getRequestDispatcher("/sport.jsp");
        r1.include(req, resp);
    }
}