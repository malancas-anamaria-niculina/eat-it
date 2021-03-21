package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eatit.R;
import com.example.eatit.classes.MenuObjects;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    MenuObjects menuObject;

    public void openBmiCalculatorPage(View view){
        Intent intent = new Intent(AboutActivity.this, BmiCalculatorActivity.class);
        intent.putExtra("menuObject", menuObject);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openHomePage(View view){
        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
        intent.putExtra("menuObject", menuObject);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void showHideMenu(){
        ImageView menu = (ImageView) findViewById(R.id.menuSelector);
        ImageView closeMenu = (ImageView) findViewById(R.id.closeSelector);

        menu.setOnClickListener(view -> menuObject.showMenu());

        closeMenu.setOnClickListener(view -> menuObject.hideMenu());
    }

    private MenuObjects createMenuObjects(){
        MenuObjects menuObjects = new MenuObjects();
        menuObjects.setAboutTextView(findViewById(R.id.about_button));
        menuObjects.setBackgroundSelectorImage(findViewById(R.id.selectorBackground));
        menuObjects.setBmiCalcTextView(findViewById(R.id.bmi_calculator_button));
        menuObjects.setCloseSelectorImage(findViewById(R.id.closeSelector));
        menuObjects.setHomeTextView(findViewById(R.id.home_button));
        menuObjects.setMenuSelectorImage(findViewById(R.id.menuSelector));

        return menuObjects;
    }

    private float calculateKilograms(float height, double bmi){
        return (float)(Math.round(height*height*bmi/10000));
    }

    private String createHeightMessage(int height){
        return calculateKilograms(height,18.5) + " kg - " + calculateKilograms(height,24.9) + " kg";
    }

    private String recommendedWaist(boolean man){
        String text = "A healthy measurement for a woman is considered a waist below 88.9 cm.\n";

        if (man){
            text += "A healthy measurement for a men is considered a waist below 101.6 cm.\n";
        }
        return text;
    }

    private String createWaistMessage(int waist){
        if (waist < 90){
            return ("A waist of " + waist + " cm is considered optimal for both women and men.");
        }else if (waist < 102){
            return ("A waist of " + waist + " cm is considered optimal for men.\n " +
                   recommendedWaist( false));
        }
        return ("A waist of " + waist + " cm is considered not optimal for both women and men.\n " +
                recommendedWaist(true));
    }

    private void updateTextBox(int progress, TextView resultText){
        resultText.setVisibility(View.VISIBLE);

        if (resultText.getId() == R.id.waistResultTextBox){
            TextView waistSizeText = (TextView) findViewById(R.id.selected_waist_size);
            waistSizeText.setText(String.valueOf(progress));

            resultText.setText(createWaistMessage(progress));

        }else if (resultText.getId() == R.id.heightResultTextBox){
            TextView heightText = (TextView) findViewById(R.id.selected_height);
            heightText.setText(String.valueOf(progress));

            String text = "The healty weight for the height " + progress + " (cm) is considered between:";

            resultText.setText(text);

            TextView kilogramsMessageText = (TextView) findViewById(R.id.recommended_kilograms);

            kilogramsMessageText.setVisibility(View.VISIBLE);
            kilogramsMessageText.setText(createHeightMessage(progress));
        }

    }

    private void setWaistText(){
        SeekBar waistSlide = (SeekBar) findViewById(R.id.waistSeekBar);
        final TextView resultText = (TextView) findViewById(R.id.waistResultTextBox);

        waistSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextBox(progress, resultText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setHeightText(){
        SeekBar heigthSlide = (SeekBar) findViewById(R.id.heightSeekBar);
        TextView heightTextView = (TextView) findViewById(R.id.heightResultTextBox);

        heigthSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextBox(progress,heightTextView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.about_layout);

        menuObject = createMenuObjects();
        showHideMenu();

        setHeightText();
        setWaistText();
    }
}
