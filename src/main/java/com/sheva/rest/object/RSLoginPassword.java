package com.sheva.rest.object;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vlad on 19.09.16.
 */
@XmlRootElement
public class RSLoginPassword {
    private String login;
    private String password;

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
}
