package com.sheva.dao;

import com.sheva.dao.object.DBUser;

import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */
public interface DBUserDAO {
      String USER_TABLE = "USER";
      DBUser getUserById(UUID id);
      boolean deleteUserById(UUID id);
      boolean updateUser(DBUser dbUser, DBUser dbUserNew);
      boolean addUser(DBUser dbUser);
      DBUser getUserByLogin(String login);


}
