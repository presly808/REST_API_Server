package com.sheva.dao.elastic;

import com.sheva.dao.DBUserDAO;
import com.sheva.dao.object.DBUser;
import org.elasticsearch.action.get.GetResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */
@Repository
public class ElasticUserDAOImpl implements DBUserDAO {
    private static final Logger log = Logger.getLogger(ElasticUserDAOImpl.class);

    @Autowired
    private ElasticsearchClient client;

    private ObjectMapper jsonObject;

    @PostConstruct
    public void init() {
        jsonObject = new ObjectMapper();
    }

    public DBUser getUserById(UUID id) {
        log.info("Start search user by id: " + id);
        GetResponse response = client.getClient().prepareGet(client.getIndex(), USER_TABLE, id.toString()).execute().actionGet();
        String source = response.getSourceAsString();
        if (source != null) {
            try {
                DBUser dbUser = jsonObject.readValue(source, DBUser.class);
                log.info("User found by id: " + id + " -> " + dbUser.toString());
                return dbUser;
            } catch (IOException e) {
                log.warn("Exception, find user by id: " + id, e);
                e.printStackTrace();
            }
        }
        log.warn("User not found by id: " + id);
        return null;
    }

    public boolean deleteUserById(UUID id) {
        log.info("Start delete user: " + id);
        boolean result;
        try {
            DeleteResponse deleteResponse = client.getClient().prepareDelete(client.getIndex(), USER_TABLE, id.toString())
                    .execute().actionGet();
            result = true;
        } catch (Exception e) {
            log.warn("Exception, not delete user: " + id, e);
            result = false;
        }
        return result;
    }

    public boolean updateUser(DBUser dbUser, DBUser dbUserNew) {
        String id = String.valueOf(dbUser.getId());
        boolean result;
        log.info("Start update user: " + id);
        try {
            String source = jsonObject.writeValueAsString(dbUserNew);
            client.getClient().prepareIndex(client.getIndex(), USER_TABLE, id).setSource(source).execute().actionGet();
            log.info("User update, user: " + id);
            result = true;
        } catch (IOException e) {
            log.warn("Exception, user not update: " + id, e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean addUser(DBUser dbUser) {
        boolean result;
        if (dbUser.getId() == null){
            dbUser.setId(UUID.randomUUID());
        }
        log.info("Start save user: " + dbUser.getId().toString());
        try {
            String source = jsonObject.writeValueAsString(dbUser);
            client.getClient().prepareIndex(client.getIndex(), USER_TABLE, String.valueOf(dbUser.getId())).setSource(source).execute().actionGet();
            log.info("User saved, user: " + dbUser.getId().toString());
            result = true;
        } catch (IOException e) {
            log.warn("Exception, user not save: " + dbUser.getId().toString(), e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DBUser getUserByLogin(String login) {
        log.info("Start search user by login: " + login);
        SearchRequestBuilder builder = client.getClient().prepareSearch(client.getIndex()).setTypes(USER_TABLE)
                .setQuery(QueryBuilders.commonTermsQuery("login", login));
        SearchResponse response = builder.get();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DBUser dbUser = null;
        SearchHit[] hits = response.getHits().getHits();
        if (hits != null && hits.length > 0) {
            String source = hits[0].getSourceAsString();

            try {
                dbUser = jsonObject.readValue(source, DBUser.class);
                log.info("User found by login: " + login + " -> " + dbUser.toString());
                return dbUser;
            } catch (IOException e) {
                log.warn("Exception, find user by login: " + login, e);
                e.printStackTrace();
            }
        }
        log.warn("User not found by login: " + login);
        return dbUser;
    }


}
