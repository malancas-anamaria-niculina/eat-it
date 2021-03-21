package com.example.eatit.classes;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatit.R;

import java.io.Serializable;

public class MenuObjects implements Serializable {
    private transient ImageView backgroundSelectorImage;
    private transient ImageView menuSelectorImage;
    private transient ImageView closeSelectorImage;
    private transient TextView homeTextView;
    private transient TextView bmiCalcTextView;
    private transient TextView aboutTextView;

    public MenuObjects(){
    }

    public void showMenu(){
        backgroundSelectorImage.setVisibility(View.VISIBLE);
        backgroundSelectorImage.setAnimation(AnimationUtils.loadAnimation(menuSelectorImage.getContext(), R.anim.scale_up));
        closeSelectorImage.setVisibility(View.VISIBLE);
        homeTextView.setVisibility(View.VISIBLE);
        bmiCalcTextView.setVisibility(View.VISIBLE);
        aboutTextView.setVisibility(View.VISIBLE);
        menuSelectorImage.setVisibility(View.INVISIBLE);
    }
    public void hideMenu(){

        backgroundSelectorImage.setVisibility(View.INVISIBLE);
        backgroundSelectorImage.setAnimation(AnimationUtils.loadAnimation(closeSelectorImage.getContext(), R.anim.scale_down));
        closeSelectorImage.setVisibility(View.INVISIBLE);
        homeTextView.setVisibility(View.INVISIBLE);
        bmiCalcTextView.setVisibility(View.INVISIBLE);
        aboutTextView.setVisibility(View.INVISIBLE);
        menuSelectorImage.setVisibility(View.VISIBLE);
    }

    public void setBackgroundSelectorImage(ImageView backgroundSelectorImage) {
        this.backgroundSelectorImage = backgroundSelectorImage;
    }

    public void setMenuSelectorImage(ImageView menuSelectorImage) {
        this.menuSelectorImage = menuSelectorImage;
    }

    public void setCloseSelectorImage(ImageView closeSelectorImage) {
        this.closeSelectorImage = closeSelectorImage;
    }

    public void setHomeTextView(TextView homeTextView) {
        this.homeTextView = homeTextView;
    }

    public void setBmiCalcTextView(TextView bmiCalcTextView) {
        this.bmiCalcTextView = bmiCalcTextView;
    }

    public void setAboutTextView(TextView aboutTextView) {
        this.aboutTextView = aboutTextView;
    }

    public ImageView getBackgroundSelectorImage() {
        return backgroundSelectorImage;
    }

    public ImageView getMenuSelectorImage() {
        return menuSelectorImage;
    }

    public ImageView getCloseSelectorImage() {
        return closeSelectorImage;
    }

    public TextView getHomeTextView() {
        return homeTextView;
    }

    public TextView getBmiCalcTextView() {
        return bmiCalcTextView;
    }

    public TextView getAboutTextView() {
        return aboutTextView;
    }
}
