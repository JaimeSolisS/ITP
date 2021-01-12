package com.itp.practica1.service;

import com.itp.practica1.data.Users;
import com.itp.practica1.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class ServiceUser {

    private static List<User> userList = Users.getUsers();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(userList).build();
    }
}
