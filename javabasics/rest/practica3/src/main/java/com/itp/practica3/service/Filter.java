package com.itp.practica3.service;

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

        if (request.getHeader("authorization").equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjEwMDgxMTc4LCJleHAiOjE2MTA5NDUxNzh9.Qfqg9-FdsMMMFyoKwk5NcGr1bWUKnv_tPZTBeiAF2ADJSG0c6FDF2RFUtaQORGLXtyCpIjmitbh61VWZxm6A5g")){
           chain.doFilter(request, response);
        } else response.setStatus(401);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
