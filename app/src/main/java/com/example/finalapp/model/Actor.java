package com.example.finalapp.model;

import com.example.finalapp.remoterepository.ActorPojo;

public class Actor {
    private int id;
    private String avatarUrl;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Actor(int id, String avatarUrl, String fullName) {
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
    }

    public static Actor convertPojo(ActorPojo actorPojo){
        return new Actor(actorPojo.id, actorPojo.avatarUrl, actorPojo.fullName);
    }
}
