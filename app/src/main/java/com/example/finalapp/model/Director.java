package com.example.finalapp.model;

import com.example.finalapp.remoterepository.DirectorPojo;

public class Director {
    private int id;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Director(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public static Director convertPojo(DirectorPojo directorPojo){
        return new Director(directorPojo.id, directorPojo.fullName);
    }
}
