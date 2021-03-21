package com.example.eatit.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpFoodRequest {
    FoodGroceryData foodGroceryData;

    public OkHttpFoodRequest(FoodGroceryData foodGroceryData) {
        this.foodGroceryData = foodGroceryData;
    }

    private Food createFoodObject(JSONObject foodJs) throws JSONException {
        Food food = new Food();

        food.setUri(foodJs.getString("uri"));
        food.setCategory(foodJs.getString("category"));
        food.setCategoryLabel(foodJs.getString("categoryLabel"));
        food.setLabel(foodJs.getString("label"));
        food.setFoodId(foodJs.getString("foodId"));
        food.setImage(foodJs.getString("image"));

        return food;
    }

    private Nutrients createNutrientsObject(JSONObject foodNutrients) throws JSONException {
        Nutrients nutrients = new Nutrients();

        nutrients.setENERC_KCAL(foodNutrients.getDouble("ENERC_KCAL"));
        nutrients.setCHOCDF(foodNutrients.getDouble("CHOCDF"));
        nutrients.setFAT(foodNutrients.getDouble("FAT"));
        nutrients.setFIBTG(foodNutrients.getDouble("FIBTG"));
        nutrients.setPROCNT(foodNutrients.getDouble("PROCNT"));

        return nutrients;
    }

    public FoodGroceryData parseJsonToFoodGroceryData(String myResponse){
        try {
            JSONObject jsonObject = new JSONObject(myResponse);
            String text = jsonObject.getString("text");
            JSONArray parsedJso = new JSONArray(jsonObject.getString("parsed"));
            JSONObject foodJso = parsedJso.getJSONObject(0);
            JSONObject foodJs = foodJso.getJSONObject("food");

            Food food = createFoodObject(foodJs);

            JSONObject foodNutrients = foodJs.getJSONObject("nutrients");
            Nutrients nutrients = createNutrientsObject(foodNutrients);

            food.setNutrients(nutrients);

            foodGroceryData.setFood(food);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodGroceryData;
    }

    public Call request(String searchText){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://edamam-food-and-grocery-database.p.rapidapi.com/parser?ingr="+searchText+"")
                .get()
                .addHeader("x-rapidapi-key", "X-RAPID-KEY")
                .addHeader("x-rapidapi-host", "edamam-food-and-grocery-database.p.rapidapi.com")
                .build();

        return client.newCall(request);
    }

}