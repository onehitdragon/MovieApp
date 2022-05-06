package com.example.finalapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.model.Genre;
import com.example.finalapp.model.Movie;
import com.example.finalapp.remoterepository.GenrePojo;
import com.example.finalapp.remoterepository.GenreService;
import com.example.finalapp.remoterepository.MoviePojo;
import com.example.finalapp.remoterepository.MovieService;
import com.example.finalapp.remoterepository.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModelFrag extends ViewModel {
    private Retrofit retrofit;
    private MovieService movieService;
    private GenreService genreService;
    private MutableLiveData<ArrayList<Movie>> listNewestMovie;
    private MutableLiveData<ArrayList<Genre>> listGenre;
    private MutableLiveData<ArrayList<Movie>> listMovieByGenre;
    private Genre currentGenre;
    private Movie currentMovie;

    public MutableLiveData<ArrayList<Movie>> getListNewestMovie() {
        return listNewestMovie;
    }

    public void setListNewestMovie(MutableLiveData<ArrayList<Movie>> listNewestMovie) {
        this.listNewestMovie = listNewestMovie;
    }

    public MutableLiveData<ArrayList<Genre>> getListGenre() {
        return listGenre;
    }

    public void setListGenre(MutableLiveData<ArrayList<Genre>> listGenre) {
        this.listGenre = listGenre;
    }

    public MutableLiveData<ArrayList<Movie>> getListMovieByGenre() {
        return listMovieByGenre;
    }

    public void setListMovieByGenre(MutableLiveData<ArrayList<Movie>> listMovieByGenre) {
        this.listMovieByGenre = listMovieByGenre;
    }

    public Genre getCurrentGenre() {
        return currentGenre;
    }

    public void setCurrentGenre(Genre currentGenre) {
        this.currentGenre = currentGenre;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public HomeViewModelFrag() {
        listNewestMovie = new MutableLiveData<>();
        listGenre = new MutableLiveData<>();
        listMovieByGenre = new MutableLiveData<>();
        retrofit = RetrofitClient.createRetrofit();
        movieService = retrofit.create(MovieService.class);
        genreService = retrofit.create(GenreService.class);

        loadListNewestMovie();
        loadListGenre();
    }

    public void loadListNewestMovie(){
        int amount = 5;
        Call<ArrayList<MoviePojo>> call = movieService.getListNewestMovie(amount);
        call.enqueue(new Callback<ArrayList<MoviePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<MoviePojo>> call, Response<ArrayList<MoviePojo>> response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                if (response.body() == null) return;
                ArrayList<Movie> listMovieResult = new ArrayList<>();
                for (MoviePojo moviePojo : response.body()){
                    Movie movie = Movie.convertPojo(moviePojo);
                    listMovieResult.add(movie);
                }
                listNewestMovie.setValue(listMovieResult);
            }

            @Override
            public void onFailure(Call<ArrayList<MoviePojo>> call, Throwable t) {
                Log.e("TAG", "Fail: " + t.getMessage());
            }
        });
    }

    public void loadListGenre(){
        Call<ArrayList<GenrePojo>> call = genreService.getListGenre();
        call.enqueue(new Callback<ArrayList<GenrePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GenrePojo>> call, Response<ArrayList<GenrePojo>> response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                if (response.body() == null) return;
                ArrayList<Genre> listGenreResult = new ArrayList<>();
                for(GenrePojo genrePojo : response.body()){
                    Genre genre = Genre.convertPojo(genrePojo);
                    listGenreResult.add(genre);
                }
                listGenre.setValue(listGenreResult);
            }

            @Override
            public void onFailure(Call<ArrayList<GenrePojo>> call, Throwable t) {
                Log.e("TAG", "Fail: " + t.getMessage());
            }
        });
    }

    public void loadListMovieByGenre(Genre genre){
        if(genre == currentGenre) return;
        Call<ArrayList<MoviePojo>> call = movieService.getListMovieByGenre(genre.getId());
        call.enqueue(new Callback<ArrayList<MoviePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<MoviePojo>> call, Response<ArrayList<MoviePojo>> response) {
                Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                if (response.body() == null) return;
                ArrayList<Movie> listMovieResult = new ArrayList<>();
                for (MoviePojo moviePojo : response.body()){
                    Movie movie = Movie.convertPojo(moviePojo);
                    listMovieResult.add(movie);
                }
                listMovieByGenre.setValue(listMovieResult);
            }

            @Override
            public void onFailure(Call<ArrayList<MoviePojo>> call, Throwable t) {
                Log.e("TAG", "Fail: " + t.getMessage());
            }
        });
        currentGenre = genre;
    }
}
