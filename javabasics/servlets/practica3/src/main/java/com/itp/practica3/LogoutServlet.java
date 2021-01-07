package com.itp.practica3;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession(false);
        if (session!=null){
            request.getRequestDispatcher("index.jsp").include(request, response);
            out.print("You are not logged in");
        } else{
            request.getRequestDispatcher("index.jsp").include(request, response);

            session=request.getSession();
            session.invalidate();

            out.print("You are logged out");
        }
        out.close();
    }
}