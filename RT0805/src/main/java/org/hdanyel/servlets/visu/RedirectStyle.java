/*package org.hdanyel.servlets.visu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hdanyel.Beans.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(urlPatterns = "/res/*" , name="redirectServlet")
public class RedirectStyle extends HttpServlet {

    private ServletContext servletContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String chemin = pathInfo.substring(1);
        //String[] dossier = chemin.split("/");
        System.out.println(chemin);
    }

}*/
