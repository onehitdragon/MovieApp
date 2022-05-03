package com.example.finalapp.myanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;

public class MyButton2Animation implements MyAnimation{
    private Context context;
    private Button button;
    private ImageView imgView;
    private boolean isOpen;
    private String textButton;
    public MyButton2Animation(Context context, Button button, ImageView imgView) {
        this.context = context;
        this.button = button;
        this.imgView = imgView;
        this.isOpen = false;
        this.textButton = button.getText().toString();
    }
    @Override
    public void toggleButtonAnimation() {
        if(!isOpen){
            startButtonAnimation();
            button.setClickable(false);
        }
        else{
            stopButtonAnimation();
        }
        isOpen = !isOpen;

    }
    private void startButtonAnimation(){
        Glide.with(context).load(R.drawable.output_onlinegiftools).into(imgView);
        ValueAnimator animatorScaleIn = ValueAnimator.ofInt(button.getWidth(), 90);
        animatorScaleIn.setDuration(500);
        animatorScaleIn.setInterpolator(new FastOutSlowInInterpolator());
        animatorScaleIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                button.setText("");
            }
        });
        animatorScaleIn.addUpdateListener((ValueAnimator animation) -> {
            button.getLayoutParams().width = (int) animation.getAnimatedValue();
            button.requestLayout();
        });
        animatorScaleIn.start();
    }
    private void stopButtonAnimation(){
        Glide.with(context).clear(imgView);
        ValueAnimator animatorScaleOut = ValueAnimator.ofInt(90, 290);
        animatorScaleOut.setDuration(500);
        animatorScaleOut.setInterpolator(new FastOutSlowInInterpolator());
        animatorScaleOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                button.setText(textButton);
                button.setClickable(true);
            }
        });
        animatorScaleOut.addUpdateListener((ValueAnimator animation) -> {
            button.getLayoutParams().width = (int) animation.getAnimatedValue();
            button.requestLayout();
        });
        animatorScaleOut.start();
    }
}
