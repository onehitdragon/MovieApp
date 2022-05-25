package com.example.finalapp.remoterepository;

import retrofit2.Call;
import retrofit2.http.*;

public interface AccountService {
    @POST("Account/Check")
    @FormUrlEncoded
    Call<AccountCheckPojo> check(@Field("email") String email, @Field("password") String password);
    @POST("Account/Register")
    @FormUrlEncoded
    Call<Void> register(@Field("email") String email, @Field("password") String password, @Field("lastName") String lastName,
        @Field("firstName") String firstName, @Field("birthday") String birthDay, @Field("gender") boolean gender);

}
