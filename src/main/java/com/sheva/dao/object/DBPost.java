package com.sheva.dao.object;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by vlad on 17.09.16.
 */

public class DBPost {
    private UUID id; //           идентификатор записи
    private UUID userId;//        ид юзера которому пренадлежит пост
    private Date date;//	     время публикации записи в формате Date
    private String text;//       текст записи
    private List<String> media;// список ссылок на медиа файлы

    public DBPost() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "DBPost{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", media=" + media +
                '}';
    }
}
