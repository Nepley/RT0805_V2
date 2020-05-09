package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import javax.servlet.RequestDispatcher;

@WebServlet(value="/map/*", name="mapServlet")
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        /*int id_u = int.valueOf(pathInfo.substring(1));
        int id_sport = int.valueOf(pathInfo.substring(2));*/
        RequestDispatcher r1 = req.getRequestDispatcher("map.jsp");
        r1.include(req, resp);
        //RequestDispatcher r1 = request.getRequestDispatcher("incluse.html");
    }

}
