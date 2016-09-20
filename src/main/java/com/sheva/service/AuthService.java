package com.sheva.service;

import com.sheva.dao.object.DBUser;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by vlad on 19.09.16.
 */
public interface AuthService {
    DBUser getCurrentUser(SecurityContext securityContext);
    void authorizeUser(DBUser user);
}
