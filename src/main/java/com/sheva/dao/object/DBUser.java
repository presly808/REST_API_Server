package com.sheva.dao.object;

import java.util.List;
import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */

public class DBUser {
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

    public List<DBPost> getPosts() {
        return posts;
    }

    public void setPosts(List<DBPost> posts) {
        this.posts = posts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DBUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                '}';
    }
}
