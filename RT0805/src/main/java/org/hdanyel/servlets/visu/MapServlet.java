package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hdanyel.Beans.Activite;
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
        /*String login = pathInfo.substring(1);
        int id_sport = Integer.parseInt(pathInfo.substring(2));
        int id_activite = Integer.parseInt(pathInfo.substring(3));
        int id_sport = 0;
        String id_activite = "2";*/
        String id_u = "";
        String login = "Nep";
        String id_activite = pathInfo.substring(1);
        JSONConfig activites = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/activites.json");
        JSONConfig users = new JSONConfig("/home/user1/Bureau/projet_java/RT0805/donnees/users.json");

        JSONArray json_users = users.getJSON().getJSONArray("users");
        if(json_users.length() != 0)
		{
			for(int i =0; i < json_users.length(); i++)
			{
				JSONObject temp = new JSONObject(json_users.get(i).toString());
				if(temp.getString("login").equals(login))
				{
					id_u = temp.getString("id");
				}
			}
		}

        JSONArray json_activites = activites.getJSON().getJSONArray("sports");
        Activite act = new Activite();
        if(json_activites.length() != 0)
		{
			for(int i =0; i < json_activites.length(); i++)
			{
				JSONObject temp = new JSONObject(json_activites.get(i).toString());
                System.out.println("id et id comparé :" + temp.getString("id") +"," + id_activite);
                System.out.println("id_u et id_u comparé :" + temp.getString("id_u") +"," + id_u);
				if(temp.getString("id").equals(id_activite) && temp.getString("id_u").equals(id_u))
				{
                    act.setId_u(id_u);
                    act.setId_s(temp.getInt("id_sport"));
                    act.setId_activite(id_activite);
                    act.setDate_debut(temp.getString("debut"));
                    act.setDate_fin(temp.getString("fin"));
                    act.SetPts(temp.getJSONArray("pts"));
				}
			}
		}


        System.out.println("activite :");
        System.out.println(act.getDate_debut());

        req.setAttribute("act", act);

        RequestDispatcher r1 = req.getRequestDispatcher("/map.jsp");
        r1.include(req, resp);
        //RequestDispatcher r1 = request.getRequestDispatcher("incluse.html");
    }
}
