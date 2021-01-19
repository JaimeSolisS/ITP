package com.itp.service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session=request.getSession(false);

        if(request.getMethod().equalsIgnoreCase("GET") && session.getAttribute("authorization")== null){
                response.sendRedirect("login.html");
                return;
            }else {
                response.setHeader("authorization", session.getAttribute("authorization").toString());
                response.setStatus(200);
                chain.doFilter(request, response);
            }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
