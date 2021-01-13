package com.itp.practica2.resource;

import com.itp.practica2.data.Users;
import com.itp.practica2.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {

     private static List<User> userList = Users.getUsers();
    /*private static HashMap<Integer, User> users = Users.getUsers();*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        GenericEntity<List<User>> entity  = new GenericEntity<List<User>>( userList ) {};

        return Response.ok(entity).build();
    }

    @GET
    /*@Path("/{id}")*/
    @Path("/getuser")
    @Produces(MediaType.APPLICATION_JSON)
    /*public Response getUser(@PathParam("id") int id){*/
    public Response getUser(@HeaderParam("userId") int id){
        User found = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() ==(id)) {
                found = userList.get(i);
                break;
            }
        }
        if (found == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        } else {
            return Response.ok(found).build();
        }
    }

    @POST
    @Path("/createuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role){
        User found = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equalsIgnoreCase(username)) {
                found = userList.get(i);
                break;
            }
        }
        if (found == null) {
            int id = userList.size()+1;
            String status = "STATUS" + id;
            User user = new User(id, username, password, role, status);
            userList.add(user);
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Username already exist").build();
        }

    }

    @PUT
    @Path("/updateuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@HeaderParam("userId") int id, @HeaderParam("username") String username,
                               @HeaderParam("password") String password, @HeaderParam("role") String role,
                               @HeaderParam("status") String status) {
        User found = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() ==(id)) {
                found = userList.get(i);
            }
        }
        if (found == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        } else {
            found.setUsername(username);
            found.setPassword(password);
            found.setRole(role);
            found.setStatus(status);

            return Response.ok(found).build();
        }
    }

    @DELETE
    @Path("/deleteuser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updUser(@HeaderParam("userId") int id){
        User found = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() ==(id)) {
                found = userList.get(i);
                userList.remove(found);
                break;
            }
        }
        if (found == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        } else {
            return Response.ok(found).build();
        }
    }

}