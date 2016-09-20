package com.sheva.dao.elastic;

import com.sheva.dao.DBPostDAO;
import com.sheva.dao.object.DBPost;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */
@Repository
public class ElasticPostDAOImpl implements DBPostDAO{

    private static final Logger log = Logger.getLogger(ElasticPostDAOImpl.class);


    @Autowired
    private ElasticsearchClient client;

    private ObjectMapper jsonObject;

    @PostConstruct
    public void init(){
        jsonObject = new ObjectMapper();
    }

    public DBPost findPostById(UUID id) {
        log.info("Start search user by id: " + id);
        GetResponse response = client.getClient().prepareGet(client.getIndex(), POST_TABLE, id.toString()).execute().actionGet();
        String source = response.getSourceAsString();
        if(source != null){
            try {
                DBPost dbPost = jsonObject.readValue(source, DBPost.class);
                log.info("Post found by id: " + id + " -> " + dbPost.toString());
                return dbPost;
            } catch (IOException e) {
                log.warn("Exception, find post by id: " + id, e);
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deletePostById(UUID id) {
        log.info("Start delete post: " + id);
        try {
            DeleteResponse deleteResponse = client.getClient().prepareDelete(client.getIndex(), POST_TABLE, id.toString())
                    .execute().actionGet();
        } catch (Exception e) {
            log.warn("Exception, not delete post: " + id, e);
            return false;
        }
        return true;
    }

    public boolean updatePost(DBPost dbPost, DBPost dbPostNew) {
        String id = dbPost.getId().toString();
        log.info("Start update post: " + id);
        boolean result;
        try {
            String source = jsonObject.writeValueAsString(dbPostNew);
            client.getClient().prepareIndex(client.getIndex(), POST_TABLE, id).setSource(source).execute().actionGet();
            log.info("Post saved, post: " + id);
             result = true;
        } catch (IOException e) {
            log.warn("Exception, post not save: " + id, e);
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean addPost(DBPost dbPost) {
        String id = dbPost.getId().toString();
        boolean result;
        log.info("Start save, post id: " + id);

        try {
            String source = jsonObject.writeValueAsString(dbPost);
            client.getClient().prepareIndex(client.getIndex(), POST_TABLE, id).setSource(source).execute().actionGet();
            log.info("Post saved, post id: " + id);
            result = true;
        } catch (IOException e) {
            log.error("Exception, post not save, id: " + id, e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
