package com.example.practica5.data;

import com.example.practica5.model.User;

import java.util.HashMap;

public class Users {

        public static HashMap<String, User> createUsers(){
            HashMap<String, User> users = new HashMap<String, User>();

            String username;
            String password;
            String role;
            String status;

            for(int i=0; i<10; i++){
                if (i == 0){
                     username = "admin";
                     password = "pass" ;
                     role = "admin";
                } else {
                    username = "user" + i;
                    password = "pass" + i;
                    role = "user";
                }
                status = "STATUS" +i;
                User user = new User(username, password, role, status);
                users.put(username, user);
            }
            return users;
        }



}

