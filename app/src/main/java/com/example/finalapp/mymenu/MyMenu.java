package com.example.finalapp.mymenu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.lifecycle.ViewModelProvider;

import com.example.finalapp.R;
import com.example.finalapp.model.InfoDownloadMovie;
import com.example.finalapp.model.Movie;
import com.example.finalapp.mydialog.MyDialog;
import com.example.finalapp.mydialog.MyDialogFactory;
import com.example.finalapp.service.MovieDownloadService;
import com.example.finalapp.view.MainActivity;
import com.example.finalapp.viewmodel.DownloadViewModelFrag;
import com.example.finalapp.viewmodel.LaterMovieViewModelFrag;
import com.example.finalapp.viewmodel.MovieWatchingViewModelFrag;

public class MyMenu {
    private Context context;

    public MyMenu(Context context) {
        this.context = context;
    }

    public PopupMenu createLaterMovieMenu(View itemView, Movie movie){
        PopupMenu popupMenu = new PopupMenu(context, itemView);
        popupMenu.inflate(R.menu.later_movie_menu);
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            int id = item.getItemId();
            if(id == R.id.deleteFromLater){
                LaterMovieViewModelFrag laterMovieViewModelFrag = new ViewModelProvider((MainActivity)context).get(LaterMovieViewModelFrag.class);
                laterMovieViewModelFrag.deleteMovieFromLater(movie);
                return true;
            }
            return false;
        });

        return popupMenu;
    }

    public PopupMenu createMovieMenu(View itemView, Movie movie, Movie.OnMovieClick onMovieClick){
        PopupMenu popupMenu = new PopupMenu(context, itemView);
        popupMenu.inflate(R.menu.movie_menu);
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            int id = item.getItemId();
            if(id == R.id.watchingMovie){
                onMovieClick.click(movie);
                return true;
            }
            if(id == R.id.addToLater){
                MovieWatchingViewModelFrag movieWatchingViewModelFrag = new ViewModelProvider((MainActivity)context).get(MovieWatchingViewModelFrag.class);
                movieWatchingViewModelFrag.addMovieToLater(movie);
                MyDialog myDialog = MyDialogFactory.createAddedLaterMovie(context);
                myDialog.show();
                return true;
            }

            return false;
        });

        return popupMenu;
    }

    public PopupMenu createDownloadMovieMenu(View itemView, InfoDownloadMovie infoDownloadMovie){
        PopupMenu popupMenu = new PopupMenu(context, itemView);
        popupMenu.inflate(R.menu.download_movie_menu);
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            int id = item.getItemId();
            if(id == R.id.removeFromDownload){
                DownloadViewModelFrag downloadViewModelFrag = new ViewModelProvider((MainActivity)context).get(DownloadViewModelFrag.class);
                downloadViewModelFrag.deleteFromDownload(infoDownloadMovie);
                Intent intent = new Intent(context, MovieDownloadService.class);
                context.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        MovieDownloadService movieDownloadService = ((MovieDownloadService.MyBinder)iBinder).getService();
                        movieDownloadService.removeFromDownload(infoDownloadMovie);
                        context.unbindService(this);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                }, Context.BIND_AUTO_CREATE);

                return true;
            }

            return false;
        });

        return popupMenu;
    }

    public PopupMenu createDownloadedMovieMenu(View itemView, InfoDownloadMovie infoDownloadMovie){
        PopupMenu popupMenu = new PopupMenu(context, itemView);
        popupMenu.inflate(R.menu.downloaded_movie_menu);
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            int id = item.getItemId();
            if(id == R.id.removeFromDownload){
                DownloadViewModelFrag downloadViewModelFrag = new ViewModelProvider((MainActivity)context).get(DownloadViewModelFrag.class);
                downloadViewModelFrag.deleteFromDownload(infoDownloadMovie);
                infoDownloadMovie.getDestinationPath(context).delete();
                return true;
            }

            return false;
        });

        return popupMenu;
    }
}
