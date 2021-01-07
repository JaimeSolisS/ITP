package com.itp.practica3;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession(false);
        if (session!=null){
            request.getRequestDispatcher("index.jsp").include(request, response);
            out.print("You have to login!!!");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession();
        String name=request.getParameter("username");
        String password=request.getParameter("password");



            if(password.equals("qwerty") && name.equals("admin")){
                session.setAttribute("username",name);
                request.getRequestDispatcher("links.html").include(request, response);
                out.print("Welcome, " + name);
                request.getRequestDispatcher("Register.html").include(request, response);
            }
            else{
                request.getRequestDispatcher("index.jsp").include(request, response);
                out.print("Username or password error");
            }
            out.close();
        }
    }

