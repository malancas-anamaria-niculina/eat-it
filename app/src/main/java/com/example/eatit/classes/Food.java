package com.example.eatit.classes;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;

public class Food{
    private String foodId;
    private String uri;
    private String label;
    private Nutrients nutrients;
    private String category;
    private String categoryLabel;
    private String image;

    public String getFoodId() {
        return foodId;
    }

    public Uri getUri() {
        return Uri.parse(uri);
    }

    public String getLabel() {
        return label;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public String getImage() {
        return image;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NotNull
    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", nutrients=" + nutrients +
                ", category='" + category + '\'' +
                ", categoryLabel='" + categoryLabel + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
