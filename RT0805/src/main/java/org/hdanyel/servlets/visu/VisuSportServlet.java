package org.hdanyel.servlets.visu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.beans.Activite;
import org.hdanyel.beans.Utilisateur;
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
    /**
     * Calcule la distance en Kilomètres entre deux points GPS
     * @param lat1 Latitude du premier point
     * @param lon1 Longitude du premier point
     * @param lat2 Latitude du deuxième point
     * @param lon2 Longitude du deuxième point
     */
    private double Distance(double lat1, double lon1, double lat2, double lon2) 
    {
        int earthRadiusKm = 6371;

        double dLat = (lat2-lat1) * Math.PI / 180;
        double dLon = (lon2-lon1) * Math.PI / 180;
        
        //Conversion des points donnés de Degrés en Radians
        lat1 = lat1 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;

        //Théorème de Pythagore pour calculer la distance
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        return earthRadiusKm * c;
    }

    /**
     * Fonction servant à lister les activités et les stats, appelée dans doGet et doPost
     * @param req La requête reçue, on va y attacher les activités et les stats puis la retourner
     * @param user L'utilisateur courant, utilisé pour la session
     */
    private HttpServletRequest lister_activites(HttpServletRequest req, Utilisateur user)
    {
        // Récupération des JSON
        JSONConfig activites = new JSONConfig("donnees/activites.json");
        JSONConfig sports = new JSONConfig("donnees/sports.json");

        // Création des variables
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
            // On regarde pour chaque activité si l'id correspond à l'id de l'utilsateur connecté,
			for(int i =0; i < json_activites.length(); i++)
			{
                // Si oui, on regarde quel sport correspond à l'activité grâce à l'id du sport
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

                    // Puis, on ajoute les informations qui nous interesse dans un JSON que l'on ajoute dans la liste des activités
                    JSONObject act = new JSONObject();
                    act.put("Sport", nom_sport);
                    act.put("Activite", temp.getInt("id"));
                    act.put("Heure", temp.getString("debut"));
                    act.put("Date", temp.getString("date"));
                    liste_activite.put(act);
                    
                    // Juste après, on met à jour le nombre d'activité, la distance parcouru totale ainsi que la vérification 
                    // de savoir si cette dernière activité et celle avec le plus de distance. 
                    nb_activite += 1;

                    double dist = 0;
                    JSONArray points = temp.getJSONArray("pts");
                    
                    //On parcoure la liste des points et on additionne la distance entre chacun d'eux pour avoir la distance totale
                    for( int k = 0; k < points.length()-1; k++)
                    {
                        dist += Distance(points.getJSONObject(k).getDouble("coord_x"), points.getJSONObject(k).getDouble("coord_y"),points.getJSONObject(k+1).getDouble("coord_x"), points.getJSONObject(k+1).getDouble("coord_y"));
                    }
                    distance_totale += dist;

                    //Si la distance est la plus grande qu'on ait rencontré, on met à jour le JSON des stats
                    if(distance_plus_grande < dist)
                    {
                        distance_plus_grande = dist;
                        activite_plus_distance = temp.getInt("id");
                    }
				}
            }
            // On mets les variables concernant les stats dans un JSON
            DecimalFormat df = new DecimalFormat("0.##");
            stats.put("Nb_activite", nb_activite);
            stats.put("Activite_plus_distance", activite_plus_distance);
            stats.put("Distance_totale", df.format(distance_totale));
        }

        // On met les JSON en tant qu'attribut pour qu'ils soient utilisable dans le jsp
        req.setAttribute("liste_activite", liste_activite);
        req.setAttribute("stats", stats);

        return req;
    }

    //Cas où l'utilisateur veut juste voir ces activités
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        //On met en place la session, si l'utilisateur s'est connecté on le met en attribut sinon on redirige vers l'index.
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }
        else // L'utilisateur ne doit pas accéder à cette page s'il n'est pas connecté
            resp.sendRedirect("/index");

        req = lister_activites(req, user);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/WEB-INF/jsp/sport.jsp");
        r1.include(req, resp);
    }

    //On traite les cas où on supprime une activité
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
 
        //On met en place la session, si l'utilisateur s'est connecté on le met en attribut sinon on redirige vers l'index.
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }
        else //Les utilisateurs non connectés ne peuvent accéder à la page
            resp.sendRedirect("/index");

        // L'id de l'activité est transmise en paramètre, on la récupère elle est les activités faites
        String id = req.getParameter("id_sport");
        JSONConfig activites = new JSONConfig("donnees/activites.json");
        JSONArray json_activites = activites.getJSON().getJSONArray("sports");

        resp.setStatus(401);

        if(user != null)
        {
            // On regarde pour chaque activité, si l'id correspond à l'id du sport à supprimer aisni que l'id de l'utilsateur
            // pour confirmer que l'activité lui appartient
            for(int i = 0; i < json_activites.length(); i++)
            {
                JSONObject temp = new JSONObject(json_activites.get(i).toString());
                if(temp.getString("id").equals(id))
                {
                    if(temp.getString("id_u").equals(user.getId()))
                    {
                        //On supprime l'activité et on renvoie 200
                        activites.supprimerDansTab("sports", id);
                        activites.sauvegarder();
                        resp.setStatus(200);
                    }
                }
            }
        }
        
        //L'utilisateur n'est pas redirigé, donc on affiche tout de même les activités.
        req = lister_activites(req, user);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/WEB-INF/jsp/sport.jsp");
        r1.include(req, resp);
     }
 
}