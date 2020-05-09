package org.hdanyel.servlets.visu;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet(value="/index", name="homeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        RequestDispatcher r1 = req.getRequestDispatcher("index.jsp");
        r1.include(req, resp);
        //RequestDispatcher r1 = request.getRequestDispatcher("incluse.html");
    }

}
