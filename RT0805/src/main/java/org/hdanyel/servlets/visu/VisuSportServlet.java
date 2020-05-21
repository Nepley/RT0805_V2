package org.hdanyel.servlets.visu;

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

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;

@WebServlet(urlPatterns = "/visu/sports", name = "VisuSportServlet")
public class VisuSportServlet extends HttpServlet
{
    private double Distance(double lat1, double lon1, double lat2, double lon2) 
    {
        int earthRadiusKm = 6371;
                
        double dLat = (lat2-lat1) * Math.PI / 180;
        double dLon = (lon2-lon1) * Math.PI / 180;
        
        lat1 = lat1 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        return earthRadiusKm * c;
    }

    private HttpServletRequest lister_activites(HttpServletRequest req, Utilisateur user)
    {
        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");
        JSONConfig sports = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/sports.json");

        JSONArray json_activites = activites.getJSON().getJSONArray("sports");
        JSONArray json_sports = sports.getJSON().getJSONArray("sports");
        JSONArray liste_activite = new JSONArray();
        JSONObject stats = new JSONObject();

        int nb_activite = 0;
        int activite_plus_distance = -1;
        double distance_totale = 0;
        double distance_plus_grande = 0;

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
                    
                    //Stats 
                    nb_activite += 1;

                    double dist = 0;
                    JSONArray points = temp.getJSONArray("pts");
                    
                    for( int k = 0; k < points.length()-1; k++)
                    {
                        dist += Distance(points.getJSONObject(k).getDouble("coord_x"), points.getJSONObject(k).getDouble("coord_y"),points.getJSONObject(k+1).getDouble("coord_x"), points.getJSONObject(k+1).getDouble("coord_y"));
                    }
                    distance_totale += dist;

                    if(distance_plus_grande < dist)
                    {
                        distance_plus_grande = dist;
                        activite_plus_distance = temp.getInt("id");
                    }
				}
            }
            DecimalFormat df = new DecimalFormat("0.##");
            stats.put("Nb_activite", nb_activite);
            stats.put("Activite_plus_distance", activite_plus_distance);
            stats.put("Distance_totale", df.format(distance_totale));
        }

        req.setAttribute("liste_activite", liste_activite);
        req.setAttribute("stats", stats);

        return req;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }
        else
            resp.sendRedirect("/index");

        req = lister_activites(req, user);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/sport.jsp");
        r1.include(req, resp);
    }

    //On traite les cas où on supprime une activité
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }
        else
            resp.sendRedirect("/index");

        String id = req.getParameter("id_sport");
        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");
        JSONArray json_activites = activites.getJSON().getJSONArray("sports");

        resp.setStatus(401);

        if(user != null)
        {
            for(int i = 0; i < json_activites.length(); i++)
            {
                JSONObject temp = new JSONObject(json_activites.get(i).toString());
                if(temp.getString("id").equals(id))
                {
                    if(temp.getString("id_u").equals(user.getId()))
                    {
                        activites.supprimerDansTab("sports", id);
                        activites.sauvegarder();
                        resp.setStatus(200);
                    }
                }
            }
        }
        
        req = lister_activites(req, user);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/sport.jsp");
        r1.include(req, resp);
     }
 
}