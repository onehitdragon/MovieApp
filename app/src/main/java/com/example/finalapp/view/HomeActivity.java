package com.example.finalapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.finalapp.R;

public class HomeActivity extends AppCompatActivity {
    private RelativeLayout wrapBtnHome, wrapBtnHistory, wrapBtnDownload, wrapBtnUser;
    private RelativeLayout currentWrapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        wrapBtnHome = findViewById(R.id.wrapBtnHome);
        wrapBtnHistory = findViewById(R.id.wrapBtnHistory);
        wrapBtnDownload = findViewById(R.id.wrapBtnDownload);
        wrapBtnUser = findViewById(R.id.wrapBtnUser);
        currentWrapBtn = wrapBtnHome;

        View.OnClickListener wrapBtnOnClickListener = (View view) -> {
            activeWrapBtn((RelativeLayout) view);
        };
        wrapBtnHome.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnHistory.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnDownload.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnUser.setOnClickListener(wrapBtnOnClickListener);
    }
    private void activeWrapBtn(RelativeLayout wrapBtn){
        if(currentWrapBtn == wrapBtn) return;
        wrapBtn.setAlpha(1);
        wrapBtn.getChildAt(1).setVisibility(ViewGroup.VISIBLE);
        wrapBtn.setBackgroundResource(R.drawable.shadow);
        currentWrapBtn.setAlpha(0.4f);
        currentWrapBtn.getChildAt(1).setVisibility(ViewGroup.INVISIBLE);
        currentWrapBtn.setBackgroundResource(0);
        currentWrapBtn = wrapBtn;
    }
}