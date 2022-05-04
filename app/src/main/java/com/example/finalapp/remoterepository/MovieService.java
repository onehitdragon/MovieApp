package com.example.finalapp.remoterepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("Movie/ListNewestMovie")
    Call<ArrayList<MoviePojo>> getListNewestMovie(@Query("amount") int amount);

    @GET("Movie/ListMovieByGenre")
    Call<ArrayList<MoviePojo>> getListMovieByGenre(@Query("idGenre") int idGenre);
}
