package com.example.practica4;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession();
        String name=request.getParameter("username");
        String password=request.getParameter("password");

        if(password.equals(getServletContext().getInitParameter("Password")) && name.equals(getServletContext().getInitParameter("Username"))){
            session.setAttribute("username",name);
            response.sendRedirect( "Register.jsp");
        }
        else{
            out.println(request.getContextPath());
            response.sendRedirect("index.jsp");
            out.print("Username or password error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

