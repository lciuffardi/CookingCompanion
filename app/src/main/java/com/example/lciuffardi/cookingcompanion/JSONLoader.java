package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class JSONLoader extends AppCompatActivity {

    public static String loadRecipesJSON(Context context){
        String json = null;
        try {
            InputStream input = context.getAssets().open("json/RecipeDataLookUp.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
