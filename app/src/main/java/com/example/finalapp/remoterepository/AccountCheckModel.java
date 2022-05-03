package com.example.finalapp.remoterepository;

public class AccountCheckModel {
    public boolean emailIsValid;
    public boolean passwordIsValid;

    public AccountCheckModel(){}

    public AccountCheckModel(boolean emailIsValid, boolean passwordIsValid) {
        this.emailIsValid = emailIsValid;
        this.passwordIsValid = passwordIsValid;
    }
}
