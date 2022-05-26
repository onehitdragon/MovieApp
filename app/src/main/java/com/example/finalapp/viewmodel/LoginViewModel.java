package com.example.finalapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.localrepository.AccountRepository;
import com.example.finalapp.model.Account;
import com.example.finalapp.remoterepository.AccountCheckPojo;
import com.example.finalapp.remoterepository.AccountService;
import com.example.finalapp.remoterepository.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "AccountViewModel";
    private Account account;
    private final Retrofit retrofit;
    private MutableLiveData<AccountCheckPojo> accountCheckModel;
    private AccountRepository accountRepository;

    public MutableLiveData<AccountCheckPojo> getAccountCheckModel() {
        return accountCheckModel;
    }

    public void setAccountCheckModel(MutableLiveData<AccountCheckPojo> accountCheckModel) {
        this.accountCheckModel = accountCheckModel;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LoginViewModel(@NonNull Application application){
        super(application);
        account = Account.getInstance();
        retrofit = RetrofitClient.createRetrofit();
        accountCheckModel = new MutableLiveData<>();
        accountRepository = new AccountRepository(application);
    }

    public void checkAccount(){
        AccountService accountService = retrofit.create(AccountService.class);
        Call<AccountCheckPojo> call = accountService.check(account.getEmail(), account.getPassword());
        call.enqueue(new Callback<AccountCheckPojo>() {
            @Override
            public void onResponse(Call<AccountCheckPojo> call, Response<AccountCheckPojo> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                accountCheckModel.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AccountCheckPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
                accountCheckModel.setValue(null);
            }
        });
    }

    public void saveAccountToLocal(){
        accountRepository.insert(account);
    }
}
