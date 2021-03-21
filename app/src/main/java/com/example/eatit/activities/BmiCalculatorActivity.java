package com.example.eatit.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eatit.R;
import com.example.eatit.classes.MenuObjects;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BmiCalculatorActivity extends AppCompatActivity {
    private NumberPicker height;
    private Button genderButton;
    private MenuObjects menuObject;


    public void openHomePage(View view){
        Intent intent = new Intent(BmiCalculatorActivity.this,MainActivity.class);
        intent.putExtra("menuObject", menuObject);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openAboutPage(View view){
        Intent intent = new Intent(BmiCalculatorActivity.this,AboutActivity.class);
        intent.putExtra("menuObject", menuObject);
        startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private String bmiClasiffication(float bmi){
        if (bmi < 18.5){
            return "underweight.";
        }else if (bmi <= 24.9){
            return "normal.";
        }else if (bmi < 29.9){
            return "overweight.";
        }else if (bmi < 39.9){
            return "obesity.";
        }
        return "extreme obesity.";
    }

    private String lowerWaist(float bmi){
        if (bmi >= 25 && bmi < 29.9){
            return "\nIncreased health risks.";
        }else if (bmi >= 30 && bmi < 34.9){
            return "\nHigh health risks.";
        }else if (bmi >= 35 && bmi < 39.9){
            return "\nVery high health risks.";
        }else if (bmi >= 40){
            return "\nExtremely high health risk.";
        }
        return "";
    }

    private String higherWaist(float bmi){
        if (bmi >= 25 && bmi < 29.9){
            return "\nHigh health risks.";
        }else if (bmi >= 30 && bmi < 34.9){
            return "\nVery high health risks.";
        }else if (bmi >= 35 && bmi < 39.9){
            return "\nVery high health risks.";
        }else if (bmi >= 40){
            return "\nExtremely high health risk.";
        }
        return "";
    }

    private String genderSelect(double value, Button genderButton, float waist, float bmi){
        if (genderButton.isSelected()){
            if (waist <= value){
                return lowerWaist(bmi);
            }else if (waist > value){
                return higherWaist(bmi);
            }
        }
        return "";
    }

    private float calculateBMI(float kilograms, float height, float waist){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        return Float.parseFloat(df.format(10000*kilograms/height/height));
    }

    private String calculate(float bmi){
        String displayText = "BMI = ";

        displayText += bmi;

        return displayText;
    }

    @SuppressLint("SetTextI18n")
    public void showBMI(View view){
        SeekBar waist = (SeekBar) findViewById(R.id.waistSlider);
        TextView kilograms = (TextView) findViewById(R.id.kilograms);

        float bmi = calculateBMI(Float.parseFloat(kilograms.getText().toString().trim()),Float.parseFloat(String.valueOf(height.getValue())),Float.parseFloat(String.valueOf(waist.getProgress())));

        height = (NumberPicker) findViewById(R.id.heightNumberPicker);
        TextView conclusionText = (TextView) findViewById(R.id.conclusionText);
        //float kg = Float.valueOf((TextView)findViewById(R.id.kilograms));
        TextView bmiText = (TextView) findViewById(R.id.bmi);
        bmiText.setVisibility(View.VISIBLE);
        bmiText.setText(String.valueOf(bmi));

        conclusionText.setVisibility(View.VISIBLE);
        String conclusionStringText = "This is considered " + bmiClasiffication(bmi) + "\n";
        conclusionStringText += genderSelect(
                101.6,
                (Button) findViewById(R.id.maleButton),
                Float.parseFloat(String.valueOf(waist.getProgress())),
                bmi);

        conclusionStringText += genderSelect(
                88.9,
                (Button) findViewById(R.id.femaleButton),
                Float.parseFloat(String.valueOf(waist.getProgress())),
                bmi);

        conclusionText.setText(conclusionStringText);
    }

    public void selectMaleButton(View view){
        view.setSelected(!view.isSelected());
        genderButton = (Button) findViewById(R.id.femaleButton);
        genderButton.setSelected(false);
    }

    public void selectFemaleButton(View view){
        view.setSelected(!view.isSelected());
        genderButton = (Button) findViewById(R.id.maleButton);
        genderButton.setSelected(false);
    }

    public void incDec(View view){
        TextView kilo = (TextView) findViewById(R.id.kilograms);
        int kilograms =  Integer.parseInt(kilo.getText().toString().trim());
        if (view.getId() == R.id.inc){
            kilograms++;
        }else if (view.getId() == R.id.dec){
            kilograms--;
        }
        kilo.setText(String.valueOf(kilograms));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void instantiateNumberPicker(){
        height = (NumberPicker) findViewById(R.id.heightNumberPicker);
        height.setMinValue(30);
        height.setMaxValue(240);
        height.setValue(160);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.heightSeekBar);
        seekBar.setMin(30);
        seekBar.setMax(330);
        seekBar.setProgress(160);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                height.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        height.setOnValueChangedListener((numberPicker, oldValue, newValue) -> seekBar.setProgress(newValue));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void waistSliderListener(){
        final SeekBar waistSlide = (SeekBar) findViewById(R.id.waistSlider);
        final TextView waistValue = (TextView) findViewById(R.id.waist);
        waistSlide.setMax(150);
        waistSlide.setMin(10);
        waistSlide.setProgress(30);
        waistValue.setText(String.valueOf(30));
        waistSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waistValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.bmi_layout);

        menuObject = createMenuObjects();

        showHideMenu();

        instantiateNumberPicker();
        waistSliderListener();
    }

}
