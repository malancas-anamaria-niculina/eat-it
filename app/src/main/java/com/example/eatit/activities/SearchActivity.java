package com.example.eatit.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eatit.R;
import com.example.eatit.classes.FoodGroceryData;
import com.example.eatit.classes.OkHttpFoodRequest;
import com.example.eatit.classes.ShowImageTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    FoodGroceryData foodGroceryData;

    private void updateImage(FoodGroceryData foodGroceryData){

        runOnUiThread(()->{
            System.out.println(foodGroceryData);
            setContentView(R.layout.search_details);
            System.out.println(foodGroceryData);
            ImageView searchFoodImage = (ImageView) findViewById(R.id.searchResultImage);

            Bitmap bitmap = null;
            try {
                URL url = new URL(foodGroceryData.getFood().getImage());
                new ShowImageTask(searchFoodImage).execute(url);
                //image.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
            } catch(IOException e) {
                System.out.println(e);
            }

            searchFoodImage.setImageBitmap(bitmap);
            //loadData.setVisibility(View.INVISIBLE);
        });
    }

    private void updateTexts(FoodGroceryData foodGroceryData){
        DecimalFormat df = new DecimalFormat("####.####");

        TextView enercKcal = (TextView) findViewById(R.id.caloriesTextView);
        System.out.println("enercKcal " + enercKcal);
        System.out.println(foodGroceryData);
        enercKcal.setText(df.format(foodGroceryData.getFood().getNutrients().getENERC_KCAL()));

        TextView procnt = (TextView) findViewById(R.id.proteinProcTextView);
        procnt.setText(df.format(foodGroceryData.getFood().getNutrients().getPROCNT()));

        TextView fat = (TextView) findViewById(R.id.fatTextView);
        fat.setText(df.format(foodGroceryData.getFood().getNutrients().getFAT()));

        TextView chocdf = (TextView) findViewById(R.id.carbohydratesProcTextView);
        chocdf.setText(df.format(foodGroceryData.getFood().getNutrients().getCHOCDF()));

        TextView fibtg = (TextView) findViewById(R.id.fiberProcTextView);
        fibtg.setText(df.format(foodGroceryData.getFood().getNutrients().getFIBTG()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateProgressBars(FoodGroceryData foodGroceryData){
        //Update fiber progress bar
        ProgressBar fiberProgressBar = (ProgressBar) findViewById(R.id.fiberProgressBar);
        fiberProgressBar.setMax(100);
        fiberProgressBar.setProgress((int)(foodGroceryData.getFood().getNutrients().getFIBTG()),false);

        //Update carbohydrates progress bar
        ProgressBar carbohydratesProgressBar = (ProgressBar) findViewById(R.id.carbohydratesProgressBar);
        carbohydratesProgressBar.setMax(100);
        carbohydratesProgressBar.setProgress((int)(foodGroceryData.getFood().getNutrients().getCHOCDF()),false);

        //Update protein progress bar
        ProgressBar proteinProgressBar = (ProgressBar) findViewById(R.id.proteinProgressBar);
        proteinProgressBar.setMax(100);
        proteinProgressBar.setProgress((int)(foodGroceryData.getFood().getNutrients().getPROCNT()),false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateContent(FoodGroceryData foodGroceryData){
        runOnUiThread(()->{
            updateImage(foodGroceryData);
            updateTexts(foodGroceryData);
            updateProgressBars(foodGroceryData);
        });
    }

    private void requestData(String searchText){
        foodGroceryData = new FoodGroceryData();
        OkHttpFoodRequest clientCall = new OkHttpFoodRequest(foodGroceryData);

        Call clientRequest = clientCall.request(searchText);
        clientRequest.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    foodGroceryData = clientCall.parseJsonToFoodGroceryData(Objects.requireNonNull(response.body()).string());
                    System.out.println("MainActivity show: " + foodGroceryData);
                    updateContent(foodGroceryData);
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        String foodGroceryDataSearchText = (String) getIntent().getSerializableExtra("food");

        requestData(foodGroceryDataSearchText);
    }
}
