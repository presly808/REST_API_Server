package com.sheva.rest;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sheva.dao.BasicElasticsearchDAOTest;
import com.sheva.dao.DBUserDAO;
import com.sheva.dao.object.DBUser;
import com.sheva.rest.object.RSLoginPassword;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vlad on 19.09.16.
 */
public class BasicIntegrationTest extends BasicElasticsearchDAOTest{

    private static Server server;
    public static String BASE_URL = "http://localhost:9090/";
    protected static DBUser BASIC_USER;
    private static JerseyClient client;

    @Autowired
    protected DBUserDAO usersDAO;

    @BeforeClass
    public static void init() throws Exception {
        server = new Server(9090);
        server.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setResourceBase("src/test/resources");
        webAppContext.setDescriptor("src/test/resources/test-web.xml");
        webAppContext.setClassLoader(BasicIntegrationTest.class.getClassLoader());
        server.addHandler(webAppContext);
        server.start();
    }

    protected Client getClient() {
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            client = JerseyClientBuilder.createClient(clientConfig);
        }
        return client;
    }

    protected Cookie login(String login, String password) {
        RSLoginPassword loginPassword = new RSLoginPassword();
        loginPassword.setLogin(login);
        loginPassword.setPassword(password);
        Entity<RSLoginPassword> entity = Entity.entity(loginPassword, MediaType.APPLICATION_JSON);
        Invocation.Builder builder = path("/auth/login").request();
        Response response = builder.post(entity, Response.class);
        return response.getCookies().get("JSESSIONID");
    }

    protected void logout(Cookie cookie) {
        path("/auth/logout").request().cookie(cookie).get();
    }

    protected WebTarget path(String path) {
        return getClient().target(BASE_URL).path(path);
    }

    protected WebTarget path(String path, Map<String, Object> params) {
        WebTarget target = getClient().target(BASE_URL);
        for(String name : params.keySet()){
            target = target.queryParam(name, params.get(name));
        }
        return target.path(path);
    }

    protected static void destroyClient() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    public void truncate(String table) {
        truncate(table);
    }

    @AfterClass
    public static void shutDown() throws Exception {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}