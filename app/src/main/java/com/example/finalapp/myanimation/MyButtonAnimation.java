package com.example.finalapp.myanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.example.finalapp.R;

public class MyButtonAnimation implements MyAnimation{
    private Context context;
    private Button button;
    private View areaView;
    public MyButtonAnimation(Context context, Button button, View areaView) {
        this.context = context;
        this.button = button;
        this.button.setPivotX(0.5f);
        this.button.setPivotY(0f);
        this.areaView = areaView;
        this.areaView.setScaleY(0);
        this.areaView.setVisibility(View.GONE);
        this.areaView.setPivotX(0.5f);
        this.areaView.setPivotY(0.2f);
    }
    @Override
    public void toggleButtonAnimation() {
        // scale btn in
        AnimatorSet animatorScaleInSet = new AnimatorSet();
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(button, "scaleY", 0.5f);
        animatorScaleInSet.playTogether(scaleInY);
        animatorScaleInSet.setInterpolator(new FastOutSlowInInterpolator());
        animatorScaleInSet.setDuration(500);
        scaleInY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                button.setTextColor(context.getResources().getColor(R.color.transparent));
                if(areaView.getVisibility() == View.VISIBLE){
                    button.setClickable(true);
                    stopAnimationArea();
                }
            }
        });
        // scale btn out
        AnimatorSet animatorScaleOutSet = new AnimatorSet();
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(button, "scaleY", 1.0f);
        scaleOutY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(areaView.getVisibility() == View.GONE){
                    button.setClickable(false);
                    startAnimationArea();
                }
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                button.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
        animatorScaleOutSet.setDuration(400);
        animatorScaleOutSet.playTogether(scaleOutY);
        animatorScaleInSet.setInterpolator(new FastOutSlowInInterpolator());
        //
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animatorScaleInSet, animatorScaleOutSet);
        animatorSet.start();
    }
    private void stopAnimationArea(){
        // area animation
        ObjectAnimator areaScaleIn = ObjectAnimator.ofFloat(areaView, "scaleY", 0f);
        areaScaleIn.setDuration(500);
        areaScaleIn.setInterpolator(new FastOutSlowInInterpolator());
        areaScaleIn.start();
        areaScaleIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                areaView.setVisibility(View.GONE);
            }
        });
    }
    private void startAnimationArea(){
        // area animation
        areaView.setVisibility(View.VISIBLE);
        ObjectAnimator areaScaleOut = ObjectAnimator.ofFloat(areaView, "scaleY", 1.0f);
        areaScaleOut.setDuration(500);
        areaScaleOut.setStartDelay(100);
        areaScaleOut.setInterpolator(new FastOutSlowInInterpolator());
        areaScaleOut.start();
    }
}
