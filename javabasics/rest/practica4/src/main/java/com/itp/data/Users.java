package com.itp.data;

import com.itp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public static  List<User>  getUsers() {

        List<User> list = new ArrayList();

        int id;
        String username;
        String password;
        String role;
        String status;

        for (int i = 1; i <= 10; i++) {
            id = i;
            if (i == 1) {
                username = "admin";
                password = "pass";
                role = "admin";
            } else {
                username = "user" + i;
                password = "pass" + i;
                role = "user";
            }
            status = "STATUS" + i;
            User user = new User(id, username, password, role, status);
            list.add(user);
        }
        return list;
    }

}