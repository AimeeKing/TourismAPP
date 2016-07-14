package com.example.aimee.bottombar.newServer.model;

/**
 * Created by Aimee on 2016/5/7.
 */
public class Song {
    private int id;
    private String url;

    public Song(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Song(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
