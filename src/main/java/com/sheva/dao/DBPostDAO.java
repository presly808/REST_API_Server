package com.sheva.dao;

import com.sheva.dao.object.DBPost;
import org.elasticsearch.action.delete.DeleteResponse;

import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */
public interface DBPostDAO {
    String POST_TABLE ="POST";
    DBPost findPostById(UUID id);
    boolean deletePostById(UUID id);
    boolean updatePost(DBPost dbPost, DBPost dbPostNew);
    boolean addPost(DBPost dbPost);
}
