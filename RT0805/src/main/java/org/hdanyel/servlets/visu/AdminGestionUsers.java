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
import org.json.JSONArray;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(value="/visu/admin/users", name="adminGestionUsers")
public class AdminGestionUsers extends HttpServlet {

    private ServletContext servletContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        Utilisateur user = GestionUsers.SessionUser(req);
        if(user != null)
        {
            req.setAttribute("user", user);
            req.setAttribute("auth", true);
        }

        if(user.getType().equals("1"))
        {
            req.setAttribute("user", user);
        }
        else
            resp.sendRedirect("/index");

        JSONArray liste_users = GestionUsers.listeUsers();
        req.setAttribute("liste_users", liste_users);

        resp.setContentType("text/html; charset=UTF-8");
        RequestDispatcher r1 = req.getRequestDispatcher("/admin_users.jsp");
        r1.include(req, resp);
        //RequestDispatcher r1 = request.getRequestDispatcher("incluse.html");
    }

}
