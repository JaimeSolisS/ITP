package com.itp.controller;

import com.itp.auth.Token;
import com.itp.data.Users;
import com.itp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequestWrapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private static List<User> userList = Users.getUsers();


    public User findUser(String username){
        User found = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                found = userList.get(i);
                break;
            }
        }
        return found;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        HttpSession session=request.getSession();
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        User found = findUser(username);

        if (found == null){
            /*response.setHeader("Error", "User not found");*/
            response.sendRedirect("login.html");
        } else if (password.equals(found.getPassword())){
            Token token = new Token();
            session.setAttribute("authorization", token.getToken());
            response.sendRedirect("menu.html");
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
