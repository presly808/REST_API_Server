package com.sheva.rest;

import com.sheva.dao.DBPostDAO;
import com.sheva.dao.object.DBPost;
import com.sheva.mapper.PostMapper;
import com.sheva.rest.object.RSPost;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


/**
 * Created by vlad on 17.09.16.
 */
@Controller
@Path("/post")
public class PostResource  {
    private Logger log = Logger.getLogger(PostResource.class);

    @Autowired
    private DBPostDAO dbPostDAO;

    @Autowired
    private PostMapper mapper;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPostById(@PathParam("id") UUID id){
        DBPost dbPost = dbPostDAO.findPostById(id);
        if(dbPost == null){
            log.info("Can't find post with id = " + id);
        }
        return Response.ok().entity(mapper.map(dbPost)).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(RSPost rsPost){
     if(!dbPostDAO.addPost(mapper.map(rsPost))){
        log.info("Can't add post with id = " + rsPost.getId());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
       return Response.ok().entity(mapper.map(rsPost)).build();
    }
    
    @DELETE
    @Path("/delete/{id}")
    public Response deletePost(@PathParam("id") UUID id) {
        if(!dbPostDAO.deletePostById(id)){
            log.info("Can't delete post with id = "+ id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
        }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(RSPost dbPost){
       if( !dbPostDAO.updatePost(mapper.map(dbPost), mapper.map(dbPost))){
           log.info("Can't update post with id = " +dbPost.getId());
           return Response.status(Response.Status.BAD_REQUEST).build();
       }
        return Response.ok().entity(dbPost.getId()).build();
    }


    }

