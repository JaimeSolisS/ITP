package com.itp.practica1.data;

import com.itp.practica1.model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public static List<User> getUsers() {

        List<User> list = new ArrayList();
        String username;
        String password;
        String role;
        String status;

        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                username = "admin";
                password = "pass";
                role = "admin";
            } else {
                username = "user" + i;
                password = "pass" + i;
                role = "user";
            }
            status = "STATUS" + i;
            User user = new User(username, password, role, status);
            list.add(user);
        }
        return list;
    }

}
