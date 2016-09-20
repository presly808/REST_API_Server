package com.sheva.service;

import com.sheva.dao.object.DBUser;
import com.sheva.security.JwtAuthenticationToken;
import com.sheva.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.SecurityContext;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    public DBUser getCurrentUser(SecurityContext securityContext) {
        JwtAuthenticationToken userAuth = (JwtAuthenticationToken) securityContext.getUserPrincipal();
        if (userAuth == null || userAuth.getToken() == null || userAuth.getToken().isEmpty()) {
            return null;
        }
        return jwtUtil.parseToken(userAuth.getToken());
    }

    public void authorizeUser(DBUser user) {
        String token = jwtUtil.generateToken(user);
        user.setToken(token);
        Authentication a = new JwtAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(a);
    }
}
