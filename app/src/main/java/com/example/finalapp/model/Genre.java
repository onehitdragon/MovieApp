package com.example.finalapp.model;

import com.example.finalapp.remoterepository.GenrePojo;

public class Genre {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Genre convertPojo(GenrePojo genrePojo){
        return new Genre(genrePojo.id, genrePojo.name);
    }
}
