package com.sheva.rest;

import com.sheva.dao.DBPostDAO;
import com.sheva.dao.DBUserDAO;
import com.sheva.dao.elastic.ElasticPostDAOImpl;
import com.sheva.dao.elastic.ElasticUserDAOImpl;
import com.sheva.dao.object.DBPost;
import com.sheva.dao.object.DBUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vlad on 19.09.16.
 */
public class PostResourseIT extends BasicIntegrationTest {

    @Autowired
    private DBPostDAO postDAO;

    @Before
    public void before() {
        truncate(ElasticPostDAOImpl.POST_TABLE);
    }

    @After
    public void after() {
        truncate(ElasticPostDAOImpl.POST_TABLE);

    }

    // todo unimportant method
    @Test
    public void addUser() {
        DBUser dbUser = new DBUser();
    }

}
