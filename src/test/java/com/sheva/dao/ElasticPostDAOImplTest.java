package com.sheva.dao;

import com.sheva.dao.elastic.ElasticPostDAOImpl;
import com.sheva.dao.elastic.ElasticUserDAOImpl;
import com.sheva.dao.object.DBPost;
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
import java.util.Date;
import java.util.UUID;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vlad on 17.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration("/testApplication.xml") })
public class ElasticPostDAOImplTest extends BasicElasticsearchDAOTest {

    @Autowired
    private ElasticPostDAOImpl postDAO;


    @Before
    public void before(){ truncate(DBPostDAO.POST_TABLE);}

    @After
    public void after(){ truncate(DBPostDAO.POST_TABLE);}

    private DBPost create() {
        DBPost post = new DBPost();
        UUID random = UUID.randomUUID();
        post.setId(random);
        post.setText("Test text"+random);
        post.setUserId(random);
        post.setUserId(random);
        post.setDate(new Date());
        post.setMedia(new ArrayList<String>());
        post.getMedia().add("Test"+random);
        return post;
    }
    private void check(DBPost post,  DBPost post1) {
        assertThat(post.getId(), equalTo(post1.getId()));
        assertThat(post.getUserId(), equalTo(post1.getUserId()));
        assertThat(post.getText(), equalTo(post1.getText()));
        assertThat(post.getDate(), equalTo(post1.getDate()));
        assertThat(post.getMedia(), equalTo(post1.getMedia()));
    }
    @Test
    public void test() throws InterruptedException {
        Assert.assertNotNull(postDAO);

        DBPost post = create();
        postDAO.addPost(post);
        //todo delete unnecessary comments
        //Thread.sleep(1000);
        DBPost post1 = postDAO.findPostById(post.getId());
        check(post, post1);
        postDAO.deletePostById(post.getId());
        DBPost post2 = postDAO.findPostById(post.getId());
        Assert.assertNull(post2);
        DBPost post3 = create();
        postDAO.addPost(post);

        postDAO.updatePost(post,post3);


        check(postDAO.findPostById(post.getId()),post3);
    }

}
