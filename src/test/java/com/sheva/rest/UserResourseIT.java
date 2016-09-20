package com.sheva.rest;

import com.sheva.dao.DBUserDAO;
import com.sheva.dao.elastic.ElasticUserDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vlad on 19.09.16.
 */
public class UserResourseIT extends BasicIntegrationTest {

    @Autowired
    DBUserDAO userDAO;

    @Before
    public void before() {
        truncate(ElasticUserDAOImpl.USER_TABLE);
    }

    @After
    public void after() {
        truncate(ElasticUserDAOImpl.USER_TABLE);

    }

}
