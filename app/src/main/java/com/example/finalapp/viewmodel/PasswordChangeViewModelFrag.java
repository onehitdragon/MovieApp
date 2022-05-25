package com.example.finalapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.finalapp.model.Account;
import com.example.finalapp.model.PasswordChangeAccount;

public class PasswordChangeViewModelFrag extends ViewModel {
    private PasswordChangeAccount passwordChangeAccount;

    public PasswordChangeAccount getPasswordChangeAccount() {
        return passwordChangeAccount;
    }

    public void setPasswordChangeAccount(PasswordChangeAccount passwordChangeAccount) {
        this.passwordChangeAccount = passwordChangeAccount;
    }

    public PasswordChangeViewModelFrag(){
        passwordChangeAccount = new PasswordChangeAccount();
    }
}
