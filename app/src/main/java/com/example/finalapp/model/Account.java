package com.example.finalapp.model;

public class Account{
    private String email;
    private String password;
    private static Account instance;

    public static Account getInstance(){
        if(instance == null){
            instance = new Account();
        }

        return instance;
    }

    public static Account getInstance(String email, String password){
        if(instance == null){
            instance = new Account(email, password);
        }

        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(){}

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
