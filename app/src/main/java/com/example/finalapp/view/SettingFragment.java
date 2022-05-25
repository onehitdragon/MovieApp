package com.example.finalapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalapp.R;

public class SettingFragment extends Fragment {
    private Context context;
    private CardView btnYoutube, btnDiscord, btnGithub, btnChangePassword, btnFeedBack, btnPolicy;
    private String youtubeUrl = "https://www.youtube.com/channel/UClN-6RYy1Dvr1eAUTqn8HgQ";
    private String discordUrl = "https://discord.gg/H3rgYFVk";
    private String githubUrl = "https://github.com/onehitdragon";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        btnYoutube = view.findViewById(R.id.btnYoutube);
        btnDiscord = view.findViewById(R.id.btnDiscord);
        btnGithub = view.findViewById(R.id.btnGithub);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnFeedBack = view.findViewById(R.id.btnFeedBack);
        btnPolicy = view.findViewById(R.id.btnPolicy);

        // init
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // event
        btnYoutube.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(youtubeUrl));
            startActivity(intent);
        });
        btnDiscord.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(discordUrl));
            startActivity(intent);
        });
        btnGithub.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(githubUrl));
            startActivity(intent);
        });
        btnChangePassword.setOnClickListener((View v) -> {
            ((MainActivity) context).openFragmentExisted(new PasswordChangeFragment(), true, "PasswordChangeFragment");
        });
        btnFeedBack.setOnClickListener((View v) -> {

        });
        btnPolicy.setOnClickListener((View v) -> {

        });
    }
}