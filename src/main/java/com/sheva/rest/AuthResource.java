package com.sheva.rest;

import com.sheva.dao.object.DBUser;
import com.sheva.mapper.UserMapper;
import com.sheva.rest.filters.JwtAuthenticationFilter;
import com.sheva.rest.object.RSLoginPassword;
import com.sheva.service.AuthService;
import com.sheva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by vlad on 19.09.16.
 */
@Controller
@Path("/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(RSLoginPassword loginPassword) throws URISyntaxException, IOException {
        DBUser user = userService.authenticateUserByLoginOrEmail(loginPassword.getLogin(), loginPassword.getPassword());
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        authService.authorizeUser(user);
        return Response.ok().entity(userMapper.map(user)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerStub(RSLoginPassword loginPassword) throws URISyntaxException, IOException {
        DBUser user = userService.getUserByLogin(loginPassword.getLogin());
        if (user != null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        user = new DBUser();
        user.setLogin(loginPassword.getLogin());
        user.setPassword(loginPassword.getPassword());
        authService.authorizeUser(user);
        DBUser dbUser = userService.saveUser(user);
        return Response.ok().entity(dbUser).build();
    }

}
