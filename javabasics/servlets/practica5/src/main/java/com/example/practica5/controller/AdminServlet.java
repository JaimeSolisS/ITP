package com.example.practica5.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session=request.getSession(false);
        if (!session.getAttribute("role").equals("admin")){
            response.sendRedirect("index.jsp");
        }
        PrintWriter out=response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Admin Servlet</h2>");
        out.println("Username: <b>" + session.getAttribute("username") + "</b><br>");
        out.println("Password: <b>" + session.getAttribute("password") + "</b><br>");
        out.println("Role: <b>" + session.getAttribute("role") + "</b><br>");
        out.println("Status: <b>" + session.getAttribute("status") + "</b><br>");
        out.println("</body></html>");


    }
}
