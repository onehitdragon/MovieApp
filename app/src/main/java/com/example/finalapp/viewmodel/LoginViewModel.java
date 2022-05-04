package com.example.finalapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.model.Account;
import com.example.finalapp.remoterepository.AccountCheckPojo;
import com.example.finalapp.remoterepository.AccountService;
import com.example.finalapp.remoterepository.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "AccountViewModel";
    private Account account;
    private final Retrofit retrofit;
    private MutableLiveData<AccountCheckPojo> accountCheckModel;

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

    public LoginViewModel(){
        account = new Account();
        retrofit = RetrofitClient.createRetrofit();
        accountCheckModel = new MutableLiveData<>();
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
}
