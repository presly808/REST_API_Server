package com.sheva.dao;
import com.sheva.dao.elastic.ElasticUserDAOImpl;
import com.sheva.dao.object.DBPost;
import com.sheva.dao.object.DBUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
/**
 * Created by vlad on 18.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration("/testApplication.xml") })
public class ElasticUserDAOImplTest extends BasicElasticsearchDAOTest {

    @Autowired
    private ElasticUserDAOImpl userDAO;

 @Before
    public void before() {
        truncate(DBUserDAO.USER_TABLE);
    }

  @After
    public void after() {
        truncate(DBUserDAO.USER_TABLE);
    }

    private void check(DBUser user,  DBUser user1) {
        assertThat(user.getId(), equalTo(user1.getId()));
        assertThat(user.getLogin(), equalTo(user1.getLogin()));
        assertThat(user.getPassword(), equalTo(user1.getPassword()));
        //assertThat(user.getPosts(), equalTo(user1.getPosts()));
    }

    private DBUser create() {
        DBUser user = new DBUser();

        UUID random = UUID.randomUUID();
        user.setId(random);
        user.setLogin("Test" + random);
        user.setPassword("Test");
        user.setPosts(new ArrayList<DBPost>());
        user.setToken("Test");

        return user;
    }
    @Test
    public void Test()throws InterruptedException{
        Assert.assertNotNull(userDAO);

       DBUser user = create();
        userDAO.addUser(user);
        DBUser user1 = userDAO.getUserById(user.getId());
        check(user, user1);
        userDAO.deleteUserById(user.getId());
        DBUser user2 = userDAO.getUserById(user.getId());
        Assert.assertNull(user2);
        DBUser user3 = create();
        userDAO.addUser(user);

        userDAO.updateUser(user,user3);


        check(userDAO.getUserById(user.getId()),user3);
        DBUser user4 = create();
        userDAO.addUser(user4);
        Thread.sleep(3000);
        check(userDAO.getUserByLogin(user4.getLogin()),user4);
    }




}