package com.example.eatit.classes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class ShowImageTask extends AsyncTask<URL, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    ImageView imageView;

    public ShowImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bitmap = null;
        //Load the first element
        URL url = urls[0];
        try {
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmapResult) {
        imageView.setImageBitmap(bitmapResult);
    }
}