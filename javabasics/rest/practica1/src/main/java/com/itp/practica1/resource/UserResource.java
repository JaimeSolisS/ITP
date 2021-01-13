package com.itp.practica1.resource;

import com.itp.practica1.data.Users;
import com.itp.practica1.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {

    private static List<User> userList = Users.getUsers();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        GenericEntity<List<User>> entity  = new GenericEntity<List<User>>( userList ) {};

        return Response.ok(entity).build();
    }
}
