package com.example.finalapp.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.model.Episode;
import com.example.finalapp.model.InfoDownloadMovie;
import com.example.finalapp.model.Movie;
import com.example.finalapp.mymenu.MyMenu;
import com.example.finalapp.service.MovieDownloadService;
import com.example.finalapp.view.MainActivity;

import java.util.ArrayList;

public class RecycleViewDownloadMovieListAdapter extends RecyclerView.Adapter<RecycleViewDownloadMovieListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<InfoDownloadMovie> listMovie;
    private LifecycleOwner lifecycleOwner;
    private InfoDownloadMovie.OnDownloadMovieClick onDownloadMovieClick;

    public RecycleViewDownloadMovieListAdapter(Context context, ArrayList<InfoDownloadMovie> listMovie, LifecycleOwner lifecycleOwner, InfoDownloadMovie.OnDownloadMovieClick onDownloadMovieClick) {
        this.context = context;
        this.listMovie = listMovie;
        this.lifecycleOwner = lifecycleOwner;
        this.onDownloadMovieClick = onDownloadMovieClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.download_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar;
        private TextView textViewNameMovie, textViewEngName, textViewRating, textViewGenreName, textViewPercent;
        private LinearLayout presentDownloadArea, presentDownloadedArea;
        private ProgressBar progressBarDownloading;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewNameMovie = itemView.findViewById(R.id.textViewNameMovie);
            textViewEngName = itemView.findViewById(R.id.textViewEngName);
            presentDownloadArea = itemView.findViewById(R.id.presentDownloadArea);
            presentDownloadedArea = itemView.findViewById(R.id.presentDownloadedArea);
            textViewRating = presentDownloadedArea.findViewById(R.id.textViewRating);
            textViewGenreName = presentDownloadedArea.findViewById(R.id.textViewGenreName);
            progressBarDownloading = presentDownloadArea.findViewById(R.id.progressBarDownloading);
            textViewPercent = presentDownloadArea.findViewById(R.id.textViewPercent);
        }

        public void bind(InfoDownloadMovie infoDownloadMovie){
            Movie movie = infoDownloadMovie.getMovie();
            Episode episode = infoDownloadMovie.getEpisode();
            Glide.with(context).load(movie.getAvatarUrl()).into(imageViewAvatar);
            if(movie.getListEpisode().size() == 1){
                textViewNameMovie.setText(movie.getTitle());
            }
            else{
                String nameMovie = movie.getTitle() + " Tập " + episode.getNumber();
                textViewNameMovie.setText(nameMovie);
            }
            textViewEngName.setText(movie.getEngTitle());
            textViewRating.setText(String.valueOf(movie.getRating()));

            Intent intent = new Intent(context, MovieDownloadService.class);
            context.bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.e("TAG", "onServiceConnected: ");
                    MovieDownloadService movieDownloadService = ((MovieDownloadService.MyBinder) iBinder).getService();
                    InfoDownloadMovie infoDownloadMovieResult = movieDownloadService.getInfoDownloadMovie(infoDownloadMovie);
                    if(infoDownloadMovieResult == null){
                        // complete
                        complete(infoDownloadMovie);
                    }
                    else{
                        // not complete
                        inComplete(infoDownloadMovie);
                        infoDownloadMovieResult.getProgress().observe(lifecycleOwner, (Integer progress) -> {
                            String percent = progress + "%";
                            textViewPercent.setText(percent);
                            if(progress == 100){
                                complete(infoDownloadMovie);
                            }
                        });
                    }
                    context.unbindService(this);
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    Log.e("TAG", "onServiceDisconnected: ");
                }
            }, Context.BIND_AUTO_CREATE);
        }

        private void complete(InfoDownloadMovie infoDownloadMovie){
            presentDownloadArea.setVisibility(View.GONE);
            presentDownloadedArea.setVisibility(View.VISIBLE);
            textViewGenreName.setText("Đã tải");

            // show menu
            itemView.setOnLongClickListener((View view) -> {
                MyMenu myMenu = new MyMenu(context);
                PopupMenu popupMenu = myMenu.createDownloadedMovieMenu(itemView, infoDownloadMovie);
                popupMenu.show();
                return true;
            });

            //
            itemView.setOnClickListener((View view) -> {
                infoDownloadMovie.getEpisode().setDestinationPathSaved(infoDownloadMovie.getDestinationPath(context));
                onDownloadMovieClick.click(infoDownloadMovie);
            });
        }

        private void inComplete(InfoDownloadMovie infoDownloadMovie){
            presentDownloadArea.setVisibility(View.VISIBLE);
            presentDownloadedArea.setVisibility(View.GONE);

            // show menu
            itemView.setOnLongClickListener((View view) -> {
                MyMenu myMenu = new MyMenu(context);
                PopupMenu popupMenu = myMenu.createDownloadMovieMenu(itemView, infoDownloadMovie);
                popupMenu.show();
                return true;
            });
        }
    }
}
