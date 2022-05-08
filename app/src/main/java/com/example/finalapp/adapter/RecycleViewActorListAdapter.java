package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.model.Actor;

import java.util.ArrayList;

public class RecycleViewActorListAdapter extends RecyclerView.Adapter<RecycleViewActorListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Actor> listActor;

    public RecycleViewActorListAdapter(Context context, ArrayList<Actor> listActor) {
        this.context = context;
        this.listActor = listActor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.actor_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listActor.get(position));
    }

    @Override
    public int getItemCount() {
        return listActor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatarActor;
        private TextView textViewNameActor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatarActor = itemView.findViewById(R.id.imageViewAvatarActor);
            textViewNameActor = itemView.findViewById(R.id.textViewNameActor);
        }
        public void bind(Actor actor){
            Glide.with(context).load(actor.getAvatarUrl()).into(imageViewAvatarActor);
            textViewNameActor.setText(actor.getFullName());
        }
    }
}
