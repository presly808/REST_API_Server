package com.sheva.mapper;

import com.sheva.dao.object.DBPost;
import com.sheva.rest.object.RSPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by vlad on 20.09.16.
 */
@Component
public class PostMapper {

    @Autowired
    private BasicDozerMapper mapper;

    public RSPost map(DBPost dbPost) {
        return mapper.map(dbPost, RSPost.class);
    }

    public DBPost map(RSPost rsPost) {
        return mapper.map(rsPost, DBPost.class);
    }

}
