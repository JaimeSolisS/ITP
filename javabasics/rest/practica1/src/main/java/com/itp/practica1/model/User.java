package com.itp.practica1.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User  {
    public String username;
    public String password;
    public String role;
    public String status;

    public User(){

    }

    public User(String username, String password, String role, String status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }


    public String getStatus() {
        return status;
    }

}
