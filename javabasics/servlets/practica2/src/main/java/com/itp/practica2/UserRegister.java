package com.itp.practica2;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("Get Method");
        out.println("Este es un parametro del request:" + request.getParameter("name"));
        out.println("Este es un parametro del request:" + request.getParameter("password"));
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("Este es un parametro del request:" + request.getParameter("name"));
        out.println("Este es un parametro del request:" + request.getParameter("password"));
        out.println("</body></html>");
    }

    public void destroy() {
    }
}