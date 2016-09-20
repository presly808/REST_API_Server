package com.sheva.mapper;

import com.sheva.dao.object.DBUser;
import com.sheva.rest.object.RSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by vlad on 20.09.16.
 */
@Component
public class UserMapper {

    @Autowired
    private BasicDozerMapper mapper;

    public RSUser map(DBUser dbUser) {
        return mapper.map(dbUser, RSUser.class);
    }

    public DBUser map(RSUser rsUser) {
        return mapper.map(rsUser, DBUser.class);
    }

}
