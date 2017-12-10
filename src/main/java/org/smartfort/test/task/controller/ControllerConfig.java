package org.smartfort.test.task.controller;

import org.smartfort.test.task.model.User;
import org.smartfort.test.task.service.UserService;
import org.smartfort.test.task.service.impl.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.smartfort.test.task.controller.App.ROOT_PATH;

@Path(ROOT_PATH)
public class ControllerConfig {

    private UserService service = UserServiceImpl.getInstance();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        try {
            service.createUser(user);
            return Response.status(201).build();
        } catch (final IllegalArgumentException e) {
            return  Response.status(400).build();
        } catch (final Exception e) {
            return  Response.status(500).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") final int id) {
        try {
            User user = service.getUserById(id);
            return Response.ok(user).build();
        } catch (final IllegalArgumentException e) {
            return  Response.status(400).build();
        } catch (final Exception e) {
            return  Response.status(500).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try{
            List<User> users = service.getUsers();
            return Response.ok(users).build();
        } catch (final IllegalArgumentException e) {
            return  Response.status(400).build();
        } catch (final Exception e) {
            return  Response.status(500).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        try {
            service.updateUser(user);
            return Response.status(200).build();
        } catch (final IllegalArgumentException e) {
            return  Response.status(400).build();
        } catch (final Exception e) {
            return  Response.status(500).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserById(@PathParam("id") int id) {
        try {
            service.deleteUserById(id);
            return Response.status(200).build();
        } catch (final IllegalArgumentException e) {
            return  Response.status(400).build();
        } catch (final Exception e) {
            return  Response.status(500).build();
        }
    }
}
