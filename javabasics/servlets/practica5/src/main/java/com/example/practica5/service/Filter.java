package com.example.practica5.service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.print("filter is invoked before" + "<br>");

        if(request.getMethod().equalsIgnoreCase("GET")){
            if (session.getAttribute("username")==null){
                response.sendRedirect("index.jsp");
            } else chain.doFilter(request, response);
        }else chain.doFilter(request, response);

        out.print("filter is invoked after");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
