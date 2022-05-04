package com.example.finalapp.remoterepository;

public class AccountCheckPojo {
    public boolean emailIsValid;
    public boolean passwordIsValid;

    public AccountCheckPojo(){}

    public AccountCheckPojo(boolean emailIsValid, boolean passwordIsValid) {
        this.emailIsValid = emailIsValid;
        this.passwordIsValid = passwordIsValid;
    }
}
