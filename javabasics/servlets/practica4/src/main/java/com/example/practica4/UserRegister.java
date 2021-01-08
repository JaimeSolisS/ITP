package com.example.practica4;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        print(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        print(request, response);
    }

    private void print(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        HttpSession session=request.getSession(false);
        if(session!=null){
            /*request.getRequestDispatcher("index.jsp").include(request, response);*/

            out.println("<html><body>");
            out.println("<h2>Request parameters </h2>");
            out.println("<b>Firstname: </b>" + request.getParameter("firstname") + "<br>");
            out.println("<b>Lastname: </b>" + request.getParameter("lastname") + "<br>");
            out.println("<b>Age: </b>" + request.getParameter("age") + "<br>");
            out.println("<b>Country: </b>" + request.getParameter("country") + "<br>");
            out.println("<b>Subject: </b>" + request.getParameter("subject") + "<br>");
            out.println("<br><br><br>");


            /*Atributo de sesión*/

            Integer count = (Integer) session.getAttribute("trackerCount");
            if (count == null)
                count = 1;
            else count = count.intValue() + 1;

            if (Integer.valueOf(request.getParameter("age")) < 18) {
                out.println("" + "Hello " + request.getParameter("firstname") + "");
                out.println("");
                out.println("Sorry, you are not allowed to see this");
            } else {
                /*Atributo de request*/
                out.println("<h2>Request Attribute </h2>");
                request.setAttribute("Name", request.getParameter("firstname"));
                request.setAttribute("Age", request.getParameter("age"));
                out.println("" + "Hello " + request.getAttribute("Name") + " <--This is a request attribute" + "<br>");
                out.println("Congratulations, you are allowed to enter" + "<br>");

                out.println("<h2>Session Attribute </h2>");
                session.setAttribute("trackerCount", count);
                out.println("You've visited this page " + count + ((count.intValue() == 1) ? " time." : " times.") + "<--This is a session attribute" + "<br>");

                /*Atributo de aplication */
                out.println("<h2>Application Attribute </h2>");
                ServletContext context = getServletContext();
                if (context.getAttribute("Name") == null) {
                    context.setAttribute("Name", request.getParameter("firstname"));
                }
                out.println(context.getAttribute("Name") + "<--This is a Context attribute" + "<br>");

            }
            out.println("</body></html>");

        }
        else{
            out.print("Please login first");
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
        out.close();

    }
}
