package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.finalapp.localrepository.AccountRepository;

public class SettingViewModelFrag extends AndroidViewModel {
    private AccountRepository accountRepository;

    public SettingViewModelFrag(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public void removeAccountFromLocal(){
        accountRepository.delete();
    }
}
