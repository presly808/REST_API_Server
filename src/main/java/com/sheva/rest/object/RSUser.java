package com.sheva.rest.object;

import com.sheva.dao.object.DBPost;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

/**
 * Created by gleb on 20.09.16.
 */
@XmlRootElement
public class RSUser {
    private UUID id;
    private String login;
    private String password;
    private String token;
    private List<DBPost> posts;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DBPost> getPosts() {
        return posts;
    }

    public void setPosts(List<DBPost> posts) {
        this.posts = posts;
    }
}
