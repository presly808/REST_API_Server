package com.sheva.rest;

import com.sheva.dao.DBUserDAO;
import com.sheva.dao.object.DBUser;
import com.sheva.mapper.UserMapper;
import com.sheva.rest.object.RSUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by vlad on 18.09.16.
 */
@Controller
@Path("/user")
public class UserResource {
    private Logger log = Logger.getLogger(UserResource.class);

    @Autowired
    private DBUserDAO dbUserDAO;

    @Autowired
    private UserMapper mapper;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") UUID id){
        DBUser dbUser = dbUserDAO.getUserById(id);
        if(dbUser == null){
            log.info("Can't find user with id = " + id);
        }
        return Response.ok().entity(mapper.map(dbUser)).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(RSUser dbUser){
        if(!dbUserDAO.addUser(mapper.map(dbUser))){
            log.info("Can't add user with id = " + dbUser.getId());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(mapper.map(dbUser)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") UUID id) {
        if(!dbUserDAO.deleteUserById(id)){
            log.info("Can't delete user with id = "+ id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(RSUser dbUser){
        if( !dbUserDAO.updateUser(mapper.map(dbUser), mapper.map(dbUser))){
            log.info("Can't update user with id = " +dbUser.getId());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(dbUser.getId()).build();
    }

}
