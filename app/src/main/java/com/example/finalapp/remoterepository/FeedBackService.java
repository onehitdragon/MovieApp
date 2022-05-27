package com.example.finalapp.remoterepository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FeedBackService {
    @POST("FeedBack/SendFeedBack")
    @FormUrlEncoded
    Call<Void> sendFeedBack(@Field("email") String email, @Field("content") String content);
}
