package com.example.practica5.controller;

import com.example.practica5.model.User;
import com.example.practica5.data.Users;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    HashMap<String, User> users = Users.createUsers();
    public void init(){
       /*
        HashMap<String, User> users = Users.createUsers();
        ServletContext application = getServletContext();
        application.setAttribute("users", listOfUsers);
        */
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session=request.getSession();
        String name=request.getParameter("username");
        String password=request.getParameter("password");
       /*
        ServletContext application=getServletContext();
       HashMap<String, User> users = (HashMap<String, User>) application.getAttribute("users");
        */
        session.setAttribute("Message", "");
        if (users.get(name)==null){
            session.setAttribute("Message", "User doesn't exist");
            response.sendRedirect("index.jsp");
        } else {
            if(password.equals(users.get(name).password) && name.equals(users.get(name).username)){
                session.setAttribute("Message", "");
                session.setAttribute("username", name);
                session.setAttribute("password", password);
                session.setAttribute("role", users.get(name).role );
                session.setAttribute("status", users.get(name).status);

                switch (users.get(name).role){
                    case "admin":
                       /* request.getRequestDispatcher("AdminServlet").include(request,response);*/
                        response.sendRedirect("AdminServlet");
                        break;
                    case "user":
                      /*  request.getRequestDispatcher("UserServlet").include(request,response);*/
                        response.sendRedirect("UserServlet");
                }
            } else {
                session.setAttribute("Message", "Incorrect password");
                response.sendRedirect("index.jsp");
            }
        }

        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
