package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.finalapp.localrepository.AccountRepository;
import com.example.finalapp.model.Account;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public Account getAccountLocal(){
        return accountRepository.get();
    }
}
