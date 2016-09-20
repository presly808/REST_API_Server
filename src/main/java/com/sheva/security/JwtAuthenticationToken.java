package com.sheva.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by vlad on 19.09.2016.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
        setDetails(token);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        final String token = (String) getDetails();
        if (token == null) {
            return null;
        }
        return token;
    }

    public String getToken() {
        return token;
    }
}
