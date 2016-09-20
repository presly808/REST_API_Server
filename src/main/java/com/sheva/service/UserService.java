package com.sheva.service;

import com.sheva.dao.DBUserDAO;
import com.sheva.dao.object.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vlad on 20.09.16.
 */
@Service
public class UserService {

    @Autowired
    private DBUserDAO userDAO;

    public DBUser authenticateUserByLoginOrEmail(String login, String pass) {
        DBUser user = userDAO.getUserByLogin(login);
        if (user == null || !user.getPassword().equals(pass)) {
            return null;
        }
        return user;
    }

    public DBUser getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    public DBUser saveUser(DBUser dbUser) {
        userDAO.addUser(dbUser);
        return dbUser;
    }

}
