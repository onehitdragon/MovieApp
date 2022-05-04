package com.example.finalapp.remoterepository;

import com.example.finalapp.model.Genre;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreService {
    @GET("Genre/ListGenre")
    Call<ArrayList<GenrePojo>> getListGenre();
}
