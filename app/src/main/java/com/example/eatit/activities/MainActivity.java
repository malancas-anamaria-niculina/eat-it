package com.example.eatit.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eatit.R;
import com.example.eatit.classes.FoodGroceryData;
import com.example.eatit.classes.MenuObjects;
import com.example.eatit.classes.OkHttpFoodRequest;
import com.example.eatit.classes.ShowImageTask;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    FoodGroceryData foodGroceryData = new FoodGroceryData();

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

    private void showValues(FoodGroceryData foodGroceryData){
        runOnUiThread(()->{

            if (foodGroceryData.getFood() != null) {
                TextView kcal = (TextView) findViewById(R.id.kcalText);
                TextView category = (TextView) findViewById(R.id.catText);
                TextView name = (TextView) findViewById(R.id.productNameText);
                ShapeableImageView image = (ShapeableImageView) findViewById(R.id.presPhotoBox);

                //Set image from received url
                Bitmap bitmap = null;
                try {
                    URL url = new URL(foodGroceryData.getFood().getImage());
                    new ShowImageTask(image).execute(url);

                    ProgressBar uploadImage = (ProgressBar) findViewById(R.id.upload_image);
                    uploadImage.setVisibility(View.INVISIBLE);

                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    System.out.println(e);
                }

                //Set image corners
                image.setAdjustViewBounds(true);
                image.setShapeAppearanceModel(image.getShapeAppearanceModel());

                kcal.setText(null);
                String textt = foodGroceryData.getFood().getNutrients().getENERC_KCAL() + " kcal";
                kcal.setText(textt);
                textt = "Category: " + foodGroceryData.getFood().getCategory();
                category.setText(textt);
                name.setText(foodGroceryData.getFood().getLabel());

            }

        });
    }

    public void requestData(){
        foodGroceryData = new FoodGroceryData();
        OkHttpFoodRequest clientCall = new OkHttpFoodRequest(foodGroceryData);

        Call clientRequest = clientCall.request("apple");

        clientRequest.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    foodGroceryData = clientCall.parseJsonToFoodGroceryData(Objects.requireNonNull(response.body()).string());
                    showValues(foodGroceryData);
                }
            }
        });

    }

    private void showHideMenu(){
        ImageView menu = (ImageView) findViewById(R.id.menuSelector);
        ImageView closeMenu = (ImageView) findViewById(R.id.closeSelector);

        MenuObjects menuObjects = createMenuObjects();

        menu.setOnClickListener(view -> menuObjects.showMenu());

        closeMenu.setOnClickListener(view -> menuObjects.hideMenu());
    }

    public void openAboutPage(View view){
        Intent intent = new Intent(com.example.eatit.activities.MainActivity.this, AboutActivity.class);
        MenuObjects menuObjects = createMenuObjects();
        intent.putExtra("menuObject", (Serializable) menuObjects);
        startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openBmiCalculatorPage(View view){
        Intent intent = new Intent(com.example.eatit.activities.MainActivity.this, BmiCalculatorActivity.class);
        MenuObjects menuObjects = createMenuObjects();
        intent.putExtra("menuObject", (Serializable) menuObjects);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void searchFood(){
        EditText editText = (EditText) findViewById(R.id.textInputEditText);

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("food", v.getText().toString());
                startActivity(intent);
            }
            return false;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        showHideMenu();
        requestData();
        searchFood();
    }
}