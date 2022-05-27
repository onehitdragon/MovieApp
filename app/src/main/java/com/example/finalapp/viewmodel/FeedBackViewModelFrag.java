package com.example.finalapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.model.Account;
import com.example.finalapp.model.FeedBack;
import com.example.finalapp.remoterepository.FeedBackService;
import com.example.finalapp.remoterepository.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedBackViewModelFrag extends ViewModel {
    private FeedBack feedBack;
    private FeedBackService feedBackService;
    private MutableLiveData<Boolean> sendFeedBackSuccess;

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public MutableLiveData<Boolean> getSendFeedBackSuccess() {
        return sendFeedBackSuccess;
    }

    public FeedBackViewModelFrag() {
        feedBack = new FeedBack("");
        Retrofit retrofit = RetrofitClient.createRetrofit();
        feedBackService = retrofit.create(FeedBackService.class);
        sendFeedBackSuccess = new MutableLiveData<>();
    }

    public void sendFeedBack(){
        if(!feedBack.getContent().isEmpty()){
            Account account = Account.getInstance();
            Call<Void> call = feedBackService.sendFeedBack(account.getEmail(), feedBack.getContent());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.e("TAG", "onResponse: ");
                    sendFeedBackSuccess.setValue(true);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("TAG", "onFailure: ");
                    sendFeedBackSuccess.setValue(false);
                }
            });
        }
    }
}
