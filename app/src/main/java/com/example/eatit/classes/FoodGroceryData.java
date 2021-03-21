package com.example.eatit.classes;

import java.io.Serializable;

public class FoodGroceryData implements Serializable {
    private String text;
    private Food food;

    public FoodGroceryData() {
    }

    public String getText() {
        return text;
    }

    public Food getFood() {
        return food;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "FoodGroceryData{" +
                "text='" + text + '\'' +
                ", food=" + food +
                '}';
    }
}